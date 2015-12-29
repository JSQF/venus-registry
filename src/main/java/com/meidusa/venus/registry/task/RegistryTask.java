package com.meidusa.venus.registry.task;

import com.meidusa.venus.client.simple.SimpleServiceFactory;
import com.meidusa.venus.registry.domain.VenusServer;
import com.meidusa.venus.registry.domain.VenusService;
import com.meidusa.venus.registry.domain.VenusServiceMapping;
import com.meidusa.venus.registry.exception.VenusRegistryException;
import com.meidusa.venus.registry.service.RemoteVenusService;
import com.meidusa.venus.registry.service.VenusServerService;
import com.meidusa.venus.registry.service.VenusServiceMappingService;
import com.meidusa.venus.registry.service.VenusServiceService;
import com.meidusa.venus.registry.utils.ResponseUtils;
import com.meidusa.venus.registry.utils.ServiceUtils;
import com.meidusa.venus.service.registry.ServiceDefinition;
import com.meidusa.venus.service.registry.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by huawei on 12/18/15.
 */
public class RegistryTask {

    private static Logger logger = LoggerFactory.getLogger(RegistryTask.class);

    @Resource
    private RemoteVenusService remoteVenusService;

    @Resource
    private VenusServerService venusServerService;

    @Resource
    private VenusServiceService venusServiceService;

    @Resource
    private VenusServiceMappingService venusServiceMappingService;

    private TaskObserver taskObserver;

    public void registry(){

        List<VenusServer> servers = this.venusServerService.getAllServer();

        for(VenusServer server : servers) {
            try {
                List<VenusServiceMapping> venusServiceMappings = this.remoteVenusService.findRemoteService(server.getHostname(), server.getPort());

                if (venusServiceMappings == null) {
                    continue;
                }
                ServiceUtils.registerService(server, venusServiceMappings, venusServiceService, venusServiceMappingService);
            } catch (VenusRegistryException e) {
                logger.error("", e);
            }
        }

    }



    public void clean() {
        List<VenusServer> servers = this.venusServerService.getAllServer();
        for(VenusServer server : servers) {
            List<VenusServiceMapping> localMappings = this.venusServiceMappingService.getMappings(server.getId(), null);
            Map<String, VenusServiceMapping> localMappingMap = new HashMap<>();
            for(VenusServiceMapping localMapping : localMappings) {
                localMappingMap.put(localMapping.getService().getName(), localMapping);
            }
            List<String> remoteServiceNameList = new ArrayList<>();
            try {
                List<VenusServiceMapping> remoteMappings = this.remoteVenusService.findRemoteService(server.getHostname(), server.getPort());
                for(VenusServiceMapping remoteMapping : remoteMappings) {
                    remoteServiceNameList.add(remoteMapping.getService().getName());
                }
            } catch (VenusRegistryException e) {
                e.printStackTrace();
            }
            Iterator<String> localMappingIterator = localMappingMap.keySet().iterator();
            while(localMappingIterator.hasNext()) {
                String name = localMappingIterator.next();
                //System.out.println(Arrays.toString(remoteServiceNameList.toArray()) + ", " + name + ", " + remoteServiceNameList.contains(name));
                if (remoteServiceNameList.contains(name)) {
                    localMappingIterator.remove();
                }
            }
            for(VenusServiceMapping removeMapping : localMappingMap.values()) {
                this.venusServiceMappingService.deleteMapping(removeMapping.getId());
            }
        }
    }

    public void cleanEvent(){

    }

}
