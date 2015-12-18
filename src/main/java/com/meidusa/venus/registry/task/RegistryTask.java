package com.meidusa.venus.registry.task;

import com.meidusa.venus.registry.domain.Server;
import com.meidusa.venus.registry.domain.ServiceMapping;
import com.meidusa.venus.service.registry.ServiceDefinition;

import java.util.List;

/**
 * Created by huawei on 12/18/15.
 */
public class RegistryTask {

    public void registry(){

        List<Server> servers = null;

        for(Server server : servers) {
            List<ServiceDefinition> definitions = null;
            for(ServiceDefinition serviceDefinition : definitions) {
                String serviceName = serviceDefinition.getName();
                String range = serviceDefinition.getVersionRange();
            }
        }

    }

    public void clean() {

        List<Server> servers = null;

        List<ServiceMapping> mappings = null;

        for(Server server : servers) {
            List<ServiceDefinition> definitions = null;


        }

    }

}
