package server.auth;

import java.net.InetAddress;
import java.net.SocketAddress;

public class RegisterException extends Exception{
    private final SocketAddress address;

    public RegisterException(String message, SocketAddress address) {
        super(message);
        this.address = address;
    }

    public SocketAddress getAddress() {
        return address;
    }
}
