package client.utils.input;

import java.util.Arrays;

public class ArrayReader implements Reader{
    private volatile String[] stream;
    private volatile int pos;

    @Override
    public boolean hasNextLine() {
        return pos < stream.length;
    }

    @Override
    public String nextLine() {
        return stream[pos++];
    }

    public void setStream(String[] stream) {
        pos = 0;
        this.stream = stream;
    }
}
