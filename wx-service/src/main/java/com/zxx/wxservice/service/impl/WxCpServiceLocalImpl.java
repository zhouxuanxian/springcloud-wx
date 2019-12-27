package com.zxx.wxservice.service.impl;

import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.zxx.wxservice.config.WxCp.WxCpConfiguration;
import com.zxx.wxservice.config.WxCp.WxCpUserInfo;
import com.zxx.wxservice.config.WxMa.WxMaConfiguration;
import com.zxx.wxservice.service.WxCpServiceLocal;
import me.chanjar.weixin.cp.api.WxCpUserService;
import me.chanjar.weixin.cp.bean.WxCpUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @version v1.0
 * @ProjectName: springcloud-admin
 * @ClassName: WxCpServiceImpl
 * @Description: TODO
 * @Author: zxx
 * @Date: 2019/12/27 16:11
 */
@Service
public class WxCpServiceLocalImpl implements WxCpServiceLocal {

    final static Logger logger = LoggerFactory.getLogger(WxCpServiceLocalImpl.class);

    /**
     * @param code
     * @return Object
     * @Author: zxx on 2019/12/27 16:10
     * @Description
     */
    @Override
    public Object getUserInfoByCode(String code) {
        WxMaJscode2SessionResult session = null;
        try {
            session = WxMaConfiguration.getMaService().jsCode2SessionInfo(code);
        } catch (Exception e) {
            logger.error("openId获取失败!" + e.toString());
            return "openId获取失败!";
        }
        WxCpUserService wxCpUserService = WxCpConfiguration.getWxCpService().getUserService();
        String userId = null;
        if (session != null){
            try {
                userId = wxCpUserService.openid2UserId(session.getOpenid());
            }catch (Exception e) {
                logger.error("userId获取失败!" + e.toString());
                return "userId获取失败!";
            }
        }
        WxCpUser wxCpUser = null;
        if (userId != null){
            try {
                wxCpUser = wxCpUserService.getById(userId);
            }catch (Exception e) {
                logger.error("userId获取失败!" + e.toString());
                return "userId获取失败!";
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
        return wxCpUser;
    }
}
