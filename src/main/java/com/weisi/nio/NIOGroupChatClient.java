package com.weisi.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * Created on 2017/7/17.
 * Title: Simple
 * Description: Example
 * Copyright: Copyright(c) 2016
 * Company: 杭州公共交通云科技有限公司
 *
 * @author 维斯
 */
public class NIOGroupChatClient {
    private static final String address = "127.0.0.1";
    private static final int port = 7767;
    private Selector selector;
    private SocketChannel socketChannel;
    private String userName;
    public NIOGroupChatClient() throws IOException {
        selector = Selector.open();
        socketChannel = SocketChannel.open(new InetSocketAddress(address,port));
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);
        userName = socketChannel.getLocalAddress().toString().substring(1);
        System.out.println(userName + "is OK!");
    }

    public void sendInfo(String info) {
        info = userName + "说" + info;
        try {
            socketChannel.write(ByteBuffer.wrap(info.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readInfo() {
        try {
            int count = selector.select();
        } catch (Exception e) {

        }
    }




}
