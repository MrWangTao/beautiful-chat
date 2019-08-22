package com.xt.love.nio.server;

import static com.xt.love.nio.common.ReadWriteOperation.read;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * Create User: wangtao
 * Create In 2019-08-21 16:14
 * Description:
 **/
public class NIOServer {

    private Selector selector;

    public void init() {
        try {
            ServerSocketChannel channel = ServerSocketChannel.open();
            channel.configureBlocking(false);
            this.selector = Selector.open();
            channel.register(selector, SelectionKey.OP_ACCEPT);
            channel.socket().bind(new InetSocketAddress(8099));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 监听channel注册到Selector的事件
     */
    public void listen() {
        while (true) {
            try {
                this.selector.select();
                Iterator<SelectionKey> keys = this.selector.selectedKeys().iterator();
                while (keys.hasNext()) {
                    SelectionKey key = keys.next();
                    /**
                     * 处理完删除，防止重复处理
                     */
                    keys.remove();
                    if (key.isValid()) {
                        if (key.isAcceptable()) {
                            // 如果正在连接，则完成连接
                            ServerSocketChannel server = (ServerSocketChannel) key.channel();
                            SocketChannel channel = server.accept();
                            channel.configureBlocking(false);
                            channel.write(ByteBuffer.wrap("链接服务器成功".getBytes()));
                            channel.register(this.selector, SelectionKey.OP_READ);
                        } else if (key.isWritable()) {

                        } else if (key.isReadable()) {
                            read(key);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
