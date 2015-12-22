package com.meidusa.venus.registry.service;

import com.meidusa.venus.registry.domain.Pagination;
import com.meidusa.venus.registry.domain.VenusService;
import com.meidusa.venus.registry.exception.VenusRegistryException;

/**
 * Created by huawei on 12/18/15.
 */
public interface VenusServiceService {

    VenusService addService(String serviceName, String description) throws VenusRegistryException;

    VenusService getVenusService(int id) throws VenusRegistryException;

    VenusService getVenusService(String name) throws VenusRegistryException;

    Pagination<VenusService> paginate(String serviceName, int start, int length);
}
