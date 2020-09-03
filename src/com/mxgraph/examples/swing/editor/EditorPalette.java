package com.mxgraph.examples.swing.editor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Point;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.TransferHandler;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.swing.util.mxGraphTransferable;
import com.mxgraph.swing.util.mxSwingConstants;
import com.mxgraph.util.mxEvent;
import com.mxgraph.util.mxEventObject;
import com.mxgraph.util.mxEventSource;
import com.mxgraph.util.mxPoint;
import com.mxgraph.util.mxRectangle;
import com.mxgraph.util.mxEventSource.mxIEventListener;

public class EditorPalette extends JPanel {

    /**
     * 选中项
     */
    protected JLabel selectedEntry = null;

    /**
     * 事件源
     */
    protected mxEventSource eventSource = new mxEventSource(this);

    /**
     * 生成外层容器
     */
    @SuppressWarnings("serial")
    public EditorPalette() {
        // 设置背景颜色
        setBackground(new Color(149, 230, 190));
        // 设置布局方式（流式布局）
        setLayout(new FlowLayout(FlowLayout.LEADING, 5, 5));
        // 添加鼠标事件监听
        addMouseListener(new MouseListener() {
            /*
             *  鼠标按下
             */
            public void mousePressed(MouseEvent e) {
                clearSelection();
            }

            /*
             * 鼠标点击并松开
             */
            public void mouseClicked(MouseEvent e) {
            }

            /*
             * 鼠标移入
             */
            public void mouseEntered(MouseEvent e) {
            }

            /*
             * 鼠标移出
             */
            public void mouseExited(MouseEvent e) {
            }

            /*
             * 鼠标释放
             */
            public void mouseReleased(MouseEvent e) {
            }
        });

        /*
         * Shows a nice icon for drag and drop but doesn't import anything
         * 设置数据传输属性
         */
        setTransferHandler(new TransferHandler() {
            public boolean canImport(JComponent comp, DataFlavor[] flavors) {
                return true;
            }
        });
    }

    /**
     * 清空选中项
     */
    public void clearSelection() {
        setSelectionEntry(null, null);
    }

    /**
     * 设置选中项
     */
    public void setSelectionEntry(JLabel entry, mxGraphTransferable t) {
        JLabel previous = selectedEntry;
        selectedEntry = entry;
        // 如果之前有选中项，重置其样式
        if (previous != null) {
            previous.setBorder(null);
            previous.setOpaque(false);
        }
        if (selectedEntry != null) {
            selectedEntry.setBorder(ShadowBorder.getSharedInstance());
            selectedEntry.setOpaque(true);
        }
        // 启动选择事件
        eventSource.fireEvent(new mxEventObject(mxEvent.SELECT, "entry", selectedEntry, "transferable", t, "previous", previous));
    }

    /**
     * 设置可滚动容器总宽高
     */
    public void setPreferredWidth(int width) {
        int cols = Math.max(1, width / 55);
        setPreferredSize(new Dimension(width, (getComponentCount() * 55 / cols) + 30));
        // 重新生成
        revalidate();
    }

    /**
     * 添加连接线模板
     *
     * @param name   名称
     * @param icon   图标
     * @param style  样式
     * @param width  宽度
     * @param height 高度
     * @param value  值
     */
    public void addEdgeTemplate(final String name, ImageIcon icon, String style, int width, int height, Object value) {
        // 单元几何形状
        mxGeometry geometry = new mxGeometry(0, 0, width, height);
        // 设置源点
        geometry.setTerminalPoint(new mxPoint(0, height), true);
        // 设置目标点
        geometry.setTerminalPoint(new mxPoint(width, 0), false);
        // 设置几何相对状态
        geometry.setRelative(true);
        // 添加单元格
        mxCell cell = new mxCell(value, geometry, style);
        // 将单元格设置为线形
        cell.setEdge(true);
        // 添加该线形到模板列表中
        addTemplate(name, icon, cell);
    }

