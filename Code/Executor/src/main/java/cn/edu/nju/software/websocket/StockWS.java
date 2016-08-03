package cn.edu.nju.software.websocket;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * Created by song on 16-8-3.
 * <p>
 * 用于股票数据传输的WebSocket
 */
@ServerEndpoint("/stock")
public class StockWS {

    private Session session;

    // 建立链接
    @OnOpen
    public void start(Session session) {
        this.session = session;
        send("连接成功......");
    }

    @OnMessage
    public void responseMessage(String message) {
        System.out.println("Server.responseMessage");
        System.out.println(message);
    }

    @OnError
    public void error(Session session, Throwable t) {
        t.printStackTrace();
    }

    /**
     * 发送消息
     *
     * @param message 消息
     */
    private void send(String message) {
        try {
            for (int i = 0; i < 3; i++) {
                session.getBasicRemote().sendText(message);
                Thread.sleep(3000);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
