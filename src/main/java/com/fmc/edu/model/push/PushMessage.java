package com.fmc.edu.model.push;

import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yu on 7/12/2015.
 */
public class PushMessage {

    /**
     * message title
     */
    private String mTitle;
    /**
     * message content
     */
    private String mDescription;

    private int mNotification_basic_style;
    /**
     * please reference to the url to see the standard message format:http://push.baidu.com/doc/restapi/msg_struct
     */
    private Map<String, String> mCustom_content;

    public PushMessage() {
        mCustom_content = new HashMap<String, String>();
    }

    public PushMessage(String pTitle, String pContent) {
        this();
        setTitle(pTitle);
        setDescription(pContent);
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String pTitle) {
        mTitle = pTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String pDescription) {
        mDescription = pDescription;
    }

    public Map<String, String> getCustom_content() {
        return mCustom_content;
    }

    public int getNotification_basic_style() {
        if (mNotification_basic_style == 0) {
            mNotification_basic_style = MessageNotificationBasicStyle.BEL_VIBRA_ERASIBLE;
        }
        return mNotification_basic_style;
    }

    public void setNotification_basic_style(int pNotification_basic_style) {
        mNotification_basic_style = mNotification_basic_style | pNotification_basic_style;
    }

    public void addCustomContents(String pKey, String pValue) {
        mCustom_content.put(pKey, pValue);
    }

    @Override
    public String toString() {

        String message = JSONObject.fromObject(this).toString();
        return message.replace("\\", message);
    }
}
