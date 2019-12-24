package com.zxx.wxservice.config.WxMa;

import lombok.Data;

@Data
public class WxMaUserInfo {
    private String openId;
    private String userId;
    private String sessionKey;
}
