package com.mxgraph.examples.swing;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * @description:
 * @author: Zhaokl
 * @create: 2020-12-08 16:06
 **/
public class MyTable {
    public static JTable getMyTable(String path) {
        Object[][] playerInfo = {
                // 创建表格中的数据
                {"线路名称", "xxx"},
                {"线路长度", "xxx"},
                {"绝缘长度", "xxx"},
                {"架空长度", "xxx"},
                {"电缆长度", "xxx"},
                {"配变台数", "xxx"},
                {"配变容量", "xxx"},
                {"公变台数", "xxx"},
                {"公变容量", "xxx"},
                {"专变台数", "xxx"},
                {"专变容量", "xxx"}
        };
        // 创建表格中的横标题
        String[] Names = {"name", "value"};
        // 以Names和playerInfo为参数，创建一个表格
        JTable table = new JTable(playerInfo, Names);
        table.setRowHeight(48);
        DefaultTableCellRenderer render = new DefaultTableCellRenderer();
        render.setHorizontalAlignment(SwingConstants.CENTER);
        table.getColumn("name").setCellRenderer(render);
        DefaultTableCellRenderer render1 = new DefaultTableCellRenderer();
        render1.setHorizontalAlignment(SwingConstants.RIGHT);
        table.getColumn("value").setCellRenderer(render1);
        // 设置此表视图的首选大小
        table.setPreferredScrollableViewportSize(new Dimension(200, 1200));
        // 将表格加入到滚动条组件中
        return table;
    }
}
