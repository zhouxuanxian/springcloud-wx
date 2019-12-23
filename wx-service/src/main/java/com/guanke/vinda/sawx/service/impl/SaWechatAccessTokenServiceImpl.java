package com.guanke.vinda.sawx.service.impl;

import com.guanke.vinda.sawx.service.SaWechatAccessTokenService;
import com.guanke.vinda.samodels.model.entity.SaWeChatAccessTokenEntity;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import com.guanke.vinda.sawx.mapper.SaWechatAccessTokenMapper;

/**
 * @author Nicemorning
 */
@Service
public class SaWechatAccessTokenServiceImpl implements SaWechatAccessTokenService {

    @Resource
    private SaWechatAccessTokenMapper saWechatAccessTokenMapper;

    @Override
    public int deleteByPrimaryKey(String id) {
        return saWechatAccessTokenMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(SaWeChatAccessTokenEntity record) {
        return saWechatAccessTokenMapper.insert(record);
    }

    @Override
    public int insertSelective(SaWeChatAccessTokenEntity record) {
        return saWechatAccessTokenMapper.insertSelective(record);
    }

    @Override
    public SaWeChatAccessTokenEntity selectByPrimaryKey(String id) {
        return saWechatAccessTokenMapper.selectByPrimaryKey(id);
    }

    @Override
    public SaWeChatAccessTokenEntity selectLastOne() {
        return saWechatAccessTokenMapper.selectLastOne();
    }

    @Override
    public int updateByPrimaryKeySelective(SaWeChatAccessTokenEntity record) {
        return saWechatAccessTokenMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(SaWeChatAccessTokenEntity record) {
        return saWechatAccessTokenMapper.updateByPrimaryKey(record);
    }

}
