package com.fmc.edu.util;

/**
 * Created by Yove on 9/15/2015.
 */
public class ArrayUtils extends org.apache.commons.lang3.ArrayUtils {

	public static int[] safeConvertStringArrayToIntArray(String[] pStrings) {
		if (isEmpty(pStrings)) {
			return EMPTY_INT_ARRAY;
		}
		int[] ints = new int[pStrings.length];
		for (int i = 0; i < pStrings.length; i++) {
			int value = RepositoryUtils.safeParseId(pStrings[i]);
			ints[i] = value;
		}
		return ints;
	}

	public static boolean[] convertBooleanArray(Boolean[] pBooleanArray) {
		if (isEmpty(pBooleanArray)) {
			return EMPTY_BOOLEAN_ARRAY;
		}
		boolean[] values = new boolean[pBooleanArray.length];
		for (int i = 0; i < pBooleanArray.length; i++) {
			boolean value = pBooleanArray[i] == null ? false : pBooleanArray[i].booleanValue();
			values[i] = value;
		}
		return values;
	}
}
