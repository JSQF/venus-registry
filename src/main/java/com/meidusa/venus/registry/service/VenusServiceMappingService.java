package com.meidusa.venus.registry.service;

import com.meidusa.venus.registry.domain.Pagination;
import com.meidusa.venus.registry.domain.VenusServer;
import com.meidusa.venus.registry.domain.VenusService;
import com.meidusa.venus.registry.domain.VenusServiceMapping;
import com.meidusa.venus.registry.exception.VenusRegistryException;

import java.util.List;

/**
 * Created by huawei on 12/18/15.
 */
public interface VenusServiceMappingService {
    VenusServiceMapping getMapping(int serverId, int serviceId) throws VenusRegistryException;

    void addMapping(VenusServer server, VenusService service, VenusServiceMapping mapping) throws VenusRegistryException;

    void updateMapping(VenusServer server, VenusService service, VenusServiceMapping mapping) throws VenusRegistryException;

    void updateMapping(Integer mappingId, String version, Boolean active, Boolean sync) throws VenusRegistryException;

    Pagination<VenusServiceMapping> paginate(Integer serverId, Integer serviceId, String searchValue, int start, int length) throws VenusRegistryException;

    List<VenusServiceMapping> getAllActivityServiceMapping();

    VenusServiceMapping getMapping(String name, int version);

    List<VenusServiceMapping> getMappings(Integer serverId, Integer serviceId);

    void deleteMapping(Integer id);

    void syncOn(Integer id);
}
