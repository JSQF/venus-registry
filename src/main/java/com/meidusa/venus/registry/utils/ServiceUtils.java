package com.meidusa.venus.registry.utils;

import com.meidusa.venus.registry.domain.VenusServer;
import com.meidusa.venus.registry.domain.VenusService;
import com.meidusa.venus.registry.domain.VenusServiceMapping;
import com.meidusa.venus.registry.exception.VenusRegistryException;
import com.meidusa.venus.registry.service.VenusServiceMappingService;
import com.meidusa.venus.registry.service.VenusServiceService;

import java.util.List;

/**
 * Created by huawei on 12/22/15.
 */
public class ServiceUtils {

    public static void  registerService(VenusServer server, List<VenusServiceMapping> mappingList, VenusServiceService venusServiceService, VenusServiceMappingService venusServiceMappingService) throws VenusRegistryException {
        for(VenusServiceMapping mapping: mappingList) {
            VenusService venusService = venusServiceService.addService(mapping.getService().getName(), mapping.getService().getDescription());

            VenusServiceMapping dbMapping = venusServiceMappingService.getMapping(server.getId(), venusService.getId());

            if (dbMapping == null) {
                venusServiceMappingService.addMapping(server, venusService, mapping);
            }else {
                if (dbMapping.isSync()) {
                    venusServiceMappingService.updateMapping(server, venusService, mapping);
                }
            }
        }
    }
}
