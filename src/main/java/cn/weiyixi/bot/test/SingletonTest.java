package cn.weiyixi.bot.test;

import cn.hutool.core.io.FileUtil;
import net.mamoe.mirai.utils.AtomicInteger;

import java.io.File;

/**
 * @Author：weiyixi
 * @Package：cn.weiyixi.bot.test
 * @Project：weiyixi-bot
 * @name：单例模式测试
 * @Date：2025/8/11 1:57
 * @Filename：SingletonTest
 */
public class SingletonTest {

    public static AtomicInteger s = new AtomicInteger(0);
    static Object object = new Object();
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                String path = "D:/TemporaryData/Thread/"+s.getAndIncrement()+".txt";
                FileUtil.touch(path);
                for (int j = 0; j < 200; j++) {
                    for (int k = 0; k < 200; k++) {
                        FileUtil.appendString("你妈死了\t", path,"UTF-8" );
                    }
                    FileUtil.appendString("\n", path,"UTF-8" );
                }
            }).start();
        }
    }

}