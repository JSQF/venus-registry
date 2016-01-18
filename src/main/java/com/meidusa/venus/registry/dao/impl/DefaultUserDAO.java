package com.meidusa.venus.registry.dao.impl;

import com.meidusa.venus.registry.dao.UserDAO;
import com.meidusa.venus.registry.domain.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by huawei on 1/18/16.
 */
@Component
public class DefaultUserDAO implements UserDAO {
    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public User getUser(String username) {
        return jdbcTemplate.query("select * from t_registry_user where username = ?", new Object[]{username}, new ResultSetExtractor<User>() {
            @Override
            public User extractData(ResultSet rs) throws SQLException, DataAccessException {
                if(rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setUsername(rs.getString("username"));
                    user.setActive(rs.getBoolean("active"));
                    user.setCreateTime(rs.getTimestamp("create_time"));
                    user.setUpdateTime(rs.getTimestamp("update_time"));
                    return user;
                }
                return null;
            }
        });
    }
}
