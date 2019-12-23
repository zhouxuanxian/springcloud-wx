package com.guanke.vinda.sawx.biz;

import java.util.Map;

public interface TencentMapRequestBiz {

    /**
     * 通过经纬度获取该位置名称
     *
     * @param latitude  纬度
     * @param longitude 经度
     * @return 返回微信的响应结果
     */
    Map<String, Object> getLocation(double latitude, double longitude);

    /**
     * 获取指定半径范围内的所有地点
     *
     * @param latitude  纬度
     * @param longitude 经度
     * @param radius    半径
     * @param pageSize  页容量
     * @param pageIndex 页码
     * @return 返回符合条件的地点
     */
    Map<String, Object> getLocationList(Double latitude,
                                        Double longitude,
                                        Integer radius,
                                        int pageSize,
                                        int pageIndex);
}
