package com.fmc.edu.repository.impl;

import com.fmc.edu.model.profile.BaseProfile;
import com.fmc.edu.model.relationship.ParentStudentRelationship;
import com.fmc.edu.repository.BaseRepository;
import com.fmc.edu.repository.IMyAccountRepository;
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
    public boolean updateParentAuditStatus(final int pTeacherId, final int[] pParentIds, final boolean pPass) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", pTeacherId);
        params.put("parentIds", pParentIds);
        params.put("pass", pPass ? 2 : 1);
        params.put("now", new Timestamp(System.currentTimeMillis()));
        return getSqlSession().update(UPDATE_PARENT_AUDIT_STATUS, params) > 0;

    }

    @Override
    public boolean updateAllParentAuditStatus(final int pTeacherId, final boolean pPass) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", pTeacherId);
        params.put("pass", pPass ? 2 : 1);
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

}
