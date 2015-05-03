package com.fmc.edu.push;

/**
 * Created by YW on 2015/5/3.
 */
public interface IBaiDuPushNotification {
    boolean pushMsg(final long pChannelId, final String pUserId, final String pMsg) throws Exception;
}
