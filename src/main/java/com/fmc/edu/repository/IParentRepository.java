package com.fmc.edu.repository;

import com.fmc.edu.model.profile.ParentProfile;

/**
 * Created by Yove on 5/8/2015.
 */
public interface IParentRepository {

	String INITAIL_PARENT_PROFILE = "com.fmc.edu.profile.parent.initialParentProfile";

	boolean initialParentProfile(final ParentProfile pParentProfile);
}
