package com.fmc.edu.img;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

/**
 * Created by Yu on 2015/5/23.
 */
public class ImgCompress {
    private static float IAMGE_QUNTITY = 0.5f;
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
        Iterator<ImageWriter> imageWriters = ImageIO.getImageWritersByFormatName("jpg");
        if (!imageWriters.hasNext()) {
            throw new IllegalStateException("jpg imageWriter not found.");
        }
        BufferedImage bufferedImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        bufferedImage.getGraphics().drawImage(img, 0, 0, w, h, null);
        ByteArrayOutputStream tempImageOutputStream = new ByteArrayOutputStream();

        ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream(tempImageOutputStream);
        ImageWriter imageWriter = imageWriters.next();
        imageWriter.setOutput(imageOutputStream);
        ImageWriteParam imageWriteParam = imageWriter.getDefaultWriteParam();
        //Set the compress quality metrics
        imageWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        imageWriteParam.setCompressionQuality(IAMGE_QUNTITY);
        //Created image
        imageWriter.write(null, new IIOImage(bufferedImage, null, null), imageWriteParam);


        byte[] imgBytes = tempImageOutputStream.toByteArray();
        // close all streams
        tempImageOutputStream.close();
        imageOutputStream.close();
        imageWriter.dispose();
        return imgBytes;
    }
}
