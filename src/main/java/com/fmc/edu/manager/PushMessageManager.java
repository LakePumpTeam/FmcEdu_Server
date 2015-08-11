package com.fmc.edu.manager;

import com.fmc.edu.model.push.PushMessage;
import com.fmc.edu.service.impl.PushMessageService;
import com.fmc.edu.util.pagenation.Pagination;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Yu on 2015/7/26.
 */
@Component("pushMessageManager")
public class PushMessageManager {
    @Resource(name = "pushMessageService")
    private PushMessageService mPushMessageService;

    public List<PushMessage> queryAllPushMessageByProfileId(int pProfileId, Pagination pPagination) {
        return getPushMessageService().queryAllPushMessageByProfileId(pProfileId, pPagination);
    }

    public boolean insertPushMessages(List<PushMessage> pPushMessages) {
        return getPushMessageService().insertPushMessages(pPushMessages);
    }

    public PushMessageService getPushMessageService() {
        return mPushMessageService;
    }

    public void setPushMessageService(PushMessageService pPushMessageService) {
        mPushMessageService = pPushMessageService;
    }
}
