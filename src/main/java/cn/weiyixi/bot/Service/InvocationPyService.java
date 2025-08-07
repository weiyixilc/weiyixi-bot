package cn.weiyixi.bot.Service;

import com.tc.common.resp.RespInfo;
import org.springframework.stereotype.Service;

/**
 * @Author：weiyixi
 * @Package：cn.weiyixi.bot.Service
 * @Project：weiyixi-bot
 * @name：InvocationPyService
 * @Date：2025/8/7 1:50
 * @Filename：InvocationPyService
 */

public interface InvocationPyService {


    /**
     * 判断值是否为禁漫号
     * @return 判断结果
     */
    RespInfo IsJMNuber(String message);


    /**
     * 下载和打包禁漫漫画
     * @return 打包结果
     */
    RespInfo DownloadAndPackageJMComic(String jmNuber);



}
