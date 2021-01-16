package com.mxgraph.entity.excelEntity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import java.util.Date;

/**
 * @description: 开关(表头)
 * @author: Zhaokl
 * @create: 2021-01-14 17:07
 **/
@HeadFontStyle(fontHeightInPoints = 10, fontName = "等线", bold = false)
@HeadRowHeight(20)
@HeadStyle(fillForegroundColor = 9)
@ContentFontStyle(fontHeightInPoints = 10, fontName = "宋体")
@ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
@ContentRowHeight(20)
public class SwitchHeadData {
    @ExcelProperty({"开关台账", "序号"})
    private Integer index;
    @ContentStyle(horizontalAlignment = HorizontalAlignment.LEFT)
    @ColumnWidth(50)
    @ExcelProperty({"开关台账", "开关编号"})
    private String name;
    @ExcelProperty({"开关台账", "装设地点(杆号)"})
    private String poleId;
    @ExcelProperty({"开关台账", "型号"})
    private String model;
    @ExcelProperty({"开关台账", "厂家"})
    private String vendor;
    @ExcelProperty({"开关台账", "安装时间"})
    @DateTimeFormat("yyyy-MM-dd")
    private Date putInto;
    @ExcelProperty({"开关台账", "产权归属"})
    private String user;
    @ExcelProperty({"开关台账", "开关下侧变台数", "公变"})
    private Integer publicNum;
    @ExcelProperty({"开关台账", "开关下侧变台数", "专变"})
    private Integer privateNum;
}
