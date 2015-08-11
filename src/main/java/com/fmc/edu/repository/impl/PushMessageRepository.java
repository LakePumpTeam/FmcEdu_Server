package com.fmc.edu.repository.impl;

import com.fmc.edu.model.push.PushMessage;
import com.fmc.edu.repository.BaseRepository;
import com.fmc.edu.repository.IPushMessageRepository;
import com.fmc.edu.util.pagenation.Pagination;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Yu on 2015/7/26.
 */
@Repository("pushMessageRepository")
public class PushMessageRepository extends BaseRepository implements IPushMessageRepository {
    @Override
    public List<PushMessage> queryAllPushMessageByProfileId(int pProfileId, Pagination pPagination) {
        Map<String, Object> parameter = paginationToParameters(pPagination);
        parameter.put("profileId", pProfileId);
        return getSqlSession().selectList(QUERY_ALL_PUSH_MESSAGE_BY_PROFILE_ID, parameter);
    }

    @Override
    public boolean insertPushMessages(List<PushMessage> pPushMessages) {
        Map<String, List<PushMessage>> parameter = new HashMap<String, List<PushMessage>>(1);
        parameter.put("pushMessages", pPushMessages);
        return getSqlSession().insert(INSERT_PUSH_MESSAGES, parameter) > 0;
    }
}
