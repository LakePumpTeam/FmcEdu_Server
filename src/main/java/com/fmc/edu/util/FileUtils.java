package com.fmc.edu.util;

import com.fmc.edu.configuration.WebConfig;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Yu on 5/22/2015.
 */
public class FileUtils {
    public static boolean writeFileToDisk(MultipartFile pFile, String pRelativePath) throws IOException {

        File baseDir = new File(WebConfig.getUploadFileRootPath() + File.separator);
        if (!baseDir.exists()) {
            baseDir.mkdirs();
        }

        File serverFile = new File(WebConfig.getUploadFileRootPath() + File.separator + pRelativePath);
        byte[] bytes = pFile.getBytes();
        BufferedOutputStream stream = new BufferedOutputStream(
                new FileOutputStream(serverFile));
        stream.write(bytes);
        stream.close();
        return true;
    }
}
