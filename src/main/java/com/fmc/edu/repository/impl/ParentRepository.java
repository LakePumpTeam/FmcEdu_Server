package com.fmc.edu.repository.impl;

import com.fmc.edu.model.profile.ParentProfile;
import com.fmc.edu.repository.BaseRepository;
import com.fmc.edu.repository.IParentRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Yove on 5/8/2015.
 */
@Repository(value = "parentRepository")
public class ParentRepository extends BaseRepository implements IParentRepository {

	@Override
	public boolean initialParentProfile(final ParentProfile pParentProfile) {
		return getSqlSession().update(INITAIL_PARENT_PROFILE, pParentProfile) > 0;
	}
}
