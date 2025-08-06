package cn.weiyixi.bot.Service.Impl;

import cn.weiyixi.bot.Service.InvocationPyService;
import com.tc.common.resp.RespInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Author：weiyixi
 * @Package：cn.weiyixi.bot.Service.Impl
 * @Project：weiyixi-bot
 * @name：InvocationPyServiceImpl
 * @Date：2025/8/7 2:10
 * @Filename：InvocationPyServiceImpl
 */
@Slf4j
@Service
public class InvocationPyServiceImpl implements InvocationPyService {


    /**
     * 判断消息是否为禁漫号
     * @param message QQ群中获取到的消息
     * @return 结果
     */
    @Override
    public RespInfo IsJMNuber(String message) {
        if (message == null || message.isEmpty()) {
            return RespInfo.successResult(888,"无");
        }
        String[] jmSmall = message.split("jm");

        if(jmSmall.length <= 1){
            //再次判断是否为大写
            String[] jmBig = message.split("JM");
            if(jmBig.length <= 1){
                //返回
                return RespInfo.successResult(888,"无");
            }else{
                //执行逻辑
                //正则判断下标为1的是否位数字
                boolean matches = jmBig[1].matches("\\d+");
                if(matches){
                    //全数字则是jm号，返回jm号
                    return RespInfo.successResult(200,jmBig[1]);
                }else{
                    //不是禁漫号，提醒他
                    return RespInfo.successResult(222,"你给的不是jm号哦");
                }
            }
        }else{
            //执行逻辑
            //正则判断下标为1的是否位数字
            boolean matches = jmSmall[1].matches("\\d+");
            if(matches){
                //全数字则是jm号，返回jm号
                return RespInfo.successResult(200,jmSmall[1]);
            }else{
                //不是禁漫号，提醒他
                return RespInfo.successResult(222,"你给的不是jm号哦");
            }
        }
    }


    /**
     * 下载并打包JM图片
     * @param jmNuber jm号
     * @return 压缩包信息或者失败信息
     */
    @Override
    public RespInfo DownloadAndPackageJMComic(String jmNuber) {


        return null;
    }


    /**
     * 修改调用python的配置文件
     * @param jmNuber jm号
     * @return 当前正要下载的本子路径
     */
    private String UpdateOption(String jmNuber){

        return null;
    }

    /**
     * 下载漫画
     * @param jmNuber jm号
     * @return 返回的状态码
     */
    private Integer DownloadJMComic(String jmNuber) {

        return null;
    }


    /**
     * 打包本子
     * @param ComicPath 需要打包的本子路径
     * @return 打包后的压缩包全路径
     */
    private String PackageJMComic(String ComicPath){

        return null;
    }


}
