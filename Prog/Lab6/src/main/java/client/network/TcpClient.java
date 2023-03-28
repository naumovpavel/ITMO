package client.network;

import common.request.InfoRequest;
import common.request.Request;
import common.response.Response;
import org.apache.commons.lang3.ArrayUtils;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

public class TcpClient extends Client{

    protected Socket socket;
    protected InputStream inputStream;
    protected OutputStream outputStream;
    private static TcpClient instance;
    private TcpClient() throws IOException {
        try {
            connect();
        } catch (IOException e) {
            System.out.println("Сервер временно не доступен, попробуйте позже");;
        }
    }

    @Override
    public Response sendAndReceive(Request request) {
        Response response = null;
        try {
            if(socket == null) {
                connect();
            }
            send(request);
            response = receive();
        } catch (IOException | ClassNotFoundException e) {
            response = new Response( false, "Сервер временно не доступен, попробуйте позже");
            socket = null;
        }
        return response;
    }

    @Override
    protected void send(Request request) throws IOException {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        new ObjectOutputStream(byteOut).writeObject(request);
        byte[] bytes = byteOut.toByteArray();

        int n = (bytes.length + DATA_SIZE - 1) / DATA_SIZE;
        for (int i = 0; i < n; i++) {
            byte[] buf = Arrays.copyOfRange(bytes, i*DATA_SIZE, (i+1)*DATA_SIZE);
            if(i != n-1) {
                buf = ArrayUtils.add(buf, (byte) 0);
            } else {
                buf = ArrayUtils.add(buf, (byte) 1);
            }
            outputStream.write(buf);
            outputStream.flush();
        }
    }

    @Override
    protected Response receive() throws IOException, ClassNotFoundException {
        ArrayList<Byte> bytes = new ArrayList<>();
        int n = readSize();
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream, n*PACKAGE_SIZE);

        while (true) {
            byte[] buf = new byte[PACKAGE_SIZE];
            int m = bufferedInputStream.read(buf);
            if(m == -1 || m != PACKAGE_SIZE) {
                throw new IOException("Ошибка при чтение ответа от сервера");
            }
            for (int j = 0; j < DATA_SIZE; j++) {
                bytes.add(buf[j]);
            }
            if(buf[PACKAGE_SIZE-1] == 1) {
                break;
            }
        }

        Byte[] res = new Byte[bytes.size()];
        res = bytes.toArray(res);
        ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(ArrayUtils.toPrimitive(res)));
        return (Response) in.readObject();
    }

    protected int readSize() throws IOException {
        byte[] buf = new byte[32];
        int m = inputStream.read(buf);

        if(m == -1) {
            throw new IOException();
        }

        int n = 0;
        for(int i = 0; i< 32; i++) {
            if(buf[i] == 0) {
                break;
            } else if(buf[i] == 49) {
                n += (1 << i);
            }
        }
        return n;
    }

    public void connect() throws IOException {
        socket = new Socket(host.getHostAddress(), port);
        inputStream = socket.getInputStream();
        outputStream = socket.getOutputStream();
    }

    @Override
    public void close() {
        if(socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("При закрытие соединения произошла ошибка");
            }
        }
    }

    public static Client getInstance() {
        if(instance == null) {
            try {
                instance = new TcpClient();
            } catch (IOException e) {
                System.out.println("При подключении к серверу произошла ошибка");;
            }
        }
        return instance;
    }

    public boolean isConnected() {
        if(socket == null) {
            return true;
        }
        Response response = sendAndReceive(new InfoRequest());
        return !response.getResult();
    }
}