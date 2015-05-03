package com.fmc.edu.repository.impl;

import com.fmc.edu.model.profile.TempParentProfile;
import com.fmc.edu.repository.BaseRepository;
import com.fmc.edu.repository.ITempProfileRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

/**
 * Created by Yove on 5/3/2015.
 */
@Repository("tempProfileRepository")
public class TempProfileRepository extends BaseRepository implements ITempProfileRepository {


	@Override
	public boolean saveTempParent(TempParentProfile pTempParent) {
		return getSqlSession().update(SAVE_TEMP_PARENT, pTempParent) > 0;
	}

	@Override
	public TempParentProfile queryTempParentProfileByPhone(String pPhone) {
		return getSqlSession().selectOne(QUERY_TEMP_PARENT_BY_PHONE, pPhone);
	}

	@Override
	public boolean saveTempParentWithPhone(TempParentProfile pTempParent) {
		if (pTempParent.getCreationDate() == null) {
			pTempParent.setCreationDate(new Timestamp(System.currentTimeMillis()));
		}
		return getSqlSession().insert(SAVE_TEMP_PARENT_WITH_PHONE, pTempParent) > 0;
	}

}
