package com.zxx.wxservice.config.WxCp;

import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.api.impl.WxCpServiceImpl;
import me.chanjar.weixin.cp.config.WxCpInMemoryConfigStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WxCpConfiguration {

    @Autowired
    private WxCpProperties wxCpProperties;

    private static WxCpService wxCpService = new WxCpServiceImpl();

    @Bean
    public void initCpServices() {

        WxCpInMemoryConfigStorage configStorage = new WxCpInMemoryConfigStorage();
        configStorage.setCorpId(wxCpProperties.getCorpId());
        configStorage.setCorpSecret(wxCpProperties.getSecret());
        configStorage.setAgentId(wxCpProperties.getAgentId());
        wxCpService.setWxCpConfigStorage(configStorage);
 
    }

    public static WxCpService getWxCpService(){
        return wxCpService;
    }

}
