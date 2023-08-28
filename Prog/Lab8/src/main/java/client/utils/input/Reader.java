package client.utils.input;

/**
 * Interface of Reader
 */
public interface Reader {
    /**
     *
     * @return true if reader has next line else false
     */
    boolean hasNextLine();

    /**
     *
     * @return next line if it's existing
     */
    String nextLine();

}
