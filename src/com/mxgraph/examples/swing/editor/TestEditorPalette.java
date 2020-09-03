package com.mxgraph.examples.swing.editor;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * @description: 测试EditorPalette
 * @author: Zhaokl
 * @create: 2020-09-03 19:24
 **/
public class TestEditorPalette extends JPanel {

    /**
     * 生成外层容器
     */
    @SuppressWarnings("serial")
    public TestEditorPalette() {
        // 设置背景颜色
//        setBackground(new Color(255, 255, 255));
        // 设置布局方式（流式布局）
//        setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        // 添加鼠标事件监听
//        addMouseListener(new MouseListener() {
//            /*
//             *  鼠标按下
//             */
//            public void mousePressed(MouseEvent e) {
//            }
//
//            /*
//             * 鼠标点击并松开
//             */
//            public void mouseClicked(MouseEvent e) {
//            }
//
//            /*
//             * 鼠标移入
//             */
//            public void mouseEntered(MouseEvent e) {
//            }
//
//            /*
//             * 鼠标移出
//             */
//            public void mouseExited(MouseEvent e) {
//            }
//
//            /*
//             * 鼠标释放
//             */
//            public void mouseReleased(MouseEvent e) {
//            }
//        });

        /*
         * Shows a nice icon for drag and drop but doesn't import anything
         * 设置数据传输属性
         */
//        setTransferHandler(new TransferHandler() {
//            public boolean canImport(JComponent comp, DataFlavor[] flavors) {
//                return true;
//            }
//        });
    }


    public void setPreferredWidth(int width) {
        int cols = Math.max(1, width / 55);
        setPreferredSize(new Dimension(width, (getComponentCount() * 55 / cols) + 30));
        // 重新生成
        revalidate();
    }
}
