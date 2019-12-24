package com.zxx.wxservice.config.WxMa;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.WxMaInMemoryConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WxMaConfiguration {

    @Autowired
    private  WxMaProperties wxMaProperties;

    private static WxMaService wxMaService = new WxMaServiceImpl();

    @Bean
    public Object services() {

        WxMaInMemoryConfig wxMaInMemoryConfig = new WxMaInMemoryConfig();
        wxMaInMemoryConfig.setAppid(wxMaProperties.getAppid());
        wxMaInMemoryConfig.setSecret(wxMaProperties.getSecret());

        wxMaService.setWxMaConfig(wxMaInMemoryConfig);
        return Boolean.TRUE;
    }

    public static WxMaService getMaService() {
        return wxMaService;
    }
}
