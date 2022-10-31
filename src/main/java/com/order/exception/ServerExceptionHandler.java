package com.order.exception;


import com.alibaba.fastjson.JSON;
import com.order.constant.ResponseErrorEnum;
import com.order.response.HttpResponse;
import com.order.response.ServerException;
import com.order.util.http.RestResultGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author: wanqian
 * @date: 2020/10/19 20:37
 */
@RestControllerAdvice
public class ServerExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ServerExceptionHandler.class);

    @ExceptionHandler(value = ServerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public HttpResponse defaultErrorHandler(ServerException ex) throws Exception {
        if (ex.getResponse() != null) {
            logger.error("responseResult : {}", JSON.toJSONString(ex.getResponse()));
            return ex.getResponse();
        }

        return RestResultGenerator.genErrorResult(ex.getErrorEnum());

    }


    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public HttpResponse defaultErrorHandler(Exception ex) throws Exception {
        logger.error("errorHandler : {}", ex.getMessage());
        return RestResultGenerator.genErrorResult(ResponseErrorEnum.UNKOWN_ERROR);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public HttpResponse argumentNotNullError(MethodArgumentNotValidException ex) {
        return RestResultGenerator.genErrorResult(ResponseErrorEnum.PARAM_BLACK);
    }
}
