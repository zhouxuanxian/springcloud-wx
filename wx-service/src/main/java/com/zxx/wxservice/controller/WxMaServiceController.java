package com.zxx.wxservice.controller;

import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.zxx.wxservice.config.WxMa.WxMaConfiguration;
import com.zxx.wxservice.mode.response.ObjectGeneralResponseEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@Api("微信接口")
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
    @ApiOperation("根据code获取WxMaJscode2SessionResult信息")
    @GetMapping("/getWxMaJscode2Session")
    public ObjectGeneralResponseEntity getWxMaJscode2Session(@RequestParam(name = "code",required = true) @ApiParam("登录临时code") String code){
        WxMaJscode2SessionResult session = null;
        try {
            session = WxMaConfiguration.getMaService().jsCode2SessionInfo(code);
        } catch (Exception e) {
            logger.error("openId获取失败!" + e.toString());
            return new ObjectGeneralResponseEntity.Builder().data("openId获取失败!").build();
        }
        return  new ObjectGeneralResponseEntity.Builder().data(session).build();
    }



}
