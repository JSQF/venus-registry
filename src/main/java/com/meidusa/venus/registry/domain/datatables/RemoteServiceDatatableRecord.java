package com.meidusa.venus.registry.domain.datatables;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Created by huawei on 12/20/15.
 */
public class RemoteServiceDatatableRecord {

    private String name;
    private String version;
    private String description;
    private boolean active;
    private String status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
