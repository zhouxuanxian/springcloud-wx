package com.guanke.vinda.sawx.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.guanke.vinda.sawx.config.QiniuyunParamsConfig;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.InputStream;

/**
 * 七牛云SDK的工具类
 *
 * @author Nicemorning
 */
@Service
public class QiniuSdkHelper {
    private final static Logger LOGGER = LoggerFactory.getLogger(QiniuSdkHelper.class);

    private final QiniuyunParamsConfig qiniuyunParamsConfig;

    private static QiniuSdkHelper qiniuSdkHelper;

    public QiniuSdkHelper(QiniuyunParamsConfig qiniuyunParamsConfig) {
        this.qiniuyunParamsConfig = qiniuyunParamsConfig;
    }

    @PostConstruct
    public void init() {
        qiniuSdkHelper = this;
    }

    /**
     * 获取最新的AccessToken
     *
     * @return Access token
     */
    public static String getUpLoadToken() {
        return qiniuSdkHelper.getUpLoadTokenInner();
    }

    /**
     * 上传图片到七牛云
     *
     * @return 上传后的外链
     */
    public static String upLoadImage(InputStream uploadBytes, String key) {
        return qiniuSdkHelper.upLoadImageInner(uploadBytes, key);
    }

    private String getUpLoadTokenInner() {
        Auth auth = Auth.create(qiniuyunParamsConfig.getAccessKey(),
                qiniuyunParamsConfig.getSecretKey());
        return auth.uploadToken(qiniuyunParamsConfig.getBucket());
    }

    private String upLoadImageInner(InputStream uploadBytes, String key) {
        Configuration cfg = new Configuration(Region.region2());
        UploadManager uploadManager = new UploadManager(cfg);
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        try {
            Response response = uploadManager.put(uploadBytes, key, this.getUpLoadTokenInner(), null, "image/jpeg");
            JSONObject jsonObject = JSON.parseObject(response.bodyString());
            key = jsonObject.getString("key");
        } catch (QiniuException ex) {
            Response r = ex.response;
            LOGGER.info("QN upload image throw an exception: " + ex.getMessage() + "\n" +
                    "response: " + r.toString());
            try {
                LOGGER.info(r.bodyString());
            } catch (QiniuException ignored) {
            }
        }
        return key;
    }
}
