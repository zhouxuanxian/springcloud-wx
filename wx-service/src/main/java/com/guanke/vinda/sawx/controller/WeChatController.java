package com.guanke.vinda.sawx.controller;

import com.guanke.vinda.samodels.model.response.ObjectGeneralResponseEntity;
import com.guanke.vinda.sawx.biz.WeChatRequestBiz;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

/**
 * 微信接口调用
 *
 * @author Nicemorning
 */
@RestController
@RequestMapping("user")
public class WeChatController {
    private static final Logger LOGGER = LoggerFactory.getLogger(WeChatController.class);

    private final WeChatRequestBiz weChatRequestBiz;

    public WeChatController(WeChatRequestBiz weChatRequestBiz) {
        this.weChatRequestBiz = weChatRequestBiz;
    }

    @GetMapping("getUserInfoByCode")
    public ObjectGeneralResponseEntity getUserInfoByCode(String code) {
        return new ObjectGeneralResponseEntity.Builder().data(
                weChatRequestBiz.getUserIdByWeChatTemplateCode(code)
        ).build();
    }

    @GetMapping("getUserInfoByUserId")
    public ObjectGeneralResponseEntity getUserInfoByUserId(String userId) {
        return new ObjectGeneralResponseEntity.Builder().data(
                weChatRequestBiz.getUserInfoByUserId(userId)
        ).build();
    }
}
