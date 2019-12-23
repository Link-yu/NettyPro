package com.weisi.nio;

import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

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
    private final static String PORT = "8899";

    public NIOGroupChat() {

    }
}
