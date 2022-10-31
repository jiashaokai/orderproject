package com.order.config;


import com.order.constant.RedisConstant;
import com.order.modle.LoginParam;
import com.order.modle.entity.UserInfoDO;
import com.order.repository.UserInfoRepository;
import com.order.service.RedisService;
import com.order.util.RSAUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @Author: jiakun
 * @Date: 2020/10/24 16:20
 * @Description SpringSecurity的自定义用户密码验证
 */
@Component
public class MyAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private RedisService redisService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Object principal = authentication.getPrincipal();
        LoginParam loginParam = null;
        if (principal instanceof LoginParam) {
            loginParam = (LoginParam) principal;
        }
        if (loginParam == null) {
            throw new BadCredentialsException("请先登录！");
        }
        String username = loginParam.getUserName();
        String presentedPassword = loginParam.getPassword();
        // 根据用户名获取用户信息
        Optional<UserInfoDO> optional = this.userInfoRepository.findByLoginName(username);
        if (!optional.isPresent()) {
            throw new BadCredentialsException("用户名不存在");
        }
        UserInfoDO userInfoDO = optional.get();
        //密码错误次数限制
        Object object = redisService.get(RedisConstant.USER_PASSWORD_ERROR + userInfoDO.getUserId());
        if (object != null){
            Integer num = Integer.valueOf(object.toString());
            if (num >= 3){
                throw new BadCredentialsException("密码连续3次输入错误,锁定5分钟");
            }
        }
        loginParam.setUserId(userInfoDO.getUserId());
        // 自定义的加密规则，用户名、输的密码和数据库保存的盐值进行加密
        String encodedPassword = RSAUtil.decrypt(presentedPassword);
        if (authentication.getCredentials() == null) {
            if (object == null){
                redisService.set(RedisConstant.USER_PASSWORD_ERROR + userInfoDO.getUserId(),1,300);
            }else {
                Integer num = Integer.valueOf(object.toString());
                redisService.set(RedisConstant.USER_PASSWORD_ERROR + userInfoDO.getUserId(),num + 1,300);
            }
            throw new BadCredentialsException("登录名或密码错误");
        } else if (!encodedPassword.equals(RSAUtil.decrypt(userInfoDO.getPassword()))) {
            if (object == null){
                redisService.set(RedisConstant.USER_PASSWORD_ERROR + userInfoDO.getUserId(),1,300);
            }else {
                Integer num = Integer.valueOf(object.toString());
                redisService.set(RedisConstant.USER_PASSWORD_ERROR + userInfoDO.getUserId(),num + 1,300);
            }
            throw new BadCredentialsException("登录名或密码错误");
        } else {
            redisService.del(RedisConstant.USER_PASSWORD_ERROR + userInfoDO.getUserId());
            UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(loginParam, userInfoDO.getPassword(), userInfoDO.getAuthorities());
            result.setDetails(authentication.getDetails());
            return result;
        }

    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
