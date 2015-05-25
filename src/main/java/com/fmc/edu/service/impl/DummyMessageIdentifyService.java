package com.fmc.edu.service.impl;

import com.fmc.edu.service.IMessageIdentifyService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Dummy message identifying service will generate a identifying code without request the third part and send sms message.
 * Created by Yove on 5/3/2015.
 */
@Service(value = "dummyMessageIdentifyService")
public class DummyMessageIdentifyService implements IMessageIdentifyService {

    private static final int LENGTH = 6;
    private Set<String> mHistoryRandomNumbers = new HashSet<String>();

    @Override
    public String sendIdentifyRequest(final String pPhoneNumber) {

        return RandomStringUtils.randomNumeric(LENGTH);

        //return randomNumberSequence(LENGTH);
    }

    public String randomNumberSequence(final int pLength) {
        int[] array = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        Random rand = new Random();
        for (int i = 10; i > 1; i--) {
            int index = rand.nextInt(i);

            int tmp = array[index];
            array[index] = array[i - 1];
            array[i - 1] = tmp;
        }
        int resultInt = 0;
        for (int i = 0; i < pLength; i++) {
            if (i == 0 && array[i] == 0) {
                array[i] = array[array.length - 1];
            }
            resultInt = resultInt * 10 + array[i];
        }
        String result = String.valueOf(resultInt);
        if (mHistoryRandomNumbers.add(result)) {
            // not duplicate
            return result;
        }
        // duplicate, clear history and generate again
        mHistoryRandomNumbers.clear();
        return randomNumberSequence(pLength);
    }

}
