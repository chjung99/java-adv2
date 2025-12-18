package network.chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import static util.MyLogger.log;

public class ClientV2 {
    private static final int PORT = 12345;

    public static void main(String[] args) throws IOException, InterruptedException {
        log("클라이언트 시작");

        Socket socket = new Socket("localhost", PORT);
        DataInputStream input = new DataInputStream(socket.getInputStream());
        DataOutputStream output = new DataOutputStream(socket.getOutputStream());

        log("소켓 연결: " + socket);
        Scanner scanner = new Scanner(System.in);
        Thread read = new Thread(new Read(input), "read");
        Thread write = new Thread(new Write(output, scanner), "write");

        read.start();
        write.start();

        //todo: 일단 스레드를 나누긴 했지만 큰 의미 없고, 나중에 non blocking으로 갈 필요 있음
        //blocking과 busy waiting의 차이!
        read.join();
        write.join();

        socket.close();
    }

    static class Read implements Runnable {
        private final DataInputStream input;
        public Read(DataInputStream input) {
            this.input = input;
        }

        @Override
        public void run() {
            while (true) {
                String received = null;
                try {
                    received = input.readUTF();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                log("client <- server: " + received);
            }
        }
    }

    static class Write implements Runnable {
        private final DataOutputStream output;
        private final Scanner scanner;


        public Write(DataOutputStream output, Scanner scanner) {
            this.output = output;
            this.scanner = scanner;
        }

        @Override
        public void run() {
            while (true) {
                System.out.println("전송 문자: ");
                String toSend = scanner.nextLine();

                try {
                    output.writeUTF(toSend);
                    output.flush();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                log("client -> server: " + toSend);
            }
        }
    }

}
