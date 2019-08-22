package com.xt.love.nio.client;

import com.xt.love.nio.common.ReadWriteOperation;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * Create User: wangtao
 * Create In 2019-08-21 17:02
 * Description:
 **/
public class NIOClient {

    private Selector selector;

    public void init() {
        try {
            SocketChannel channel = SocketChannel.open();
            channel.configureBlocking(false);
            this.selector = Selector.open();
            channel.connect(new InetSocketAddress("localhost", 8099));
            channel.register(this.selector, SelectionKey.OP_CONNECT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listen() {
        while (true) {
            try {
                this.selector.select();
                Iterator<SelectionKey> keys = this.selector.selectedKeys().iterator();
                if (keys.hasNext()) {
                    SelectionKey key = keys.next();
                    keys.remove();
                    if (!key.isValid()) {
                        continue;
                    }
                    if (key.isConnectable()) {
                        SocketChannel channel = (SocketChannel) key.channel();
                        if (channel.isConnectionPending()) {
                            channel.finishConnect();
                        }
                        channel.configureBlocking(false);
                        channel.register(this.selector, SelectionKey.OP_READ);
                    } else if (key.isReadable()) {
                        ReadWriteOperation.read(key);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
