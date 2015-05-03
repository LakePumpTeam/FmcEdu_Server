package com.fmc.edu.configuration;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by YW on 2015/5/3.
 */
@Component
@Scope("singleton")
public class WebConfig {
    private static final Logger LOG = Logger.getLogger(WebConfig.class);

    public static final int DEPLOY_STATUS_DEVELOPER = 1;

    public static final int DEPLOY_STATUS_PRODUCTION = 2;

    private static final String DEPLOY_STATUS = "deployStatus";

    private static final String API_KEY = "apiKey";

    private static final String SECRET_KEY = "secretKey";

    private static Properties props;

    static {
        Resource resource = new ClassPathResource("/config/web.properties");
        try {
            props = PropertiesLoaderUtils.loadProperties(resource);
        } catch (IOException e) {
            LOG.error("Reading web.properties failed.", e);
        }
    }

    public int deployStatus() {

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

    public boolean isDevelopment() {
        return deployStatus() == WebConfig.DEPLOY_STATUS_DEVELOPER;
    }

    public boolean isProduction() {
        return !isDevelopment();
    }

    public String getApiKey() throws Exception {
        if (props == null) {
            throw new Exception("Bai Du apiKey not was configured.");
        }
        return props.getProperty(WebConfig.API_KEY);
    }

    public String getSecretKey() throws Exception {
        if (props == null) {
            throw new Exception("Bai Du secretKey not was configured.");
        }
        return props.getProperty(WebConfig.SECRET_KEY);
    }
}
