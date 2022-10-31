package com.order.config;


import com.order.config.handle.*;

import com.order.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @Author: jiakun
 * @Date: 2020/10/24 16:18
 * @Description TODO
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyAuthenticationProvider myAuthenticationProvider;

    @Autowired
    private AjaxLogoutSuccessHandler ajaxLogoutSuccessHandler;

    @Autowired
    private AjaxAuthenticationSuccessHandler ajaxAuthenticationSuccessHandler;

    @Autowired
    private AjaxAccessDeniedHandler ajaxAccessDeniedHandler;

    @Autowired
    private AjaxAuthenticationEntryPoint ajaxAuthenticationEntryPoint;

    @Autowired
    private AjaxAuthenticationFailureHandler ajaxAuthenticationFailureHandler;

    @Autowired
    private UrlFilterInvocationSecurityMetadataSource urlFilterInvocationSecurityMetadataSource;

    @Autowired
    private MyAccessDecisionManager myAccessDecisionManager;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeRequests()
                .antMatchers("/api/v1/auth/user/**").authenticated()
                .antMatchers("/api/v1/auth/validate").permitAll()
                .antMatchers("/api/v1/auth/publicKey").permitAll()
                .antMatchers("/api/v1/auth/checkToken").permitAll()

                .and()
                //登录后,访问没有权限处理类
                .exceptionHandling().accessDeniedHandler(ajaxAccessDeniedHandler)
                //匿名访问,没有权限的处理类
                .authenticationEntryPoint(ajaxAuthenticationEntryPoint)


//                .and()
//                .formLogin()  //开启登录
//                .successHandler(ajaxAuthenticationSuccessHandler) // 登录成功
//                .failureHandler(ajaxAuthenticationFailureHandler) // 登录失败
//                .permitAll()

                .and()
                .logout()
                .logoutUrl("/api/v1/auth/logout")
                .logoutSuccessHandler(ajaxLogoutSuccessHandler)
                .permitAll()

                .and()
                .addFilter(new JWTAuthorizationFilter(authenticationManager(), userInfoRepository));

        http.addFilterAt(jwtLoginFilter(), UsernamePasswordAuthenticationFilter.class); // 无权访问 JSON 格式的数据
    }

    @Bean
    JwtLoginFilter jwtLoginFilter() {
        ProviderManager providerManager = new ProviderManager(myAuthenticationProvider);

        JwtLoginFilter jwtLoginFilter = new JwtLoginFilter();
        jwtLoginFilter.setAuthenticationManager(providerManager);
        jwtLoginFilter.setAuthenticationSuccessHandler(ajaxAuthenticationSuccessHandler);
        jwtLoginFilter.setAuthenticationFailureHandler(ajaxAuthenticationFailureHandler);
        return jwtLoginFilter;
    }
}
