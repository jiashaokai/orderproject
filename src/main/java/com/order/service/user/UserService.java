package com.order.service.user;




import com.order.modle.VO.user.UserVO;
import com.order.modle.entity.UserInfoDO;
import com.order.modle.param.ChangePasswordParam;
import com.order.modle.param.ModifyPersonalInfoParam;
import com.order.modle.param.UserParam;
import com.order.response.ServerException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author: wanqian
 * @Date: 2020/10/22 11:36
 */
public interface UserService {

    /**
     * 管理员用户初始化
     */
    void userInit();

    /**
     * 获取所有用户视图
     *
     * @return
     */
    List<UserVO> listUserVO();

    /**
     * 创建用户
     *
     * @param httpRequest
     * @param userParam
     * @return
     * @throws ServerException
     */
    void createUser(HttpServletRequest httpRequest, UserParam userParam) throws ServerException;

    /**
     * 修改用户信息
     *
     * @param httpRequest
     * @param id
     * @param userParam
     * @return
     * @throws ServerException
     */
    UserVO updateUser(HttpServletRequest httpRequest, Integer id, UserParam userParam) throws ServerException;

    /**
     * 删除用户
     *
     * @param id
     */
    void deleteUser(HttpServletRequest httpRequest, Integer id) throws ServerException;

    /**
     * 根据用户信息构造用户视图
     *
     * @param userInfoDO
     * @return
     */
    UserVO convertUserInfoDO2VO(UserInfoDO userInfoDO);

    /**
     * 重置密码
     *
     * @param httpRequest
     * @param id
     * @return
     * @throws ServerException
     */
    UserVO resetPassword(HttpServletRequest httpRequest, Integer id) throws ServerException;

    /**
     * 修改密码
     *
     * @param httpRequest
     * @param id
     * @param changePasswordParam
     * @return
     * @throws ServerException
     */
    UserVO changePassword(HttpServletRequest httpRequest, Integer id, ChangePasswordParam changePasswordParam) throws ServerException;

    /**
     * 修改个人信息
     *
     * @param httpRequest
     * @param id
     * @param modifyPersonalInfoParam
     * @throws ServerException
     */
    UserVO modifyPersonalInfo(HttpServletRequest httpRequest, Integer id, ModifyPersonalInfoParam modifyPersonalInfoParam) throws ServerException;
}
