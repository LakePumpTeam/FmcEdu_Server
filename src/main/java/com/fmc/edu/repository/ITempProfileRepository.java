package com.fmc.edu.repository;

import com.fmc.edu.model.profile.TempParentProfile;

/**
 * Created by Yove on 5/4/2015.
 */
public interface ITempProfileRepository {

	String QUERY_TEMP_PARENT_BY_PHONE = "com.fmc.edu.profile.queryTempParentByPhone";

	TempParentProfile queryTempParentProfileByPhone(String pPhone);

	String SAVE_TEMP_PARENT_WITH_PHONE = "com.fmc.edu.profile.saveTempParentWithPhone";

	boolean saveTempParentWithPhone(TempParentProfile pTempParent);

	String SAVE_TEMP_PARENT = "com.fmc.edu.profile.saveTempParent";

	boolean saveTempParent(TempParentProfile pTempParent);
}
