package com.order.config.handle;

import com.alibaba.fastjson.JSON;

import com.order.constant.RedisConstant;
import com.order.response.HttpResponse;
import com.order.service.RedisService;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: jiakun
 * @Date: 2020/10/27 12:06
 * @Description 注销
 */
@Component
public class AjaxLogoutSuccessHandler implements LogoutSuccessHandler {

    private RedisService redisService;

    public AjaxLogoutSuccessHandler(RedisService redisService) {
        this.redisService = redisService;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        HttpResponse responseBody = new HttpResponse();

        responseBody.setCode(100);
        String jwtToken = httpServletRequest.getHeader("token");

        try {
            redisService.del(RedisConstant.TOKEN + jwtToken);
        } catch (Exception ignored) {
        }

        responseBody.setMessage("注销成功");
        httpServletResponse.setContentType("application/json;charset=utf-8");
        httpServletResponse.getWriter().write(JSON.toJSONString(responseBody));
    }
}
