package com.fmc.edu.service.impl;

import com.fmc.edu.service.IMessageIdentifyService;
import org.springframework.stereotype.Service;

/**
 * Used to invoke the third part API to send sms message and save the response code to local database in order to the next verification.
 */
@Service(value = "messageIdentifyService")
public class MessageIdentifyService implements IMessageIdentifyService {
    @Override
    public String sendIdentifyRequest(String pPhoneNumber) {
        return null;
    }
}
