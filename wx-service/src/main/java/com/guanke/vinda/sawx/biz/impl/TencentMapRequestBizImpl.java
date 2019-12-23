package com.guanke.vinda.sawx.biz.impl;

import com.alibaba.fastjson.JSONObject;
import com.guanke.vinda.samodels.model.utils.MD5Util;
import com.guanke.vinda.samodels.model.utils.MapUtil;
import com.guanke.vinda.sawx.biz.TencentMapRequestBiz;
import com.guanke.vinda.sawx.config.TencentMapConfig;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class TencentMapRequestBizImpl implements TencentMapRequestBiz {
    private static final Logger LOGGER = LoggerFactory.getLogger(TencentMapRequestBizImpl.class);

    private final TencentMapConfig tencentMapConfig;

    public TencentMapRequestBizImpl(TencentMapConfig tencentMapConfig) {
        this.tencentMapConfig = tencentMapConfig;
    }

    /**
     * 获取加密后的SIG请求URL
     *
     * @param path   请求路径
     * @param params 请求参数
     * @return 符合要求的SIG请求URL
     */
    private String getRequestUrl(String baseUrl, String path, List<String> params) {
        params.add("key=" + tencentMapConfig.getMapKey());
        params.sort(String::compareTo);
        return baseUrl + path + "?"
                + StringUtils.join(params.toArray(), "&")
                + "&sig=" + MD5Util.md5(path + "?"
                + StringUtils.join(params.toArray(), '&')
                + tencentMapConfig.getSecretKey());
    }

    /**
     * 通过经纬度获取该位置名称
     *
     * @param latitude  纬度
     * @param longitude 经度
     * @return 返回微信的响应结果
     */
    @Override
    public Map<String, Object> getLocation(double latitude, double longitude) {
        Map<String, Object> result = new HashMap<>();
        String baseUrl = "https://apis.map.qq.com";
        String path = "/ws/geocoder/v1";
        List<String> params = new ArrayList<>();
        params.add("location=" + latitude + "," + longitude);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(this.getRequestUrl(baseUrl, path, params)).build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                LOGGER.info("Request to tencent map to get location failed: \n" + response.body());
            }
            String responseBody = Objects.requireNonNull(response.body()).string();
            JSONObject jsonObject = JSONObject.parseObject(responseBody);
            if (jsonObject.getInteger("status") != 0) {
                LOGGER.info("Request to tencent map failed, code: " + jsonObject.getInteger("status"));
                return null;
            }
            Map<String, Object> mapObject = MapUtil.objectToMap(jsonObject.get("result"));
            Map<String, Object> map = (Map<String, Object>) mapObject.get("map");
            result.put("address", map.get("address"));
            result.put("addressDetail", map.get("ad_info"));
        } catch (IOException e) {
            LOGGER.info("Request to tencent map to get location throw an exception: " + e.getMessage());
        }
        return result;
    }

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
    @Override
    public Map<String, Object> getLocationList(Double latitude, Double longitude, Integer radius, int pageSize, int pageIndex) {
        Map<String, Object> result = new HashMap<>();
        String baseUrl = "https://apis.map.qq.com";
        String path = "/ws/geocoder/v1";
        List<String> params = new ArrayList<>();
        params.add("location=" + latitude + "," + longitude);
        params.add("get_poi=1");
        params.add("poi_options=radius=" + radius);
        params.add("page_size=" + pageSize);
        params.add("page_index=" + pageIndex);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(this.getRequestUrl(baseUrl, path, params)).build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                LOGGER.info("Request to tencent map to get location failed: \n" + response.body());
            }
            String responseBody = Objects.requireNonNull(response.body()).string();
            JSONObject jsonObject = JSONObject.parseObject(responseBody);
            if (jsonObject.getInteger("status") != 0) {
                LOGGER.info("Request to tencent map failed, code: " + jsonObject.getInteger("status"));
                return null;
            }
            Map<String, Object> mapObject = MapUtil.objectToMap(jsonObject.get("result"));
            Map<String, Object> map = (Map<String, Object>) mapObject.get("map");
            result.put("address", map.get("address"));
            result.put("location", map.get("location"));
            result.put("addressList", map.get("pois"));
        } catch (IOException e) {
            LOGGER.info("Request to tencent map to get location throw an exception: " + e.getMessage());
        }
        return result;
    }
}
