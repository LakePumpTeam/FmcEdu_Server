package com.fmc.edu.repository.impl;

import com.fmc.edu.model.clockin.ClockInRecord;
import com.fmc.edu.repository.BaseRepository;
import com.fmc.edu.repository.IClockInRecordRepository;
import com.fmc.edu.util.DateUtils;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Yu on 7/19/2015.
 */
@Repository("clockInRecordRepository")
public class ClockInRecordRepository extends BaseRepository implements IClockInRecordRepository {
    @Override
    public List<ClockInRecord> queryClockInRecords(Map pMap) {
        return getSqlSession().selectList(QUERY_CLOCK_IN_RECORDS, pMap);
    }

    @Override
    public List<Map> queryClockInRecordsAttendances(List<Integer> pProfiles, Integer pClassId, Timestamp pDate, int pPeriod) {
        Map<String, Object> paramter = new HashMap<String, Object>();
        paramter.put("profileIds", pProfiles);
        paramter.put("classId", pClassId);
        if (pPeriod == 0) {
            paramter.put("startDate", new Timestamp(DateUtils.getDateTimeStart(pDate).getTime()));
            paramter.put("endDate", new Timestamp(DateUtils.getDateTimeEnd(pDate).getTime()));
        } else if (pPeriod == 1) {//just morning records
            paramter.put("startDate", new Timestamp(DateUtils.getDateTimeStart(pDate).getTime()));
            paramter.put("endDate", new Timestamp(DateUtils.getDateTimeMiddle(pDate).getTime()));
        } else if (pPeriod == 2) {//just afternoon records
            paramter.put("startDate", new Timestamp(DateUtils.getDateTimeMiddle(pDate).getTime()));
            paramter.put("endDate", new Timestamp(DateUtils.getDateTimeEnd(pDate).getTime()));
        }

        return getSqlSession().selectList(QUERY_CLOCK_IN_RECORDS_BY_PROFILE_IDS, paramter);
    }

    @Override
    public List<Map> queryNotAttendancesRecords(List<Integer> pProfileIds, Integer pClassId) {
        Map<String, Object> paramter = new HashMap<String, Object>();
        paramter.put("profileIds", pProfileIds);
        paramter.put("classId", pClassId);
        return getSqlSession().selectList(QUERY_NOT_ATTENDANCE_RECORDS, paramter);
    }
}
