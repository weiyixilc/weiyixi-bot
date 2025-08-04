package cn.weiyixi.bot;

import cn.weiyixi.bot.ws.Client;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @Author：weiyixi
 * @Package：cn.weiyixi.bot
 * @Project：weiyixi-bot
 * @name：Star
 * @Date：2025/8/4 15:46
 * @Filename：Star
 */
@Component
public class Star implements CommandLineRunner {
    //Spring容器启动完成时触发
    @Override
    public void run(String... args) throws Exception {
        //调用连接方法
        Client.connect("");

    }
}
