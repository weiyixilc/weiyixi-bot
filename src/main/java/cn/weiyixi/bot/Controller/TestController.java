package cn.weiyixi.bot.Controller;

import com.tc.common.resp.RespInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @Author：weiyixi
 * @Package：cn.weiyixi.bot.Controller
 * @Project：weiyixi-bot
 * @name：TestController
 * @Date：2025/8/3 23:47
 * @Filename：TestController
 */
@Api(tags = "测试")
@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {


    @ApiOperation(value = "测试调用python脚本", notes = "测试调用python脚本", httpMethod = "POST")
    @PostMapping(value = "/testPy")
    public RespInfo InvocationPy (){
        int exitCode = -1;
        try {
            // 指定Python解释器的路径，例如在Windows上是"python.exe"，在Linux/Mac上是"python3"
            String pythonCommand = "D:/python/python.exe";
            // 指定Python脚本的路径
            String scriptPath = "D:/java/Python/code/test.py";

            // 构建命令行命令
            ProcessBuilder builder = new ProcessBuilder(pythonCommand, scriptPath);
            Map<String,String> env = builder.environment();
            env.put("charset","UTF-8");
            builder.redirectErrorStream(true); // 将错误输出和标准输出合并

            // 启动进程
            Process process = builder.start();

            // 读取进程的输出（标准输出和错误输出）
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            // 等待进程结束
            exitCode = process.waitFor();
            System.out.println("Exited with code : " + exitCode);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return RespInfo.successResult(200,exitCode);
    }

}
