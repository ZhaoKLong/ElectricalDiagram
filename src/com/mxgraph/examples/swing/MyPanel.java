package com.mxgraph.examples.swing;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static com.mxgraph.examples.swing.Util.*;

/**
 * @description:
 * @author: Zhaokl
 * @create: 2020-12-07 16:33
 **/

public class MyPanel extends JPanel {

    private static final int Margin = 20;
    private static final int LineWidth = 1;
    private static final int ImageSize = 16;
    private static final int FontSize = 3;
    private static final int LocalScale = 116987;

    private Dimension preferredSize = new Dimension(800 - 19, 600 - 42);
    private double[] localBorder;
    private double length;
    private double scale;
    private JSONArray jsonArray;

    public MyPanel(JSONArray jsonArray) {
        super();
        this.jsonArray = jsonArray;
        setPreferredSize(preferredSize);
        localBorder = getLocalBorder(jsonArray);
        length = getLength(new double[]{localBorder[2], localBorder[0], localBorder[3], localBorder[1]});
        // mouse listener to detect scrollwheel events
        addMouseWheelListener(new MouseWheelListener() {
            public void mouseWheelMoved(MouseWheelEvent e) {
                // Ctrl按下
                if (e.isControlDown()) {
                    // getWheelRotation()返回滚轮的方向，值为-1或1
                    updatePreferredSize(e.getWheelRotation(), e.getPoint());
                }
            }
        });
    }

    private void updatePreferredSize(int wheelRotation, Point stablePoint) {
        double scaleFactor = findScaleFactor(wheelRotation);
        scaleBy(scaleFactor);
        Point offset = findOffset(stablePoint, scaleFactor);
        offsetBy(offset);
        getParent().doLayout();
    }

    private double findScaleFactor(int wheelRotation) {
        double d = wheelRotation * 1.08;
        return (d > 0) ? 1 / d : -d;
    }

    private void scaleBy(double scaleFactor) {
        int w = (int) (getWidth() * scaleFactor);
        int h = (int) (getHeight() * scaleFactor);
        preferredSize.setSize(w, h);
    }

    private Point findOffset(Point stablePoint, double scaleFactor) {
        int x = (int) (stablePoint.x * scaleFactor - stablePoint.x);
        int y = (int) (stablePoint.y * scaleFactor - stablePoint.y);
        return new Point(x, y);
    }

    private void offsetBy(Point offset) {
        Point location = getLocation();
        setLocation(location.x - offset.x, location.y - offset.y);
    }

    public Dimension getPreferredSize() {
        return preferredSize;
    }

    public void paint(Graphics g) {
        super.paint(g);
        if (length != 0.0) {
            draw(g);
        }
    }

