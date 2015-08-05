package com.fmc.edu.configuration;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by YW on 2015/5/3.
 */
public class WebConfig {
    private static final Logger LOG = Logger.getLogger(WebConfig.class);

    public static final int DEPLOY_STATUS_DEVELOPER = 1;
    public static final int DEPLOY_STATUS_PRODUCTION = 2;
    private static final String DEPLOY_STATUS = "deployStatus";
    private static final String ENCODE_BASE64_INPUT_PARAM = "encodeBase64InputParam";

    private static final String API_KEY_ANDROID = "apiKey_android";

    private static final String SECRET_KEY_ANDROID = "secretKey_android";

    private static final String API_KEY_IOS = "apiKey_ios";

    private static final String SECRET_KEY_IOS = "secretKey_ios";

    private static final String DES_PASSWORD = "desPassword";

    private static final String DEFAULT_DES_PASSWORD = "9288028820109132570743325311898426347855298773549268758875018579537756572163084478873699447306034466200616411960574122434059469100235892702736860872901247123456";

    private static final String IDENTITY_CODE_SURVIVOR_PERIOD = "identifyCodeSurvivalPeriod";

    private static final String UPLOAD_FILE_ROOT_PATH = "uploadFileRootPath";

    private static final String COMPRESS_IMAGE_HEIGHT = "compressedImageHight";

    private static final String COMPRESS_IMAGE_WIDTH = "compressedImageWidth";

    private static final String FMC_WEB_CONTEXT = "webContext";

    private static final String BAIDU_MSG_EXPIRES = "msgExpires";
    private static Properties props;

    static {
        Resource resource = new ClassPathResource("/web.properties");
        try {
            props = PropertiesLoaderUtils.loadProperties(resource);
        } catch (IOException e) {
            LOG.error("Loading web.properties failed.", e);
        }
    }

    public static int deployStatus() {

        if (props == null) {
            LOG.debug("web.properties read failed, then returning #WebConfig.DEPLOY_STATUS_DEVELOPER");
            return WebConfig.DEPLOY_STATUS_DEVELOPER;
        }
        String deployStatus = props.getProperty(WebConfig.DEPLOY_STATUS);
        if (StringUtils.isAlpha(deployStatus)) {
            LOG.debug("\"deployStatus\" not was configured in the web.properties file, then returning #WebConfig.DEPLOY_STATUS_DEVEPLOPER");
            return WebConfig.DEPLOY_STATUS_DEVELOPER;
        }
        return Integer.valueOf(deployStatus);
    }

    public static boolean isDevelopment() {
        return deployStatus() == WebConfig.DEPLOY_STATUS_DEVELOPER;
    }

    public static boolean isProduction() {
        return !isDevelopment();
    }

    public static String getAndroidApiKey() throws Exception {
        if (props == null) {
            throw new Exception("Bai Du android apiKey not was configured.");
        }
        return props.getProperty(WebConfig.API_KEY_ANDROID);
    }

    public static String getAndroidSecretKey() throws Exception {
        if (props == null) {
            throw new Exception("Bai Du android secretKey not was configured.");
        }
        return props.getProperty(WebConfig.SECRET_KEY_ANDROID);
    }

    public static String getIOSApiKey() throws Exception {
        if (props == null) {
            throw new Exception("Bai Du ios apiKey not was configured.");
        }
        return props.getProperty(WebConfig.API_KEY_IOS);
    }

    public static String getIOSSecretKey() throws Exception {
        if (props == null) {
            throw new Exception("Bai Du ios secretKey not was configured.");
        }
        return props.getProperty(WebConfig.SECRET_KEY_IOS);
    }

    public static Integer getBaiDuMsgExpires() throws Exception {
        if (props == null) {
            throw new Exception("Bai Du msgExpires not was configured.");
        }
        return Integer.valueOf(props.getProperty(WebConfig.BAIDU_MSG_EXPIRES));
    }

    public static String getDESPassword() throws Exception {
        if (props == null) {
            throw new Exception("DES password not was configured.");
        }
        String password = props.getProperty(WebConfig.DES_PASSWORD);
        if (StringUtils.isBlank(password)) {
            return DEFAULT_DES_PASSWORD;
        }
        return password;
    }

    public static boolean isEncodeBase64InputParam() {
        if (props == null) {
            return false;
        }
        String encodeBase64InputParam = props.getProperty(WebConfig.ENCODE_BASE64_INPUT_PARAM);
        if (StringUtils.isBlank(encodeBase64InputParam)) {
            return false;
        }
        return Boolean.valueOf(encodeBase64InputParam);
    }

    public static int getIdentifyCodeSurvivalPeriod() {
        if (props == null) {
            return 60;
        }
        String identifyCodeSurvivalPeriod = props.getProperty(WebConfig.IDENTITY_CODE_SURVIVOR_PERIOD);
        return Integer.valueOf(identifyCodeSurvivalPeriod);
    }

    public static String getUploadFileRootPath() {
        if (props == null) {
            return "/var/upload";
        }
        String uploadFileRootPath = props.getProperty(WebConfig.UPLOAD_FILE_ROOT_PATH);
        return uploadFileRootPath;
    }


    public static int getCompressedImageHeight() {
        if (props == null) {
            return 60;
        }
        String compressedImageHight = props.getProperty(WebConfig.COMPRESS_IMAGE_HEIGHT);
        return Integer.valueOf(compressedImageHight);
    }

    public static int getCompressedImageWidth() {
        if (props == null) {
            return 60;
        }
        String compressedImageWidth = props.getProperty(WebConfig.COMPRESS_IMAGE_WIDTH);
        return Integer.valueOf(compressedImageWidth);
    }

    public static String getFMCWebContext() {
        if (props == null) {
            return "/";
        }
        String webContext = props.getProperty(WebConfig.FMC_WEB_CONTEXT);
        if (StringUtils.isBlank(webContext)) {
            return "/";
        }
        return webContext;
    }
}
