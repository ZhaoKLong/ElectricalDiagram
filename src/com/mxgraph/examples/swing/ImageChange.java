package com.mxgraph.examples.swing;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageChange {

    public static InputStream rotateImg(BufferedImage image, double degree, Color bgcolor) throws IOException {

        int iw = image.getWidth();//原始图象的宽度
        int ih = image.getHeight();//原始图象的高度
        int w = 0;
        int h = 0;
        int x = 0;
        int y = 0;
        degree = degree % 360;
        if (degree < 0)
            degree = 360 + degree;//将角度转换到0-360度之间
//        double ang = Math.toRadians(degree);//将角度转为弧度
        double ang = degree * Math.PI / 180;//将角度转为弧度

        /**
         *确定旋转后的图象的高度和宽度
         */

        if (degree == 180 || degree == 0) {
            w = iw;
            h = ih;
        } else if (degree == 90 || degree == 270) {
            w = ih;
            h = iw;
        } else {
            int d = iw + ih;
//            w = (int) (d * Math.abs(Math.cos(ang)));
//            h = (int) (d * Math.abs(Math.sin(ang)));
            double tan = Math.tan(degree * Math.PI / 180);
            if (tan < 0) {
                w = (int) ((iw * (1 - tan)) / (Math.sqrt(tan * tan + 1)));
                h = (int) ((ih * (1 - tan)) / (Math.sqrt(tan * tan + 1)));
            } else {
                w = (int) ((iw * (1 + tan)) / (Math.sqrt(tan * tan + 1)));
                h = (int) ((ih * (1 + tan)) / (Math.sqrt(tan * tan + 1)));
            }
        }

        x = (w / 2) - (iw / 2);//确定原点坐标
        y = (h / 2) - (ih / 2);
        BufferedImage rotatedImage = new BufferedImage(w, h, image.getType());
        Graphics2D gs = (Graphics2D) rotatedImage.getGraphics();
        if (bgcolor == null) {
            rotatedImage = gs.getDeviceConfiguration().createCompatibleImage(w, h, Transparency.TRANSLUCENT);
        } else {
            gs.setColor(bgcolor);
            gs.fillRect(0, 0, w, h);//以给定颜色绘制旋转后图片的背景
        }

        AffineTransform at = new AffineTransform();
        at.rotate(ang, w / 2.0, h / 2.0);//旋转图象
        at.translate(x, y);
//        at.translate(-x, -y);
        AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_BICUBIC);
        op.filter(image, rotatedImage);
        image = rotatedImage;

        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ImageOutputStream iamgeOut = ImageIO.createImageOutputStream(byteOut);

        ImageIO.write(image, "png", iamgeOut);
        InputStream inputStream = new ByteArrayInputStream(byteOut.toByteArray());

        return inputStream;
    }
}
