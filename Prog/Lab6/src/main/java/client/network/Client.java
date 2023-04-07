package client.network;

import common.network.Request;
import common.network.Response;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;

/**
 * The abstract base class for a client that sends and receives data over a network.
 */
public abstract class Client {

    protected static int port = 3674;
    protected static InetAddress host;
    protected int PACKAGE_SIZE = 1024;
    protected int DATA_SIZE = 1023;

    /**
     * Creates a new instance of the client.
     */
    public Client() {}

    /**
     * Sends a request to the server and waits for a response.
     * @param request the request to send to the server
     * @return the response received from the server
     */
    abstract public Response sendAndReceive(Request request);

    /**
     * Sends a request to the server.
     * @param request the request to send to the server
     * @throws IOException if there is an error sending the request
     */
    abstract protected void send(Request request) throws IOException;

    /**
     * Receives a response from the server.
     * @return the response received from the server
     * @throws IOException if there is an error receiving the response
     * @throws ClassNotFoundException if the response object received is of an unknown class
     */
    abstract protected Response receive() throws IOException, ClassNotFoundException;

    /**
     * Closes the connection to the server.
     */
    abstract public void close();

    /**
     * Sets the port number for the client to use when connecting to the server.
     * @param args the command-line arguments passed to the client program
     */
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

    /**
     * Sets the host address for the client to use when connecting to the server.
     * @param args the command-line arguments passed to the client program
     */
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

    /**
     * Connects to the server.
     * @throws IOException if there is an error connecting to the server
     */
    abstract public void connect() throws IOException;

    /**
     * Checks whether the client is currently connected to the server.
     * @return true if the client is connected, false otherwise
     */
    abstract public boolean isConnected();
}
