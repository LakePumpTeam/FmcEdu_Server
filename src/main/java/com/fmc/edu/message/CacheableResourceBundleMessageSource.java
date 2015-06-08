package com.fmc.edu.message;

import com.fmc.edu.constant.GlobalConstant;
import com.fmc.edu.util.StringUtils;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Yu on 6/6/2015.
 */
@Component("cacheableResourceBundleMessageSource")
public class CacheableResourceBundleMessageSource extends ReloadableResourceBundleMessageSource {

    private static final String NULL = "null";

    Map<String, String> encodingCache = new ConcurrentHashMap<String, String>(20);

    protected String resolveCodeWithoutArguments(String code, Locale locale) {
        String message = super.resolveCodeWithoutArguments(code, locale);
        return decodeString(message, GlobalConstant.CHARSET_UTF8);
    }

    protected MessageFormat createMessageFormat(String msg, Locale locale) {
        if (logger.isDebugEnabled()) {
            logger.debug("Creating MessageFormat for pattern [" + msg + "] and locale '" + locale + "'");
        }
        msg = decodeString(msg, GlobalConstant.CHARSET_UTF8);
        return new MessageFormat((msg != null ? msg : StringUtils.EMPTY), locale);
    }

    private String decodeString(String message, String encode) {
        String encodeMessage = encodingCache.get(message);
        if (encodeMessage == null) {
            try {
                encodeMessage = new String(message.getBytes(encode), encode);
                if (message != null) {
                    encodingCache.put(message, encodeMessage);
                } else {
                    encodingCache.put(message, NULL);
                }
            } catch (UnsupportedEncodingException e) {
                logger.error(e);
            }
        }
        return encodeMessage;
    }
}
