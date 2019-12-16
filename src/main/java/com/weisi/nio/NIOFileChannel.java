package com.weisi.nio;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created on 2017/7/17.
 * Title: Simple
 * Description: Example
 * Copyright: Copyright(c) 2016
 * Company: 杭州公共交通云科技有限公司
 *
 * @author 维斯
 */
public class NIOFileChannel {
    public static void main(String[] args) throws Exception{
        String hello = "hello, weisi";
        //创建一个输出流->channel
        FileOutputStream fileOutputStream = new FileOutputStream("d:\\file01.txt");
        //通过输出流获取对应的fileChannel
        //chanel真实类型是FileChannelImpl
        FileChannel channel = fileOutputStream.getChannel();
        //创建一个缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        //将string放入到ByteBuffer
        byteBuffer.put(hello.getBytes());
        byteBuffer.flip();
        channel.write(byteBuffer);
        fileOutputStream.close();


    }
}
