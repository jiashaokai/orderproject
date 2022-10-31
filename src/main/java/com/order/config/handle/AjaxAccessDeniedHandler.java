package com.order.config.handle;

import com.alibaba.fastjson.JSON;

import com.order.response.HttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: jiakun
 * @Date: 2020/10/27 12:05
 * @Description 无权访问
 */
@Component
public class AjaxAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        HttpResponse responseBody = new HttpResponse();

        responseBody.setCode(300);

        responseBody.setMessage("无访问权限");

        httpServletResponse.getWriter().write(JSON.toJSONString(responseBody));
    }
}
