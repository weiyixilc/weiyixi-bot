package cn.weiyixi.bot;



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
//
        Bot bot = BotFactory.INSTANCE.newBot(987553211, BotAuthorization.byQRCode(), configuration -> {
            //configuration.setProtocol(BotConfiguration.MiraiProtocol.ANDROID_WATCH);
            configuration.setProtocol(BotConfiguration.MiraiProtocol.MACOS);
            //心跳策略
            configuration.setHeartbeatStrategy(BotConfiguration.HeartbeatStrategy.STAT_HB);
        });
        bot.login();


        // 3. 注册群消息监听器
        GlobalEventChannel.INSTANCE.subscribeAlways(GroupMessageEvent.class, event -> {
            MessageChain message = event.getMessage();

            long botId = event.getBot().getId();

            // 4. 检查消息是否@了机器人
            boolean isAtBot = message.stream().anyMatch(singleMsg ->
                            singleMsg instanceof At && ((At) singleMsg).getTarget() == botId
                    );

            if (isAtBot) {
                // 5. 获取消息内容(去除@部分)
                String content = message.contentToString()
                        .replace("@" + botId, "")
                        .trim();

                // 6. 构建回复消息
                String reply = "收到你的@消息";
                if (!content.isEmpty()) {
                    reply += "，你说: " + content;
                }

                // 7. 发送回复(包含@发送者)
            /*event.getGroup()
                获取触发当前事件的群组对象，返回一个Group实例，代表消息来源的QQ群。该方法是所有群组相关操作的基础入口点。
                sendMessage()
                Group类提供的消息发送方法，用于向该群组发送消息内容。支持多种消息类型参数，包括纯文本、消息链或单个消息元素。
                new At(event.getSender().getId())构造一个@消息元素：
                event.getSender() 获取消息发送者成员对象
                getId() 获取发送者的QQ号码
                new At() 创建针对该用户的@消息元素
                .plus("\n" + reply) 消息链构建方法：
                .plus() 将多个消息元素连接成消息链
                "\n" 添加换行符
                reply 包含机器人要回复的文本内容 */
                event.getGroup().sendMessage(
                        new At(event.getSender().getId()).plus("\n" + reply)
                );
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                event.getGroup().sendMessage("测试直接发送文本");
            }



        });


        new Thread(bot::join).start();
    }
}
