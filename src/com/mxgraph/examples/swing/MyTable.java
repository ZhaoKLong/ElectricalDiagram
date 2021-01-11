package com.mxgraph.examples.swing;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mxgraph.entity.Trunkline;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * @description:
 * @author: Zhaokl
 * @create: 2020-12-08 16:06
 **/
public class MyTable extends JTable {
    private static MyTable myTable;
    private static DefaultTableModel defaultTableModel = new DefaultTableModel();
    private static Object[][] playerInfo = {
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

    /**
     * 创建表格中的横标题
     */
    private static String[] names = {"name", "value"};

    public MyTable() {
        super(defaultTableModel);
        myTable = this;
        defaultTableModel.setDataVector(playerInfo, names);
        // 以Names和playerInfo为参数，创建一个表格
        this.setRowHeight(48);
        DefaultTableCellRenderer render = new DefaultTableCellRenderer();
        render.setHorizontalAlignment(SwingConstants.CENTER);
        this.getColumn("name").setCellRenderer(render);
        DefaultTableCellRenderer render1 = new DefaultTableCellRenderer();
        render1.setHorizontalAlignment(SwingConstants.RIGHT);
        this.getColumn("value").setCellRenderer(render1);
        // 设置此表视图的首选大小
        this.setPreferredScrollableViewportSize(new Dimension(200, 1200));
        this.setEnabled(false);
        // 将表格加入到滚动条组件中
    }

    public static void changeData(String name, JSONArray jsonArray) {
        double data2 = 0;
        double data3 = 0;
        double data4 = 0;
        double data5 = 0;
        int data6 = 0;
        double data7 = 0;
        int data8 = 0;
        double data9 = 0;
        int data10 = 0;
        double data11 = 0;
        for (Object aJsonArray : jsonArray) {
            JSONObject jsonObject = (JSONObject) aJsonArray;
            JSONArray nextEquipmentList = jsonObject.getJSONObject("next").getJSONArray("equipmentList");
            if (nextEquipmentList.size() != 0) {
                for (Object aNextEquipmentList : nextEquipmentList) {
                    JSONObject aNextEquipmentList1 = (JSONObject) aNextEquipmentList;
                    if ((Integer) aNextEquipmentList1.get("type") == 1) {
                        data6++;
                        data8++;
                        Object capacity = aNextEquipmentList1.get("capacity");
                        if (capacity.getClass() == Integer.class) {
                            data7 += ((Integer) capacity).doubleValue();
                            data9 += ((Integer) capacity).doubleValue();
                        } else {
                            data7 += (Double) capacity;
                            data9 += (Double) capacity;
                        }
                    } else if ((Integer) aNextEquipmentList1.get("type") == 2) {
                        data6++;
                        data10++;
                        Object capacity = aNextEquipmentList1.get("capacity");
                        if (capacity.getClass() == Integer.class) {
                            data7 += ((Integer) capacity).doubleValue();
                            data11 += ((Integer) capacity).doubleValue();
                        } else {
                            data7 += (Double) capacity;
                            data11 += (Double) capacity;
                        }
                    } else if ((Integer) aNextEquipmentList1.get("type") == 13) {
                        data6++;
                        data8++;
                        Object capacity = aNextEquipmentList1.get("capacity");
                        if (capacity.getClass() == Integer.class) {
                            data7 += ((Integer) capacity).doubleValue();
                            data9 += ((Integer) capacity).doubleValue();
                        } else {
                            data7 += (Double) capacity;
                            data9 += (Double) capacity;
                        }
                    } else if ((Integer) aNextEquipmentList1.get("type") == 7) {
                        data6++;
                        data10++;
                        Object capacity = aNextEquipmentList1.get("capacity");
                        if (capacity.getClass() == Integer.class) {
                            data7 += ((Integer) capacity).doubleValue();
                            data11 += ((Integer) capacity).doubleValue();
                        } else {
                            data7 += (Double) capacity;
                            data11 += (Double) capacity;
                        }
                    }
                }
            }
            JSONArray prevEquipmentList = jsonObject.getJSONObject("prev").getJSONArray("equipmentList");
            if (prevEquipmentList.size() != 0) {
                for (Object aPrevEquipmentList : prevEquipmentList) {
                    JSONObject aPrevEquipmentList1 = (JSONObject) aPrevEquipmentList;
                    if ((Integer) aPrevEquipmentList1.get("type") == 1) {
                        data6++;
                        data8++;
                        Object capacity = aPrevEquipmentList1.get("capacity");
                        if (capacity.getClass() == Integer.class) {
                            data7 += ((Integer) capacity).doubleValue();
                            data9 += ((Integer) capacity).doubleValue();
                        } else {
                            data7 += (Double) capacity;
                            data9 += (Double) capacity;
                        }
                    } else if ((Integer) aPrevEquipmentList1.get("type") == 2) {
                        data6++;
                        data10++;
                        Object capacity = aPrevEquipmentList1.get("capacity");
                        if (capacity.getClass() == Integer.class) {
                            data7 += ((Integer) capacity).doubleValue();
                            data11 += ((Integer) capacity).doubleValue();
                        } else {
                            data7 += (Double) capacity;
                            data11 += (Double) capacity;
                        }
                    } else if ((Integer) aPrevEquipmentList1.get("type") == 13) {
                        data6++;
                        data8++;
                        Object capacity = aPrevEquipmentList1.get("capacity");
                        if (capacity.getClass() == Integer.class) {
                            data7 += ((Integer) capacity).doubleValue();
                            data9 += ((Integer) capacity).doubleValue();
                        } else {
                            data7 += (Double) capacity;
                            data9 += (Double) capacity;
                        }
                    } else if ((Integer) aPrevEquipmentList1.get("type") == 7) {
                        data6++;
                        data10++;
                        Object capacity = aPrevEquipmentList1.get("capacity");
                        if (capacity.getClass() == Integer.class) {
                            data7 += ((Integer) capacity).doubleValue();
                            data11 += ((Integer) capacity).doubleValue();
                        } else {
                            data7 += (Double) capacity;
                            data11 += (Double) capacity;
                        }
                    }
                }
            }
            Object type = jsonObject.get("type");
            Object length = jsonObject.get("length");
            if (length.getClass() == Integer.class) {
                if (null != type && (Integer) type == 12) {
                    data5 += ((Integer) length).doubleValue();
                } else {
                    data4 += ((Integer) length).doubleValue();
                }
                data2 += ((Integer) length).doubleValue();
            } else {
                if ((Integer) type == 12) {
                    data5 += (Double) length;
                } else {
                    data4 += (Double) length;
                }
                data2 += (Double) length;
            }
        }
        playerInfo = new Object[][]{
                {"线路名称", name},
                {"线路长度", data2},
                {"绝缘长度", data3},
                {"架空长度", data4},
                {"电缆长度", data5},
                {"配变台数", data6},
                {"配变容量", data7},
                {"公变台数", data8},
                {"公变容量", data9},
                {"专变台数", data10},
                {"专变容量", data11}
        };
        defaultTableModel.setDataVector(playerInfo, names);
        DefaultTableCellRenderer render = new DefaultTableCellRenderer();
        render.setHorizontalAlignment(SwingConstants.CENTER);
        myTable.getColumn("name").setCellRenderer(render);
        DefaultTableCellRenderer render1 = new DefaultTableCellRenderer();
        render1.setHorizontalAlignment(SwingConstants.RIGHT);
        myTable.getColumn("value").setCellRenderer(render1);
    }
}
