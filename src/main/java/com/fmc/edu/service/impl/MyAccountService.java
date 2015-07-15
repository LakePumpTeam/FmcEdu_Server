package com.fmc.edu.service.impl;

import com.fmc.edu.model.profile.BaseProfile;
import com.fmc.edu.model.relationship.ParentStudentRelationship;
import com.fmc.edu.repository.IMyAccountRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Yu on 5/12/2015.
 */
@Service("myAccountService")
public class MyAccountService {
    @Resource(name = "myAccountRepository")
    private IMyAccountRepository mMyAccountRepository;

    public BaseProfile findUser(final String pPhone) {
        return getMyAccountRepository().findUser(pPhone);
    }

    public BaseProfile findUserById(String pProfileId) {
        return getMyAccountRepository().findUserById(pProfileId);
    }

    public boolean saveLoginStatus(final BaseProfile pLoginedUser) {
        int updatedCount = getMyAccountRepository().saveLoginStatus(pLoginedUser);
        return updatedCount > 0 ? true : false;
    }

    public boolean updateParentAuditStatus(final int pTeacherId, final int[] pParentIds, final int pPass) {
        return getMyAccountRepository().updateParentAuditStatus(pTeacherId, pParentIds, pPass);
    }

    public boolean updateAllParentAuditStatus(final int pTeacherId, final int pPass) {
        return getMyAccountRepository().updateAllParentAuditStatus(pTeacherId, pPass);
    }

    public int resetPassword(BaseProfile pLoginedUser) {
        return getMyAccountRepository().resetPassword(pLoginedUser);
    }

    public List<Map<String, Object>> getPendingAuditParents(final int pTeacherId) {
        return getMyAccountRepository().queryPendingAuditParents(pTeacherId);
    }

    public boolean deleteProfile(int userId) {
        return getMyAccountRepository().deleteProfile(userId);
    }

    public boolean parentBoundStudent(int parentId) {
        return false;
    }

    public List<ParentStudentRelationship> queryStudentParentRelationByParentId(int parentId) {
        return getMyAccountRepository().queryStudentParentRelationByParentId(parentId);
    }
    public boolean updateBaseProfile(BaseProfile pBaseProfile) {
        return getMyAccountRepository().updateBaseProfile(pBaseProfile);
    }

    public boolean addLikeNewsRelation(final int pProfileId, final int pNewsId) {
        return getMyAccountRepository().addLikeNewsRelation(pProfileId, pNewsId);
    }

    public boolean deleteLikeNewsRelation(int pProfileId, int pNewsId) {
        return getMyAccountRepository().deleteLikeNewsRelation(pProfileId, pNewsId);
    }

    public Map<String, Object> queryLikeNewsRelation(int pProfileId, int pNewsId) {
        return getMyAccountRepository().queryLikeNewsRelation(pProfileId, pNewsId);
    }

    public IMyAccountRepository getMyAccountRepository() {
        return mMyAccountRepository;
    }

    public void setMyAccountRepository(IMyAccountRepository pMyAccountRepository) {
        mMyAccountRepository = pMyAccountRepository;
    }
}
