package com.order.service.user.impl;



import com.order.modle.entity.UserInfoDO;
import com.order.repository.UserInfoRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Optional;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private UserInfoRepository userInfoRepository;

    /**
     * 实现UserDetailsService中的loadUserByUsername方法，用于加载用户数据
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfoDO> optional = userInfoRepository.findByLoginName(username);
        if (!optional.isPresent()) {
            throw new UsernameNotFoundException("用户不存在");
        }
        UserInfoDO userInfoDO = optional.get();
        //用户权限列表
        Collection<? extends GrantedAuthority> authorities = userInfoDO.getAuthorities();

        return userInfoDO;
    }
}
