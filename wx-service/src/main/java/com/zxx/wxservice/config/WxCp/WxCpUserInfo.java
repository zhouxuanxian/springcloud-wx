package com.zxx.wxservice.config.WxCp;


import lombok.Data;

@Data
public class WxCpUserInfo {
    private String openId;
    private String userId;
    private String sessionKey;
}
