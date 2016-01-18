package com.meidusa.venus.registry.service.impl;

import com.meidusa.venus.registry.dao.UserDAO;
import com.meidusa.venus.registry.domain.User;
import com.meidusa.venus.registry.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * Created by huawei on 1/18/16.
 */
@Component
public class DefaultUserService implements UserService {

    @Resource
    private UserDAO userDAO;

    @Override
    public User getUser(String username) {
        if (!StringUtils.hasLength(username)) {
            return null;
        }
        return this.userDAO.getUser(username);
    }
}
