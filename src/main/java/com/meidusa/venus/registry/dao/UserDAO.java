package com.meidusa.venus.registry.dao;

import com.meidusa.venus.registry.domain.User;

/**
 * Created by huawei on 1/18/16.
 */
public interface UserDAO {

     User getUser(String username);
}
