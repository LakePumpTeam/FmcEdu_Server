package com.fmc.edu.repository.impl;

import com.fmc.edu.model.clockin.ClockInRecord;
import com.fmc.edu.repository.BaseRepository;
import com.fmc.edu.repository.IClockInRecordRepository;
import org.springframework.stereotype.Repository;

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
}
