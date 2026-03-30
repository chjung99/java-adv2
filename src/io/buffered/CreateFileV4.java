package io.buffered;

import java.io.FileOutputStream;
import java.io.IOException;

import static io.buffered.BufferedConst.FILE_NAME;
import static io.buffered.BufferedConst.FILE_SIZE;

public class CreateFileV4 {
    public static void main(String[] args) throws IOException {
        FileOutputStream fos = new FileOutputStream(FILE_NAME);
        long startTime = System.currentTimeMillis();

        byte[] buffer = new byte[FILE_SIZE];
        for (int i = 0; i < FILE_SIZE; i++) {
            buffer[i] = 1;
        }
        fos.write(buffer);
        // 한번에 저장하기 때문에 메모리 사용량이 많다, 하지만 시스템 콜을 한번만 호출하므로 시간이 단축된다
        fos.close();

        long endTime = System.currentTimeMillis();
        System.out.println("file created: " + FILE_NAME);
        System.out.println("file size: " + FILE_SIZE / 1024 / 1024 + "MB");
        System.out.println("Time taken " + (endTime - startTime) + "ms");
    }
}
