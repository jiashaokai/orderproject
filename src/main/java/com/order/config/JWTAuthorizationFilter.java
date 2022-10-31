package com.order.config;



import com.order.modle.entity.UserInfoDO;
import com.order.repository.UserInfoRepository;
import com.order.util.JwtTokenUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * @Author: jiakun
 * @Date: 2020/11/11 11:56
 * @Description
 */

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private UserInfoRepository userInfoRepository;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, UserInfoRepository userInfoRepository) {
        super(authenticationManager);
        this.userInfoRepository = userInfoRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String tokenHeader = request.getHeader(JwtTokenUtils.TOKEN_HEADER);
        // 如果请求头中没有Authorization信息则直接放行了
        if (tokenHeader == null || "".equals(tokenHeader) || "undefined".equals(tokenHeader)) {
            chain.doFilter(request, response);
            return;
        }
        String userName = null;
        try {
            userName = JwtTokenUtils.getUsername(tokenHeader);
        } catch (Exception ignored) {
            chain.doFilter(request, response);
            return;
        }
        Optional<UserInfoDO> userInfoDO = userInfoRepository.findByLoginName(userName);
        if (userInfoDO.isPresent()) {

            // 如果请求头中有token，则进行解析，并且设置认证信息
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userInfoDO.get(), null, userInfoDO.get().getAuthorities()));
            super.doFilterInternal(request, response, chain);
        }
    }
}
