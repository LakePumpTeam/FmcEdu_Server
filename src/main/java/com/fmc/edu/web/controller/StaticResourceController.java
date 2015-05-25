package com.fmc.edu.web.controller;

import com.fmc.edu.util.ImageUtils;
import com.fmc.edu.util.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by Yu on 2015/5/24.
 */
@Controller
@RequestMapping("/static")
public class StaticResourceController {
    private static final Logger LOG = Logger.getLogger(StaticResourceController.class);

    @RequestMapping("/img/{size}/{userId}/{imgName}")
    public void requestImageResource(@PathVariable("size") String size,
                                     @PathVariable("userId") String userId,
                                     @PathVariable("imgName") String imgName, HttpServletRequest request,
                                     HttpServletResponse response) {
        if (StringUtils.isEmpty(imgName)) {
            return;
        }
        String baseImagePath;
        if (StringUtils.isBlank(size) || "high".equals(size)) {
            baseImagePath = ImageUtils.getHighBaseImagePath(userId);
        } else {
            baseImagePath = ImageUtils.getLowBaseImagePath(userId);
        }

        String imagePath = baseImagePath + File.separator + imgName;
        File file = new File(imagePath);
        if (!(file.exists() && file.canRead())) {
            LOG.debug("Can not find image:" + imagePath);
            return;
        }
        try {
            response.setContentType("image/jpg");
            FileCopyUtils.copy(FileCopyUtils.copyToByteArray(file), response.getOutputStream());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
