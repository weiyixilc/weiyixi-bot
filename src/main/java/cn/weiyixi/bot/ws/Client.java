package cn.weiyixi.bot.ws;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;

/**
 * @Author：weiyixi
 * @Package：cn.weiyixi.bot.ws
 * @Project：weiyixi-bot
 * @name：Client
 * @Date：2025/8/4 15:32
 * @Filename：Client
 */
@ClientEndpoint
public class Client {
    private Session session;

    private static Client INSTANCE;

    private Client(String url) throws DeploymentException, IOException {
        session = ContainerProvider.getWebSocketContainer().connectToServer(this, URI.create(url));
    }

    public synchronized static boolean connect(String url){
        try {
            INSTANCE = new Client(url);
            return true;
        } catch (DeploymentException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    //连接成功时触发
    @OnOpen
    public void onOpen(Session session){
        System.out.println("连接成功");
    }

    //收到消息时触发
    @OnMessage
    public void onMessage(String json){
        System.out.println("收到消息："+json);
    }


    //连接关闭时触发
    @OnClose
    public void onClose(Session session){
        System.out.println("连接关闭");
    }


    //连接异常时触发
    @OnError
    public void onError(Session session,Throwable throwable){
        System.out.println("连接异常");
    }


}
