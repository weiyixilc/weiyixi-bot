package cn.weiyixi.bot.monitoring;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author：weiyixi
 * @Package：cn.weiyixi.bot.monitoring
 * @Project：weiyixi-bot
 * @name：监听私聊事件类
 * @Date：2025/8/7 14:54
 * @Filename：MonitoringPrivateChatEvents
 */
@Component
public class MonitoringPrivateChatEvents {

    @Value("${packagePassword}")
    String packagePassword;



}
