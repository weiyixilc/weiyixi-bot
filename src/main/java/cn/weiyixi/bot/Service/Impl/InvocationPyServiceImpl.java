package cn.weiyixi.bot.Service.Impl;

import cn.hutool.core.io.FileUtil;
import cn.weiyixi.bot.Service.InvocationPyService;
import com.tc.common.resp.RespInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Arrays;
import java.util.List;

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

    @Value("scriptPath")
    String scriptPath;

    @Value("comicPath")
    String comicPath;

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
        //清空文件夹
        delPathFile(scriptPath);

        // 1. 创建文件
        FileUtil.touch(comicPath);

        // 3. 准备多行数据
        List<String> lines = Arrays.asList(
                "这是第一行数据",
                "这是第二行数据",
                "这是第三行数据"
        );
        // 4. 追加多行内容
        //FileUtil.appendUtf8Lines(lines, filePath);
        System.out.println("数据写入完成");
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


    /**
     * 清空文件夹内所有文件
     * @return 是否成功
     */
    private void delPathFile(String path){
        // 判断文件夹是否存在
        if (!FileUtil.exist(path)) {
            System.out.println("文件夹不存在: " + path);
            return;
        }
        // 判断文件夹内是否有文件
        if (FileUtil.isNotEmpty(new File(path))) {
            System.out.println("文件夹包含文件，开始删除...");
            FileUtil.del(path);  // 完全删除文件夹及其所有内容
            System.out.println("文件删除完成");
        } else {
            System.out.println("文件夹为空，无需删除");
        }
    }


}