    /**
     * 画图
     */
    private void draw(Graphics g) {
        // 创建 Graphics 的副本, 需要改变 Graphics 的参数,
        // 这里必须使用副本, 避免影响到 Graphics 原有的设置
        Graphics2D g2d = (Graphics2D) g.create();
        // 抗锯齿
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // 设置画笔颜色
        g2d.setColor(Color.BLACK);
        BasicStroke bs1 = new BasicStroke((int) ((scale * length * LineWidth) / (LocalScale * 800)));       // 笔画的轮廓（画笔宽度/线宽为5px）
        float[] dash = new float[]{5, 10};
        BasicStroke bs2 = new BasicStroke(
                (int) ((scale * length * LineWidth) / (LocalScale * 800)),                      // 画笔宽度/线宽
                BasicStroke.CAP_SQUARE,
                BasicStroke.JOIN_MITER,
                10.0f,
                dash,                   // 虚线模式数组
                0.0f
        );
        // bs1实线  bs2虚线
        g2d.setStroke(bs1);
        // 根据canvas宽高计算缩放级别
        double xScale = (getWidth() - Margin * 2) / (localBorder[1] - localBorder[0]);
        double yScale = (getHeight() - Margin * 2 - 50) / (localBorder[3] - localBorder[2]);
        scale = Math.min(xScale, yScale);
        List<JSONObject> allCable = getAllCable(jsonArray);
        for (JSONObject cable : allCable) {
            if (null == cable.get("type")) {
                //如果type==null 实线
                g2d.drawLine(
                        (int) ((Double.parseDouble((String) cable.getJSONObject("prev").get("longitude")) - localBorder[0]) * scale) + Margin,
                        (int) ((localBorder[3] - Double.parseDouble((String) cable.getJSONObject("prev").get("latitude"))) * scale) + Margin,
                        (int) ((Double.parseDouble((String) cable.getJSONObject("next").get("longitude")) - localBorder[0]) * scale) + Margin,
                        (int) ((localBorder[3] - Double.parseDouble((String) cable.getJSONObject("next").get("latitude"))) * scale) + Margin
                );
            } else {
                //如果type==12 虚线
                g2d.setStroke(bs2);
                g2d.drawLine(
                        (int) ((Double.parseDouble((String) cable.getJSONObject("prev").get("longitude")) - localBorder[0]) * scale) + Margin,
                        (int) ((localBorder[3] - Double.parseDouble((String) cable.getJSONObject("prev").get("latitude"))) * scale) + Margin,
                        (int) ((Double.parseDouble((String) cable.getJSONObject("next").get("longitude")) - localBorder[0]) * scale) + Margin,
                        (int) ((localBorder[3] - Double.parseDouble((String) cable.getJSONObject("next").get("latitude"))) * scale) + Margin
                );
                g2d.setStroke(bs1);
            }
            JSONArray objectEquipment = cable.getJSONObject("prev").getJSONArray("equipmentList");
            String filepath = "";
            int size = 0;
            if (null != objectEquipment) {
                size = objectEquipment.size();
            }
            if (size > 0) {
                for (int i = 0; i < size; i++) {
                    int type = (int) objectEquipment.getJSONObject(i).get("type");
                    filepath = getIconPath(type);
                    double degree = getDegree(Double.parseDouble((String) cable.getJSONObject("prev").get("longitude")) - localBorder[0], localBorder[3] - Double.parseDouble((String) cable.getJSONObject("prev").get("latitude")), Double.parseDouble((String) cable.getJSONObject("next").get("longitude")) - localBorder[0], localBorder[3] - Double.parseDouble((String) cable.getJSONObject("next").get("latitude")));
                    double[] offset = getOffset(degree, (scale * length * ImageSize) / (LocalScale * 800), cable, i);
                    // 绘制图片（如果宽高传的不是图片原本的宽高, 则图片将会适当缩放绘制）
                    drawImage(g2d, filepath, degree, (int) ((Double.parseDouble((String) cable.getJSONObject("prev").get("longitude")) - localBorder[0]) * scale + Margin + offset[0]),
                            (int) ((localBorder[3] - Double.parseDouble((String) cable.getJSONObject("prev").get("latitude"))) * scale + Margin + offset[1]));
                }
            }
        }
        //标记点名字
        for (JSONObject cable : allCable) {

            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // 设置字体样式, null 表示使用默认字体, Font.PLAIN 为普通样式, 大小为 25px
            g2d.setFont(new Font(null, Font.PLAIN, (int) ((scale * length * FontSize) / (LocalScale * 800))));

            // 绘制文本, 其中坐标参数指的是文本绘制后的 左下角 的位置
            // 首次绘制需要初始化字体, 可能需要较耗时

            //前点坐标名
            g2d.drawString((String) cable.getJSONObject("prev").get("name"), (int) ((Double.parseDouble((String) cable.getJSONObject("prev").get("longitude")) - localBorder[0]) * scale) + Margin + (int) ((scale * length * FontSize) / (LocalScale * 800) / 2),
                    (int) ((localBorder[3] - Double.parseDouble((String) cable.getJSONObject("prev").get("latitude"))) * scale) + Margin + (int) ((scale * length * FontSize) / (LocalScale * 800)));
            //后点坐标名
            g2d.drawString((String) cable.getJSONObject("next").get("name"), (int) ((Double.parseDouble((String) cable.getJSONObject("next").get("longitude")) - localBorder[0]) * scale) + Margin + (int) ((scale * length * FontSize) / (LocalScale * 800) / 2),
                    (int) ((localBorder[3] - Double.parseDouble((String) cable.getJSONObject("next").get("latitude"))) * scale) + Margin + (int) ((scale * length * FontSize) / (LocalScale * 800)));
        }
        // 自己创建的副本用完要销毁掉
        g2d.dispose();
    }

    /**
     * 画图片
     */
    private void drawImage(Graphics g, String filepath, double degree, int x, int y) {
        Graphics2D g2d = (Graphics2D) g.create();

        // 从本地读取一张图片
        Image image = Toolkit.getDefaultToolkit().getImage(filepath);
        BufferedImage bi = new BufferedImage(Math.min((int) ((scale * length * ImageSize) / (LocalScale * 800)), 1024), Math.min((int) ((scale * length * ImageSize) / (LocalScale * 800)), 1024), BufferedImage.TYPE_INT_ARGB);
        Graphics gg = bi.getGraphics();
        gg.drawImage(image, 0, 0, Math.min((int) ((scale * length * ImageSize) / (LocalScale * 800)), 1024), Math.min((int) ((scale * length * ImageSize) / (LocalScale * 800)), 1024), this);
        try {
            InputStream inputStream = ImageChange.rotateImg(bi, degree, null);
            BufferedImage read = ImageIO.read(inputStream);
            g2d.drawImage(read, x, y, read.getWidth(), read.getHeight(), this);
            gg.dispose();
        } catch (IOException e) {
            e.printStackTrace();
        }
        g2d.dispose();
    }
}