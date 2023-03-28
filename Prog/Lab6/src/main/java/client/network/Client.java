package client.network;

import common.request.Request;
import common.response.Response;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Objects;

public abstract class Client {
    protected static int port = 3674;
    protected static InetAddress host;

    protected int PACKAGE_SIZE = 1024;
    protected int DATA_SIZE = 1023;

    public Client() {

    }

    abstract public Response sendAndReceive(Request request);
    abstract protected void send(Request request) throws IOException;
    abstract protected Response receive() throws IOException, ClassNotFoundException;
    abstract public void close();

    public static void setPort(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if(i + 1 < args.length  && Objects.equals(args[i], "-p")) {
                try {
                    Client.port = Integer.parseInt(args[i+1]);
                    return;
                } catch (NumberFormatException e) {
                    System.out.println("Порт задан не правильно");
                }
            }
        }
    }



    public static void setHost(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if(i + 1 < args.length && Objects.equals(args[i], "-host")) {
                try {
                    Client.host = InetAddress.getByName(args[i+1]);
                    return;
                } catch (UnknownHostException e) {
                    System.out.println("Хост задан не правильно");
                }
            }
        }
        try {
            Client.host = InetAddress.getLocalHost();
        } catch (UnknownHostException ex) {
            System.out.println("Сервер не найден на локальном адресе");
        }
    }

    abstract public void connect() throws IOException;

    abstract public boolean isConnected();
}
