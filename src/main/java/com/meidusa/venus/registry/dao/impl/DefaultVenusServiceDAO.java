package com.meidusa.venus.registry.dao.impl;

import com.meidusa.venus.registry.dao.VenusServiceDAO;
import com.meidusa.venus.registry.domain.VenusService;
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
public class DefaultVenusServiceDAO implements VenusServiceDAO {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public void addService(String serviceName, String description) throws VenusRegistryException {
        String sql = "insert into t_venus_service (name, description, create_time, update_time) values (?, ?, now(), now())";
        try{
            this.jdbcTemplate.update(sql, serviceName, description);
        }catch (Exception e) {
            throw new VenusRegistryException("保存服务异常", e);
        }
    }

    @Override
    public VenusService getVenusService(int id) throws VenusRegistryException{
        String sql = "select id, name, description, create_time, update_time from t_venus_service where id = ?";
        return this.jdbcTemplate.query(sql, new Object[]{id}, new ResultSetExtractor<VenusService>() {
            @Override
            public VenusService extractData(ResultSet rs) throws SQLException, DataAccessException {
                if (rs.next()) {
                    return ResultSetExctractUtils.extractVenusServiceData(rs);
                }
                return  null;
            }
        });
    }

    @Override
    public VenusService getVenusService(String serviceName) throws VenusRegistryException{
        String sql = "select id, name, description, create_time, update_time from t_venus_service where name = ?";
        return this.jdbcTemplate.query(sql, new Object[]{serviceName}, new ResultSetExtractor<VenusService>() {
            @Override
            public VenusService extractData(ResultSet rs) throws SQLException, DataAccessException {
                if(rs.next()) {
                    return ResultSetExctractUtils.extractVenusServiceData(rs);
                }
                return null;
            }
        });
    }

    @Override
    public List<VenusService> paginate(String serviceName, int start, int length) {

        if (serviceName == null){
            serviceName = "%%";
        }else {
            serviceName = "%" + serviceName +"%";
        }

        String sql = "select id, name, description, create_time, update_time from t_venus_service where name like ? limit ?, ?";

        return this.jdbcTemplate.query(sql, new Object[]{serviceName, start, length}, new ResultSetExtractor<List<VenusService>>() {
            @Override
            public List<VenusService> extractData(ResultSet rs) throws SQLException, DataAccessException {
                List<VenusService> services = new ArrayList<>();
                while(rs.next()){
                    services.add(ResultSetExctractUtils.extractVenusServiceData(rs));
                }
                return services;
            }
        });
    }

    @Override
    public int count(String serviceName) {

        if (serviceName == null){
            serviceName = "%%";
        }else {
            serviceName = "%" + serviceName +"%";
        }

        String sql = "select count(*) from t_venus_service where name like ?";

        return this.jdbcTemplate.queryForObject(sql, new Object[]{serviceName}, Integer.class);
    }
}
