package com.mxgraph.entity.excelEntity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import java.util.Date;

/**
 * @description: 基础资料(表头)
 * @author: Zhaokl
 * @create: 2021-01-13 11:14
 **/
//@OnceAbsoluteMerge(firstRowIndex = 1, lastRowIndex = 1, firstColumnIndex = 1, lastColumnIndex = 8)
@HeadFontStyle(fontHeightInPoints = 10, fontName = "等线", bold = false)
@HeadRowHeight(20)
@HeadStyle(fillForegroundColor = 9)
@ContentFontStyle(fontHeightInPoints = 9, color = 10)
@ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
@ContentRowHeight(16)
public class BaseHeadData {
    @ExcelProperty({"配电线路设备基本情况统计表", "序号"})
    private Integer index;
    @ExcelProperty({"配电线路设备基本情况统计表", "出口变电站"})
    private String factory;
    @ExcelProperty({"配电线路设备基本情况统计表", "线路编号及名称"})
    private String trunklineName;
    @ExcelProperty({"配电线路设备基本情况统计表", "10kV配电线路", "线路总长度", "(km)"})
    private Double totalLength;
    @ExcelProperty({"配电线路设备基本情况统计表", "10kV配电线路", "主干线长度", "(km)"})
    private Double mainLength;
    @ExcelProperty({"配电线路设备基本情况统计表", "10kV配电线路", "主干线导线型号"})
    private String mainType;
    @ExcelProperty({"配电线路设备基本情况统计表", "10kV配电线路", "分支总长度", "(km)"})
    private Double branchLength;
    @ExcelProperty({"配电线路设备基本情况统计表", "10kV配电线路", "绝缘导线长度", "(km)"})
    private Double insulationLength;
    @ExcelProperty({"配电线路设备基本情况统计表", "10kV配电线路", "电缆", "(km)"})
    private Double cableLength;
    @ExcelProperty({"配电线路设备基本情况统计表", "10kV配电线路", "裸导线", "(km)"})
    private Double bareWireLength;
    @ExcelProperty({"配电线路设备基本情况统计表", "10kV设备", "开关", "(台)"})
    private Integer switchNum;
    @ExcelProperty({"配电线路设备基本情况统计表", "10kV设备", "电容器", "(组)"})
    private Integer capacitorNum;
    @ExcelProperty({"配电线路设备基本情况统计表", "配电变压器", "配变总台数", "(台)"})
    private Integer transformerNum;
    @ExcelProperty({"配电线路设备基本情况统计表", "配电变压器", "配变总容量", "(kVA)"})
    private Double transformerCapacity;
    @ExcelProperty({"配电线路设备基本情况统计表", "配电变压器", "箱变数量", "(台)"})
    private Integer boxTransformerNum;
    @ExcelProperty({"配电线路设备基本情况统计表", "配电变压器", "箱变容量", "(kVA)"})
    private Double boxTransformerCapacity;
    @ExcelProperty({"配电线路设备基本情况统计表", "配电变压器", "柱上变数量", "(台)"})
    private Integer poleTransformerNum;
    @ExcelProperty({"配电线路设备基本情况统计表", "配电变压器", "柱上变容量", "(kVA)"})
    private Double poleTransformerCapacity;
    @ExcelProperty({"配电线路设备基本情况统计表", "配电变压器", "公用数量", "(台)"})
    private Integer publicTransformerNum;
    @ExcelProperty({"配电线路设备基本情况统计表", "配电变压器", "公用容量", "(kVA)"})
    private Double publicTransformerCapacity;
    @ExcelProperty({"配电线路设备基本情况统计表", "配电变压器", "专用数量", "(台)"})
    private Integer privateTransformerNum;
    @ExcelProperty({"配电线路设备基本情况统计表", "配电变压器", "专用容量", "(kVA)"})
    private Double privateTransformerCapacity;
    @ExcelProperty({"配电线路设备基本情况统计表", "低压线路", "合计", "(km)"})
    private Double lowPressure;
    @ExcelProperty({"配电线路设备基本情况统计表", "低压线路", "0.4kV", "(km)"})
    private Double lowPressure4;
    @ExcelProperty({"配电线路设备基本情况统计表", "低压线路", "0.2kV", "(km)"})
    private Double lowPressure2;
    @ExcelProperty({"配电线路设备基本情况统计表", "线路投运时间", "(年/月)"})
    @DateTimeFormat("yyyy-MM-dd")
    private Date putInto;
    @ExcelProperty({"配电线路设备基本情况统计表", "户表", "(户)"})
    private Integer household;
}
