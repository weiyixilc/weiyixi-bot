package cn.weiyixi.bot.test;

/**
 * @Author：weiyixi
 * @Package：cn.weiyixi.bot.test
 * @Project：weiyixi-bot
 * @name：单例模式测试
 * @Date：2025/8/11 1:57
 * @Filename：SingletonTest
 */
public class SingletonTest {

    private static volatile SingletonTest instance;

    private SingletonTest() {}

    public static SingletonTest getInstance() {
        if (instance == null) {
            synchronized (SingletonTest.class) {
                if (instance == null) {
                    instance = new SingletonTest();
                }
            }
        }
        return instance;
    }

}