package com.weisi.nio;

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
public class NIOServer {
    public static void main(String[] args) throws Exception{
        //创建serversocketchannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //创建Selector，静态方法创建
        Selector selector = Selector.open();
        //serversocketchannel 绑定6666端口监听
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));
        //serversocketchannel 设置非阻塞
        serverSocketChannel.configureBlocking(false);
        //将serversocketchannel注册到selector
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);


        while (true) {
            //返回selectKeys（准备好的i/o，socketchannel），等待一秒
            if (selector.select(1000) == 0) {
                System.out.println("服务器等待了1秒，无连接");
                continue;
            }
            //说明有返回selectkey
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            //遍历
            Iterator<SelectionKey> iterator = selectionKeys.iterator();

            while(iterator.hasNext()) {
                SelectionKey key = iterator.next();
                //如果事件是OP_ACCEPT
                if (key.isAcceptable()) {
                    //拿到socketchannel
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    System.out.println("客户端连接成功，生产了一个scoketChannel" + socketChannel.hashCode());

                    //socketchanne设置非阻塞
                    socketChannel.configureBlocking(false);
                    //设置监听READ事件
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }

                if (key.isReadable()) {
                    SocketChannel channel = (SocketChannel) key.channel();

                    //= ByteBuffer.allocate(1024);效果一样
                    ByteBuffer byteBuffer  = (ByteBuffer) key.attachment();

                    channel.read(byteBuffer);
                    System.out.println("from 客户端" + new String(byteBuffer.array()));
                }
                iterator.remove();
            }
        }


    }
}
