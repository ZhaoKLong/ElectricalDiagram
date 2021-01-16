package com.mxgraph.entity.excelEntity;

import lombok.Data;

import java.util.Date;

/**
 * @description: 开关
 * @author: Zhaokl
 * @create: 2021-01-14 17:15
 **/
@Data
public class SwitchData {
    private Integer index;
    private String name;
    private String poleId;
    private String model;
    private String vendor;
    private Date putInto;
    private String user;
    private Integer publicNum;
    private Integer privateNum;

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

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public Date getPutInto() {
        return putInto;
    }

    public void setPutInto(Date putInto) {
        this.putInto = putInto;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Integer getPublicNum() {
        return publicNum;
    }

    public void setPublicNum(Integer publicNum) {
        this.publicNum = publicNum;
    }

    public Integer getPrivateNum() {
        return privateNum;
    }

    public void setPrivateNum(Integer privateNum) {
        this.privateNum = privateNum;
    }
}
