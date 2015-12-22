package com.meidusa.venus.registry.service.impl;

import com.meidusa.toolkit.common.util.StringUtil;
import com.meidusa.venus.annotations.Param;
import com.meidusa.venus.registry.domain.VenusServiceMapping;
import com.meidusa.venus.registry.service.VenusServiceMappingService;
import com.meidusa.venus.service.registry.ServiceDefinition;
import com.meidusa.venus.service.registry.ServiceRegistry;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by huawei on 12/18/15.
 */
@Component(value = "DefaultRegistryService")
public class DefaultRegistryService implements ServiceRegistry {

    @Resource
    private VenusServiceMappingService mappingService;

    @Override
    public List<ServiceDefinition> getServiceDefinitions() {
        List<ServiceDefinition> definitionList = new ArrayList<>();

        List<VenusServiceMapping> mappingList = this.mappingService.getAllActivityServiceMapping();

        if (mappingList == null || mappingList.size() == 0) {
            return definitionList;
        }

        Map<String, ServiceDefinition> definitionMap = new HashMap<>();

        for(VenusServiceMapping mapping : mappingList) {
            String name = mapping.getService().getName();
            String version = mapping.getVersion();
            String key = name +(StringUtils.hasLength(version)? version : "All");

            if (!StringUtils.hasLength(version)) {
                version = "";
            }

            ServiceDefinition sd = definitionMap.get(key);

            if (sd == null) {
                sd = new ServiceDefinition();
                Set<String> ipAddress = new HashSet<>();
                ipAddress.add(getIpAddress(mapping.getServer().getHostname(), mapping.getServer().getPort()));
                sd.setName(mapping.getService().getName());
                sd.setDescription(mapping.getService().getDescription());
                sd.setActive(true);
                sd.setIpAddress(ipAddress);
                sd.setVersionRange(version);
                definitionMap.put(key, sd);
            }else {
                sd.getIpAddress().add(getIpAddress(mapping.getServer().getHostname(), mapping.getServer().getPort()));
            }
        }

        for(ServiceDefinition definition : definitionMap.values()){
            definitionList.add(definition);
        }

        return definitionList;
    }

    @Override
    public ServiceDefinition getServiceDefinition(@Param(name = "name") String name, @Param(name = "version") int version) {

        if (!StringUtils.hasLength(name)) {
            return null;
        }
        VenusServiceMapping mapping = this.mappingService.getMapping(name, version);
        if (mapping == null) {
            return null;
        }
        ServiceDefinition sd = new ServiceDefinition();
        Set<String> ipAddress = new HashSet<>();
        ipAddress.add(getIpAddress(mapping.getServer().getHostname(), mapping.getServer().getPort()));
        sd.setName(mapping.getService().getName());
        sd.setDescription(mapping.getService().getDescription());
        sd.setActive(true);
        sd.setIpAddress(ipAddress);
        sd.setVersionRange(mapping.getVersion());
        return sd;
    }

    private String getIpAddress(String hostname, int port) {
        return hostname + ":" + port;
    }
}
