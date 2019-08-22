package com.xt.love.nio.server;

/**
 * Create User: wangtao
 * Create In 2019-08-21 16:31
 * Description: 启动后可以使用telnet ip port 进行连接
 * 退出telnet命令， ctrl+], telnet> quit 回车
 **/
public class ServerApp {

    public static void main(String[] args) {
        NIOServer server = new NIOServer();
        server.init();
        server.listen();
    }

}
