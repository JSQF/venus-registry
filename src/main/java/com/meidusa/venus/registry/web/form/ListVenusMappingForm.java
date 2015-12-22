package com.meidusa.venus.registry.web.form;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by huawei on 12/22/15.
 */
public class ListVenusMappingForm extends DatatableForm {

    private Integer serverId;
    private Integer serviceId;

    public Integer getServerId() {
        return serverId;
    }

    public void setServerId(Integer serverId) {
        this.serverId = serverId;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
