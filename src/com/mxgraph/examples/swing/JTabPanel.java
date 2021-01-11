package com.mxgraph.examples.swing;

import com.alibaba.fastjson.JSONArray;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class JTabPanel extends JTabbedPane {
    private static JTabPanel jTabPanel;

    private static List<Object> dataList = new ArrayList<>();

    public JTabPanel() {
        jTabPanel = this;

        //设置标签面板的changeListener,注册事件
        jTabPanel.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
            }
        });

        //注册鼠标点击事件
        jTabPanel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                MyTable.changeData((String) dataList.get(jTabPanel.getSelectedIndex() * 2), (JSONArray) dataList.get(jTabPanel.getSelectedIndex() * 2 + 1));
                if (e.getButton() == 2) {
                    closeIndex(jTabPanel.getSelectedIndex());
                }
                if (e.getButton() == 1 && e.getClickCount() == 2) {
                    closeIndex(jTabPanel.getSelectedIndex());
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
    }

    public static void addPanel(String name, JSONArray dataJsonArray) {
        dataList.add(name);
        dataList.add(dataJsonArray);
        int tabNum = jTabPanel.getTabCount();
        boolean isCreate = true;
        int index = 0;
        for (int i = 0; i < tabNum; i++) {
            String titleAt = jTabPanel.getTitleAt(i);
            if (titleAt.equals(name)) {
                isCreate = false;
                index = i;
            }
        }
        if (isCreate) {
            JScrollPane jScrollPane = new JScrollPane(new MyPanel(dataJsonArray));
            jTabPanel.add(name, jScrollPane);
            jTabPanel.setSelectedComponent(jScrollPane);
        } else {
            jTabPanel.setSelectedComponent(jTabPanel.getComponentAt(index));
        }
    }

    private void closeIndex(int index) {
        removeTabAt(index);
        dataList.remove(index * 2);
        dataList.remove(index * 2);
    }
}