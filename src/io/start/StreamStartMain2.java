package io.start;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class StreamStartMain2 {
    public static void main(String[] args) throws IOException {
        FileOutputStream fos = new FileOutputStream("temp/hello.dat");
        fos.write(65);
        fos.write(66);
        fos.write(67);
        fos.close();

        FileInputStream fis = new FileInputStream("temp/hello.dat");
        int data; // read()의 반환 타입은 byte가 아닌 int임
        // 1. 부호 없는 바이트 표현
        // java에서 byte는 부호 있는 8비트 값(-128 ~ 127)이다
        // int로 반환함으로써 0에서 255까지의 모든 가능한 바이트 값을 부호 없이 표현가능
        // 2. EOF 표시
        // byte를 표현하혀면 256 종류의 값을 모두 사용해야 한다
        // 자바의 byte는 -128에서 127까지 256 종류의 값만 가질 수 있어, EOF를 위한 특별한 값 할당 어렵
        // int는 0 ~ 255까지 모든 가능한 바이트 값을 표현하고, 여기에 추가로 -1을 반환하여 스트림의 끝을 나타냄
        while ((data = fis.read()) != -1) {
            System.out.println(data);
        }
        fis.close();
    }
}
