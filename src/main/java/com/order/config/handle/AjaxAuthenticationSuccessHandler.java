package com.order.config.handle;

import com.alibaba.fastjson.JSON;
import com.order.constant.RedisConstant;
import com.order.modle.LoginParam;
import com.order.modle.VO.TokenVO;
import com.order.response.HttpResponse;
import com.order.service.RedisService;
import com.order.util.JwtTokenUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: jiakun
 * @Date: 2020/10/27 11:50
 * @Description 登录成功
 */
@Component
public class AjaxAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private RedisService redisService;

    public AjaxAuthenticationSuccessHandler(RedisService redisService) {
        this.redisService = redisService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        HttpResponse responseBody = new HttpResponse();

        responseBody.setCode(200);
        LoginParam loginParam = (LoginParam) authentication.getPrincipal();
        String token = JwtTokenUtils.createToken(loginParam.getUserName(), loginParam.isRememberMe());
        responseBody.setData(new TokenVO(token));
        redisService.set(RedisConstant.TOKEN + token, String.valueOf(loginParam.getUserId()), loginParam.isRememberMe() ? 604800L : 3600L);
        responseBody.setMessage("登录成功");
        httpServletResponse.setContentType("application/json;charset=utf-8");
        httpServletResponse.getWriter().write(JSON.toJSONString(responseBody));
    }
}
