package cn.weiyixi.bot.monitoring;
import cn.weiyixi.bot.Service.Impl.InvocationPyServiceImpl;
import com.tc.common.resp.RespInfo;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.contact.file.AbsoluteFile;
import net.mamoe.mirai.contact.file.RemoteFiles;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.internal.message.data.FileMessageImpl;
import net.mamoe.mirai.message.data.*;
import net.mamoe.mirai.utils.ExternalResource;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Author：weiyixi
 * @Package：cn.weiyixi.bot.monitoring
 * @Project：weiyixi-bot
 * @name：监听群组事件类
 * @Date：2025/8/7 14:52
 * @Filename：MonitoringGroupEvents
 */
@Slf4j
@Component
public class MonitoringGroupEvents {

    @Resource
    InvocationPyServiceImpl invocationPyServiceImpl;

    @Value("${packagePassword}")
    String packagePassword;


    /**
     * 监听群组事件
     */
    public void MonitoringGroup(){
        // 3. 注册群消息监听器
        GlobalEventChannel.INSTANCE.subscribeAlways(GroupMessageEvent.class, event -> {
            ExternalResource resource = null;
            try {
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
                    //构建回复消息
                    String reply = "";
                    if (!content.isEmpty()) {
                        //判断是不是禁漫号
                        RespInfo respInfo = invocationPyServiceImpl.IsJMNuber(content);
                        if("200".equals(respInfo.getContent().toString())){
                            //是jm号，调用下载
                            RespInfo downloadRespInfo = invocationPyServiceImpl.DownloadAndPackageJMComic(respInfo.getMessage());
                            if("200".equals(downloadRespInfo.getContent().toString())){
                                //成功
                                File file = new File(downloadRespInfo.getMessage());
                                resource  = ExternalResource.create(file);
                                AbsoluteFile absoluteFile = event.getGroup().getFiles().uploadNewFile("/" + respInfo.getMessage() + ".7z", resource);
                                log.info("发送状态："+absoluteFile.toString());
                                resource.close();
                                reply="下好了喵！解压密码是"+packagePassword;
                            }else{
                                reply=downloadRespInfo.getMessage();
                            }
                        }else if("222".equals(respInfo.getContent())){
                            //jm号错误
                            reply=respInfo.getMessage();
                        }else{
                            //其他消息
                            reply=respInfo.getMessage();
                        }
                    }
                    event.getGroup().sendMessage(new At(event.getSender().getId()).plus("\n"+reply));
                    //发送回复(包含@发送者)
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
                }
            }catch (Exception e){
                log.info(e.getMessage());
            }finally {
                try {
                    if(resource != null){
                        //释放资源
                        resource.close();
                    }
                } catch (IOException e) {
                    log.info(e.getMessage());
                }
            }

        });

    }
    
}
