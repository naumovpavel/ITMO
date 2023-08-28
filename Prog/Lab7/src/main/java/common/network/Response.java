package common.network;

import java.io.Serializable;
import java.util.HashMap;

/**
 * The Response class represents the response to a Request object. It contains an answer string, a status object,
 * an error message string, and a HashMap that holds key-value pairs of data. The answer string holds the answer to a
 * successful request, while the error message string holds the error message to an unsuccessful request. The
 * status object represents the status of the response, which can be either "OK" or "ERROR". The data HashMap
 * can store any additional data that needs to be returned with the response.
 */
public class Response implements Serializable {
    protected String answer;
    protected Status status;
    protected String error;
    protected HashMap<String, Object> data;

    /**
     * Creates a new Response object with an answer and a status.
     *
     * @param answer The answer to the request.
     * @param status The status of the response.
     */
    public Response(String answer, Status status) {
        if(status == Status.OK)
            this.answer = answer;
        else
            this.error = answer;
        this.status = status;
        this.data = new HashMap<>();
    }

    /**
     * Creates a new Response object with a status only.
     *
     * @param status The status of the response.
     */
    public Response(Status status) {
        this.status = status;
        this.data = new HashMap<>();
    }

    /**
     * Returns the error message.
     *
     * @return The error message.
     */
    public String getError() {
        return error;
    }

    /**
     * Returns the answer.
     *
     * @return The answer.
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * Returns the status of the response.
     *
     * @return The status of the response.
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Adds a key-value pair to the data HashMap.
     *
     * @param key The key to add.
     * @param value The value to add.
     * @return The Response object.
     */
    public <T> Response put(String key, T value) {
        data.put(key, value);
        return this;
    }

    /**
     * Returns the value for a given key in the data HashMap.
     *
     * @param key The key to search for.
     * @return The value associated with the key, or null if the key is not found.
     */
    public <T> T get(String key) {
        if(!data.containsKey(key))
            return null;
        return (T) data.get(key);
    }
}
