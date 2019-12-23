package com.guanke.vinda.sawx.biz;

import java.util.Map;

/**
 * 与微信服务端交互业务处理类
 *
 * @author Nicemorning
 */
public interface WeChatRequestBiz {

    /**
     * 获取并保存AccessToken到Redis中
     *
     * @return 返回最新的AccessToken
     */
    String storeAccessToken();

    /**
     * 通过企业微信的临时CODE获取当前登录用户的基本信息
     *
     * @param code 用户的临时登录CODE
     * @return 该用户的基本信息，如果获取失败将返回null
     */
    Map<String, String> getUserIdByWeChatTemplateCode(String code);

    /**
     * 通过用户的企业微信ID获取该用户的企业微信通讯录信息
     *
     * @param userId 用户的企业微信ID，即企业微信工号
     * @return 该用户的通讯录信息
     */
    Map<String, String> getUserInfoByUserId(String userId);

    /**
     * 获取企业的jsapi_ticket,<a href="https://work.weixin.qq.com/api/doc#10029/%E9%99%84%E5%BD%951-JS-SDK%E4%BD%BF%E7%94%A8%E6%9D%83%E9%99%90%E7%AD%BE%E5%90%8D%E7%AE%97%E6%B3%95">查看企业微信文档</a>
     *
     * @return 企业的jsapi_ticket
     */
    Map<String, Object> storeJsApiTicket();

    /**
     * 获取企业微信JS SDK的签名,<a href="https://work.weixin.qq.com/api/doc#10029/%E9%99%84%E5%BD%951-JS-SDK%E4%BD%BF%E7%94%A8%E6%9D%83%E9%99%90%E7%AD%BE%E5%90%8D%E7%AE%97%E6%B3%95">查看企业微信文档</a>
     *
     * @param url 页面URL
     * @return 企业微信JS SDK的签名
     */
    Map<String, Object> getJsApiSignature(String url);

    /**
     * 通过媒体ID获取微信临时服务器中的文件并上传至七牛云
     *
     * @param mediaId  媒体ID
     * @param fileName 指定文件名，如果不需要指定文件名则传入null
     * @return 返回文件在七牛云中的KEY列表
     */
    String upLoadToQiNiuYun(String mediaId, String fileName);

}
