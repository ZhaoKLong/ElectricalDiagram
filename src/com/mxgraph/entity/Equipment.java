package com.mxgraph.entity;

import lombok.Data;

import java.util.Date;

/**
 * @Description
 * @Author Zkl
 * @Date 2020-10-09
 */

@Data
public class Equipment {

    /**
     * 名字
     */
    private String name;

    /**
     * 类型
     */
    private Integer type;

    /**
     * 备注（环境信息）
     */
    private String remark;

    /**
     * 设备指向的电杆id
     */
    private Long toPoleId;

    /**
     * 设备型号
     */
    private String model;

    /**
     * 设备容量（变压器-kVA，电容器-Kvar，其余为0）
     */
    private Double capacity;

    /**
     * 设备投运日期
     */
    private Date putInto;

    /**
     * 设备厂商
     */
    private String vendor;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getToPoleId() {
        return toPoleId;
    }

    public void setToPoleId(Long toPoleId) {
        this.toPoleId = toPoleId;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Double getCapacity() {
        return capacity;
    }

    public void setCapacity(Double capacity) {
        this.capacity = capacity;
    }

    public Date getPutInto() {
        return putInto;
    }

    public void setPutInto(Date putInto) {
        this.putInto = putInto;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }
}
