package com.mxgraph.examples.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Rectangle2D;

public class FPanel extends javax.swing.JPanel {
    private Dimension preferredSize = new Dimension(400, 400);

    public static void main(String[] args) {
        JFrame jf = new JFrame(" test ");
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setSize(400, 400);
        jf.add(new JScrollPane(new FPanel()));
        jf.setVisible(true);
    }

    public FPanel() {
        // generate rectangles with pseudo-random coords
        // mouse listener to detect scrollwheel events
        addMouseWheelListener(new MouseWheelListener() {
            public void mouseWheelMoved(MouseWheelEvent e) {
                updatePreferredSize(e.getWheelRotation(), e.getPoint());
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
        int x = (int) (stablePoint.x * scaleFactor) - stablePoint.x;
        int y = (int) (stablePoint.y * scaleFactor) - stablePoint.y;
        return new Point(x, y);
    }

    private void offsetBy(Point offset) {
        Point location = getLocation();
        setLocation(location.x - offset.x, location.y - offset.y);
    }

    public Dimension getPreferredSize() {
        return preferredSize;
    }

    private Rectangle2D r = new Rectangle2D.Float();

    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.red);
        int w = getWidth();
        int h = getHeight();
        r.setRect(0 * w, 0.1 * h, 0.9 * w, 0.8 * h);
        ((Graphics2D) g).draw(r);
    }
}