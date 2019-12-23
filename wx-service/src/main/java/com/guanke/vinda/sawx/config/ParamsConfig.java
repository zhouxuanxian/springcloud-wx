package com.guanke.vinda.sawx.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 微信配置内容读取
 *
 * @author Nicemorning
 */
@Component
@ConfigurationProperties(prefix = "wx")
@Data
public class ParamsConfig {
    private String corpId;
    private String agentId;
    private String secret;
}