    /**
     * 添加顶点模板
     *
     * @param name   名称
     * @param icon   图标
     * @param style  样式
     * @param width  初始宽度
     * @param height 初始高度
     * @param value  值
     */
    public void addTemplate(final String name, ImageIcon icon, String style, int width, int height, Object value) {
        // 添加单元格
        mxCell cell = new mxCell(value, new mxGeometry(0, 0, width, height), style);
        // 将单元格设置为顶点
        cell.setVertex(true);
        // 添加该顶点到模板列表中
        addTemplate(name, icon, cell);
    }

    /**
     * 将单元格添加到模板列表中
     *
     * @param name 名称
     * @param icon 图标
     * @param cell 单元格
     */
    public void addTemplate(final String name, ImageIcon icon, mxCell cell) {
        // 将单元格设为二维矩形
        mxRectangle bounds = (mxGeometry) cell.getGeometry().clone();
        // 序列化
        final mxGraphTransferable t = new mxGraphTransferable(new Object[]{cell}, bounds);
        // 统一图标大小
        if (icon != null) {
            if (icon.getIconWidth() > 32 || icon.getIconHeight() > 32) {
                icon = new ImageIcon(icon.getImage().getScaledInstance(32, 32, 0));
            }
        }
        final JLabel entry = new JLabel(icon);
        // 设置首选大小
        entry.setPreferredSize(new Dimension(50, 50));
        // 设置背景颜色
        entry.setBackground(EditorPalette.this.getBackground().brighter());
        // 设置字体
        entry.setFont(new Font(entry.getFont().getFamily(), 0, 10));
        // 设置文字垂直排列位置
        entry.setVerticalTextPosition(JLabel.BOTTOM);
        // 设置文字水平排列位置
        entry.setHorizontalTextPosition(JLabel.CENTER);
        // 设置图片和文字间的间隔
        entry.setIconTextGap(0);
        // 设置提示文字
        entry.setToolTipText(name);
        // 设置文字
        entry.setText(name);
        // 添加鼠标监听
        entry.addMouseListener(new MouseListener() {
            /*
             * 鼠标按下
             */
            public void mousePressed(MouseEvent e) {
                setSelectionEntry(entry, t);
            }

            /*
             * 鼠标点击
             */
            public void mouseClicked(MouseEvent e) {
            }

            /*
             * 鼠标移入
             */
            public void mouseEntered(MouseEvent e) {
            }

            /*
             * 鼠标移出
             */
            public void mouseExited(MouseEvent e) {
            }

            /*
             * 鼠标释放
             */
            public void mouseReleased(MouseEvent e) {
            }
        });

        // 鼠标拖拽事件监听
        DragGestureListener dragGestureListener = new DragGestureListener() {
            /**
             * 拖动手势识别
             */
            public void dragGestureRecognized(DragGestureEvent e) {
                e.startDrag(null, mxSwingConstants.EMPTY_IMAGE, new Point(), t, null);
            }
        };
        // 创建用于接收拖动源事件的抽象适配器
        DragSource dragSource = new DragSource();
        // 创建一个新的DragGestureRecognizer（是一个针对与平台相关的侦听器规范的抽象基类，它可以与特定 Component 关联以标识与平台相关的拖动开始动作）
        dragSource.createDefaultDragGestureRecognizer(entry, DnDConstants.ACTION_COPY, dragGestureListener);
        // 将事件添加到容器
        add(entry);
    }

    /**
     * 添加事件监听器
     *
     * @param eventName 事件名称
     * @param listener  监听器
     * @see mxEventSource#addListener(String, mxIEventListener)
     */
    public void addListener(String eventName, mxIEventListener listener) {
        eventSource.addListener(eventName, listener);
    }

    /**
     * 移除事件监听器
     *
     * @param listener 监听器
     * @see mxEventSource#removeListener(mxIEventListener)
     */
    public void removeListener(mxIEventListener listener) {
        eventSource.removeListener(listener);
    }

    /**
     * 移除事件监听器
     *
     * @param eventName 事件名称
     * @param listener  监听器
     * @see mxEventSource#removeListener(String, mxIEventListener)
     */
    public void removeListener(mxIEventListener listener, String eventName) {
        eventSource.removeListener(listener, eventName);
    }
}
