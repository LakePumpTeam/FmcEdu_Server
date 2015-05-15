package com.fmc.edu.repository;

import com.fmc.edu.model.common.IdentityCode;

/**
 * Created by silly on 2015/5/15.
 */
public interface IIdentityCodeRepository {
    String INSER_IDENTITY_CODE = "com.fmc.edu.identitycode.insertIdentityCode";

    int insertIdentityCode(final IdentityCode pIdentityCode);

    String QUERY_IDENTITY_CODE_BY_PROFILE_ID = "com.fmc.edu.identitycode.queryIdentityCodeByProfileId";

    IdentityCode queryIdentityCodeByProfileId(final int pProfileId);
}
