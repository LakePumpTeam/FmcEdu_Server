package com.fmc.edu.repository.impl;

import com.fmc.edu.model.profile.TempParentProfile;
import com.fmc.edu.repository.BaseRepository;
import com.fmc.edu.repository.ITempParentRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

/**
 * Created by Yove on 5/3/2015.
 */
@Repository("tempParentRepository")
public class TempParentRepository extends BaseRepository implements ITempParentRepository {


	@Override
	public boolean updateTempParentProfileIdentify(TempParentProfile pTempParent) {
		return getSqlSession().update(UPDATE_TEMP_PARENT_IDENTIFY, pTempParent) > 0;
	}

	@Override
	public TempParentProfile queryTempParentProfileByPhone(String pPhone) {
		return getSqlSession().selectOne(QUERY_TEMP_PARENT_BY_PHONE, pPhone);
	}

	@Override
	public boolean initialTempParentProfile(TempParentProfile pTempParent) {
		if (pTempParent.getCreationDate() == null) {
			pTempParent.setCreationDate(new Timestamp(System.currentTimeMillis()));
		}
		if (getSqlSession().insert(INITIAL_TEMP_PARENT, pTempParent) > 0) {
			// save super table success
			return getSqlSession().insert(INITIAL_AUXILIARY_TEMP_PARENT, pTempParent) > 0;
		}
		return false;
	}

	@Override
	public TempParentProfile queryTempParentProfileByIdentifyCode(TempParentProfile pTempParent) {
		return getSqlSession().selectOne(QUERY_TEMP_PARENT_BY_IDENTIFY_CODE, pTempParent);
	}

	@Override
	public boolean initialParentProfile(final int pId) {
		return getSqlSession().insert(INITIAL_PARENT_PROFILE, pId) > 0;
	}
}
