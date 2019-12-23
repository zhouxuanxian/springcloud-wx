package com.guanke.vinda.sawx.mapper;

import com.guanke.vinda.samodels.model.entity.SaWeChatAccessTokenEntity;

/**
 * @author Nicemorning
 */
public interface SaWechatAccessTokenMapper {
    int deleteByPrimaryKey(String id);

    int insert(SaWeChatAccessTokenEntity record);

    int insertSelective(SaWeChatAccessTokenEntity record);

    SaWeChatAccessTokenEntity selectByPrimaryKey(String id);

    SaWeChatAccessTokenEntity selectLastOne();

    int updateByPrimaryKeySelective(SaWeChatAccessTokenEntity record);

    int updateByPrimaryKey(SaWeChatAccessTokenEntity record);
}