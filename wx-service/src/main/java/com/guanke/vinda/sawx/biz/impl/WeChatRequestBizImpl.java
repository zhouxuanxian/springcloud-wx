package com.guanke.vinda.sawx.biz.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.guanke.vinda.sawx.biz.WeChatRequestBiz;
import com.guanke.vinda.sawx.config.ParamsConfig;
import com.guanke.vinda.sawx.utils.*;
import okhttp3.*;
import okhttp3.Request.Builder;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;

/**
 * 与微信服务端交互业务处理类
 *
 * @author Nicemorning
 */
@Service
public class WeChatRequestBizImpl implements WeChatRequestBiz {
    private static final Logger LOGGER = LoggerFactory.getLogger(WeChatRequestBiz.class);

    private final ParamsConfig paramsConfig;

    private final RedisUtil redisUtil;

    public WeChatRequestBizImpl(ParamsConfig paramsConfig, RedisUtil redisUtil) {
        this.paramsConfig = paramsConfig;
        this.redisUtil = redisUtil;
    }

    /**
     * 获取并保存AccessToken到Redis中
     *
     * @return 返回最新的AccessToken
     */
    @Override
    public String storeAccessToken() {
        String accessToken = null;
        OkHttpClient client = new OkHttpClient();
        String params = "?corpid=" +
                paramsConfig.getCorpId() +
                "&corpsecret=" +
                paramsConfig.getSecret();
        Request request = new Builder().url("https://qyapi.weixin.qq.com/cgi-bin/gettoken" + params).build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                LOGGER.info("Request weChat for access token is failed: \n" + response.body());
            }
            String responseBody = Objects.requireNonNull(response.body()).string();
            JSONObject jsonObject = JSONObject.parseObject(responseBody);
            accessToken = String.valueOf(jsonObject.get("access_token"));
            if (accessToken != null && !("null".equals(accessToken))) {
                redisUtil.set("accessToken", accessToken,
                        Long.parseLong(String.valueOf(jsonObject.get("expires_in"))));
            }
        } catch (IOException e) {
            LOGGER.info("Request weChat for access token throw an exception: " + e.getMessage());
        }
        return accessToken;
    }

    /**
     * 通过企业微信的临时CODE获取当前登录用户的基本信息
     *
     * @param code 用户的临时登录CODE
     * @return 该用户的基本信息，如果获取失败将返回null
     */
    @Override
    public Map<String, String> getUserIdByWeChatTemplateCode(String code) {
        String params = "?access_token=" + WeChatAccessTokenHepler.getAccessToken() + "&code=" + code;
        OkHttpClient client = new OkHttpClient();
        Request request = new Builder().url("https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo" + params).build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                LOGGER.info("Get user id failed: \n" + response.body());
            }
            String responseBody = Objects.requireNonNull(response.body()).string();
            JSONObject jsonObject = JSONObject.parseObject(responseBody);
            String userId = String.valueOf(jsonObject.get("UserId"));
            if (userId != null && !("null".equals(userId))) {
                return this.getUserInfoByUserId(userId);
            } else {
                LOGGER.info("User did not follow this company`s weChat.");
                userId = this.getUserIdByOpenId(String.valueOf(jsonObject.get("OpenId")));
                return this.getUserInfoByUserId(userId);
            }
        } catch (IOException e) {
            LOGGER.info("Get user id throw an exception: " + e.getMessage());
        }
        return null;
    }

    /**
     * 通过用户的openId换取用户的userId
     *
     * @param openId 用户的openId
     * @return 用户的userId，如果获取失败，将返回null
     */
    private String getUserIdByOpenId(String openId) {
        String userId = null;
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),
                "{\"openid\": \"" + openId + "\"}");
        Request request = new Builder().url(
                "https://qyapi.weixin.qq.com/cgi-bin/user/convert_to_userid?access_token="
                        + WeChatAccessTokenHepler.getAccessToken()).post(body).build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                LOGGER.info("Get user id by open id failed: " + response.body());
            }
            String responseBody = Objects.requireNonNull(response.body()).string();
            JSONObject jsonObject = com.alibaba.fastjson.JSON.parseObject(responseBody);
            userId = String.valueOf(jsonObject.get("userid"));
        } catch (IOException e) {
            LOGGER.info("Get user id by open id throw an exception: " + e.getMessage());
        }
        return userId;
    }

    /**
     * 通过用户的企业微信ID获取该用户的企业微信通讯录信息
     *
     * @param userId 用户的企业微信ID，即企业微信工号
     * @return 该用户的通讯录信息
     */
    @Override
    public Map<String, String> getUserInfoByUserId(String userId) {
        Map<String, String> result = new HashMap<>();
        String params = "?access_token=" + WeChatAccessTokenHepler.getAccessToken() + "&userid=" + userId;
        OkHttpClient client = new OkHttpClient();
        Request request = new Builder().url("https://qyapi.weixin.qq.com/cgi-bin/user/get" + params).build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                LOGGER.info("Get user information failed: \n" + response.body());
            }
            String responseBody = Objects.requireNonNull(response.body()).string();
            JSONObject jsonObject = JSONObject.parseObject(responseBody);
            result.put("avatar", String.valueOf(jsonObject.get("avatar")));
            result.put("userId", String.valueOf(jsonObject.get("userid")));
            result.put("phone", String.valueOf(jsonObject.get("mobile")));
            result.put("name", String.valueOf(jsonObject.getString("name")));
        } catch (IOException e) {
            LOGGER.info("Get user information throw an exception: " + e.getMessage());
        }
        return result;
    }

    /**
     * 获取企业的jsapi_ticket,<a href="https://work.weixin.qq.com/api/doc#10029/%E9%99%84%E5%BD%951-JS-SDK%E4%BD%BF%E7%94%A8%E6%9D%83%E9%99%90%E7%AD%BE%E5%90%8D%E7%AE%97%E6%B3%95">查看企业微信文档</a>
     *
     * @return 企业的jsapi_ticket
     */
    @Override
    public Map<String, Object> storeJsApiTicket() {
        Map<String, Object> result = new HashMap<>();
        OkHttpClient client = new OkHttpClient();
        Request request = new Builder().url(
                "https://qyapi.weixin.qq.com/cgi-bin/get_jsapi_ticket?access_token=" +
                        WeChatAccessTokenHepler.getAccessToken()).build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                LOGGER.info("Get weChat js api ticket failed: " + response.body());
            }
            String responseBody = Objects.requireNonNull(response.body()).string();
            JSONObject jsonObject = JSON.parseObject(responseBody);
            if (0 == jsonObject.getInteger("errcode")) {
                result.put("ticket", jsonObject.getString("ticket"));
                result.put("expires_in", jsonObject.getInteger("expires_in"));
                redisUtil.set("apiTicket", jsonObject.getString("ticket"),
                        jsonObject.getInteger("expires_in"));
            } else {
                LOGGER.info("Get weChat js api ticket require an error, error code is: "
                        + jsonObject.getInteger("errcode"));
                return null;
            }
        } catch (IOException e) {
            LOGGER.info("Get weChat js api ticket throw an exception: " + e.getMessage());
            return null;
        }
        return result;
    }

    /**
     * 获取企业微信JS SDK的签名,<a href="https://work.weixin.qq.com/api/doc#10029/%E9%99%84%E5%BD%951-JS-SDK%E4%BD%BF%E7%94%A8%E6%9D%83%E9%99%90%E7%AD%BE%E5%90%8D%E7%AE%97%E6%B3%95">查看企业微信文档</a>
     *
     * @return 企业微信JS SDK的签名
     */
    @Override
    public Map<String, Object> getJsApiSignature(String url) {
        Map<String, Object> result = new HashMap<>();
        String apiTicket = WeChatJsApiTicketHelper.getJsApiTicket();
        if (StringUtils.isEmpty(apiTicket) || "null".equals(apiTicket)) {
            LOGGER.info("See logs.");
            return null;
        }
        String nonceString = RandomStringUtils.randomAlphanumeric(16);
        Long timestamp = System.currentTimeMillis() / 1000;
        String originalSignatrue = null;
        try {
            originalSignatrue = "jsapi_ticket=" + apiTicket +
                    "&noncestr=" + nonceString +
                    "&timestamp=" + timestamp +
                    "&url=" + URLDecoder.decode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        result.put("corpId", paramsConfig.getCorpId());
        result.put("signature", Sha1EncodeHelper.getSha1(originalSignatrue));
        result.put("nonce", nonceString);
        result.put("timestamp", timestamp);
        return result;
    }

    /**
     * 通过媒体ID获取微信临时服务器中的文件并上传至七牛云
     *
     * @param mediaId  媒体ID
     * @param fileName 指定文件名，如果不需要指定文件名则传入null
     * @return 返回文件在七牛云中的KEY列表
     */
    @Override
    public String upLoadToQiNiuYun(String mediaId, String fileName) {
        String key = "";
        String token = WeChatAccessTokenHepler.getAccessToken();
        String baseUrl = "https://qyapi.weixin.qq.com/cgi-bin/media/get?access_token=" + token + "&media_id=";
        try {
            URL url = new URL(baseUrl + mediaId);
            HttpURLConnection conn;
            conn = (HttpURLConnection) url.openConnection();
            //设置超时间为5秒
            conn.setConnectTimeout(5 * 1000);
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            InputStream inputStream = conn.getInputStream();
            key = QiniuSdkHelper.upLoadImage(inputStream, fileName);
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (IOException e) {
            LOGGER.info("Download weChat template file throw an exception: " + e.getMessage());
        }
        return key;
    }

}
