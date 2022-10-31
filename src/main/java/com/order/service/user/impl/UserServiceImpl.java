package com.order.service.user.impl;

import com.alibaba.fastjson.JSONObject;

import com.order.constant.RedisConstant;
import com.order.constant.ResponseErrorEnum;
import com.order.constant.rbac.UserRoleEnum;
import com.order.modle.VO.user.UserVO;
import com.order.modle.entity.UserInfoDO;
import com.order.modle.param.ChangePasswordParam;
import com.order.modle.param.ModifyPersonalInfoParam;
import com.order.modle.param.UserParam;
import com.order.repository.UserInfoRepository;
import com.order.response.ServerException;
import com.order.service.RedisService;
import com.order.service.user.UserService;
import com.order.util.RSAUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author: wanqian
 * @Date: 2020/10/22 11:37
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Value("${order-project.super-admin.username}")
    private String superAdminUserName;

    @Value(("${order-project.super-admin.password}"))
    private String superAdminPassword;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private RedisService redisService;

    @Override
    public void userInit() {

        //将没有用户组的成员划分到默认用户组中
        List<UserInfoDO> all = userInfoRepository.findAll();
        //初始化创建超级管理员账号
        if (all.isEmpty()) {
            UserInfoDO userInfoDO = UserInfoDO.builder()
                    .userName("管理员")
                    .password(RSAUtil.encrypt(superAdminPassword))
                    .roleCode(UserRoleEnum.SUPER_ADMIN.roleCode)
                    .loginName(superAdminUserName)
                    .createTime(new Date())
                    .build();
            userInfoRepository.save(userInfoDO);

            UserVO userVO = convertUserInfoDO2VO(userInfoDO);
            this.redisService.hset(RedisConstant.USER_ROLE,String.valueOf(userVO.getUserId()), JSONObject.toJSON(userVO));

        }else {
            for (UserInfoDO userInfoDO : all) {
                UserVO userVO = convertUserInfoDO2VO(userInfoDO);
                this.redisService.hset(RedisConstant.USER_ROLE,String.valueOf(userVO.getUserId()), JSONObject.toJSON(userVO));
            }
        }
    }

    @Override
    public List<UserVO> listUserVO() {
        List<UserInfoDO> all = userInfoRepository.findAll();
        List<UserVO> list = all.parallelStream().map(this::convertUserInfoDO2VO).collect(Collectors.toList());
        return list;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createUser(HttpServletRequest httpRequest, UserParam userParam) throws ServerException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserInfoDO currentUserInfoDO = (UserInfoDO) authentication.getPrincipal();
        Optional<UserInfoDO> userOpt = userInfoRepository.findByUserName(userParam.getUserName());
        if (userOpt.isPresent()) {
            throw new ServerException(ResponseErrorEnum.USER_IS_EXISTED);
        }
        Optional<UserInfoDO> userInfoOptional = userInfoRepository.findByLoginName(userParam.getLoginName());
        if (userInfoOptional.isPresent()) {
            throw new ServerException(ResponseErrorEnum.USER_IS_EXISTED);
        }

        //存入用户信息
        UserInfoDO userInfoDO = UserInfoDO.builder()
                .userName(userParam.getUserName())
                .loginName(userParam.getLoginName())
                .password(userParam.getPassword())
                .roleCode(UserRoleEnum.getByRoleCode(userParam.getRoleCode()).roleCode)
                .createTime(new Date())
                .build();
        userInfoRepository.save(userInfoDO);

        UserVO userVO = convertUserInfoDO2VO(userInfoDO);
        this.redisService.hset(RedisConstant.USER_ROLE,String.valueOf(userVO.getUserId()), JSONObject.toJSON(userVO));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserVO updateUser(HttpServletRequest httpRequest, Integer id, UserParam userParam) throws ServerException {

        Optional<UserInfoDO> userOpt = userInfoRepository.findById(id);
        if (!userOpt.isPresent()) {
            throw new ServerException(ResponseErrorEnum.USER_NOT_EXIST);
        }
        //构建用户信息用于修改
        UserInfoDO userInfoDO = UserInfoDO.builder()
                .userName(userParam.getUserName())
                .password(userParam.getPassword())
                .roleCode(UserRoleEnum.getByRoleCode(userParam.getRoleCode()).roleCode)
                .status(userParam.getStatus())
                .userId(id)
                .build();
        userInfoRepository.save(userInfoDO);

        UserVO userVO = convertUserInfoDO2VO(userInfoDO);
        this.redisService.hset(RedisConstant.USER_ROLE,String.valueOf(userVO.getUserId()), JSONObject.toJSON(userVO));
        return userVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(HttpServletRequest httpRequest, Integer id) throws ServerException {
        Optional<UserInfoDO> userOpt = userInfoRepository.findById(id);
        if (!userOpt.isPresent()) {
            throw new ServerException(ResponseErrorEnum.USER_NOT_EXIST);
        }
        //删除用户信息
        userInfoRepository.deleteById(id);

        this.redisService.hdel(RedisConstant.USER_ROLE,String.valueOf(id));
    }


    @Override
    public UserVO convertUserInfoDO2VO(UserInfoDO userInfoDO) {
        UserVO userVO = UserVO.builder()
                .userId(userInfoDO.getUserId())
                .status(userInfoDO.getStatus())
                .userName(userInfoDO.getUsername())
                .loginName(userInfoDO.getLoginName())
                .createTime(userInfoDO.getCreateTime())
                .build();

        return userVO;
    }

    @Override
    public UserVO resetPassword(HttpServletRequest httpRequest, Integer id) throws ServerException {
        //判断用户是否存在
        Optional<UserInfoDO> userOpt = userInfoRepository.findById(id);
        if (!userOpt.isPresent()) {
            throw new ServerException(ResponseErrorEnum.USER_NOT_EXIST);
        }
        //修改密码为123456
        UserInfoDO userInfoDO = userOpt.get();
        userInfoDO.setPassword(RSAUtil.encrypt("123456"));
        userInfoRepository.save(userInfoDO);
        //切面注入
        return convertUserInfoDO2VO(userInfoDO);
    }

    @Override
    public UserVO changePassword(HttpServletRequest httpRequest, Integer id, ChangePasswordParam changePasswordParam) throws ServerException {
        //判断用户是否存在
        Optional<UserInfoDO> userOpt = userInfoRepository.findById(id);
        if (!userOpt.isPresent()) {
            throw new ServerException(ResponseErrorEnum.USER_NOT_EXIST);
        }
        UserInfoDO userInfoDO = userOpt.get();
        //正确的密码
        String password = RSAUtil.decrypt(userInfoDO.getPassword());
        //传输过来的密码
        String loginPassword = RSAUtil.decrypt(changePasswordParam.getOldPassword());
        if (!loginPassword.equals(password)) {
            throw new ServerException(ResponseErrorEnum.USER_PASSWORD_ERROR);
        }
        userInfoDO.setPassword(changePasswordParam.getNewPassword());
        userInfoRepository.save(userInfoDO);

        return convertUserInfoDO2VO(userInfoDO);
    }

    @Override
    public UserVO modifyPersonalInfo(HttpServletRequest httpRequest, Integer id, ModifyPersonalInfoParam modifyPersonalInfoParam) throws ServerException {
        //判断用户是否存在
        Optional<UserInfoDO> userOpt = userInfoRepository.findById(id);
        if (!userOpt.isPresent()) {
            throw new ServerException(ResponseErrorEnum.USER_NOT_EXIST);
        }
        UserInfoDO userInfoDO = userOpt.get();
        //正确的密码
        String password = RSAUtil.decrypt(userInfoDO.getPassword());
        //传输过来的密码
        String loginPassword = RSAUtil.decrypt(modifyPersonalInfoParam.getPassword());
        if (!loginPassword.equals(password)) {
            throw new ServerException(ResponseErrorEnum.USER_VERIFY_PASSWORD_ERROR);
        }


        //更新用户名
        userInfoDO.setUserName(modifyPersonalInfoParam.getUserName());
        userInfoRepository.save(userInfoDO);
        UserVO userVO = convertUserInfoDO2VO(userInfoDO);
        this.redisService.hset(RedisConstant.USER_ROLE,String.valueOf(userVO.getUserId()), JSONObject.toJSON(userVO));
        return userVO;
    }
}
