package com.mxgraph.entity.excelEntity;

import lombok.Data;

import java.util.Date;

/**
 * @description: 变压器
 * @author: Zhaokl
 * @create: 2021-01-14 16:52
 **/
@Data
public class TransformerData {
    private Integer index;
    private String name;
    private String isPublic;
    private String type;
    private String model;
    private Double capacity;
    private Date putInto;
    private String vendor;
    private String user;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(String isPublic) {
        this.isPublic = isPublic;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
