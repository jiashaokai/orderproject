package com.order.service.user.impl;

import com.order.constant.ResponseErrorEnum;
import com.order.modle.TokenState;
import com.order.modle.VO.user.UserVO;
import com.order.modle.entity.UserInfoDO;
import com.order.modle.param.TokenCheckParam;
import com.order.repository.UserInfoRepository;
import com.order.response.ServerException;
import com.order.service.RedisService;
import com.order.service.user.UserLoginService;
import com.order.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @Author: jiakun
 * @Date: 2021/3/8 19:13
 * @Description
 */
@Service
public class UserLoginServiceImpl implements UserLoginService {

    static AntPathMatcher matcher = new AntPathMatcher();

    @Resource
    private UserInfoRepository userInfoRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RedisService redisService;

    @Override
    public UserVO validateToke(String token) throws ServerException {
        if (!redisService.hasKey(token)) {
            throw new ServerException(ResponseErrorEnum.AUTH_EXPIRE);
        }
        String username = redisService.get(token);
        Optional<UserInfoDO> optional = this.userInfoRepository.findByLoginName(username);
        if (optional.isPresent()) {
            return userService.convertUserInfoDO2VO(optional.get());
        }
        throw new ServerException(ResponseErrorEnum.AUTH_EXPIRE);
    }

    @Override
    public TokenState checkToken(TokenCheckParam param) throws ServerException {
        TokenState tokenState = new TokenState();
        if (!redisService.hasKey(param.getToken())) {
            tokenState.setExpires(true);
            return tokenState;
        }
        tokenState.setExpires(false);
        String username = redisService.get(param.getToken());
//        Optional<UserInfoDO> optional = this.userInfoRepository.findByLoginName(username);
//        if (optional.isPresent()) {
//            UserInfoDO userInfoDO = optional.get();
//            for (AuthResourceEnum authResourceEnum : AuthResourceEnum.values()) {
//                if (!authResourceEnum.method.equals(param.getMethod())){
//                    continue;
//                }
//                if (matcher.match(authResourceEnum.uri,param.getRequestURI())){
//                    if (authResourceEnum.level == 2 || authResourceEnum.level.equals(userInfoDO.getRoleId())){
//                        tokenState.setHasAuthority(true);
//                        return tokenState;
//                    }
//                    break;
//                }
//            }
//        }
        tokenState.setHasAuthority(true);
        return tokenState;
    }
}
