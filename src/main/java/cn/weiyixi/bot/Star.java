package cn.weiyixi.bot;



import cn.weiyixi.bot.monitoring.MonitoringGroupEvents;
import cn.weiyixi.bot.monitoring.MonitoringPrivateChatEvents;
import lombok.val;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.auth.BotAuthorization;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.utils.BotConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
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

    @Resource
    MonitoringGroupEvents monitoringGroupEvents;

    @Value("${qq}")
    Long QQ;


    //Spring容器启动完成时触发
    @Override
    public void run(ApplicationArguments args) throws Exception {
        //调用连接方法
        //Client.connect("");
//
        Bot bot = BotFactory.INSTANCE.newBot(QQ, BotAuthorization.byQRCode(), configuration -> {
            //configuration.setProtocol(BotConfiguration.MiraiProtocol.ANDROID_WATCH);
            configuration.setProtocol(BotConfiguration.MiraiProtocol.MACOS);
//            configuration.setProtocol(BotConfiguration.MiraiProtocol.ANDROID_PHONE);
            //心跳策略
            configuration.setHeartbeatStrategy(BotConfiguration.HeartbeatStrategy.STAT_HB);
        });
        bot.login();
        //监听群组事件
        monitoringGroupEvents.MonitoringGroup();
        new Thread(bot::join).start();
    }
}
