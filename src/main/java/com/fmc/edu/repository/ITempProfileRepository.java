package com.fmc.edu.repository;

import com.fmc.edu.model.profile.TempParentProfile;

/**
 * Created by Yove on 5/4/2015.
 */
public interface ITempProfileRepository {

	String QUERY_TEMP_PARENT_BY_PHONE = "com.fmc.edu.profile.temp.queryTempParentByPhone";

	TempParentProfile queryTempParentProfileByPhone(String pPhone);

	String INITIAL_TEMP_PROFILE = "com.fmc.edu.profile.temp.initialTempParent";
	String INITIAL_AUXILIARY_TEMP_PARENT = "com.fmc.edu.profile.temp.initialAuxiliaryTempParent";

	boolean initialTempProfile(TempParentProfile pTempParent);

	String UPDATE_IDENTIFY = "com.fmc.edu.profile.temp.updateIdentify";

	boolean updateIdentify(TempParentProfile pTempParent);
}
