package com.zxx.wxservice.controller;


import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.zxx.wxservice.config.WxCp.WxCpConfiguration;
import com.zxx.wxservice.config.WxCp.WxCpUserInfo;
import com.zxx.wxservice.config.WxMa.WxMaConfiguration;
import com.zxx.wxservice.utils.JSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import me.chanjar.weixin.cp.api.WxCpUserService;
import me.chanjar.weixin.cp.bean.Gender;
import me.chanjar.weixin.cp.bean.WxCpUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api("企业微信接口")
@RestController
@RequestMapping("cp")
public class WxCpServiceController {

    final static Logger logger = LoggerFactory.getLogger(WxCpServiceController.class);

    @ApiOperation("根据code获取用户基本信息")
    @GetMapping("/getWxcode2SessionInfo")
    public JSONResult getWxcode2SessionInfo(@RequestParam(name = "code",required = true) @ApiParam("登录临时code") String code){
        WxMaJscode2SessionResult session = null;
        try {
            session = WxMaConfiguration.getMaService().jsCode2SessionInfo(code);
        } catch (Exception e) {
            logger.error("openId获取失败!" + e.toString());
            return JSONResult.errorException("openId获取失败!");
        }
        WxCpUserService wxCpUserService = WxCpConfiguration.getWxCpService().getUserService();
        String userId = null;
        if (session != null){
            try {
                userId = wxCpUserService.openid2UserId(session.getOpenid());
            }catch (Exception e) {
                logger.error("userId获取失败!" + e.toString());
                return JSONResult.errorException("userId获取失败!");
            }
        }
        WxCpUser wxCpUser = null;
        if (userId != null){
            try {
                wxCpUser = wxCpUserService.getById(userId);
            }catch (Exception e) {
                logger.error("userId获取失败!" + e.toString());
                return JSONResult.errorException("userId获取失败!");
            }
        }
        WxCpUserInfo wxCpUserInfo = new WxCpUserInfo();
        if (wxCpUser != null){

            wxCpUserInfo.setOpenId(session.getOpenid());
            wxCpUserInfo.setSessionKey(session.getSessionKey());
            wxCpUserInfo.setUserId(userId);
            wxCpUserInfo.setAvatar(wxCpUser.getAvatar());
            wxCpUserInfo.setGender(wxCpUser.getGender().getGenderName());
            wxCpUserInfo.setMobile(wxCpUser.getMobile());
            wxCpUserInfo.setName(wxCpUser.getName());
            wxCpUserInfo.setPosition(wxCpUser.getPosition());
            wxCpUserInfo.setQrCode(wxCpUser.getQrCode());
            wxCpUserInfo.setStatus(wxCpUser.getStatus());
            wxCpUserInfo.setEmail(wxCpUser.getEmail());
        }

        return JSONResult.ok(wxCpUserInfo);
    }


}
