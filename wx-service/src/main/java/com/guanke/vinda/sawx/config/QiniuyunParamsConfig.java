package com.guanke.vinda.sawx.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 七牛云参数配置类
 *
 * @author Nicemorning
 */
@Component
@ConfigurationProperties(prefix = "qiniu")
@Data
public class QiniuyunParamsConfig {
    private String accessKey;
    private String secretKey;
    private String bucket;
    private String baseUrl;
}
