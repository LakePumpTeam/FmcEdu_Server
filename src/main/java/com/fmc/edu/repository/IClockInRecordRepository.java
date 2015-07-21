package com.fmc.edu.repository;

import com.fmc.edu.model.clockin.ClockInRecord;
import com.fmc.edu.model.clockin.MagneticCard;

import java.util.List;
import java.util.Map;

/**
 * Created by Yu on 7/19/2015.
 */
public interface IClockInRecordRepository {
    String QUERY_CLOCK_IN_RECORDS = "com.fmc.edu.clockin.queryClockInRecords";

    List<ClockInRecord> queryClockInRecords(Map pMap);

    String QUERY_MAGNETIC_BY_STUDENT_ID = "com.fmc.edu.clockin.queryMagneticByStudentId";

    MagneticCard queryMagneticByStudentId(int pStudentId);


}
