package org.tinycloud.tinyurl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.tinycloud.tinyurl.common.utils.LocalHostUtils;

@Slf4j
@SpringBootApplication(scanBasePackages = {"org.tinycloud.tinyurl"})
public class TinyUrlApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext application = SpringApplication.run(TinyUrlApplication.class, args);
        Environment env = application.getEnvironment();
        var ip = LocalHostUtils.getLocalHost();
        var port = env.getProperty("server.port");
        var banner = "\n----------------------------------------------------------\n\t" +
                "tiny-url 启动成功！\n\t" +
                "┌─┐┬ ┬┌─┐┌─┐┌─┐┌─┐┌─┐  ┌─┐┌┬┐┌─┐┬─┐┌┬┐┌─┐┌┬┐   ┬\n\t" +
                "└─┐│ ││  │  ├┤ └─┐└─┐  └─┐ │ ├─┤├┬┘ │ ├┤  ││   │\n\t" +
                "└─┘└─┘└─┘└─┘└─┘└─┘└─┘  └─┘ ┴ ┴ ┴┴└─ ┴ └─┘─┴┘   o\n\t" +
                "Access URLs:\n\t" +
                "Local: \t\thttp://localhost:" + port + "/\n\t" +
                "External: \thttp://" + ip + ":" + port + "/\n\t" +
                "Swagger-UI: http://" + ip + ":" + port + "/doc.html\n" +
                "-------------------------------------------------------------------------";
        log.info(banner);
    }

}
