package com.xt.love.nio.client;

/**
 * Create User: wangtao
 * Create In 2019-08-21 17:15
 * Description:
 **/
public class ClientApp {

    public static void main(String[] args) {
        NIOClient client = new NIOClient();
        client.init();
        client.listen();
    }

}
