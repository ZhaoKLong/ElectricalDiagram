package com.mxgraph.entity;

import lombok.Data;

import java.util.Date;

/**
 * @Description
 * @Author Zkl
 * @Date 2020-10-09
 */

@Data
public class Pole {

    /**
     * 名称
     */
    private String name;

    /**
     * 经度
     */
    private Double longitude;

    /**
     * 纬度
     */
    private Double latitude;

    /**
     * 备注（环境信息）
     */
    private String remark;

    /**
     * 是否为厂站
     */
    private Integer isFactory;

    /**
     * 是否为铁架(0-水泥杆，1-铁架)
     */
    private Integer isHob;

    /**
     * 设备列表
     */
    private Equipment[] equipmentList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getIsFactory() {
        return isFactory;
    }

    public void setIsFactory(Integer isFactory) {
        this.isFactory = isFactory;
    }

    public Integer getIsHob() {
        return isHob;
    }

    public void setIsHob(Integer isHob) {
        this.isHob = isHob;
    }

    public Equipment[] getEquipmentList() {
        return equipmentList;
    }

    public void setEquipmentList(Equipment[] equipmentList) {
        this.equipmentList = equipmentList;
    }
}
