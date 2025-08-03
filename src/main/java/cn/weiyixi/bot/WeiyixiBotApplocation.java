package cn.weiyixi.bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @Author：weiyixi
 * @Package：cn.weiyixi.bot
 * @Project：weiyixi-bot
 * @name：WeiyixiBotApplocation
 * @Date：2025/8/3 23:39
 * @Filename：WeiyixiBotApplocation
 */

@EnableAsync
@SpringBootApplication(exclude = {FreeMarkerAutoConfiguration.class})
public class WeiyixiBotApplocation {
        public static void main(String[] args) {
        try {
            SpringApplication.run(WeiyixiBotApplocation.class, args);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
