package com.zxx.wxservice.controller;

import com.zxx.wxservice.mode.response.ObjectGeneralResponseEntity;
import com.zxx.wxservice.service.WxCpServiceLocal;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api("企业微信接口")
@RestController
@RequestMapping("cp")
public class WxCpServiceController {

    final static Logger logger = LoggerFactory.getLogger(WxCpServiceController.class);

    @Autowired
    private WxCpServiceLocal wxCpServiceLocal;

    @ApiOperation("根据code获取用户基本信息")
    @GetMapping("/getWxcode2SessionInfo")
    public ObjectGeneralResponseEntity getWxcode2SessionInfo(@RequestParam(name = "code",required = true) @ApiParam("登录临时code") String code){

        return new ObjectGeneralResponseEntity.Builder().data(
                wxCpServiceLocal.getUserInfoByCode(code)
        ).build();
    }


}
