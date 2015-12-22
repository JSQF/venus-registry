package com.meidusa.venus.registry.web.form;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Created by huawei on 12/21/15.
 */
public class ListVenusServerForm extends DatatableForm {

    private String hostname;
    private String port;

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
