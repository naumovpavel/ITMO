package server.auth;

import java.net.InetAddress;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;

public class LoginException extends Exception {
    private final SocketAddress address;

    public LoginException(String message, SocketAddress address) {
        super(message);
        this.address = address;
    }

    public SocketAddress getAddress() {
        return address;
    }
}
