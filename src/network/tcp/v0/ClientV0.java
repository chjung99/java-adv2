package network.tcp.v0;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static util.MyLogger.log;

public class ClientV0 {

    private static final int PORT = 12345;

    public static void main(String[] args) throws IOException {
        log("클라이언트 시작");
        Socket socket = new Socket("localhost", PORT);
        DataInputStream input = new DataInputStream(socket.getInputStream());
        DataOutputStream output = new DataOutputStream(socket.getOutputStream());
        log("소캣 연결: " + socket);

        String toSend = "Hello";
        output.writeUTF(toSend);
        log("client -> server: " + toSend);

        String received = input.readUTF();
        log("client <- server: " + received);

        log("연결 종료: " + socket);
        input.close();
        output.close();
        socket.close();
    }
}
