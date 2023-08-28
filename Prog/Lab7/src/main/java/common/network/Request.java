package common.network;

import common.Commands;
import common.models.User;

import java.io.Serializable;
import java.net.SocketAddress;
import java.util.HashMap;

/**
 * Represents a request object that contains the command type and associated data in a HashMap.
 * Can be serialized and sent over a network.
 */
public class Request implements Serializable {
    private final Commands type;
    private final HashMap<String, Object> data;
    private User user;
    private String token;
    transient private SocketAddress address;

    /**
     * Constructs a new request object with the given command type and an empty data HashMap.
     * @param type The command type to be associated with this request.
     */
    public Request(Commands type) {
        this.type = type;
        this.data = new HashMap<>();
    }

    public Request(Commands type, User user) {
        this.type = type;
        this.user = user;
        this.data = new HashMap<>();
    }

    /**
     * Constructs a new request object with the given command type and data HashMap.
     * @param type The command type to be associated with this request.
     * @param data The data HashMap to be associated with this request.
     */
    public Request(Commands type, HashMap<String, Object> data) {
        this.type = type;
        this.data = data;
    }

    /**
     * Returns the data HashMap associated with this request.
     * @return The data HashMap associated with this request.
     */
    public HashMap<String, Object> getData() {
        return data;
    }

    /**
     * Returns the command type associated with this request.
     * @return The command type associated with this request.
     */
    public Commands getType() {
        return type;
    }

    /**
     * Puts a new key-value pair into the data HashMap associated with this request.
     * @param key The key to be associated with the value.
     * @param value The value to be associated with the key.
     * @param <T> The type of the value to be added.
     * @return This request object with the new key-value pair added.
     */
    public <T> Request put(String key, T value) {
        data.put(key, value);
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public User getUser() {
        return user;
    }

    public SocketAddress getAddress() {
        return address;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setAddress(SocketAddress address) {
        this.address = address;
    }

    /**
     * Returns the value associated with the given key in the data HashMap.
     * @param key The key of the value to be returned.
     * @param <T> The type of the value to be returned.
     * @return The value associated with the given key, or null if the key is not found.
     */
    public <T> T get(String key) {
        if(!data.containsKey(key))
            return null;
        return (T) data.get(key);
    }
}
