package com.fmc.edu.repository;

import com.fmc.edu.model.profile.BaseProfile;
import com.fmc.edu.model.relationship.ParentStudentRelationship;

import java.util.List;
import java.util.Map;

/**
 * Created by Yu on 5/12/2015.
 */
public interface IMyAccountRepository {

    String QUER_USER_BY_PHONE = "com.fmc.edu.myaccount.login";

    BaseProfile findUser(final String pPhone);

    String QUER_USER_BY_PROFILE_ID = "com.fmc.edu.myaccount.findUserById";

    BaseProfile findUserById(final String pProfileId);

    String UPDATE_LOGIN_STATUS = "com.fmc.edu.myaccount.updateLoginStatus";

    int saveLoginStatus(final BaseProfile pLoginedUser);

    String RESET_PASSWORD = "com.fmc.edu.myaccount.resetPassword";

    int resetPassword(final BaseProfile pLoginedUser);

    String UPDATE_PARENT_AUDIT_STATUS = "com.fmc.edu.myaccount.updateParentAuditStatus";

    boolean updateParentAuditStatus(int pTeacherId, int[] pParentIds, boolean pPass);

    String UPDATE_ALL_PARENT_AUDIT_STATUS = "com.fmc.edu.myaccount.updateAllParentAuditStatus";

    boolean updateAllParentAuditStatus(int pTeacherId, boolean pPass);

    String QUERY_PENDING_AUDIT_PARENTS = "com.fmc.edu.myaccount.queryPendingAuditParents";

    List<Map<String, Object>> queryPendingAuditParents(int pTeacherId);

    String DELETE_DIRTY_PROFILE_BY_ID = "com.fmc.edu.myaccount.deleteDirtyProfileById";

    boolean deleteProfile(int userId);

    String QUERY_STUDENT_PARENT_RELATION_BY_PARENTID = "com.fmc.edu.myaccount.queryStudentParentRelationByParentId";

    List<ParentStudentRelationship> queryStudentParentRelationByParentId(int parentId);
}
