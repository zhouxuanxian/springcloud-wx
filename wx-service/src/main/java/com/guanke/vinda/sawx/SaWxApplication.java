package com.guanke.vinda.sawx;

import lombok.extern.log4j.Log4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author Nicemorning
 */
@Log4j
@SpringBootApplication(scanBasePackages = "com.guanke.vinda.sawx.*")
@MapperScan("com.guanke.vinda.sawx.mapper")
@EnableEurekaClient
public class SaWxApplication {

    public static void main(String[] args) {
        SpringApplication.run(SaWxApplication.class, args);
    }

}
