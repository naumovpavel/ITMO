package server.network;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import common.Commands;
import common.models.User;
import common.network.Status;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.slf4j.LoggerFactory;
import server.auth.AuthManager;
import server.main.Main;
import server.сommands.CommandManager;
import common.network.Request;
import common.network.Response;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;


import static java.nio.channels.SelectionKey.OP_READ;
import static java.nio.channels.SelectionKey.OP_WRITE;

/**
 * TcpServer class
 */
public class TcpServer extends Server {
    private ServerSocketChannel servChan;
    private CommandManager commandManager;
    private final ConcurrentHashMap<SocketChannel, MutablePair<Integer, byte[]>> queue = new ConcurrentHashMap<>();
    private static final Logger logger = Main.logger;
    private Selector selector;
    private AuthManager authManager;
    private ExecutorService executor;
    private ForkJoinPool pool;

    public TcpServer(CommandManager commandManager, AuthManager authManager) {
        logger.setLevel(Level.INFO);
        try {
            this.selector = Selector.open();
            this.servChan = ServerSocketChannel.open();
            InetSocketAddress address = new InetSocketAddress(port);
            this.servChan.bind(address);
            this.authManager = authManager;
            this.servChan.configureBlocking(false);
            this.servChan.register(this.selector, SelectionKey.OP_ACCEPT);
            this.commandManager = commandManager;
            int poolSize = Runtime.getRuntime().availableProcessors();
            this.executor = Executors.newFixedThreadPool(poolSize);
            this.pool = new ForkJoinPool(poolSize);
            logger.info("Server start. Address: {}, port: {}", address.getHostName(), port);
        } catch (IOException e) {
            Main.logger.error("Данный порт занят");
            System.exit(0);
        }
    }

    @Override
    public void run() {
        Executors.newSingleThreadExecutor().execute(commandManager::startLocalExecuting);
        while (true) {
            try {
                selector.selectNow();
                Set<SelectionKey> keys = selector.selectedKeys();

                for (var it = keys.iterator(); it.hasNext(); ) {
                    SelectionKey key = it.next();
                    it.remove();
                    if (key.isValid()) {
                        if (key.isAcceptable()) {
                            ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                            key.interestOps(0);
                            accept(channel);
                            continue;
                        }
                        if (key.isReadable()) {
                            SocketChannel channel = (SocketChannel) key.channel();
                            key.interestOps(0);
                            handelRequest(channel);
                            continue;
                        }
                        if (key.isWritable()) {
                            SocketChannel channel = (SocketChannel) key.channel();
                            key.interestOps(0);
                            sendResponse(channel);
                            continue;
                        }
                    } else {
                        key.cancel();
                    }
                }
            } catch (IOException e) {
                logger.warn(e.getMessage());
            }
        }
    }

    private void handelRequest(SocketChannel channel) {
        pool.execute(() -> {
            try {
                Request request = read(channel);
                request.setAddress(channel.getRemoteAddress());
                executeRequest(request, channel);
            } catch (IOException | ClassNotFoundException e) {
                try {
                    channel.close();
                } catch (IOException ex) {
                    Main.logger.warn("Error while closing");
                }
                System.out.println(e.getMessage());
                Main.logger.warn("Error while reading");
            }
        });
    }

    private void executeRequest(Request request, SocketChannel channel) {
        executor.execute(() -> {
            Response response = null;
            if ((request.getType() != Commands.REGISTER) && (request.getType() != Commands.LOGIN)) {
                User user = new User();
                int id = authManager.verifyJwt(request.getToken());
                if (id < 0) {
                    System.out.println(request.getToken());
                    response = new Response("Пользователь не авторизован", Status.ERROR);
                } else {
                    user.setId(id);
                    request.setUser(user);
                    response = commandManager.executeCommand(request);
                }
            } else {
                response = commandManager.executeCommand(request);
            }
            MutablePair<Integer, byte[]> pair = null;
            try {
                pair = new MutablePair<>(0, ArrayUtils.toPrimitive(objectToByArray(response)));
                channel.register(selector, OP_WRITE);
                queue.put(channel, pair);
            } catch (IOException e) {
                Main.logger.warn("Error while reading request");
            }
        });
    }

    public void sendResponse(SocketChannel sock) {
        pool.execute(() -> {
            try {
                if (!queue.containsKey(sock)) {
                    sock.register(selector, OP_READ);
                    return;
                }
                MutablePair<Integer, byte[]>  pair = queue.get(sock);
                send(pair, sock);
            } catch (ClosedChannelException e) {
                Main.logger.warn("Connection reset");
            } catch (IOException e) {
                Main.logger.warn("Error while sending response");
            }
        });
    }

    protected void accept(ServerSocketChannel servChan) {
        executor.execute(() -> {
            SocketChannel sock = null;
            try {
                sock = servChan.accept();
                servChan.register(selector, SelectionKey.OP_ACCEPT);
                sock.configureBlocking(false);
                sock.register(selector, OP_READ);
                logger.info("New connection {}", sock.getRemoteAddress());
            } catch (IOException e) {
                Main.logger.warn("Error while accepting");
            }
        });
    }

    @Override
    protected void send(MutablePair<Integer, byte[]> pair, SocketChannel sock) throws IOException {
        byte[] bytes = pair.right;
        int start = pair.left;

        int n = (bytes.length + DATA_SIZE - 1) / DATA_SIZE;

        for (int i = start; i < n; i++) {
            byte[] buf = Arrays.copyOfRange(bytes, i * DATA_SIZE, (i + 1) * DATA_SIZE + 1);

            if (i != n - 1) {
                buf[PACKAGE_SIZE - 1] = 0;
            } else {
                buf[PACKAGE_SIZE - 1] = 1;
            }

            int m = sock.write(ByteBuffer.wrap(buf));
            if(m == 0) {
                pair.left = i;
                sock.register(selector, OP_WRITE);
                return;
            }
            if(i - start + 1 >= MAX_PACKAGES_AT_ONCE) {
                pair.left = i + 1;
                sock.register(selector, OP_WRITE);
                return;
            }
        }

        sock.register(selector, OP_READ);
        logger.info("{} bytes sent to {}", bytes.length, sock.getRemoteAddress());
    }


    @Override
    protected Request read(SocketChannel sock) throws IOException, ClassNotFoundException {
        ArrayList<Byte> bytes = new ArrayList<>();
        byte[] buf = new byte[PACKAGE_SIZE];

        do {
            ByteBuffer byteBuffer = ByteBuffer.wrap(buf);
            int n = 0;
            while (n < PACKAGE_SIZE) {
                int r = sock.read(byteBuffer);
                if (r == -1) {
                    throw new IOException();
                }
                n += r;
            }
            for (int i = 0; i < DATA_SIZE; i++) {
                bytes.add(buf[i]);
            }
        } while (buf[PACKAGE_SIZE - 1] != 1);

        if (bytes.size() == 0)
            throw new IOException();

        logger.info("{} read from {}", bytes.size(), sock.getRemoteAddress());
        Byte[] res = new Byte[bytes.size()];
        return (Request) objectFromArray(bytes.toArray(res));
    }
}