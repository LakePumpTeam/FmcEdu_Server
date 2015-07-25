package com.fmc.edu.service.impl;

import com.fmc.edu.model.clockin.ClockInRecord;
import com.fmc.edu.repository.impl.ClockInRecordRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Yu on 7/19/2015.
 */
@Service(value = "clockInRecordService")
public class ClockInRecordService {

    @Resource(name = "clockInRecordRepository")
    private ClockInRecordRepository mClockInRecordRepository;

    public List<ClockInRecord> queryClockInRecords(Map pMap) {
        return getClockInRecordRepository().queryClockInRecords(pMap);
    }

    public ClockInRecordRepository getClockInRecordRepository() {
        return mClockInRecordRepository;
    }

    public void setClockInRecordRepository(ClockInRecordRepository pClockInRecordRepository) {
        mClockInRecordRepository = pClockInRecordRepository;
    }
}
