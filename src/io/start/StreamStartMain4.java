package io.start;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class StreamStartMain4 {
    public static void main(String[] args) throws IOException {
        FileOutputStream fos = new FileOutputStream("temp/hello.dat");
        byte[] input = {65, 66, 67};
        fos.write(input);
        fos.close();

        FileInputStream fis = new FileInputStream("temp/hello.dat");
        byte[] readBytes = fis.readAllBytes();
        System.out.println(Arrays.toString(readBytes));
        fis.close();

        // 나누어 읽기 vs 한번에 읽기
        // 1. 나누어 읽기
        // 메모리 사용량을 제어할 수 있다.
        // 100M의 파일을 1M 단위로 읽고 처리하는 방식을 사용하면 한 번에 최대 1M 메모리만 사용
        // 2. 한 번에 읽기
        // 메모리에 모든 내용을 올려서 처리해야 하는 경우 적합
        // 메모리 사용량을 제어할 수 없음
        // 큰 파일의 경우 OOM 발생가능
    }
}
