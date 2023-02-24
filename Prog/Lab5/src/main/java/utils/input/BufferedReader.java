package utils.input;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class BufferedReader implements Reader {
    private final String[] input;
    private String stringInput = "";
    private int pos = 0;

    public BufferedReader(FileInputStream file) throws IOException {
        BufferedInputStream inputStream = new BufferedInputStream(file);
        ArrayList<Byte> buffer = new ArrayList<>();
        int c = 0;

        while ((c = inputStream.read()) != -1) {
            buffer.add((byte) c);
        }

        byte[] bytes = new byte[buffer.size()];
        for (int i = 0; i < buffer.size(); i++) {
            bytes[i] = buffer.get(i);
        }
        stringInput = new String(bytes, StandardCharsets.UTF_8);
        input = stringInput.split("\n");
        file.close();
    }

    public String getStringInput() {
        return stringInput;
    }

    public boolean hasNextLine() {
        return pos < input.length;
    }

    public String nextLine() {
        if(hasNextLine()) {
            return input[pos++].strip();
        }
        return "";
    }
}
