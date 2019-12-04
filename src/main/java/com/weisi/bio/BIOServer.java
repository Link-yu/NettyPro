package com.weisi.bio;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created on 2017/7/17.
 * Title: Simple
 * Description: Example
 * Copyright: Copyright(c) 2016
 * Company: 杭州公共交通云科技有限公司
 *
 * @author 维斯
 */
public class BIOServer {

    public static void main(String[] args) throws Exception{
        ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
        ServerSocket serverSocket = new ServerSocket(6666);
        System.out.println("服务器已经启动");
        while(true) {
            //监听，等待客户端连接
            final Socket socket = serverSocket.accept();
            System.out.println("连接到一个客户端");
            newCachedThreadPool.execute(new Runnable() {
                public void run() {
                    //可以和客户端通信
                    handler(socket);
                }
            });
        }

    }

    public static void handler(Socket socket) {
        try {
            byte[] bytes = new byte[1024];
            //通过socker获取数据
            InputStream inputStream = socket.getInputStream();
            //循环读取客户端发送的数据
            while (true) {
                int read = inputStream.read(bytes);
                if (read != -1) {
                    System.out.println(new String(bytes,0, read));//
                } else {
                    break;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("关闭socket");
            try {
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }
    //1.创建一个线程池
    //2.如果有客户端连接，新建
}
