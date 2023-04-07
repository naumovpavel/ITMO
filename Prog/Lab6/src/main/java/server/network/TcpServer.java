package server.network;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.slf4j.LoggerFactory;
import server.сommands.CommandManager;
import common.network.Request;
import common.network.Response;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;


import static java.nio.channels.SelectionKey.OP_READ;
import static java.nio.channels.SelectionKey.OP_WRITE;

/**
 * TcpServer class
 */
public class TcpServer extends Server {
    private ServerSocketChannel servChan;
    private CommandManager commandManager;
    private final HashMap<SocketChannel, MutablePair<Integer, byte[]>> queue = new HashMap<>();
    private static final Logger logger = (Logger) LoggerFactory.getLogger("server.network");
    private Selector selector;

    public TcpServer(CommandManager commandManager) {
        logger.setLevel(Level.INFO);
        try {
            this.selector = Selector.open();
            this.servChan = ServerSocketChannel.open();
            InetSocketAddress address = new InetSocketAddress(port);
            this.servChan.bind(address);
            //this.buffer = ByteBuffer.allocate(1024);
            this.servChan.configureBlocking(false);
            this.servChan.register(this.selector, SelectionKey.OP_ACCEPT);
            this.commandManager = commandManager;
            logger.info("Server start. Address: {}, port: {}", address.getHostName(), port);
        } catch (IOException e) {
            System.out.println("Данный порт занят");
            System.exit(0);
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                selector.select(100);
                commandManager.startLocalExecuting();
                Set<SelectionKey> keys = selector.selectedKeys();

                for (var it = keys.iterator(); it.hasNext(); ) {
                    SelectionKey key = it.next();
                    it.remove();

                    try {
                        if (key.isValid()) {
                            if (key.isAcceptable()) {
                                accept(key);
                            }

                            if (key.isReadable()) {
                                Request request = read(key);
                                Response response = commandManager.executeCommand(request);
                                MutablePair<Integer, byte[]>  pair= new MutablePair<>(0, ArrayUtils.toPrimitive(objectToByArray(response)));
                                queue.put((SocketChannel) key.channel(), pair);
                            }

                            if (key.isWritable()) {
                                if (!queue.containsKey((SocketChannel) key.channel())) {
                                    key.channel().register(selector, OP_READ);
                                }

                                MutablePair<Integer, byte[]>  pair = queue.get((SocketChannel) key.channel());
                                send(pair, key);
                            }
                        } else {
                            key.cancel();
                        }
                    } catch (IOException e) {
                        logger.info("Connection reset from {}", ((SocketChannel) key.channel()).getRemoteAddress());
                        ((SocketChannel) key.channel()).socket().close();
                        key.cancel();
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                logger.warn(e.getMessage());
            }
        }
    }

    protected void accept(SelectionKey key) {
        SocketChannel sock = null;
        try {
            ServerSocketChannel servChan = (ServerSocketChannel) key.channel();
            sock = servChan.accept();
            sock.configureBlocking(false);
            sock.register(key.selector(), OP_READ);
            logger.info("New connection {}", sock.getRemoteAddress());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void send(MutablePair<Integer, byte[]> pair, SelectionKey key) throws IOException {
        SocketChannel sock = (SocketChannel) key.channel();
        byte[] bytes = pair.right;
        int i = pair.left;

        int n = (bytes.length + DATA_SIZE - 1) / DATA_SIZE;

        for (; i < n; i++) {
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
        }

        sock.register(selector, OP_READ);
        logger.info("{} bytes sent to {}", bytes.length, sock.getRemoteAddress());
    }


    @Override
    protected Request read(SelectionKey key) throws IOException, ClassNotFoundException {
        SocketChannel sock = (SocketChannel) key.channel();
        ArrayList<Byte> bytes = new ArrayList<>();
        byte[] buf = new byte[PACKAGE_SIZE];

        do {
            int n = sock.read(ByteBuffer.wrap(buf));
            if (n == -1) {
                throw new IOException();
            }
            for (int i = 0; i < DATA_SIZE; i++) {
                bytes.add(buf[i]);
            }
        } while (buf[PACKAGE_SIZE - 1] != 1);

        if (bytes.size() == 0)
            throw new IOException();

        sock.register(key.selector(), OP_WRITE);
        logger.info("{} read from {}", bytes.size(), sock.getRemoteAddress());
        Byte[] res = new Byte[bytes.size()];
        return (Request) objectFromArray(bytes.toArray(res));
    }
}