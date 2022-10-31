package com.order.controller;



import com.order.constant.ResponseSuccessEnum;
import com.order.modle.TokenState;
import com.order.modle.VO.user.PublicKeyVO;
import com.order.modle.VO.user.UserVO;
import com.order.modle.param.TokenCheckParam;
import com.order.response.HttpResponse;
import com.order.response.ServerException;
import com.order.service.user.UserLoginService;
import com.order.util.RSAUtil;
import com.order.util.http.RestResultGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: wanqian
 * @Date: 2020/10/21 19:51
 */
@RestController
@RequestMapping("/api/v1/auth")
@Api(tags = "登陆模块")
public class LoginController {

    @Autowired
    private UserLoginService userLoginService;

    @GetMapping("/validate")
    @ApiOperation("校验用户token")
    public HttpResponse<UserVO> validateUser(@RequestHeader("token") @ApiParam("用户token") String token) throws ServerException, ServerException {
        UserVO userVO = userLoginService.validateToke(token);
        return RestResultGenerator.genSuccessResult(userVO, ResponseSuccessEnum.OPERATION_SUCCESS);
    }

    @PostMapping("/checkToken")
    @ApiOperation("校验用户token")
    public HttpResponse<TokenState> checkToken(@RequestBody @ApiParam("用户token与api路径") TokenCheckParam token) throws ServerException, ServerException {
        TokenState tokenState = userLoginService.checkToken(token);
        return RestResultGenerator.genSuccessResult(tokenState, ResponseSuccessEnum.OPERATION_SUCCESS);
    }

    @GetMapping("/publicKey")
    @ApiOperation("获取RSA加密公钥")
    public HttpResponse<PublicKeyVO> getPublicKey() {
        PublicKeyVO publicKeyVO = new PublicKeyVO();
        publicKeyVO.setPublicKey(RSAUtil.PUBLIC_KEY);
        return RestResultGenerator.genSuccessResult(publicKeyVO, ResponseSuccessEnum.QUERY_SUCCESS);
    }
}
