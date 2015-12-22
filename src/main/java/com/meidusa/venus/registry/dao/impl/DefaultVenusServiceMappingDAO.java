package com.meidusa.venus.registry.dao.impl;

import com.meidusa.venus.registry.dao.VenusServiceMappingDAO;
import com.meidusa.venus.registry.domain.VenusServiceMapping;
import com.meidusa.venus.registry.exception.VenusRegistryException;
import com.meidusa.venus.registry.utils.ResultSetExctractUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huawei on 12/21/15.
 */
@Component
public class DefaultVenusServiceMappingDAO implements VenusServiceMappingDAO {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public VenusServiceMapping getServiceMapping(int serverId, int serviceId) throws VenusRegistryException {

        String sql = "select id, server_id, service_id, version, active, sync, create_time, update_time from t_venus_service_mapping where server_id = ? and service_id = ?";

        try {
            return this.jdbcTemplate.query(sql, new Object[]{serverId, serviceId}, new ResultSetExtractor<VenusServiceMapping>() {
                @Override
                public VenusServiceMapping extractData(ResultSet rs) throws SQLException, DataAccessException {
                    if (rs.next()) {
                        return ResultSetExctractUtils.extractVenusServiceMapping(rs);
                    }
                    return null;
                }
            });
        } catch (Exception e) {
            throw new VenusRegistryException("获取服务映射关系异常", e);
        }
    }


    @Override
    public void addMapping(Integer serverId, Integer serviceId, String version, boolean active, boolean sync) throws VenusRegistryException {

        String sql = "insert into t_venus_service_mapping (server_id, service_id, version, active, sync, create_time, update_time) values (?, ?, ?, ?, ?, now(), now())";

        try {
            this.jdbcTemplate.update(sql, serverId, serviceId, version, active, sync);
        } catch (Exception e) {
            throw new VenusRegistryException("保存服务映射关系异常", e);
        }

    }

    @Override
    public void updateMapping(Integer serverId, Integer serviceId, String version, boolean active) throws VenusRegistryException {
        String sql = "update t_venus_service_mapping set version = ?, active = ? where server_id = ? and service_id = ?";
        try {
            this.jdbcTemplate.update(sql, version, active, serverId, serviceId);
        } catch (Exception e) {
            throw new VenusRegistryException("更新映射关系异常", e);
        }
    }

    @Override
    public List<VenusServiceMapping> paginate(Integer serverId, Integer serviceId, String searchValue, int start, int length) throws VenusRegistryException {

        if (searchValue == null) {
            searchValue = "";
        }

        StringBuffer sql = getFullMappingSql();

        sql.append(" and service.name like '%" + searchValue + "%' ");

        List<Integer> params = new ArrayList<>();

        if (serverId != null) {
            sql.append("and server.id = ? ");
            params.add(serverId);
        }

        if (serviceId != null) {
            sql.append("and service.id = ? ");
            params.add(serviceId);
        }

        sql.append("limit ?, ?");

        params.add(start);
        params.add(length);
        try {
            return getFullVenusServiceMapping(sql.toString(), params.toArray());
        } catch (Exception e) {
            throw new VenusRegistryException("获取映射关系分页异常", e);
        }
    }

    @Override
    public int count(Integer serverId, Integer serviceId, String searchValue) throws VenusRegistryException {
        StringBuffer sql = new StringBuffer();
        sql.append("select count(1)");
        sql.append("from t_venus_service_mapping mapping, t_venus_server server, t_venus_service service ");
        sql.append("where server.id = mapping.server_id and service.id = mapping.service_id and service.name like '%" + searchValue + "%' ");

        List<Integer> params = new ArrayList<>();

        if (serverId != null) {
            sql.append("and server.id = ? ");
            params.add(serverId);
        }

        if (serviceId != null) {
            sql.append("and service.id = ? ");
            params.add(serviceId);
        }
        try {
            return this.jdbcTemplate.queryForObject(sql.toString(), params.toArray(), Integer.class);
        } catch (Exception e) {
            throw new VenusRegistryException("统计映射关系数量异常", e);
        }
    }

    @Override
    public void updateMapping(Integer mappingId, String version, Boolean active, Boolean sync) throws VenusRegistryException {
        List<Object> params = new ArrayList<>();
        StringBuffer sql = new StringBuffer("update t_venus_service_mapping set version = ? ");
        params.add(version);
        if (active != null) {
            sql.append(" ,active = ?");
            params.add(active);
        }

        if (sync != null) {
            sql.append(" ,sync = ? ");
            params.add(sync);
        }

        sql.append("where  id = ?");

        params.add(mappingId);


        try {
            this.jdbcTemplate.update(sql.toString(), params.toArray());
        } catch (Exception e) {
            throw new VenusRegistryException("更新映射关系异常", e);
        }
    }

    @Override
    public List<VenusServiceMapping> getServiceMapping(VenusServiceMapping mapping) {

        StringBuffer sql = getFullMappingSql();

        List<Object> params = new ArrayList<>();

        if (mapping.isActive()) {
            sql.append(" and mapping.active = ?");
            params.add(mapping.isActive());
        }

        if (mapping.getService() != null && StringUtils.hasLength(mapping.getService().getName())) {
            sql.append(" and service.name = ?");
            params.add(mapping.getService().getName());
        }

        if (mapping.getService() != null && mapping.getService().getId() != null) {
            sql.append(" and service.id = ?");
            params.add(mapping.getService().getId());
        }

        if (mapping.getServer() != null && mapping.getServer().getId() != null) {
            sql.append(" and server.id = ?");
            params.add(mapping.getServer().getId());
        }

        return getFullVenusServiceMapping(sql.toString(), params.toArray());
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "delete from t_venus_service_mapping where id = ?";
        this.jdbcTemplate.update(sql, id);
    }

    @Override
    public void updateMappingSyncStatus(Integer id, boolean sync) {
        String sql = "update t_venus_service_mapping set sync = ? where id = ?";
        this.jdbcTemplate.update(sql, sync, id);
    }

    private List<VenusServiceMapping> getFullVenusServiceMapping(String sql, Object[] params) {
        return this.jdbcTemplate.query(sql.toString(), params, new ResultSetExtractor<List<VenusServiceMapping>>() {
            @Override
            public List<VenusServiceMapping> extractData(ResultSet rs) throws SQLException, DataAccessException {

                List<VenusServiceMapping> list = new ArrayList<>();

                while (rs.next()) {
                    list.add(ResultSetExctractUtils.extractFullVenusServiceMapping(rs));
                }
                return list;
            }
        });
    }

    private StringBuffer getFullMappingSql() {
        StringBuffer sql = new StringBuffer();
        sql.append("select ");
        sql.append("name, description, hostname, port, mapping.id as mappingId, server_id, service_id, version, active, sync, mapping.create_time as create_time, mapping.update_time as update_time ");
        sql.append("from t_venus_service_mapping mapping, t_venus_server server, t_venus_service service ");
        sql.append("where server.id = mapping.server_id and service.id = mapping.service_id ");

        return sql;
    }


}
