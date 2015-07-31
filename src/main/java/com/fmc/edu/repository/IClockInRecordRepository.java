package com.fmc.edu.repository;

import com.fmc.edu.model.clockin.ClockInRecord;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * Created by Yu on 7/19/2015.
 */
public interface IClockInRecordRepository {
    String QUERY_CLOCK_IN_RECORDS = "com.fmc.edu.clockin.queryClockInRecords";

    List<ClockInRecord> queryClockInRecords(Map pMap);

    String QUERY_CLOCK_IN_RECORDS_BY_PROFILE_IDS = "com.fmc.edu.clockin.queryClockInRecordsAttendances";

    List<Map> queryClockInRecordsAttendances(List<Integer> pProfiles, Integer pClassId, Timestamp pDate, int pPeriod);

    String QUERY_NOT_ATTENDANCE_RECORDS = "com.fmc.edu.clockin.queryNotAttendancesRecords";

    List<Map> queryNotAttendancesRecords(List<Integer> pProfileIds, Integer pClassId);

}
