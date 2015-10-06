package com.fmc.edu.util;

/**
 * Created by Yove on 10/5/2015.
 */
public class URLUtils {

	public static final String POINT = ".";

	public static boolean PrefixURLMatch(String pURL, String pPrefix) {
		return StringUtils.isNotBlank(pURL) && StringUtils.isNotBlank(pPrefix) && pURL.startsWith(pPrefix);
	}

	public static boolean SuffixURLMatch(String pURL, String pSuffix) {
		if (StringUtils.isNotBlank(pURL) && StringUtils.isNotBlank(pSuffix)) {
			int index = pURL.lastIndexOf(POINT);
			if (index > 0) {
				return StringUtils.equals(pURL.substring(index + 1, pURL.length()), pSuffix);
			}
		}
		return false;
	}
}
