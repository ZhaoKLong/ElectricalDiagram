package com.mxgraph.entity.excelEntity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import java.util.Date;

/**
 * @description: 变压器(表头)
 * @author: Zhaokl
 * @create: 2021-01-14 16:44
 **/
@HeadFontStyle(fontHeightInPoints = 10, fontName = "等线", bold = false)
@HeadRowHeight(30)
@HeadStyle(fillForegroundColor = 9)
@ContentFontStyle(fontHeightInPoints = 10, fontName = "宋体")
@ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
@ContentRowHeight(16)
public class TransformerHeadData {
    @ExcelProperty({"配电变压器台账", "序号"})
    private Integer index;
    @ContentStyle(horizontalAlignment = HorizontalAlignment.LEFT)
    @ColumnWidth(50)
    @ExcelProperty({"配电变压器台账", "配变名称"})
    private String name;
    @ExcelProperty({"配电变压器台账", "性质"})
    private String isPublic;
    @ExcelProperty({"配电变压器台账", "类型"})
    private String type;
    @ExcelProperty({"配电变压器台账", "配变型号"})
    private String model;
    @ExcelProperty({"配电变压器台账", "配变容量(kVA)"})
    private Double capacity;
    @ExcelProperty({"配电变压器台账", "投运年份"})
    @DateTimeFormat("yyyy-MM-dd")
    private Date putInto;
    @ExcelProperty({"配电变压器台账", "厂家"})
    private String vendor;
    @ExcelProperty({"配电变压器台账", "主要用户"})
    private String user;
}
