package com.meidusa.venus.registry.service.impl;

import com.meidusa.venus.registry.dao.VenusServiceDAO;
import com.meidusa.venus.registry.domain.Pagination;
import com.meidusa.venus.registry.domain.VenusServer;
import com.meidusa.venus.registry.domain.VenusService;
import com.meidusa.venus.registry.exception.VenusRegistryException;
import com.meidusa.venus.registry.service.VenusServiceService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by huawei on 12/21/15.
 */
@Component
public class DefaultVenusServiceService implements VenusServiceService {

    @Resource
    private VenusServiceDAO venusServiceDAO;

    @Override
    public VenusService addService(String serviceName, String description) throws VenusRegistryException{
        if (serviceName == null) {
            throw new VenusRegistryException("服务名称不能为空");
        }

        if(description == null) {
            throw new VenusRegistryException("服务描述不能为空");
        }

        VenusService service = getVenusService(serviceName);

        if (service != null) {
            return service;
        }

        try{
            this.venusServiceDAO.addService(serviceName, description);
            return getVenusService(serviceName);
        }catch (Exception e) {
            throw new VenusRegistryException("保存服务异常", e);
        }
    }

    @Override
    public VenusService getVenusService(int id) throws VenusRegistryException {
        if (id <= 0) {
            throw new VenusRegistryException("服务名不能为空");
        }
        try{
            return this.venusServiceDAO.getVenusService(id);
        }catch(Exception e) {
            throw new VenusRegistryException("获取服务信息异常", e);
        }
    }

    @Override
    public VenusService getVenusService(String serviceName) throws VenusRegistryException {
        if (serviceName == null) {
            throw new VenusRegistryException("服务名不能为空");
        }
        try{
            return this.venusServiceDAO.getVenusService(serviceName);
        }catch(Exception e) {
            throw new VenusRegistryException("获取服务信息异常", e);
        }
    }

    @Override
    public Pagination<VenusService> paginate(String serviceName, int start, int length) {
        Pagination<VenusService> pagination = new Pagination<>();

        try{
            List<VenusService> results = this.venusServiceDAO.paginate(serviceName, start, length);
            int count = this.venusServiceDAO.count(serviceName);
            pagination.setResult(results);
            pagination.setCount(count);
        }catch (Exception e) {

        }
        return pagination;
    }
}
