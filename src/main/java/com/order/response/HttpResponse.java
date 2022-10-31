package com.order.response;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;

import com.order.constant.ResponseErrorEnum;
import com.order.constant.ResponseSuccessEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: wanqian
 * @Date: 2020/10/13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HttpResponse<T> {

    @JSONField(serialzeFeatures = SerializerFeature.NotWriteDefaultValue)
    private Integer code;

    private String message;

    private T data;

    public HttpResponse setInfo(ResponseErrorEnum errorEnum) {
        this.code = errorEnum.code;
        this.message = errorEnum.name;
        return this;
    }

    public HttpResponse setInfo(ResponseSuccessEnum successEnum) {
        this.code = successEnum.code;
        this.message = successEnum.name;
        return this;
    }
}
