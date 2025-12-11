package network.exception.connect;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ConnectMain {

    public static void main(String[] args) throws IOException {
        unknowHostEx1();
        unknowHostEx2();
        connectionRefused();
    }

    private static void unknowHostEx2() throws IOException {
        try {
            Socket socket = new Socket("google.gogo", 80);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    private static void unknowHostEx1() throws IOException {
        try {
            Socket socket = new Socket("999.999.999.999", 80);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }


    private static void connectionRefused() {
        try {
            new Socket("localhost", 45678);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
