package com.mxgraph.entity.excelEntity;

import lombok.Data;

import java.util.Date;

/**
 * @description: 基础资料
 * @author: Zhaokl
 * @create: 2021-01-13 11:58
 **/
@Data
public class BaseData {
    private Integer index;
    private String factory;
    private String trunklineName;
    private Double totalLength;
    private Double mainLength;
    private String mainType;
    private Double branchLength;
    private Double insulationLength;
    private Double cableLength;
    private Double bareWireLength;
    private Integer capacitorNum;
    private Integer switchNum;
    private Integer transformerNum;
    private Double transformerCapacity;
    private Integer boxTransformerNum;
    private Double boxTransformerCapacity;
    private Integer poleTransformerNum;
    private Double poleTransformerCapacity;
    private Integer publicTransformerNum;
    private Double publicTransformerCapacity;
    private Integer privateTransformerNum;
    private Double privateTransformerCapacity;
    private Double lowPressure;
    private Double lowPressure4;
    private Double lowPressure2;
    private Date putInto;
    private Integer household;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getFactory() {
        return factory;
    }

    public void setFactory(String factory) {
        this.factory = factory;
    }

    public String getTrunklineName() {
        return trunklineName;
    }

    public void setTrunklineName(String trunklineName) {
        this.trunklineName = trunklineName;
    }

    public Double getTotalLength() {
        return totalLength;
    }

    public void setTotalLength(Double totalLength) {
        this.totalLength = totalLength;
    }

    public Double getMainLength() {
        return mainLength;
    }

    public void setMainLength(Double mainLength) {
        this.mainLength = mainLength;
    }

    public String getMainType() {
        return mainType;
    }

    public void setMainType(String mainType) {
        this.mainType = mainType;
    }

    public Double getBranchLength() {
        return branchLength;
    }

    public void setBranchLength(Double branchLength) {
        this.branchLength = branchLength;
    }

    public Double getInsulationLength() {
        return insulationLength;
    }

    public void setInsulationLength(Double insulationLength) {
        this.insulationLength = insulationLength;
    }

    public Double getCableLength() {
        return cableLength;
    }

    public void setCableLength(Double cableLength) {
        this.cableLength = cableLength;
    }

    public Double getBareWireLength() {
        return bareWireLength;
    }

    public void setBareWireLength(Double bareWireLength) {
        this.bareWireLength = bareWireLength;
    }

    public Integer getCapacitorNum() {
        return capacitorNum;
    }

    public void setCapacitorNum(Integer capacitorNum) {
        this.capacitorNum = capacitorNum;
    }

    public Integer getSwitchNum() {
        return switchNum;
    }

    public void setSwitchNum(Integer switchNum) {
        this.switchNum = switchNum;
    }

    public Integer getTransformerNum() {
        return transformerNum;
    }

    public void setTransformerNum(Integer transformerNum) {
        this.transformerNum = transformerNum;
    }

    public Double getTransformerCapacity() {
        return transformerCapacity;
    }

    public void setTransformerCapacity(Double transformerCapacity) {
        this.transformerCapacity = transformerCapacity;
    }

    public Integer getBoxTransformerNum() {
        return boxTransformerNum;
    }

    public void setBoxTransformerNum(Integer boxTransformerNum) {
        this.boxTransformerNum = boxTransformerNum;
    }

    public Double getBoxTransformerCapacity() {
        return boxTransformerCapacity;
    }

    public void setBoxTransformerCapacity(Double boxTransformerCapacity) {
        this.boxTransformerCapacity = boxTransformerCapacity;
    }

    public Integer getPoleTransformerNum() {
        return poleTransformerNum;
    }

    public void setPoleTransformerNum(Integer poleTransformerNum) {
        this.poleTransformerNum = poleTransformerNum;
    }

    public Double getPoleTransformerCapacity() {
        return poleTransformerCapacity;
    }

    public void setPoleTransformerCapacity(Double poleTransformerCapacity) {
        this.poleTransformerCapacity = poleTransformerCapacity;
    }

    public Integer getPublicTransformerNum() {
        return publicTransformerNum;
    }

    public void setPublicTransformerNum(Integer publicTransformerNum) {
        this.publicTransformerNum = publicTransformerNum;
    }

    public Double getPublicTransformerCapacity() {
        return publicTransformerCapacity;
    }

    public void setPublicTransformerCapacity(Double publicTransformerCapacity) {
        this.publicTransformerCapacity = publicTransformerCapacity;
    }

    public Integer getPrivateTransformerNum() {
        return privateTransformerNum;
    }

    public void setPrivateTransformerNum(Integer privateTransformerNum) {
        this.privateTransformerNum = privateTransformerNum;
    }

    public Double getPrivateTransformerCapacity() {
        return privateTransformerCapacity;
    }

    public void setPrivateTransformerCapacity(Double privateTransformerCapacity) {
        this.privateTransformerCapacity = privateTransformerCapacity;
    }

    public Double getLowPressure() {
        return lowPressure;
    }

    public void setLowPressure(Double lowPressure) {
        this.lowPressure = lowPressure;
    }

    public Double getLowPressure4() {
        return lowPressure4;
    }

    public void setLowPressure4(Double lowPressure4) {
        this.lowPressure4 = lowPressure4;
    }

    public Double getLowPressure2() {
        return lowPressure2;
    }

    public void setLowPressure2(Double lowPressure2) {
        this.lowPressure2 = lowPressure2;
    }

    public Date getPutInto() {
        return putInto;
    }

    public void setPutInto(Date putInto) {
        this.putInto = putInto;
    }

    public Integer getHousehold() {
        return household;
    }

    public void setHousehold(Integer household) {
        this.household = household;
    }
}
