package com.fmc.edu.push;

import com.fmc.edu.model.push.PushMessageParameter;

/**
 * Created by YW on 2015/5/3.
 */
public interface IBaiDuPushNotification {
	boolean pushMsg(final String[] pChannelIds, final String pAppId, final PushMessageParameter pMsg) throws Exception;

	boolean pushMsgToAll(PushMessageParameter pMsg) throws Exception;
}
