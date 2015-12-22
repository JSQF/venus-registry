package com.meidusa.venus.registry.dao;

import com.meidusa.venus.registry.domain.VenusService;
import com.meidusa.venus.registry.exception.VenusRegistryException;

import java.util.List;

/**
 * Created by huawei on 12/18/15.
 */
public interface VenusServiceDAO {

    void addService(String serviceName, String description) throws VenusRegistryException;

    VenusService getVenusService(int id) throws VenusRegistryException;

    VenusService getVenusService(String serviceName) throws VenusRegistryException;

    List<VenusService> paginate(String serviceName, int start, int length);

    int count(String serviceName);
}
