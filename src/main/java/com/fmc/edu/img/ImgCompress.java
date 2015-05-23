package com.fmc.edu.img;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * Created by Yu on 2015/5/23.
 */
public class ImgCompress {
    private Image img;
    private int width;
    private int height;

    public ImgCompress(File file) throws IOException {
        img = ImageIO.read(file);
        width = img.getWidth(null);
        height = img.getHeight(null);
    }

    public ImgCompress(byte[] file) throws IOException {
        img = ImageIO.read(new ByteArrayInputStream(file));
        width = img.getWidth(null);
        height = img.getHeight(null);
    }

    public byte[] resizeFix(int w, int h) throws IOException {
        if (width / height > w / h) {
            return resizeByWidth(w);
        } else {
            return resizeByHeight(h);
        }
    }

    public byte[] resizeByWidth(int w) throws IOException {
        int h = height * w / width;
        return resize(w, h);
    }

    public byte[] resizeByHeight(int h) throws IOException {
        int w = width * h / height;
        return resize(w, h);
    }

    public byte[] resize(int w, int h) throws IOException {
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        image.getGraphics().drawImage(img, 0, 0, w, h, null); // 绘制缩小后的图
        File destFile = new File("C:\\temp\\456.jpg");
        ByteArrayOutputStream tempImage = new ByteArrayOutputStream();
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(tempImage);
        encoder.encode(image);
        byte[] imgBytes = tempImage.toByteArray();
        tempImage.close();
        return imgBytes;
    }
}
