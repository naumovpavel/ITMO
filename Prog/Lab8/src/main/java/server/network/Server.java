package server.network;

import common.network.Request;
import common.network.Response;

import java.io.*;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.Objects;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.tuple.MutablePair;


/**
 * Abstract class for the server
 */
public abstract class Server {
    protected static int port = 3674;
    protected int PACKAGE_SIZE = 1024;
    protected int MAX_PACKAGES_AT_ONCE = 1024;
    protected int DATA_SIZE = 1023;

    /**
     * run the server
     */
    abstract public void run();
    /**
     * send a response to the client
     * @param pair response with index of last sent package
     * @param key client selection key
     * @throws IOException
     */
    abstract protected void send(MutablePair<Integer, byte[]> pair, SocketChannel sock) throws IOException;
    /**
     * read request from the client
     * @param key client selection kye
     * @return request
     * @throws IOException
     * @throws ClassNotFoundException
     */
    abstract protected Request read(SocketChannel sock) throws IOException, ClassNotFoundException;

    /**
     * serialize an object to a byte array using default serialization
     * @param obj object
     * @return serialized object
     * @throws IOException
     */
    protected Byte[] objectToByArray(Serializable obj) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bytes);
        out.writeObject(obj);
        return ArrayUtils.toObject(bytes.toByteArray());
    }

    /**
     * deserialize a byte array to an object using default serialization
     * @param bytes serialized object
     * @return object
     * @throws IOException
     */
    protected Object objectFromArray(Byte[] bytes) throws IOException, ClassNotFoundException {
        return new ObjectInputStream(new ByteArrayInputStream(ArrayUtils.toPrimitive(bytes))).readObject();
    }

    /**
     * set the server port from the args
     * @param args args
     */
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
