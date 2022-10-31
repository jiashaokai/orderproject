package com.order.service.user;


import com.order.modle.TokenState;
import com.order.modle.VO.user.UserVO;
import com.order.modle.param.TokenCheckParam;
import com.order.response.ServerException;


/**
 * 登陆相关方法
 *
 * @Author: wanqian
 * @Date: 2020/10/21 20:16
 */
public interface UserLoginService {

    /**
     * 校验请求token
     *
     * @param token
     * @return
     */
    UserVO validateToke(String token) throws ServerException;

    /**
     * 检查token权限
     * @param token
     * @return
     */
    TokenState checkToken(TokenCheckParam token) throws ServerException;
}
