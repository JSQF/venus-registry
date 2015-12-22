package com.meidusa.venus.registry.dao.impl;

import com.meidusa.venus.registry.dao.VenusServerDAO;
import com.meidusa.venus.registry.domain.VenusServer;
import com.meidusa.venus.registry.exception.VenusRegistryException;
import com.meidusa.venus.registry.utils.ResultSetExctractUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huawei on 12/21/15.
 */
@Component
public class DefaultVenusServerDAO implements VenusServerDAO {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public void addServer(String hostname, int port) throws VenusRegistryException {
        String sql = "insert into t_venus_server (hostname, port, create_time, update_time) values (?, ?, now(), now())";
        try {
            this.jdbcTemplate.update(sql, hostname, port);
        } catch (Exception e) {
            throw new VenusRegistryException("保存服务器信息异常", e);
        }
    }

    @Override
    public VenusServer getServer(String hostname, int port) throws VenusRegistryException {
        String sql = "select id, hostname, port, create_time, update_time from t_venus_server where hostname = ? and port = ?";
        try {
            return this.jdbcTemplate.query(sql, new Object[]{hostname, port}, new ResultSetExtractor<VenusServer>() {
                @Override
                public VenusServer extractData(ResultSet rs) throws SQLException, DataAccessException {
                    if (rs.next()) {
                        return ResultSetExctractUtils.extractVenusServerData(rs);
                    }
                    return null;
                }
            });
        } catch (Exception e) {
            throw new VenusRegistryException("获取服务器信息异常", e);
        }
    }

    @Override
    public List<VenusServer> paginate(String hostname, String port, String searchValue, int start, int length) {

        if (hostname == null) {
            hostname = "%%";
        } else {
            hostname = "%" + hostname + "%";
        }

        if (port == null) {
            port = "%%";
        } else {
            port = "%" + port + "%";
        }

        if (searchValue == null) {
            searchValue = "%%";
        }else {
            searchValue = "%" + searchValue.replace(" ", "%") + "%";
        }

        String sql = "select id, hostname, port, create_time, update_time from t_venus_server where hostname like ? and port like ? and (concat(hostname, port) like ? or concat(port, hostname) like ?) limit ?, ?";



        return this.jdbcTemplate.query(sql, new Object[]{hostname, port, searchValue, searchValue, start, length}, new ResultSetExtractor<List<VenusServer>>() {
            @Override
            public List<VenusServer> extractData(ResultSet rs) throws SQLException, DataAccessException {
                List<VenusServer> result = new ArrayList<>();
                while(rs.next()) {
                    result.add(ResultSetExctractUtils.extractVenusServerData(rs));
                }
                return result;
            }
        });
    }

    @Override
    public int count(String hostname, String port, String searchValue) {
        if (hostname == null) {
            hostname = "%%";
        } else {
            hostname = "%" + hostname + "%";
        }

        if (port == null) {
            port = "%%";
        } else {
            port = "%" + port + "%";
        }

        if (searchValue == null) {
            searchValue = "%%";
        }else {
            searchValue = "%" + searchValue.replace(" ", "%") + "%";
        }

        String sql = "select count(*) from t_venus_server where hostname like ? and port like ? and (concat(hostname, port) like ? or concat(port, hostname) like ?)";

        return this.jdbcTemplate.queryForObject(sql, new Object[]{hostname, port, searchValue, searchValue}, Integer.class);
    }

    @Override
    public List<VenusServer> getAllServer() {
        String sql = "select id, hostname, port, create_time, update_time from t_venus_server";
        return this.jdbcTemplate.query(sql, new ResultSetExtractor<List<VenusServer>>() {
            @Override
            public List<VenusServer> extractData(ResultSet rs) throws SQLException, DataAccessException {
                List<VenusServer> result = new ArrayList<>();
                while(rs.next()) {
                    result.add(ResultSetExctractUtils.extractVenusServerData(rs));
                }
                return result;
            }
        });
    }
}
