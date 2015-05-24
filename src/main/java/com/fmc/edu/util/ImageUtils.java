package com.fmc.edu.util;

import com.fmc.edu.configuration.WebConfig;
import com.fmc.edu.img.ImgCompress;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Yu on 5/22/2015.
 */
public class ImageUtils {
    private static final Logger LOG = Logger.getLogger(ImageUtils.class);

    public static boolean writeFileToDisk(MultipartFile pFile, String userId, String fileName) throws IOException {
        String highImagePath = ImageUtils.getHighBaseImagePath(userId);
        String lowImagePath = ImageUtils.getLowBaseImagePath(userId);
        File highBaseDir = new File(highImagePath);
        File lowBaseDir = new File(lowImagePath);
        if (!highBaseDir.exists()) {
            highBaseDir.mkdirs();
        }
        if (!lowBaseDir.exists()) {
            lowBaseDir.mkdirs();
        }

        //write the original image
        File highFile = new File(getHighImagePath(userId, fileName));
        LOG.debug("Writing image to:" + getHighImagePath(userId, fileName));
        byte[] bytes = pFile.getBytes();
        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(highFile));
        stream.write(bytes);
        stream.close();
        LOG.debug("Writing image completed.");
        //write the compressed image
        File lowFile = new File(getLowImagePath(userId, fileName));
        LOG.debug("Writing compressed image to:" + getLowImagePath(userId, fileName));
        byte[] lowBytes = new ImgCompress(pFile.getBytes()).resize(200, 200);
        stream = new BufferedOutputStream(new FileOutputStream(lowFile));
        stream.write(lowBytes);
        stream.close();
        LOG.debug("Writing compressed image completed.");
        return true;
    }

    public static String getHighBaseImagePath(final String userId) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(WebConfig.getUploadFileRootPath())
                .append(File.separator)
                .append("high")
                .append(File.separator)
                .append(getRelativePath(userId));
        return stringBuilder.toString();
    }

    public static String getLowBaseImagePath(final String userId) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(WebConfig.getUploadFileRootPath())
                .append(File.separator)
                .append("low")
                .append(File.separator)
                .append(getRelativePath(userId));
        return stringBuilder.toString();
    }

    public static String getHighImagePath(String userId, String fileName) {
        StringBuilder path = new StringBuilder();
        path.append(getHighBaseImagePath(userId))
                .append(File.separator)
                .append(fileName);
        return path.toString().replace(File.separator + File.separator, File.separator);
    }

    public static String getLowImagePath(String userId, String fileName) {
        StringBuilder path = new StringBuilder();
        path.append(getLowBaseImagePath(userId))
                .append(File.separator)
                .append(fileName);
        return path.toString().replace(File.separator + File.separator, File.separator);
    }

    public static String getRelativePath(String userId) {
        StringBuilder relativePath = new StringBuilder();
        relativePath.append(userId).append(File.separator);
        return relativePath.toString();
    }

    public static String getSuffixFromFileName(String pFileName) {
        if (StringUtils.isBlank(pFileName)) {
            return "";
        }
        int lastDot = pFileName.lastIndexOf(".");
        if (lastDot == -1) {
            return "";
        }
        return pFileName.substring(lastDot);
    }
}
