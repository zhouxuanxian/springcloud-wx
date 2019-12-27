package com.zxx.wxservice.mode.response;

import lombok.Getter;

/**
 * HTTP通用分页响应实体类，如需要分页数据请使用 <strong>PageableTableGeneralResponseEntity</strong> 类
 *
 * @author Nicemorning
 * @see com.zxx.wxservice.mode.response.PageableTableGeneralResponseEntity
 */
@Getter
public class PageableTableGeneralResponseEntity {
    private final String msg;
    private final Integer code;
    private final Object data;
    private final Long total;

    /**
     * HTTP通用分页响应实体类，如不需要分页数据请使用 <strong>ObjectGeneralResponseEntity</strong> 类
     * 如需更改参数请使用ObjectGeneralResponseEntity.Builder()<br/>
     * 默认返回参数为{msg="ok",code=200,data=null}
     *
     * @see com.zxx.wxservice.mode.response.PageableTableGeneralResponseEntity
     */
    public PageableTableGeneralResponseEntity() {
        this.msg = "ok";
        this.code = 200;
        this.data = null;
        this.total = 0L;
    }

    @Override
    public String toString() {
        return "Response{" +
                "msg='" + msg + '\'' +
                ", code=" + code +
                ", data=" + data +
                ", total=" + total +
                '}';
    }

    /**
     * HTTP通用分页响应实体类，如需要分页数据请使用 <strong>PageableTableGeneralResponseEntity</strong> 类
     *
     * @see com.zxx.wxservice.mode.response.PageableTableGeneralResponseEntity
     */
    private PageableTableGeneralResponseEntity(Builder pageableTableGeneralResponseEntity) {
        this.msg = pageableTableGeneralResponseEntity.msg;
        this.code = pageableTableGeneralResponseEntity.code;
        this.data = pageableTableGeneralResponseEntity.data;
        this.total = pageableTableGeneralResponseEntity.total;
    }

    public static final class Builder {
        private String msg;
        private Integer code;
        private Object data;
        private Long total;

        /**
         * HTTP通用分页响应实体Builder，如分页数据请使用 <strong>PageableTableGeneralResponseEntity.Builder()</strong>
         *
         * @see com.zxx.wxservice.mode.response.PageableTableGeneralResponseEntity
         */
        public Builder() {
            this.msg = "ok";
            this.code = 200;
            this.data = null;
            this.total = 0L;
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

        public Builder total(Long total) {
            this.total = total;
            return this;
        }

        public PageableTableGeneralResponseEntity build() {
            return new PageableTableGeneralResponseEntity(this);
        }
    }
}
