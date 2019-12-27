package com.zxx.wxservice.mode.response;


import lombok.Getter;

/**
 * HTTP通用响应实体类，不分页数据请使用 <strong>ObjectGeneralResponseEntity</strong> 类
 * @see com.zxx.wxservice.mode.response.ObjectGeneralResponseEntity
 */
@Getter
public class ObjectGeneralResponseEntity {
    private final String msg;
    private final Integer code;
    private final Object data;

    /**
     * HTTP通用响应实体默认类，不分页数据请使用 <strong>ObjectGeneralResponseEntity</strong> 类<br/>
     * 如需更改参数请使用ObjectGeneralResponseEntity.Builder()<br/>
     * 默认返回参数为{msg="ok",code=200,data=null}
     *
     * @see package com.zxx.wxservice.mode.response.ObjectGeneralResponseEntity;
     */
    public ObjectGeneralResponseEntity() {
        this.msg = "ok";
        this.code = 200;
        this.data = null;
    }

    @Override
    public String toString() {
        return "Response{" +
                "msg='" + msg + '\'' +
                ", code=" + code +
                ", data=" + data +
                '}';
    }


    /**
      * @Author: zxx on 2019/12/27 16:35
      * @param weChatResponseEntity
      * @return ObjectGeneralResponseEntity
      * @Description 通用响应实体类
      */
    private ObjectGeneralResponseEntity(Builder weChatResponseEntity) {
        this.msg = weChatResponseEntity.msg;
        this.code = weChatResponseEntity.code;
        this.data = weChatResponseEntity.data;
    }

    /**
     * HTTP通用分页响应实体Builder，如不需要分页数据请使用 <strong>ObjectGeneralResponseEntity.Builder()</strong>
     *
     * @see com.zxx.wxservice.mode.response.ObjectGeneralResponseEntity
     */
    public static final class Builder {
        private String msg;
        private Integer code;
        private Object data;


        public Builder() {
            this.msg = "ok";
            this.code = 200;
            this.data = null;
        }

        public Builder msg(String msg) {
            this.msg = msg;
            return this;
        }

        public Builder code(Integer code) {
            this.code = code;
            return this;
        }

        public Builder data(Object data) {
            this.data = data;
            return this;
        }

        public ObjectGeneralResponseEntity build(){
            return new ObjectGeneralResponseEntity(this);
        }
    }
}
