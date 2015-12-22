package com.meidusa.venus.registry.utils;

import com.meidusa.venus.registry.domain.VenusServer;
import com.meidusa.venus.registry.domain.VenusService;
import com.meidusa.venus.registry.domain.VenusServiceMapping;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by huawei on 12/21/15.
 */
public class ResultSetExctractUtils {

    public static VenusServer extractVenusServerData(ResultSet rs) throws SQLException {
        VenusServer venusServer = new VenusServer();
        venusServer.setId(rs.getInt("id"));
        venusServer.setHostname(rs.getString("hostname"));
        venusServer.setPort(rs.getInt("port"));
        venusServer.setCreateTime(rs.getTimestamp("create_time"));
        venusServer.setUpdateTime(rs.getTimestamp("update_time"));
        return venusServer;
    }

    public static VenusService extractVenusServiceData(ResultSet rs) throws SQLException {
        VenusService venusService = new VenusService();
        venusService.setId(rs.getInt("id"));
        venusService.setName(rs.getString("name"));
        venusService.setDescription(rs.getString("description"));
        venusService.setCreateTime(rs.getTimestamp("create_time"));
        venusService.setUpdateTime(rs.getTimestamp("update_time"));
        return venusService;
    }

    public static VenusServiceMapping extractVenusServiceMapping(ResultSet rs) throws SQLException {
        VenusServiceMapping venusServiceMapping = new VenusServiceMapping();
        venusServiceMapping.setId(rs.getInt("id"));
        venusServiceMapping.setVersion(rs.getString("version"));
        venusServiceMapping.setActive(rs.getBoolean("active"));
        venusServiceMapping.setSync(rs.getBoolean("sync"));
        venusServiceMapping.setCreateTime(rs.getTimestamp("create_time"));
        venusServiceMapping.setUpdateTime(rs.getTimestamp("update_time"));

        VenusServer server =new VenusServer();
        server.setId(rs.getInt("server_id"));

        VenusService service = new VenusService();
        service.setId(rs.getInt("service_id"));

        venusServiceMapping.setServer(server);
        venusServiceMapping.setService(service);

        return venusServiceMapping;
    }

    public static VenusServiceMapping extractFullVenusServiceMapping(ResultSet rs) throws SQLException{

        VenusServiceMapping venusServiceMapping = new VenusServiceMapping();
        venusServiceMapping.setId(rs.getInt("mappingId"));
        venusServiceMapping.setVersion(rs.getString("version"));
        venusServiceMapping.setActive(rs.getBoolean("active"));
        venusServiceMapping.setSync(rs.getBoolean("sync"));
        venusServiceMapping.setCreateTime(rs.getTimestamp("create_time"));
        venusServiceMapping.setUpdateTime(rs.getTimestamp("update_time"));

        VenusServer server =new VenusServer();
        server.setId(rs.getInt("server_id"));
        server.setHostname(rs.getString("hostname"));
        server.setPort(rs.getInt("port"));

        VenusService service = new VenusService();
        service.setId(rs.getInt("service_id"));
        service.setDescription(rs.getString("description"));
        service.setName(rs.getString("name"));

        venusServiceMapping.setServer(server);
        venusServiceMapping.setService(service);


        return venusServiceMapping;
    }
}
