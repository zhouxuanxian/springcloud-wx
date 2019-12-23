package com.guanke.vinda.sawx.controller;

import com.guanke.vinda.samodels.model.response.ObjectGeneralResponseEntity;
import com.guanke.vinda.sawx.biz.WeChatRequestBiz;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 微信图片上传下载接口
 *
 * @author Nicemorning
 */
@RestController
@RequestMapping("image")
public class ImageUpAndDownloadsController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ImageUpAndDownloadsController.class);

    private final WeChatRequestBiz weChatRequestBiz;

    public ImageUpAndDownloadsController(WeChatRequestBiz weChatRequestBiz) {
        this.weChatRequestBiz = weChatRequestBiz;
    }

    @GetMapping("upLoadToQiNiuYun")
    public ObjectGeneralResponseEntity upLoadToQiNiuYun(String serverIds) {
        return new ObjectGeneralResponseEntity.Builder().data(
                weChatRequestBiz.upLoadToQiNiuYun(serverIds, null)
        ).build();
    }

    @GetMapping("upLoadToQiNiuYunRename")
    public ObjectGeneralResponseEntity upLoadToQiNiuYunRename(String serverIds, String fileName) {
        return new ObjectGeneralResponseEntity.Builder().data(
                weChatRequestBiz.upLoadToQiNiuYun(serverIds, fileName)
        ).build();
    }
}
