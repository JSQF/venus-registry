package com.meidusa.venus.registry.task;

import com.meidusa.venus.registry.domain.VenusServer;
import com.meidusa.venus.registry.domain.VenusServiceMapping;
import com.meidusa.venus.service.registry.ServiceDefinition;

import java.util.List;

/**
 * Created by huawei on 12/18/15.
 */
public class RegistryTask {

    public void registry(){

        List<VenusServer> servers = null;

        for(VenusServer server : servers) {
            List<ServiceDefinition> definitions = null;
            for(ServiceDefinition serviceDefinition : definitions) {
                String serviceName = serviceDefinition.getName();
                String range = serviceDefinition.getVersionRange();
            }
        }

    }

    public void clean() {

        List<VenusServer> servers = null;

        List<VenusServiceMapping> mappings = null;

        for(VenusServer server : servers) {
            List<ServiceDefinition> definitions = null;


        }

    }

}
