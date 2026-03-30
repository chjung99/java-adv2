package io.buffered;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import static io.buffered.BufferedConst.*;

public class CreateFileV3 {
    public static void main(String[] args) throws IOException {
        FileOutputStream fos = new FileOutputStream(FILE_NAME);
        BufferedOutputStream bos = new BufferedOutputStream(fos, BUFFER_SIZE);

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < FILE_SIZE; i++) {
            bos.write(1);
            // buffer가 가득찰 때 까지 사용하고, 버퍼가 가득차면
            // FileOutPutStream에 있는 write(byte[]) 메서드를 호출한다
            // 전달된 모든 byte 배열을 시스템 콜로 OS로 전달한다
            // 버퍼가 다 차지 않아도 남아 있는 데이터를 전달하려면 flush() 메서드를 호출하면 됨
        }
        bos.close(); // 버퍼에 데이터가 남아 있는 상태로 close()를 호출하면 내부적으로 flush()를 호출한다
        // close를 호출하면 BufferedOutputStream을 해제하고 연결된 FileOutputStream도 연쇄적으로 해제된다
        // 반드시 마지막에 연결한 스트림을 해제해줘야 한다. 그렇게 해주면 연쇄적으로 해제됨

        // 기본 스트림: FileOutputStream과 같이 단독으로 사용할 수 있는 스트림
        // 보조 스트림: BufferedOutputStream 같이 단독으로 사용할 수 없고, 보조 기능을 제공하는 스트림

        // 정리
        // BufferedOutputStream은 버퍼 기능을 제공하는 보조 스트림이다.
        // BufferedOutputStream도 OutputStream의 자식이기 때문에, OutputStream의 기능을 그대로 사용할 수 있다.
        // 물론 대부분의 기능은 재정의 된다. write()의 경우도 먼저 버퍼에 쌓도록 재정의 된다
        // 버퍼의 크기만큼 데이터를 모아서 전달하기 때문에 빠른 속도로 데이터를 처리할 수 있다.

        long endTime = System.currentTimeMillis();
        System.out.println("file created: " + FILE_NAME);
        System.out.println("file size: " + FILE_SIZE / 1024 / 1024 + "MB");
        System.out.println("Time taken " + (endTime - startTime) + "ms");
    }
}
