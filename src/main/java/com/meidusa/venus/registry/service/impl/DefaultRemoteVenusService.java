package com.meidusa.venus.registry.service.impl;

import com.meidusa.venus.client.simple.SimpleServiceFactory;
import com.meidusa.venus.registry.domain.VenusServer;
import com.meidusa.venus.registry.domain.VenusService;
import com.meidusa.venus.registry.domain.VenusServiceMapping;
import com.meidusa.venus.registry.domain.datatables.RemoteServiceDataTableRecord;
import com.meidusa.venus.registry.exception.VenusRegistryException;
import com.meidusa.venus.registry.service.RemoteVenusService;
import com.meidusa.venus.registry.service.VenusServiceService;
import com.meidusa.venus.registry.utils.Constants;
import com.meidusa.venus.service.registry.ServiceDefinition;
import com.meidusa.venus.service.registry.ServiceRegistry;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huawei on 12/18/15.
 */
@Component
public class DefaultRemoteVenusService implements RemoteVenusService {

    @Resource
    private VenusServiceService venusServiceService;

    @Override
    public List<VenusServiceMapping> findRemoteService(String hostname, int port) throws VenusRegistryException {

        List<VenusServiceMapping> venusServiceMappings = new ArrayList<>();
        try {
            VenusServer venusServer = new VenusServer();
            venusServer.setHostname(hostname);
            venusServer.setPort(port);

            SimpleServiceFactory ssf = new SimpleServiceFactory(hostname, port);
            ssf.setCoTimeout(60000);
            ssf.setSoTimeout(60000);
            ServiceRegistry sr = ssf.getService(ServiceRegistry.class);

            List<ServiceDefinition> sds = sr.getServiceDefinitions();

            if (sds != null) {
                for (ServiceDefinition sd : sds) {
                    VenusService venusService = new VenusService();
                    venusService.setName(sd.getName());
                    venusService.setDescription(sd.getDescription());
                    VenusServiceMapping venusServiceMapping = new VenusServiceMapping();
                    venusServiceMapping.setActive(sd.isActive());
                    venusServiceMapping.setServer(venusServer);
                    venusServiceMapping.setService(venusService);
                    venusServiceMapping.setVersion(sd.getVersionRange());
                    venusServiceMappings.add(venusServiceMapping);
                }
            }
        } catch (Exception e) {
            throw new VenusRegistryException("查询服务异常:", e);
        }
        return venusServiceMappings;
    }

    @Override
    public List<RemoteServiceDataTableRecord> getRemoteServiceForDataTable(List<VenusServiceMapping> mappingList) {
        if (mappingList == null) {
            return null;
        }

        List<RemoteServiceDataTableRecord> records = new ArrayList<>();
        for (VenusServiceMapping mapping : mappingList) {
            RemoteServiceDataTableRecord record = new RemoteServiceDataTableRecord();
            record.setName(mapping.getService().getName());
            record.setDescription(mapping.getService().getDescription());
            record.setActive(mapping.isActive());
            record.setVersion(mapping.getVersion());
            try {
                VenusService service = venusServiceService.getVenusService(mapping.getService().getName());
                if (service != null) {
                    record.setStatus(Constants.SERVICE_NAME_EXISTS);
                } else {
                    record.setStatus(Constants.SERVICE_NAME_NOT_EXISTS);
                }
            } catch (VenusRegistryException e) {
                record.setStatus(Constants.SERVICE_NAME_GET_ERROR);
            }
            records.add(record);
        }
        return records;
    }
}
