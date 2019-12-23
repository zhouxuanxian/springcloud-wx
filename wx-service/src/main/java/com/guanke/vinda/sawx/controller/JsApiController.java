package com.guanke.vinda.sawx.controller;

import com.guanke.vinda.samodels.model.response.ObjectGeneralResponseEntity;
import com.guanke.vinda.sawx.biz.WeChatRequestBiz;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 微信JS SDK相关接口
 *
 * @author Nicemorning
 */
@RestController
@RequestMapping("jssdk")
public class JsApiController {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsApiController.class);

    private final WeChatRequestBiz weChatRequestBiz;

    public JsApiController(WeChatRequestBiz weChatRequestBiz) {
        this.weChatRequestBiz = weChatRequestBiz;
    }

    @GetMapping("getJsApiSignature")
    public ObjectGeneralResponseEntity getJsApiSignature(String url) {
        return new ObjectGeneralResponseEntity.Builder().data(
                weChatRequestBiz.getJsApiSignature(url)
        ).build();
    }
}
