package com.fmc.edu.repository;

import com.fmc.edu.model.profile.TempParentProfile;

/**
 * Created by Yove on 5/4/2015.
 */
public interface ITempParentRepository {

	String QUERY_TEMP_PARENT_BY_PHONE = "com.fmc.edu.profile.temp.queryTempParentByPhone";

	TempParentProfile queryTempParentProfileByPhone(String pPhone);

	String INITIAL_TEMP_PARENT = "com.fmc.edu.profile.temp.initialTempParent";
	String INITIAL_AUXILIARY_TEMP_PARENT = "com.fmc.edu.profile.temp.initialAuxiliaryTempParent";

	boolean initialTempParentProfile(TempParentProfile pTempParent);

	String UPDATE_TEMP_PARENT_IDENTIFY = "com.fmc.edu.profile.temp.updateTempParentProfileIdentify";

	boolean updateTempParentProfileIdentify(TempParentProfile pTempParent);

	String QUERY_TEMP_PARENT_BY_IDENTIFY_CODE = "com.fmc.edu.profile.temp.queryTempParentByIdentifyCode";

	TempParentProfile queryTempParentProfileByIdentifyCode(TempParentProfile pTempParent);

	String INITIAL_PARENT_PROFILE = "com.fmc.edu.profile.temp.initialParentProfile";

	boolean initialParentProfile(int pId);
}
