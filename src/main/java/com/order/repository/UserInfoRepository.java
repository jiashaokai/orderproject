package com.order.repository;



import com.order.modle.entity.UserInfoDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @Author: wanqian
 * @Date: 2020/10/21 16:34
 */
@Repository
public interface UserInfoRepository extends JpaRepository<UserInfoDO, Integer> {

    /**
     * 根据登录名查询
     *
     * @param userName
     * @return
     */
    Optional<UserInfoDO> findByLoginName(String userName);

    /**
     * 根据用户名查询
     *
     * @param userName
     * @return
     */
    Optional<UserInfoDO> findByUserName(String userName);
}
