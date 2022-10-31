package com.order.config;


import com.fasterxml.jackson.databind.ObjectMapper;

import com.order.modle.LoginParam;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: jiakun
 * @Date: 2020/10/27 12:08
 * @Description 登录过滤，请求登录接口后进入attemptAuthentication方法，登录成功执行successfulAuthentication方法，登录失败执行unsuccessfulAuthentication方法。
 */
public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {

    private boolean postOnly = true;

    public JwtLoginFilter() {
        super.setFilterProcessesUrl("/api/v1/auth/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse httpServletResponse) throws AuthenticationException {
        try {
            // 1. 必须为POST请求
            if (postOnly && !request.getMethod().equals("POST")) {
                throw new AuthenticationServiceException(
                        "Authentication method not supported: " + request.getMethod());
            }
            LoginParam loginParam = new ObjectMapper().readValue(request.getInputStream(), LoginParam.class);
            //2.取出用户填写的用户名和密码
            String username = loginParam.getUserName();
            String password = loginParam.getPassword();
            //3.防止出现空指针
            if (username == null) {
                username = "";
            }

            if (password == null) {
                password = "";
            }
            //4.去掉用户名的空格
            username = username.trim();
            //5.在层层校验后，开始对username和password进行封装
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                    loginParam, password);

            // Allow subclasses to set the "details" property
            setDetails(request, authRequest);
            // 6.认证逻辑
            return this.getAuthenticationManager().authenticate(authRequest);
        } catch (IOException e) {
            return null;
        }
    }
}
