package com.meidusa.venus.registry.dao;

import com.meidusa.venus.registry.domain.VenusServiceMapping;
import com.meidusa.venus.registry.exception.VenusRegistryException;

import java.util.List;

/**
 * Created by huawei on 12/18/15.
 */
public interface VenusServiceMappingDAO {

    VenusServiceMapping getServiceMapping(int serverId, int serviceId) throws VenusRegistryException;

    void addMapping(Integer serverId, Integer serviceId, String version, boolean active, boolean sync) throws VenusRegistryException;

    void updateMapping(Integer serverId, Integer serviceId, String version, boolean active) throws VenusRegistryException;

    List<VenusServiceMapping> paginate(Integer serverId, Integer serviceId, String searchValue, int start, int length) throws VenusRegistryException;

    int count(Integer serverId, Integer serviceId, String searchValue) throws VenusRegistryException;

    void updateMapping(Integer mappingId, String version, Boolean active, Boolean sync) throws VenusRegistryException;

    List<VenusServiceMapping> getServiceMapping(VenusServiceMapping mapping);

    void deleteById(Integer id);

    void updateMappingSyncStatus(Integer id, boolean b);
}
