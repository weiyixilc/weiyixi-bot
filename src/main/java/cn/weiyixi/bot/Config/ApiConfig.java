package cn.weiyixi.bot.Config;

import com.tc.apidoc.config.Config;
import com.tc.apidoc.model.ApiModelInfo;
import com.tc.apidoc.model.DocketInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class ApiConfig extends Config {


    protected final ApiModelInfo apiModelInfo = new ApiModelInfo("卫夷希机器人", "卫夷希机器人", "weiyixi", "1.0");

    @Bean
    public Docket createRestApiBaseServer() {
        DocketInfo docketInfo = new DocketInfo("dataSub", "cn.weiyixi.bot.Controller", "", "cn.weiyixi.bot.Entity");
        return createApi(apiModelInfo, docketInfo);
    }
}
