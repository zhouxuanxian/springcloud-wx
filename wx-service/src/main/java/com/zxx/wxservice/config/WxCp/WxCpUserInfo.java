package com.zxx.wxservice.config.WxCp;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import me.chanjar.weixin.cp.bean.Gender;

@Data
@ApiModel("企业微信基本信息")
public class WxCpUserInfo {

    @ApiModelProperty("微信唯一标识")
    private String openId;

    @ApiModelProperty("企业微信唯一标识")
    private String userId;

    @ApiModelProperty("会话密钥")
    private String sessionKey;

    @ApiModelProperty("名字")
    private String name;

    @ApiModelProperty("职位")
    private String position;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("手机号码")
    private String mobile;

    @ApiModelProperty("性别的英文")
    private String gender;

    @ApiModelProperty("状态")
    private Integer status;

    @ApiModelProperty("用户二维码")
    private String qrCode;

    @ApiModelProperty
    private String email;

}
