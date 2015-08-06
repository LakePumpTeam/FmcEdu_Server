package com.fmc.edu.model.news;

/**
 * Created by Yove on 8/1/2015.
 */
public class FmcNewsType implements NewsType {

	public static boolean isSchoolLevel(int pNewsType) {
		return pNewsType == SCHOOL_DYNAMICS_ACTIVITY || pNewsType == SCHOOL_DYNAMICS_ACTIVITY || pNewsType
				== SCHOOL_DYNAMICS_NOTIFICATION || pNewsType == SCHOOL_BBS;
	}

	public static boolean isClassLevel(int pNewsType) {
		return pNewsType == CLASS_DYNAMICS || pNewsType == PARENT_CHILD_EDU;
	}

	public static boolean isGlobalLevel(int pNewsType) {
		return pNewsType == PARENTING_CLASS;
	}
}

