package cn.weiyixi.bot.Service.Impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ZipUtil;
import cn.weiyixi.bot.Service.InvocationPyService;
import com.tc.common.resp.RespInfo;
import lombok.extern.slf4j.Slf4j;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.EncryptionMethod;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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

    //py配置文件和脚本路径
    @Value("${scriptPath}")
    String scriptPath;

    //漫画下载路径
    @Value("${comicPath}")
    String comicPath;

    // 指定Python解释器的路径，例如在Windows上是"python.exe"，在Linux/Mac上是"python3"
    @Value("${pythonCommand}")
    String pythonCommand;

    @Value("${packagePassword}")
    String packagePassword;

    /**
     * 判断消息是否为禁漫号
     * @param message QQ群中获取到的消息
     * @return 结果
     */
    @Override
    public RespInfo IsJMNuber(String message) throws Exception {
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
    public RespInfo DownloadAndPackageJMComic(String jmNuber) throws Exception{
        //设置配置文件和.py脚本
        String comicNumPath = UpdateOption(jmNuber);//获取这个jm号的地址，压缩做准备
        //下载漫画
        Integer i = DownloadJMComic();
        if(i == 0){
            //成功，调用打包方法
            String PackagePath = PackageJMComic(comicNumPath);
            //返回压缩包地址
            return RespInfo.successResult("200",PackagePath);
        }else{
            //失败
            //清空文件夹
            delPathFile(comicPath);
            return RespInfo.successResult(999,"漫画下载失败，可能网络不好请稍后再试");
        }
    }


    /**
     * 修改调用python的配置文件
     * @param jmNuber jm号
     * @return 当前正要下载的本子路径
     */
    private String UpdateOption(String jmNuber) throws Exception{
        //清空文件夹
        delPathFile(comicPath);
        //创建临时漫画文件夹
        FileUtil.mkdir(comicPath+"/"+jmNuber);

        //写入python脚本需要调用的配置
        List<String> optionStr = Arrays.asList(
                "dir_rule:",
                "  base_dir: "+comicPath+"/"+jmNuber
        );
        //追加多行内容
        FileUtil.appendUtf8Lines(optionStr, scriptPath+"/option.yml");

        //写入要调用的python下载脚本
        List<String> downloadStr = Arrays.asList(
                "import sys",
                "import io",
                "import jmcomic",
                "sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding='utf-8')",
                "option = jmcomic.create_option_by_file('"+scriptPath+"/option.yml')",
                "jmcomic.download_album("+jmNuber+", option)"
        );
        //追加多行内容
        FileUtil.appendUtf8Lines(downloadStr, scriptPath+"/download.py");
        log.info("数据写入完成");
        return comicPath+"/"+jmNuber;
    }

    /**
     * 下载漫画
     * @return 返回的状态码 0成功   1失败
     */
    private Integer DownloadJMComic() {
        int exitCode = -1;
        try {
            // 指定Python脚本的路径
            String pyScriptPath = scriptPath+"/download.py";
            // 构建命令行命令
            ProcessBuilder builder = new ProcessBuilder(pythonCommand, pyScriptPath);
            Map<String,String> env = builder.environment();
            env.put("charset","UTF-8");
            builder.redirectErrorStream(true); // 将错误输出和标准输出合并
            // 启动进程
            Process process = builder.start();
            // 读取进程的输出（标准输出和错误输出）
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                log.info(line);
                //System.out.println(line);
            }
            // 等待进程结束
            exitCode = process.waitFor();
            log.info("Exited with code : " + exitCode);
        } catch (Exception e) {
            e.printStackTrace();
            return exitCode;
        }
        return exitCode;
    }


    /**
     * 打包本子
     * @param ComicPath 需要打包的本子路径
     * @return 打包后的压缩包全路径
     */
    private String PackageJMComic(String ComicPath){
        // 输出ZIP文件路径（需替换为实际路径）
        String zipPath = ComicPath+".zip";
        // 打包整个文件夹（包含子目录）
        File zipFile = ZipUtil.zip(ComicPath, zipPath,StandardCharsets.UTF_8, true);
        log.info("打包zip完成：" + zipFile.getAbsolutePath());
        //继续打包成7z包并添加密码
        //packagePassword
        // 配置压缩参数
        ZipParameters parameters = new ZipParameters();
        parameters.setEncryptFiles(true);
        parameters.setEncryptionMethod(EncryptionMethod.AES); // 使用AES加密
        try {
            // 创建加密压缩文件
            ZipFile seventZFile = new ZipFile(ComicPath+".7Z", packagePassword.toCharArray());
            seventZFile.addFile(new File(zipFile.getAbsolutePath()), parameters);
            log.info("打包7z完成：" + ComicPath+".7Z");
        } catch (Exception e) {
            log.info("7z加密压缩失败", e);
        }
        return ComicPath+".7Z";
    }


    /**
     * 清空文件夹内所有文件
     * @return 是否成功
     */
    private void delPathFile(String path){
        // 判断文件夹是否存在
        if (!FileUtil.exist(path)) {
            log.info("文件夹不存在: " + path);
            return;
        }
        // 判断文件夹内是否有文件
        if (FileUtil.isNotEmpty(new File(path))) {
            log.info("文件夹包含文件，开始删除...");
            FileUtil.del(path);  // 完全删除文件夹及其所有内容
            log.info("文件删除完成");
        } else {
            log.info("文件夹为空，无需删除");
        }
    }


}
