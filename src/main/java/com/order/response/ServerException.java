package com.order.response;


import com.order.constant.ResponseErrorEnum;

/**
 * @author: wanqian
 * @date: 2020/10/19 20:34
 */
public class ServerException extends Exception {

    private static final long serialVersionUID = 1L;

    public ResponseErrorEnum errorEnum;

    private HttpResponse response;


    public ServerException(ResponseErrorEnum errorEnum) {
        this.errorEnum = errorEnum;
    }

    public ServerException(String msg) {
        HttpResponse httpResponse = new HttpResponse();
        httpResponse.setMessage(msg);
        httpResponse.setCode(500);
        this.response = httpResponse;
    }

    public HttpResponse getResponse() {
        return response;
    }

    public void setResponse(HttpResponse response) {
        this.response = response;
    }

    public ResponseErrorEnum getErrorEnum() {
        return errorEnum;
    }

    public void setErrorEnum(ResponseErrorEnum errorEnum) {
        this.errorEnum = errorEnum;
    }
}
