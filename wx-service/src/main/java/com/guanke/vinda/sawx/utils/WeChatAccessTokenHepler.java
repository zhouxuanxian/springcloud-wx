package com.guanke.vinda.sawx.utils;

import com.alibaba.fastjson.JSONObject;
import com.guanke.vinda.samodels.model.entity.SaWeChatAccessTokenEntity;
import com.guanke.vinda.samodels.model.utils.UUIDUtils;
import com.guanke.vinda.sawx.biz.WeChatRequestBiz;
import com.guanke.vinda.sawx.config.ParamsConfig;
import com.guanke.vinda.sawx.service.SaWechatAccessTokenService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * 微信AccessToken的工具类
 *
 * @author Nicemorning
 */
@Service
public class WeChatAccessTokenHepler {
    private final static Logger LOGGER = LoggerFactory.getLogger(WeChatAccessTokenHepler.class);

    private final RedisUtil redisUtil;

    private final WeChatRequestBiz weChatRequestBiz;

    private static WeChatAccessTokenHepler weChatAccessTokenHepler;

    public WeChatAccessTokenHepler(RedisUtil redisUtil,
                                   WeChatRequestBiz weChatRequestBiz) {
        this.redisUtil = redisUtil;
        this.weChatRequestBiz = weChatRequestBiz;
    }

    @PostConstruct
    public void init() {
        weChatAccessTokenHepler = this;
    }

    /**
     * 获取最新的AccessToken
     *
     * @return Access token
     */
    public static String getAccessToken() {
        return weChatAccessTokenHepler.getAccessTokenInner();
    }

    private String getAccessTokenInner() {
        String accessToken = String.valueOf(redisUtil.get("accessToken"));
        if (accessToken == null || "null".equals(accessToken)) {
            LOGGER.info("Access token is expired, refresh token now.");
            accessToken = this.refreshAccessToken();
        }
        return accessToken;
    }

    private String refreshAccessToken() {
        return this.weChatRequestBiz.storeAccessToken();
    }
}
