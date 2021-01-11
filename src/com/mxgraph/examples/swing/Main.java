package com.mxgraph.examples.swing;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static com.mxgraph.examples.swing.Util.*;

public class Main {
    public static final int Width = 800;
    public static final int Height = 600;
    public static final int Margin = 20;
    private static JSONArray jsonArray;

    public static void main(String[] args) {
        /*
         * 在 AWT 的事件队列线程中创建窗口和组件, 确保线程安全,
         * 即 组件创建、绘制、事件响应 需要处于同一线程。
         */
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                // 创建窗口对象
                MyFrame frame = new MyFrame();
                // 显示窗口
                frame.setVisible(true);
            }
        });
    }

    /**
     * 窗口
     */
    public static class MyFrame extends JFrame implements ActionListener {

        public static final String TITLE = "Java图形绘制";
        public JButton open = null;

        public static MyPanel panel = null;
        JPopupMenu pop;

        public MyFrame() {
            super();
            open();
        }

        private void initFrame() {
            // 设置 窗口标题 和 窗口大小
            setTitle(TITLE);
            setSize(Width, Height);

            // 设置窗口关闭按钮的默认操作(点击关闭时退出进程)
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            // 把窗口位置设置到屏幕的中心
            setLocationRelativeTo(null);

            // 设置窗口的内容面板
            MyPanel panel = new MyPanel(this);
            JMenuItem item1 = new JMenuItem("保存", 'S');


            add(new JScrollPane(panel));
            this.setVisible(true);
        }

        //        static class ActionHandler implements ActionListener {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                panel.savePic();
//            }
//        }
        public void open() {
            open = new JButton("导入文件");
            this.add(open);
            this.setBounds(800, 400, 200, 100);
            this.setVisible(true);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            open.addActionListener(this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser jfc = new JFileChooser();
            //设置当前路径为桌面路径,否则将我的文档作为默认路径
            FileSystemView fsv = FileSystemView.getFileSystemView();
            jfc.setCurrentDirectory(fsv.getHomeDirectory());
            // 只选择文件
            jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
            jfc.setFileFilter(new FileNameExtensionFilter("文本文件（*.txt）", "txt"));
            //用户选择的文件
            if (jfc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = jfc.getSelectedFile();
                if (file.isFile()) {
                    getLocalTxt(file.getAbsolutePath());
                    this.remove(open);
                    initFrame();
                }
            }
        }
    }


    /**
     * 内容面板
     */
    public static class MyPanel extends JPanel {
        private MyFrame frame;
        private Dimension preferredSize = new Dimension(Width - 19, Height - 42);
        private double[] localBorder = getLocalBorder(jsonArray);
        private double length = getLength(new double[]{localBorder[2], localBorder[0], localBorder[3], localBorder[1]});
        private double scale;

        public MyPanel(MyFrame frame) {
            super();
            this.frame = frame;
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
            draw(g);
        }

        /**
         * 画图
         */
        private void draw(Graphics g) {
            frame.setTitle("预览图");
            // 创建 Graphics 的副本, 需要改变 Graphics 的参数,
            // 这里必须使用副本, 避免影响到 Graphics 原有的设置
            Graphics2D g2d = (Graphics2D) g.create();
            // 抗锯齿
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            // 设置画笔颜色
            g2d.setColor(Color.BLACK);
            BasicStroke bs1 = new BasicStroke((int) (scale / length));       // 笔画的轮廓（画笔宽度/线宽为5px）
            float[] dash = new float[]{5, 10};
            BasicStroke bs2 = new BasicStroke(
                    (int) (scale / length),                      // 画笔宽度/线宽
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
                        double[] offset = getOffset(degree, scale, cable, i, false);
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
                g2d.setFont(new Font(null, Font.PLAIN, (int) ((scale / length) * 2)));

                // 绘制文本, 其中坐标参数指的是文本绘制后的 左下角 的位置
                // 首次绘制需要初始化字体, 可能需要较耗时

                //前点坐标名
                g2d.drawString((String) cable.getJSONObject("prev").get("name"), (int) ((Double.parseDouble((String) cable.getJSONObject("prev").get("longitude")) - localBorder[0]) * scale) + Margin + (int) ((scale / length) * 2),
                        (int) ((localBorder[3] - Double.parseDouble((String) cable.getJSONObject("prev").get("latitude"))) * scale) + Margin + (int) (scale / length));
                //后点坐标名
                g2d.drawString((String) cable.getJSONObject("next").get("name"), (int) ((Double.parseDouble((String) cable.getJSONObject("next").get("longitude")) - localBorder[0]) * scale) + Margin + (int) ((scale / length) * 2),
                        (int) ((localBorder[3] - Double.parseDouble((String) cable.getJSONObject("next").get("latitude"))) * scale) + Margin + (int) (scale / length));
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
            BufferedImage bi = new BufferedImage(Math.min((int) ((scale / length) * 12), 1024), Math.min((int) ((scale / length) * 12), 1024), BufferedImage.TYPE_INT_ARGB);
            Graphics gg = bi.getGraphics();
            gg.drawImage(image, 0, 0, Math.min((int) ((scale / length) * 12), 1024), Math.min((int) ((scale / length) * 12), 1024), this);
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

        /**
         * 2. 矩形 / 多边形
         */
        private void drawRect(Graphics g) {
            frame.setTitle("2. 矩形 / 多边形");
            Graphics2D g2d = (Graphics2D) g.create();

            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(Color.GRAY);

            // 1. 绘制一个矩形: 起点(30, 20), 宽80, 高100
            g2d.drawRect(30, 20, 80, 100);

            // 2. 填充一个矩形
            g2d.fillRect(140, 20, 80, 100);

            // 3. 绘制一个圆角矩形: 起点(30, 150), 宽80, 高100, 圆角宽30, 圆角高30
            g2d.drawRoundRect(30, 150, 80, 100, 30, 30);

            // 4. 绘制一个多边形(收尾相连): 点(140, 150), 点(180, 250), 点(220, 200)
            int[] xPoints = new int[]{140, 180, 220};
            int[] yPoints = new int[]{150, 250, 200};
            int nPoints = 3;
            g2d.drawPolygon(xPoints, yPoints, nPoints);

            g2d.dispose();
        }

        /**
         * 3. 圆弧 / 扇形
         */
        private void drawArc(Graphics g) {
            frame.setTitle("3. 圆弧 / 扇形");
            Graphics2D g2d = (Graphics2D) g.create();

            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(Color.RED);

            // 1. 绘制一条圆弧: 椭圆的外切矩形 左上角坐标为(0, 0), 宽100, 高100,
            //                弧的开始角度为0度, 需要绘制的角度数为-90度,
            //                椭圆右边水平线为0度, 逆时针为正角度, 顺时针为负角度
            g2d.drawArc(0, 0, 100, 100, 0, -90);

            // 2. 绘制一个圆: 圆的外切矩形 左上角坐标为(120, 20), 宽高为100
            g2d.drawArc(120, 20, 100, 100, 0, 360);

            g2d.setColor(Color.GRAY);

            // 3. 填充一个扇形
            g2d.fillArc(80, 150, 100, 100, 90, 270);

            g2d.dispose();
        }

        /**
         * 4. 椭圆 (实际上通过绘制360度的圆弧/扇形也能达到绘制圆/椭圆的效果)
         */
        private void drawOval(Graphics g) {
            frame.setTitle("4. 椭圆");
            Graphics2D g2d = (Graphics2D) g.create();

            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(Color.RED);

            // 1. 绘制一个圆: 圆的外切矩形 左上角坐标为(0, 0), 宽高为100
            g2d.drawOval(0, 0, 100, 100);

            g2d.setColor(Color.GRAY);

            // 2. 填充一个椭圆
            g2d.fillOval(120, 100, 100, 150);

            g2d.dispose();
        }

        /**
         * 6. 文本
         */
        private void drawString(Graphics g) {
            frame.setTitle("6. 文本");
            Graphics2D g2d = (Graphics2D) g.create();

            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // 设置字体样式, null 表示使用默认字体, Font.PLAIN 为普通样式, 大小为 25px
            g2d.setFont(new Font(null, Font.PLAIN, 25));

            // 绘制文本, 其中坐标参数指的是文本绘制后的 左下角 的位置
            // 首次绘制需要初始化字体, 可能需要较耗时
            g2d.drawString("Hello World!", 20, 60);
            g2d.dispose();
        }

        public void savePic() {
            // 获取到需要保存内容的组件（面板）
            JPanel panel = (JPanel) frame.getContentPane();

            // 创建一个与面板等宽高的缓存图片
            BufferedImage image = new BufferedImage(
                    panel.getWidth(),
                    panel.getHeight(),
                    BufferedImage.TYPE_INT_ARGB
            );

            // 获取缓存图片的画布
            Graphics2D g2d = image.createGraphics();

            // 把面板的内容画到画布中
            panel.paint(g2d);

            try {
                // 把缓存图片保存到本地文件
                ImageIO.write(image, "png", new File("panel.png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}