package com.fmc.edu.manager;

import com.fmc.edu.model.clockin.ClockInRecord;
import com.fmc.edu.model.clockin.MagneticCard;
import com.fmc.edu.service.impl.ClockInRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Yu on 7/19/2015.
 */
@Service("clockInRecordManager")
public class ClockInRecordManager {
    @Resource(name = "clockInRecordService")
    private ClockInRecordService mClockInRecordService;

    public List<ClockInRecord> queryClockInRecords(Map pMap) {
        return getClockInRecordService().queryClockInRecords(pMap);
    }

    public MagneticCard queryMagneticByStudentId(int pStudentId) {
        return getClockInRecordService().queryMagneticByStudentId(pStudentId);
    }

    public ClockInRecordService getClockInRecordService() {
        return mClockInRecordService;
    }

    public void setClockInRecordService(ClockInRecordService pClockInRecordService) {
        mClockInRecordService = pClockInRecordService;
    }
}
