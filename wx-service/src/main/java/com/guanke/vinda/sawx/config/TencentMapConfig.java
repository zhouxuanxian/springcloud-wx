package com.guanke.vinda.sawx.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "tencent")
@Data
public class TencentMapConfig {
    private String mapKey;
    private String secretKey;

}
