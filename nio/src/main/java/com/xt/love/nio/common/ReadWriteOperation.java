package com.xt.love.nio.common;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * Create User: wangtao
 * Create In 2019-08-21 16:23
 * Description:
 **/
public class ReadWriteOperation {

    public static void read(SelectionKey key) {
        try {
            if (key.isValid()) {
                SocketChannel channel = (SocketChannel) key.channel();
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                try {
                    channel.read(buffer);
                    byte[] array = buffer.array();
                    if (array.length > 0) {
                        System.out.println("****** " + new String(array));
                        Scanner scanner = new Scanner(System.in);
                        String input = scanner.next();
                        channel.write(ByteBuffer.wrap(input.getBytes()));
                    }
                } catch (IOException e) {
                    /**
                     * 当客户端异常关闭时，将通道也对应关闭
                     */
                    channel.close();
                }
            } else {
                System.out.println("current key invalid, key is: " + key.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
