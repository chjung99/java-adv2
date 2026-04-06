package network.tcp.v3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static util.MyLogger.log;

public class ServerV3 {
    private static final int PORT = 12345;

    public static void main(String[] args) throws IOException {
        log("서버 시작");
        ServerSocket serverSocket = new ServerSocket(PORT);
        log("서버 소켓 시작 - 리스닝 포트: " + PORT);

        while (true) {
            Socket socket = serverSocket.accept();
            log("소켓 연결: " + socket);

            SessionV3 session = new SessionV3(socket);
            Thread thread = new Thread(session);
            thread.start();
        }

    }
}

/*
* 문제: 서버는 하나의 클라이언트가 아니라, 여러 클라이언트의 연결을 처리할 수 있어야 한다
* 첫번째 접근한 클라이언트는 서버와 통신이 가능하나, 두번째 접근한 클라이언트는 서버랑 통신이 안됨
* 소켓 연결은 되지만, 메시지를 전송해도 아무런 응답이 오지 않음
* 서버에서 보면 두번쨰 클라이언트가 보낸 메세지가 안보임
* */

/*
* OS 계층에서 TCP 3 way handshake는 완료된 상태
* 아직 서버에 Socket 객체가 없을 뿐
*
* 클라이언트: 애플리케이션 -> OS TCP 송신 버퍼 -> 클라이언트 네트워크 카드
* 서버:서버 네트워크 카드 -> OS TCP 수신 버퍼 -> 애플리케이션
* 소켓 객체 없이 서버 소켓만으로도 TCP 연결은 완료된다
* 하지만 연결 이후에 서로 메시지를 주고 받으려면 소켓 객체가 필요하다
*
* 새로운 클라이언트가 접속했을 때 accept를 두번 호출할 수 없는 문제
*
* 두 개의 쓰레드를 만들어서 accept(), readXxx()각각의 일을 해주지 않으면 싱글 스레드로는 블로킹때문에 다른 코드가 동작하지 않을 수 있음
* */