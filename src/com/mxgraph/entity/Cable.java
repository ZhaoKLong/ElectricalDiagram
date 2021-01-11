package com.mxgraph.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description
 * @Author Zkl
 * @Date 2020-10-09
 */

@Data
public class Cable {

    /**
     * 名字
     */
    private String name;

    /**
     * 上一电杆
     */
    private Pole prev;

    /**
     * 下一电杆
     */
    private Pole next;

    /**
     * 电缆长度（m）
     */
    private Double length;

    /**
     * 电缆类型(type表)
     */
    private Long type;

    /**
     * 备注（环境信息）
     */
    private String remark;

    /**
     * 电杆间距离(m)
     */
    private Double distance;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Pole getPrev() {
        return prev;
    }

    public void setPrev(Pole prev) {
        this.prev = prev;
    }

    public Pole getNext() {
        return next;
    }

    public void setNext(Pole next) {
        this.next = next;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }
}
