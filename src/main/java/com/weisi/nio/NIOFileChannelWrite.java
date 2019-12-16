package com.weisi.nio;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.Selector;

/**
 * Created on 2017/7/17.
 * Title: Simple
 * Description: Example
 * Copyright: Copyright(c) 2016
 * Company: 杭州公共交通云科技有限公司
 *
 * @author 维斯
 */
public class NIOFileChannelWrite {
    public static void main(String[] args) throws Exception{
        String str = "hello, shangxuetang";
        FileOutputStream fileOutputStream = new FileOutputStream("E:\\GitLab\\NettyPro\\src\\main\\java\\com\\weisi\\nio\\1.txt");

        FileChannel fileChannel = fileOutputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.put(str.getBytes());
        byteBuffer.flip();
        fileChannel.write(byteBuffer);//缓冲区写入channel
        fileOutputStream.close();
        Selector selector = Selector.open();
        selector.select();
    }
}
