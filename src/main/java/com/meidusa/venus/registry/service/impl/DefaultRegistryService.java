package com.meidusa.venus.registry.service.impl;

import com.meidusa.venus.annotations.Param;
import com.meidusa.venus.service.registry.ServiceDefinition;
import com.meidusa.venus.service.registry.ServiceRegistry;

import java.util.List;

/**
 * Created by huawei on 12/18/15.
 */
public class DefaultRegistryService implements ServiceRegistry {
    @Override
    public List<ServiceDefinition> getServiceDefinitions() {
        return null;
    }

    @Override
    public ServiceDefinition getServiceDefinition(@Param(name = "name") String name, @Param(name = "version") int version) {
        return null;
    }
}
