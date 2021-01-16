package com.mxgraph.entity.excelEntity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import java.util.Date;

/**
 * @description: 电容器(表头)
 * @author: Zhaokl
 * @create: 2021-01-14 16:58
 **/
@HeadFontStyle(fontHeightInPoints = 10, fontName = "等线", bold = false)
@HeadRowHeight(30)
@HeadStyle(fillForegroundColor = 9)
@ContentFontStyle(fontHeightInPoints = 10, fontName = "宋体")
@ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
@ContentRowHeight(20)
public class CapacitorHeadData {
    @ExcelProperty({"电容器台账", "序号"})
    private Integer index;
    @ExcelProperty({"电容器台账", "装设地点(杆号)"})
    private String poleId;
    @ExcelProperty({"电容器台账", "型号"})
    private String model;
    @ExcelProperty({"电容器台账", "台数"})
    private Integer num;
    @ExcelProperty({"电容器台账", "总容量(Kvar)"})
    private Double capacity;
    @ExcelProperty({"电容器台账", "投运日期"})
    @DateTimeFormat("yyyy-MM-dd")
    private Date putInto;
    @ExcelProperty({"电容器台账", "厂家"})
    private String vendor;
    @ExcelProperty({"电容器台账", "备注"})
    private String remark;
}
