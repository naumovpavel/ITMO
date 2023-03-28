package server.network;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import client.network.Client;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.LoggerFactory;
import server.сommands.CommandManager;
import common.request.Request;
import common.response.Response;

import java.io.*;
import java.lang.reflect.Array;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.*;


import static java.nio.channels.SelectionKey.OP_READ;
import static java.nio.channels.SelectionKey.OP_WRITE;

public class TcpServer extends Server {
    private ServerSocketChannel servChan;
    private CommandManager commandManager;
    private final HashMap<SocketChannel, Response> queue = new HashMap<>();
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

                                queue.put((SocketChannel) key.channel(), response);
                            }
                            if (key.isWritable()) {
                                if (!queue.containsKey((SocketChannel) key.channel())) {
                                    key.channel().register(selector, OP_READ);
                                }

                                Response response = queue.get((SocketChannel) key.channel());
                                send(response, key);
                            }
                        } else {
                            key.cancel();
                        }
                    } catch (IOException e) {
                        logger.info("Connection reset from {}", ((SocketChannel) key.channel()).getRemoteAddress());
                        ((SocketChannel) key.channel()).socket().close();
                        key.cancel();
                    }

                    commandManager.startLocalExecuting();
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
    protected void send(Response response, SelectionKey key) throws IOException {
        SocketChannel sock = (SocketChannel) key.channel();
        byte[] bytes;

        if (queue.containsKey(sock)) {
            bytes = ArrayUtils.toPrimitive(objectToByArray(queue.get(sock)));
        } else {
            key.cancel();
            return;
        }

        int n = (bytes.length + DATA_SIZE - 1) / DATA_SIZE;
        sendSize(n, sock);
        for (int i = 0; i < n; i++) {
            byte[] buf = Arrays.copyOfRange(bytes, i * DATA_SIZE, (i + 1) * DATA_SIZE);

            if (i != n - 1) {
                buf = ArrayUtils.add(buf, (byte) 0);
            } else {
                buf = ArrayUtils.add(buf, (byte) 1);
            }

            int m = sock.write(ByteBuffer.wrap(buf));
            if(m == 0) {
                throw new IOException();
            }
        }
        sock.register(selector, OP_READ);
        logger.info("{} bytes sent to {}", bytes.length, sock.getRemoteAddress());
    }

    protected Request read(SelectionKey key) throws IOException, ClassNotFoundException {
        SocketChannel sock = (SocketChannel) key.channel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(PACKAGE_SIZE);
        ArrayList<Byte> bytes = new ArrayList<>();

        while (true)  {
            byte[] buf = new byte[PACKAGE_SIZE];
            int n = sock.read(ByteBuffer.wrap(buf));
            if(n == -1) {
                throw new IOException();
            }
            for (int i = 0; i < DATA_SIZE; i++) {
                bytes.add(buf[i]);
            }
            if (buf[PACKAGE_SIZE - 1] == 1) {
                break;
            }
        }

        if (bytes.size() == 0)
            throw new IOException();

        sock.register(key.selector(), OP_WRITE);
        logger.info("{} read from {}", bytes.size(), sock.getRemoteAddress());
        Byte[] res = new Byte[bytes.size()];
        return (Request) objectFromArray(bytes.toArray(res));
    }

    protected void sendSize(int n, SocketChannel sock) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(32);
        buffer.put(Integer.toBinaryString(n).getBytes());
        buffer.flip();
        buffer.limit(32);
        sock.write(buffer);
    }
}