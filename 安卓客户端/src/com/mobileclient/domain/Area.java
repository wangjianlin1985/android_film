package com.mobileclient.domain;

import java.io.Serializable;

public class Area implements Serializable {
    /*地区id*/
    private int areaId;
    public int getAreaId() {
        return areaId;
    }
    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    /*地区名称*/
    private String areaName;
    public String getAreaName() {
        return areaName;
    }
    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

}