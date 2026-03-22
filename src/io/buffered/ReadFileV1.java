package io.buffered;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static io.buffered.BufferedConst.FILE_NAME;
import static io.buffered.BufferedConst.FILE_SIZE;

public class ReadFileV1 {
    public static void main(String[] args) throws IOException {
        FileInputStream fis = new FileInputStream(FILE_NAME);
        long startTime = System.currentTimeMillis();

        int fileSize = 0;
        int data;
        while ((data = fis.read()) != -1) {
            fileSize ++;
        }
        fis.close();
        long endTime = System.currentTimeMillis();

        System.out.println("file name: " + FILE_NAME);
        System.out.println("file size: " + FILE_SIZE / 1024 / 1024 + "MB");
        System.out.println("Time taken " + (endTime - startTime) + "ms");

        // 정리
        // 10 MB 파일 하나 쓰는데 14초, 읽는데 5초라는 매우 오랜 시간이 걸림
        // 이렇게 느린 이유는 자바에서 1바이트씩 디스크에 데이터를 전달하기 때문
        // 디스크는 1바이트의 데이터를 받아서 1다이트의 데이터를 쓴다
        // 이 과정을 무려 1000만번 반복하는 것

        // 자세히는
        // write(), read()를 호출할 때마다 OS의 시스템콜을 통해 파일을 읽거나 쓰는 명령어를 전달한다
        // 이러한 시스템 콜은 상대적으로 무거운 작업이다.
        // HDD, SSD 같은 장치들도 하나의 데이터를 읽고 쓸 때마다 필요한 시간이 있다. HDD의 경우 더욱 느린데, 물리적으로 디스크의 회전이 필요하다.
        // 이러한 무거운 작업을 무려 1000만 번 반복한다

        // 참고
        // 이렇게 자바에서 운영 체제를 통해 디스크에 1Byte 씩 전달하면, 운영 체제나 하드웨어 레벨에서 여러가지 최적화가 발생함.
        // 따라서 실제로 디스크에 1바이트씩 계속 쓰는 것은 아님. 그렇다면 훨씬 더 느렸을 것임
        // 하지만 자바에서 1바이트씩 write(), read()를 호출할 때마다 운영체제로 시스템 콜이 발생하고, 이 시스템 자체가 상당한 오버헤드를 유발함
        // 디스크도 마찬가지로 1바이트 씩 쓰고 읽지 않고 어느 정도는 모아서 처리할 수 있게 최적화 됨
        // 이렇게 운영체제와 하드웨어가 어느 정도 최적화를 제공하더라도, 자주 발생하는 시스템 콜로 인한 성능 저하는 피할 수 없다.
        // 결국 자바에서 read(), write()를 호출 회수를 줄여서 시스템 콜 횟수도 줄어야 한다.

    }
}
