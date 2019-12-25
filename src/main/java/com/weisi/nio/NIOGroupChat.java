package com.weisi.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * Created on 2017/7/17.
 * Title: Simple
 * Description: Example
 * Copyright: Copyright(c) 2016
 * Company: 杭州公共交通云科技有限公司
 *
 * @author 维斯
 */
public class NIOGroupChat {
    private ServerSocketChannel serverSocketChannel;
    private Selector selector;
    private final static int PORT = 8899;

    public NIOGroupChat() {
        try{
            //创建serversocketchannel
            serverSocketChannel = ServerSocketChannel.open();
            //创建selector
            selector = Selector.open();
            //设置非阻塞
            serverSocketChannel.configureBlocking(false);
            //绑定监听的端口
            serverSocketChannel.socket().bind(new InetSocketAddress(PORT));
            //将serversocketchannel 注册到selector
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void listen() {
        try {
            while(true) {
                int count = selector.select();
                //说明有事件处理
                if (count > 0) {
                    //得到selectionKey 集合
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    //遍历selectionkey
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();
                    while(iterator.hasNext()) {
                        SelectionKey selectionKey = iterator.next();

                        //如果是accept事件
                        if (selectionKey.isAcceptable()) {
                            SocketChannel channel = (SocketChannel) selectionKey.channel();
                            channel.configureBlocking(false);
                            //将该channel注册到selector
                            channel.register(selector, SelectionKey.OP_READ);
                            System.out.println(channel.getRemoteAddress() + "上线了");
                        }

                        if (selectionKey.isReadable()) {
                            readData(selectionKey);
                        }

                        iterator.remove();
                    }
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void readData(SelectionKey key) {
        SocketChannel socketChannel = null;
        try {
            socketChannel = (SocketChannel) key.channel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            int count = socketChannel.read(byteBuffer);
            if (count > 0) {
                String msg = new String(byteBuffer.array());
                System.out.println("from 客户端" + msg);
                sendOtherCilent(msg, key);
            }
        } catch (IOException e1) {
            try {
                System.out.println(socketChannel.getRemoteAddress() +"离线了");
                key.cancel();
                socketChannel.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }

    public void sendOtherCilent(String msg,SelectionKey self) {
        System.out.println("服务器消息转发中");
        try {
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while(iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                if (selectionKey instanceof SelectionKey && selectionKey != self) {
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                    ByteBuffer byteBuffer = ByteBuffer.wrap(msg.getBytes());
                    socketChannel.write(byteBuffer);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        NIOGroupChat nioGroupChat = new NIOGroupChat();
        nioGroupChat.listen();
    }
}
