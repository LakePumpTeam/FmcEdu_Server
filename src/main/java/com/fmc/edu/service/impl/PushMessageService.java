package com.fmc.edu.service.impl;

import com.fmc.edu.model.push.PushMessage;
import com.fmc.edu.repository.impl.PushMessageRepository;
import com.fmc.edu.util.pagenation.Pagination;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Yu on 2015/7/26.
 */
@Service("pushMessageService")
public class PushMessageService {

    @Resource(name = "pushMessageRepository")
    private PushMessageRepository mPushMessageRepository;

    public List<PushMessage> queryAllPushMessageByProfileId(int pProfileId, Pagination pPagination) {
        return getPushMessageRepository().queryAllPushMessageByProfileId(pProfileId, pPagination);
    }

    public boolean insertPushMessages(List<PushMessage> pPushMessages) {
        return getPushMessageRepository().insertPushMessages(pPushMessages);
    }

    public PushMessageRepository getPushMessageRepository() {
        return mPushMessageRepository;
    }

    public void setPushMessageRepository(PushMessageRepository pPushMessageRepository) {
        mPushMessageRepository = pPushMessageRepository;
    }
}
