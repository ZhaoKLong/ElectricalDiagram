package com.mxgraph.entity.excelEntity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import java.util.Date;

/**
 * @description: 交叉跨越(表头)
 * @author: Zhaokl
 * @create: 2021-01-14 17:19
 **/
@HeadFontStyle(fontHeightInPoints = 10, fontName = "等线", bold = false)
@HeadRowHeight(30)
@HeadStyle(fillForegroundColor = 9)
@ContentFontStyle(fontHeightInPoints = 10, fontName = "宋体")
@ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
@ContentRowHeight(16)
public class CrossOverHeadData {
    @ExcelProperty({"交叉跨越记录", "序号"})
    private Integer index;
    @ContentStyle(horizontalAlignment = HorizontalAlignment.LEFT)
    @ColumnWidth(50)
    @ExcelProperty({"交叉跨越记录", "线路名称"})
    private String name;
    @ExcelProperty({"交叉跨越记录", "起止杆号"})
    private String poleId;
    @ExcelProperty({"交叉跨越记录", "杆型"})
    private String type;
    @ExcelProperty({"交叉跨越记录", "杆高(m)"})
    private Double height;
    @ExcelProperty({"交叉跨越记录", "档距(m)"})
    private Double span;
    @ExcelProperty({"交叉跨越记录", "被跨越物"})
    private String thing;
    @ExcelProperty({"交叉跨越记录", "垂直距离(m)"})
    private Double length;
    @ExcelProperty({"交叉跨越记录", "测量方法"})
    private String way;
    @ExcelProperty({"交叉跨越记录", "测量时间"})
    @DateTimeFormat("yyyy-MM-dd")
    private Date time;
    @ExcelProperty({"交叉跨越记录", "测量温度"})
    private Double temperature;
    @ExcelProperty({"交叉跨越记录", "测量人"})
    private String user;
    @ExcelProperty({"交叉跨越记录", "是否合格"})
    private String isOk;
    @ExcelProperty({"交叉跨越记录", "备注"})
    private String remark;
}
