package com.fmc.edu.constant;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by Yove on 5/4/2015.
 */
public interface GlobalConstant {

    String URL_SUFFIX = StringUtils.EMPTY;

    String CONFIG_DOMAIN = "domain";

    String CONFIG_CONTEXT = "context";

    String CHARSET_UTF8 = "utf-8";

    boolean SUCCESS = true;

    boolean FAILED = false;

    int STATUS_SUCCESS = 0;

    int STATUS_ERROR = -1;
}
