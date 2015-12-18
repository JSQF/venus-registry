package com.meidusa.venus.registry.domain;

import java.util.Date;

/**
 * Created by huawei on 12/18/15.
 */
public class VenusServiceMapping {

    private Integer id;
    private VenusServer server;
    private VenusService service;
    private String version;
    private Integer active;
    private Date createTime;
    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public VenusServer getServer() {
        return server;
    }

    public void setServer(VenusServer server) {
        this.server = server;
    }

    public VenusService getService() {
        return service;
    }

    public void setService(VenusService service) {
        this.service = service;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
