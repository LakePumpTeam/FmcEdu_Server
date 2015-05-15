package com.fmc.edu.repository.impl;

import com.fmc.edu.model.profile.BaseProfile;
import com.fmc.edu.repository.BaseRepository;
import com.fmc.edu.repository.IMyAccountRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yu on 5/12/2015.
 */
@Repository("myAccountRepository")
public class MyAccountRepository extends BaseRepository implements IMyAccountRepository {
	@Override
	public BaseProfile findUser(final String pPhone) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userAccount", pPhone);
		return getSqlSession().selectOne(QUER_USER_BY_PHONE, param);
	}

	@Override
	public BaseProfile findUserById(String pProfileId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("profileId", pProfileId);
		return getSqlSession().selectOne(QUER_USER_BY_PROFILE_ID, param);
	}

	@Override
	public int saveLoginStatus(final BaseProfile pLoginedUser) {
		return getSqlSession().update(UPDATE_LOGIN_STATUS, pLoginedUser);
	}

	@Override
	public int resetPassword(BaseProfile pLoginedUser) {
		return getSqlSession().update(RESET_PASSWORD, pLoginedUser);
	}

	@Override
	public boolean updateParentAuditStatus(final int pTeacherId, final int[] pParentIds, final boolean pPass) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", pTeacherId);
		params.put("parentIds", pParentIds);
		params.put("pass", pPass ? 1 : 2);
		params.put("now", new Timestamp(System.currentTimeMillis()));
		return getSqlSession().update(UPDATE_PARENT_AUDIT_STATUS, params) > 0;

	}

	@Override
	public boolean updateAllParentAuditStatus(final int pTeacherId, final boolean pPass) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", pTeacherId);
		params.put("pass", pPass ? 1 : 2);
		params.put("now", new Timestamp(System.currentTimeMillis()));
		return getSqlSession().update(UPDATE_ALL_PARENT_AUDIT_STATUS, params) > 0;
	}
}
