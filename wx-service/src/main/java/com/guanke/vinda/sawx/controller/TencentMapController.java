package com.guanke.vinda.sawx.controller;

import com.guanke.vinda.samodels.model.response.ObjectGeneralResponseEntity;
import com.guanke.vinda.sawx.biz.TencentMapRequestBiz;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 腾讯地图API相关接口
 *
 * @author Nicemorning
 */
@RestController
@RequestMapping("tencentMap")
public class TencentMapController {
    private final TencentMapRequestBiz tencentMapRequestBiz;

    public TencentMapController(TencentMapRequestBiz tencentMapRequestBiz) {
        this.tencentMapRequestBiz = tencentMapRequestBiz;
    }

    @ApiOperation("获取定位地址")
    @GetMapping("getLocation")
    public ObjectGeneralResponseEntity getLocation(@RequestParam("latitude") @ApiParam("纬度") Double latitude,
                                                   @RequestParam("longitude") @ApiParam("经度") Double longitude) {
        return new ObjectGeneralResponseEntity.Builder().data(
                tencentMapRequestBiz.getLocation(latitude, longitude)
        ).build();
    }

    @ApiOperation("获取定位附近范围的地址")
    @GetMapping("getLocationList")
    public ObjectGeneralResponseEntity getLocationList(@RequestParam("latitude") @ApiParam("纬度") Double latitude,
                                                       @RequestParam("longitude") @ApiParam("经度") Double longitude,
                                                       @RequestParam("radius") @ApiParam("搜索半径") Integer radius,
                                                       @RequestParam("pageSize") @ApiParam("页容量") int pageSize,
                                                       @RequestParam("page") @ApiParam("页码") int page) {
        return new ObjectGeneralResponseEntity.Builder().data(
                tencentMapRequestBiz.getLocationList(latitude, longitude,
                        radius, pageSize, page)
        ).build();
    }

}
