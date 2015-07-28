package com.fmc.edu.repository;

import com.fmc.edu.model.push.PushMessage;
import com.fmc.edu.util.pagenation.Pagination;

import java.util.List;

/**
 * Created by Yu on 2015/7/26.
 */
public interface IPushMessageRepository {
    String QUERY_ALL_PUSH_MESSAGE_BY_PROFILE_ID = "com.fmc.edu.push.message.queryAllPushMessageByProfileId";

    List<PushMessage> queryAllPushMessageByProfileId(int pProfileId, Pagination pPagination);

    String INSERT_PUSH_MESSAGE = "com.fmc.edu.push.message.insertPushMessage";

    boolean insertPushMessage(PushMessage pPushMessage);
}
