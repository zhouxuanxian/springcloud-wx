package com.zxx.wxservice.config.controller;

import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.zxx.wxservice.config.WxCp.WxCpConfiguration;
import com.zxx.wxservice.config.WxCp.WxCpUserInfo;
import com.zxx.wxservice.config.WxMa.WxMaConfiguration;
import com.zxx.wxservice.utils.JSONResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("ma")
public class WxMaServiceController {

    final static Logger logger = LoggerFactory.getLogger(WxMaServiceController.class);

    public  final static String openid = "otaH74n6ij7cZpnsX-w4Y9rVds68";
    public  final static String sessionKey = "zljyQ0OcmcYgO9eJ+MlG3Q==";

    /**
     * 根据code获取 WxMaJscode2SessionResult 信息，如 openid、session_key
     *
     * @param code
     * @return openid 用户唯一标识
     * @return session_key 会话密钥
     */
    @GetMapping("/getWxMaJscode2Session")
    public JSONResult getWxMaJscode2Session(@RequestParam(name = "code",required = true) String code){
        WxMaJscode2SessionResult session = null;
        try {
            session = WxMaConfiguration.getMaService().jsCode2SessionInfo(code);
        } catch (Exception e) {
            logger.error("openId获取失败!" + e.toString());
            return JSONResult.errorException("openId获取失败!");
        }
        return JSONResult.ok(session);
    }

    @GetMapping("/getWxcode2SessionInfo")
    public JSONResult getWxcode2SessionInfo(@RequestParam(name = "code",required = true) String code){
        WxMaJscode2SessionResult session = null;
        try {
            session = WxMaConfiguration.getMaService().jsCode2SessionInfo(code);
        } catch (Exception e) {
            logger.error("openId获取失败!" + e.toString());
            return JSONResult.errorException("openId获取失败!");
        }
        String userId = null;
        if (session != null){
            try {
                userId = WxCpConfiguration.getWxCpService().getUserService().openid2UserId(session.getOpenid());
            }catch (Exception e) {
                logger.error("userId获取失败!" + e.toString());
                return JSONResult.errorException("userId获取失败!");
            }
        }
        WxCpUserInfo wxCpUserInfo = new WxCpUserInfo();
        wxCpUserInfo.setOpenId(session.getOpenid());
        wxCpUserInfo.setSessionKey(session.getSessionKey());
        wxCpUserInfo.setUserId(userId);
        return JSONResult.ok(wxCpUserInfo);
    }

}
