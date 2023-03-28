package server.network;

import common.request.Request;
import common.response.Response;

import java.io.*;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Objects;

import org.apache.commons.lang3.ArrayUtils;

public abstract class Server {
    protected static int port = 3674;
    protected int PACKAGE_SIZE = 1024;
    protected int DATA_SIZE = 1023;

    abstract public void run();
    abstract protected void send(Response response, SelectionKey key) throws IOException;
    abstract protected Request read(SelectionKey key) throws IOException, ClassNotFoundException;

    protected Byte[] objectToByArray(Serializable obj) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bytes);
        out.writeObject(obj);
        return ArrayUtils.toObject(bytes.toByteArray());
    }

    protected Object objectFromArray(Byte[] bytes) throws IOException, ClassNotFoundException {
        return new ObjectInputStream(new ByteArrayInputStream(ArrayUtils.toPrimitive(bytes))).readObject();
    }

    public static void setPort(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if(i + 1 < args.length && Objects.equals(args[i], "-p")) {
                try {
                    Server.port = Integer.parseInt(args[i+1]);
                    return;
                } catch (NumberFormatException e) {
                    System.out.println("Порт задан не правильно");
                }
            }
        }
    }
}
