package com.meidusa.venus.registry.service.impl;

import com.meidusa.venus.registry.dao.VenusServerDAO;
import com.meidusa.venus.registry.domain.Pagination;
import com.meidusa.venus.registry.domain.VenusServer;
import com.meidusa.venus.registry.exception.VenusRegistryException;
import com.meidusa.venus.registry.service.VenusServerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by huawei on 12/21/15.
 */
@Component
public class DefaulVenusServerService implements VenusServerService {

    private static Logger logger = LoggerFactory.getLogger(DefaulVenusServerService.class);

    @Resource
    private VenusServerDAO venusServerDAO;

    @Override
    public VenusServer addVenusServer(String hostname, int port) throws VenusRegistryException {
        if (hostname == null) {
            throw new VenusRegistryException("主机名不能为空");
        }

        if (port <= 0) {
            throw new VenusRegistryException("端口不能小于等于0");
        }

        VenusServer server = getVenusServer(hostname, port);

        if (server != null) {
            return server;
        }

        try{
            this.venusServerDAO.addServer(hostname, port);
            return getVenusServer(hostname, port);
        }catch(Exception e) {
            logger.error("保存服务器信息失败:", e);
            throw new VenusRegistryException("保存服务器信息失败", e);
        }
    }

    @Override
    public VenusServer getVenusServer(String hostname, int port) throws VenusRegistryException{

        try{
            return this.venusServerDAO.getServer(hostname, port);
        }catch (Exception e) {
            throw new VenusRegistryException("获取服务器信息异常", e);
        }
    }

    @Override
    public Pagination<VenusServer> paginate(String hostname, String port, String searchValue, int start, int length) {
        Pagination<VenusServer> venusServerPagination = new Pagination<>();
        try{
            List<VenusServer> venusServers = this.venusServerDAO.paginate(hostname, port, searchValue, start, length);
            int count = this.venusServerDAO.count(hostname, port, searchValue);
            venusServerPagination.setResult(venusServers);
            venusServerPagination.setCount(count);
        }catch(Exception e) {
            logger.error("查询服务器信息异常", e);
        }
        return venusServerPagination;
    }

    @Override
    public List<VenusServer> getAllServer() {
        return this.venusServerDAO.getAllServer();
    }

}
