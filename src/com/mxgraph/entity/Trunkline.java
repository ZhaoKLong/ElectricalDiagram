package com.mxgraph.entity;

import com.alibaba.fastjson.JSONArray;
import lombok.Data;

import java.util.List;

/**
 * @Description
 * @Author Zkl
 * @Date 2021-01-06
 */

@Data
public class Trunkline {
    /**
     * id
     */
    private Integer trunklineId;

    /**
     * 父id
     */
    private Integer parentId;

    /**
     * 线路名
     */
    private String name;

    /**
     * 备注（环境信息）
     */
    private String remark;

    /**
     * 支线
     */
    private List<Trunkline> children;

    /**
     * 线路数据
     */
    private JSONArray data;

    public Integer getTrunklineId() {
        return trunklineId;
    }

    public void setTrunklineId(Integer trunklineId) {
        this.trunklineId = trunklineId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<Trunkline> getChildren() {
        return children;
    }

    public void setChildren(List<Trunkline> children) {
        this.children = children;
    }

    public JSONArray getData() {
        return data;
    }

    public void setData(JSONArray data) {
        this.data = data;
    }
}
