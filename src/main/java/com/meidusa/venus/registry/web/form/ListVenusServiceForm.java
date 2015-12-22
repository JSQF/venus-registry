package com.meidusa.venus.registry.web.form;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Created by huawei on 12/22/15.
 */
public class ListVenusServiceForm extends DatatableForm {
    private String serviceName;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
