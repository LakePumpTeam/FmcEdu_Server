package com.fmc.edu.push;

import com.fmc.edu.model.push.PushMessage;

/**
 * Created by YW on 2015/5/3.
 */
public interface IBaiDuPushNotification {
	boolean pushMsg(final String[] pChannelIds, final String pUserId, final PushMessage pMsg) throws Exception;

	boolean pushMsgToAll(PushMessage pMsg) throws Exception;
}
