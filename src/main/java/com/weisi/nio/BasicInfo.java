package com.weisi.nio;

import java.nio.IntBuffer;

public class BasicInfo {
    public static void main(String[] args) {
        //举例buffer的使用
        //创建一个buffer，大小为5，可以存放5个int
        IntBuffer intBuffer = IntBuffer.allocate(5);
        //向buffer存放数据
        intBuffer.put(10);
        intBuffer.put(11);
        intBuffer.put(12);
        intBuffer.put(13);
        intBuffer.put(14);
        //如何从buffer取数据
        //将buffer读写切换
        intBuffer.flip();
        while(intBuffer.hasRemaining()) {
            System.out.println(intBuffer.get());
        }
    }
}
