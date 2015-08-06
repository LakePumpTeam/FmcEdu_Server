package com.fmc.edu.manager;

import com.fmc.edu.model.clockin.ClockInRecord;
import com.fmc.edu.service.impl.ClockInRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
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

    public List<Map> queryClockInRecordsAttendances(List<Integer> pProfiles, Integer pClassId, Timestamp pDate, int pPeriod) {
        return getClockInRecordService().queryClockInRecordsAttendances(pProfiles, pClassId, pDate, pPeriod);
    }

    public List<Map> queryNotAttendancesRecords(List<Integer> pProfileIds, Integer pClassId) {
        return getClockInRecordService().queryNotAttendancesRecords(pProfileIds, pClassId);
    }

    public boolean insertClockInRecord(ClockInRecord pClockInRecord) {
        return getClockInRecordService().insertClockInRecord(pClockInRecord);
    }

    public ClockInRecordService getClockInRecordService() {
        return mClockInRecordService;
    }

    public void setClockInRecordService(ClockInRecordService pClockInRecordService) {
        mClockInRecordService = pClockInRecordService;
    }
}
