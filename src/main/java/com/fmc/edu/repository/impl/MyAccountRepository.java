package com.fmc.edu.repository.impl;

import com.fmc.edu.model.app.AppSetting;
import com.fmc.edu.model.profile.BaseProfile;
import com.fmc.edu.model.relationship.ParentStudentRelationship;
import com.fmc.edu.repository.BaseRepository;
import com.fmc.edu.repository.IMyAccountRepository;
import com.fmc.edu.util.RepositoryUtils;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
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
	public boolean updateParentAuditStatus(final int pTeacherId, final int[] pParentIds, final int pPass) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", pTeacherId);
		params.put("parentIds", pParentIds);
		params.put("pass", pPass);
		params.put("now", new Timestamp(System.currentTimeMillis()));
		return getSqlSession().update(UPDATE_PARENT_AUDIT_STATUS, params) > 0;

	}

	@Override
	public boolean updateAllParentAuditStatus(final int pTeacherId, final int pPass) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", pTeacherId);
		params.put("pass", pPass);
		params.put("now", new Timestamp(System.currentTimeMillis()));
		return getSqlSession().update(UPDATE_ALL_PARENT_AUDIT_STATUS, params) > 0;
	}

	@Override
	public List<Map<String, Object>> queryPendingAuditParents(final int pTeacherId) {
		return getSqlSession().selectList(QUERY_PENDING_AUDIT_PARENTS, pTeacherId);
	}

	@Override
	public boolean deleteProfile(int userId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", userId);
		return getSqlSession().delete(DELETE_DIRTY_PROFILE_BY_ID, params) > 0;
	}

	@Override
	public List<ParentStudentRelationship> queryStudentParentRelationByParentId(int parentId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("parentId", parentId);
		return getSqlSession().selectList(QUERY_STUDENT_PARENT_RELATION_BY_PARENTID, params);
	}

	@Override
	public boolean addLikeNewsRelation(final int pProfileId, final int pNewsId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("profileId", pProfileId);
		params.put("newsId", pNewsId);
		params.put("now", new Timestamp(System.currentTimeMillis()));
		return getSqlSession().insert(ADD_LIKE_NEWS, params) > 0;
	}

	@Override
	public boolean deleteLikeNewsRelation(int pProfileId, int pNewsId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("profileId", pProfileId);
		params.put("newsId", pNewsId);
		return getSqlSession().delete(DELETE_LIKE_NEWS, params) > 0;
	}

	@Override
	public Map<String, Object> queryLikeNewsRelation(int pProfileId, int pNewsId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("profileId", pProfileId);
		params.put("newsId", pNewsId);
		return getSqlSession().selectOne(QUERY_LIKE_NEWS_RELATION, params);
	}

	@Override
	public boolean updateBaseProfile(BaseProfile pBaseProfile) {
		return getSqlSession().update(UPDATE_BASEPROFILE, pBaseProfile) > 0;
	}

	@Override
	public boolean insertOrUpdateAppSetting(AppSetting pAppSetting) {
		if (RepositoryUtils.idIsValid(pAppSetting.getId())) {
			return getSqlSession().update(UPDATE_APP_SETTING, pAppSetting) > 0;
		}
		return getSqlSession().insert(INSERT_APP_SETTING, pAppSetting) > 0;
	}

	@Override
	public AppSetting queryAppSetting(int pProfileId) {
		return getSqlSession().selectOne(QUERY_APP_SETTING, pProfileId);
	}

	@Override
	public boolean checkPhoneExist(final int pId, final String pPhone) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", pId);
		params.put("phone", pPhone);
		int count = getSqlSession().selectOne(CHECK_PHONE_EXIST, params);
		return count > 0;
	}
}
