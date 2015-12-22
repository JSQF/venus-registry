package com.meidusa.venus.registry.web.form;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Created by huawei on 12/21/15.
 */
public class RegistryServiceForm {

    private String hostname;
    private Integer port;

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
