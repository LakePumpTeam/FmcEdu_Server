package com.fmc.edu.repository.impl;

import com.fmc.edu.model.clockin.MagneticCard;
import com.fmc.edu.model.relationship.PersonCarMagneticRelationship;
import com.fmc.edu.repository.BaseRepository;
import com.fmc.edu.repository.IMagneticCardRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Yu on 2015/7/23.
 */

@Repository("magneticCardRepository")
public class MagneticCardRepository extends BaseRepository implements IMagneticCardRepository {

    @Override
    public List<MagneticCard> queryMagneticByStudentIdForParent(int pStudentId) {
        return getSqlSession().selectList(QUERY_MAGNETIC_BY_STUDENT_ID_FOR_PANRENT, pStudentId);
    }

    @Override
    public List<MagneticCard> queryMagneticByStudentIdForStudents(List<Integer> pStudentIds) {
        Map<String, Object> parameter = new HashMap<String, Object>(1);
        parameter.put("studentIds", pStudentIds);
        return getSqlSession().selectList(QUERY_MAGNETIC_BY_STUDENT_ID_FOR_STUDENTS, parameter);
    }

    @Override
    public PersonCarMagneticRelationship queryPersonMagneticCardRelationship(int pMagneticCardId) {
        return getSqlSession().selectOne(QUERY_PERSON_MAGNETIC_CARD_RELATIONSHIP, pMagneticCardId);
    }

    @Override
    public MagneticCard queryMagneticCardById(int pMagneticCardId) {
        return getSqlSession().selectOne(QUERY_MAGNETIC_CARD_BY_ID, pMagneticCardId);
    }

    @Override
    public boolean updateMagneticCard(MagneticCard pMagneticCard) {
        return getSqlSession().update(UPDATE_MAGETIC_CARD, pMagneticCard) > 0;
    }

    @Override
    public boolean updatePersonMagneticCardRelationship(PersonCarMagneticRelationship pPersonCarMagneticRelationship) {
        return getSqlSession().update(UPDATE_PERSION_MAGNETIC_CARD_RELATIONSHIP, pPersonCarMagneticRelationship) > 0;
    }
}
