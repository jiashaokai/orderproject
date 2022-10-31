package com.order.util.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.order.constant.ResponseErrorEnum;
import com.order.constant.ResponseSuccessEnum;
import com.order.response.HttpResponse;
import lombok.extern.slf4j.Slf4j;


/**
 * 生成Rest风格的结果
 */
@Slf4j
public class RestResultGenerator {

    public static <T> HttpResponse<T> genSuccessResult(T data, ResponseSuccessEnum successEnum) {
        HttpResponse<T> result = new HttpResponse<>();
        result.setInfo(successEnum).setData(data);
        log.debug("responseResult : {}", JSON.toJSONString(result));
        return result;
    }

    public static <T> HttpResponse<T> genSuccessResult(ResponseSuccessEnum successEnum) {
        HttpResponse<T> result = new HttpResponse<>();
        result.setInfo(successEnum);
        log.debug("responseResult : {}", JSON.toJSONString(result));
        return result;
    }


    public static <T> HttpResponse<T> genErrorResult(ResponseErrorEnum errorEnum) {
        HttpResponse<T> result = new HttpResponse<>();
        result.setInfo(errorEnum);
        log.debug("error: {}", JSON.toJSONString(result));
        return result;
    }

    public static <T> HttpResponse<T> genErrorResultMessage(String message) {
        HttpResponse<T> result = new HttpResponse<>();
        result.setCode(500);
        result.setMessage(message);
        return result;
    }

    /**
     * 用于数据推送
     *
     * @param data
     * @param successEnum
     * @param itemKey
     * @return
     */
    public static String genJsonSuccessResult(Object data, ResponseSuccessEnum successEnum, String itemKey) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", successEnum.code);
        jsonObject.put("msg", successEnum.name);
        jsonObject.put("itemKey", itemKey);
        jsonObject.put("data", data);
        log.debug("webSocketResult: {}", jsonObject.toJSONString());
        return jsonObject.toJSONString();
    }

    /**
     * 用于数据推送
     *
     * @param data
     * @param itemKey
     * @return
     */
    public static String genJsonSuccessResult(Object data, String itemKey) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("itemKey", itemKey);
        jsonObject.put("monitorData", data);
        log.debug("webSocketResult: {}", jsonObject.toJSONString());
        return jsonObject.toJSONString();
    }

    /**
     * 用于数据推送
     *
     * @param errorEnum
     * @param type
     * @return
     */
    public static String genJsonErrorResult(ResponseErrorEnum errorEnum, String type) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", errorEnum.code);
        jsonObject.put("type", type);
        jsonObject.put("msg", errorEnum.name);
        log.debug("webSocketError: {}", jsonObject.toJSONString());
        return jsonObject.toJSONString();
    }
}
