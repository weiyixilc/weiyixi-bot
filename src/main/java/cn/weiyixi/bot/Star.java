package cn.weiyixi.bot;



import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.auth.BotAuthorization;
import net.mamoe.mirai.utils.BotConfiguration;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @Author：weiyixi
 * @Package：cn.weiyixi.bot
 * @Project：weiyixi-bot
 * @name：Star
 * @Date：2025/8/4 15:46
 * @Filename：Star
 */
@Component
public class Star implements ApplicationRunner {
    //Spring容器启动完成时触发
    @Override
    public void run(ApplicationArguments args) throws Exception {
        //调用连接方法
        //Client.connect("");
        Bot bot = BotFactory.INSTANCE.newBot(987553211, BotAuthorization.byQRCode(), configuration -> {
            configuration.setProtocol(BotConfiguration.MiraiProtocol.ANDROID_WATCH);
        });

        bot.login();

        new Thread().join();
        System.out.println("启动时");
    }
}
