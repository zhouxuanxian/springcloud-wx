package com.guanke.vinda.sawx.utils;

import com.guanke.vinda.sawx.biz.WeChatRequestBiz;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class WeChatJsApiTicketHelper {

    private final static Logger LOGGER = LoggerFactory.getLogger(WeChatJsApiTicketHelper.class);

    private final RedisUtil redisUtil;

    private final WeChatRequestBiz weChatRequestBiz;

    private static WeChatJsApiTicketHelper weChatJsApiTicketHelper;

    public WeChatJsApiTicketHelper(RedisUtil redisUtil, WeChatRequestBiz weChatRequestBiz) {
        this.redisUtil = redisUtil;
        this.weChatRequestBiz = weChatRequestBiz;
    }


    @PostConstruct
    public void init() {
        weChatJsApiTicketHelper = this;
    }

    /**
     * 获取企业微信的jsapi_ticket
     *
     * @return 企业微信的jsapi-ticket
     */
    public static String getJsApiTicket() {
        return weChatJsApiTicketHelper.getJsApiTicketInner();
    }

    private String getJsApiTicketInner() {
        String signature = String.valueOf(redisUtil.get("apiTicket"));
        if (StringUtils.isEmpty(signature) || "null".equals(signature)) {
            LOGGER.info("Api ticket is expired. refresh it now.");
            weChatRequestBiz.storeJsApiTicket();
            signature = String.valueOf(redisUtil.get("apiTicket"));
        }
        return signature;
    }
}
