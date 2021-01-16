package com.mxgraph.entity.excelEntity;

import lombok.Data;

import java.util.Date;

/**
 * @description: 电容器
 * @author: Zhaokl
 * @create: 2021-01-14 17:04
 **/
@Data
public class CapacitorData {
    private Integer index;
    private String poleId;
    private String model;
    private Integer num;
    private Double capacity;
    private Date putInto;
    private String vendor;
    private String remark;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getPoleId() {
        return poleId;
    }

    public void setPoleId(String poleId) {
        this.poleId = poleId;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
