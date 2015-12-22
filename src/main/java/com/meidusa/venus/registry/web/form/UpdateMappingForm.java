package com.meidusa.venus.registry.web.form;

/**
 * Created by huawei on 12/22/15.
 */
public class UpdateMappingForm {
    private Integer id;
    private String version;
    private Integer active;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
}
