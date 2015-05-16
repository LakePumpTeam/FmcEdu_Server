package com.fmc.edu.repository.impl;

import com.fmc.edu.model.common.IdentityCode;
import com.fmc.edu.repository.BaseRepository;
import com.fmc.edu.repository.IIdentityCodeRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yu on 2015/5/15.
 */
@Repository("identityCodeRepository")
public class IdentityCodeRepository extends BaseRepository implements IIdentityCodeRepository {
    @Override
    public int insertIdentityCode(IdentityCode pIdentityCode) {
        return getSqlSession().insert(INSER_IDENTITY_CODE, pIdentityCode);
    }

    @Override
    public IdentityCode queryIdentityCodeByPhone(final String pPhone) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("phone", pPhone);
        return getSqlSession().selectOne(QUERY_IDENTITY_CODE_BY_PROFILE_ID, params);
    }
}
