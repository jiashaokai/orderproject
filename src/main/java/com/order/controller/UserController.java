package com.order.controller;


import com.order.constant.ResponseSuccessEnum;
import com.order.modle.VO.user.UserVO;
import com.order.modle.param.ChangePasswordParam;
import com.order.modle.param.ModifyPersonalInfoParam;
import com.order.modle.param.UserParam;
import com.order.response.HttpResponse;
import com.order.response.ServerException;
import com.order.service.user.UserService;
import com.order.util.http.RestResultGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author: jiakun
 * @Date: 2020/10/22 18:24
 * @Description
 */
@RestController
@RequestMapping("/api/v1/auth/user")
@Api(tags = "用户模块")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    @ApiOperation("获取用户列表")
    public HttpResponse<List<UserVO>> listUser() {
        List<UserVO> list = userService.listUserVO();
        return RestResultGenerator.genSuccessResult(list, ResponseSuccessEnum.QUERY_SUCCESS);
    }

    @PostMapping
    @ApiOperation("新建用户")
    public HttpResponse<Boolean> createUser(@RequestBody @ApiParam("创建用户信息") UserParam userParam, HttpServletRequest httpRequest) throws ServerException {
        userService.createUser(httpRequest, userParam);
        return RestResultGenerator.genSuccessResult(true, ResponseSuccessEnum.CREATE_SUCCESS);
    }

    @PutMapping("/{id}")
    @ApiOperation("修改用户")
    public HttpResponse<UserVO> updateUser(@RequestBody @ApiParam("修改用户信息") UserParam userParam,
                                           @PathVariable(value = "id") @ApiParam(value = "用户id") Integer id, HttpServletRequest httpRequest) throws ServerException {
        UserVO userVO = userService.updateUser(httpRequest, id, userParam);
        return RestResultGenerator.genSuccessResult(userVO, ResponseSuccessEnum.UPDATE_SUCCESS);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除用户")
    public HttpResponse<Boolean> deleteUser(@PathVariable(value = "id") @ApiParam(value = "用户id") Integer id, HttpServletRequest httpRequest) throws ServerException {
        userService.deleteUser(httpRequest, id);
        return RestResultGenerator.genSuccessResult(ResponseSuccessEnum.DELETE_SUCCESS);
    }

    @PutMapping("/{id}/resetPassword")
    @ApiOperation("重置密码")
    public HttpResponse<Boolean> resetPassword(@PathVariable(value = "id") @ApiParam(value = "用户id") Integer id, HttpServletRequest httpRequest) throws ServerException {
        UserVO userVO = userService.resetPassword(httpRequest, id);
        return RestResultGenerator.genSuccessResult(true, ResponseSuccessEnum.OPERATION_SUCCESS);
    }

    @PutMapping("/modify-password/{id}")
    @ApiOperation("修改密码")
    public HttpResponse<Boolean> changePasswork(@PathVariable(value = "id") @ApiParam(value = "用户id") Integer id,
                                                @RequestBody @ApiParam("修改密码参数") ChangePasswordParam changePasswordParam, HttpServletRequest httpRequest) throws ServerException {
        UserVO userVO = userService.changePassword(httpRequest, id, changePasswordParam);
        return RestResultGenerator.genSuccessResult(true, ResponseSuccessEnum.OPERATION_SUCCESS);
    }

    @PutMapping("/personal-info/{id}")
    @ApiOperation("修改个人信息")
    public HttpResponse<Boolean> modifyPersonalInfo(@PathVariable(value = "id") @ApiParam(value = "用户id") Integer id,
                                                    @RequestBody @ApiParam("修改个人信息") ModifyPersonalInfoParam modifyPersonalInfoParam, HttpServletRequest httpRequest) throws ServerException {
        UserVO userVO = userService.modifyPersonalInfo(httpRequest, id, modifyPersonalInfoParam);
        return RestResultGenerator.genSuccessResult(true, ResponseSuccessEnum.OPERATION_SUCCESS);
    }

}
