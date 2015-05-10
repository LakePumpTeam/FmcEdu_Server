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
	public static final int DEPLOY_STATUS_DEVELOPER = 1;
	public static final int DEPLOY_STATUS_PRODUCTION = 2;
	private static final Logger LOG = Logger.getLogger(WebConfig.class);
	private static final String DEPLOY_STATUS = "deployStatus";

	private static final String API_KEY = "apiKey";

	private static final String SECRET_KEY = "secretKey";

	private static final String DES_PASSWORD = "desPassword";

	private static final String DEFAULT_DES_PASSWORD = "9288028820109132570743325311898426347855298773549268758875018579537756572163084478873699447306034466200616411960574122434059469100235892702736860872901247123456";

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

	public static String getApiKey() throws Exception {
		if (props == null) {
			throw new Exception("Bai Du apiKey not was configured.");
		}
		return props.getProperty(WebConfig.API_KEY);
	}

	public static String getSecretKey() throws Exception {
		if (props == null) {
			throw new Exception("Bai Du secretKey not was configured.");
		}
		return props.getProperty(WebConfig.SECRET_KEY);
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
}
