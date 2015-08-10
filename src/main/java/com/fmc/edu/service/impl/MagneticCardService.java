package com.fmc.edu.service.impl;

import com.fmc.edu.model.clockin.MagneticCard;
import com.fmc.edu.model.relationship.PersonCarMagneticRelationship;
import com.fmc.edu.repository.impl.MagneticCardRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yu on 2015/7/23.
 */
@Service(value = "magneticCardService")
public class MagneticCardService {
    @Resource(name = "magneticCardRepository")
    private MagneticCardRepository mMagneticCardRepository;

    public List<MagneticCard> queryMagneticCardsByStudentId(int pStudentId) {
        List<Integer> studentIds = new ArrayList<Integer>(1);
        studentIds.add(pStudentId);
        return queryMagneticCardsByStudentId(studentIds);
    }

    public List<MagneticCard> queryMagneticCardsByStudentId(List<Integer> pStudentIds) {
        return getMagneticCardRepository().queryMagneticCardsByStudentId(pStudentIds);
    }

    public PersonCarMagneticRelationship queryPersonMagneticCardRelationship(int pMagneticCardId) {
        return getMagneticCardRepository().queryPersonMagneticCardRelationship(pMagneticCardId);
    }

    public MagneticCard queryMagneticCardById(int pMagneticCardId) {
        return getMagneticCardRepository().queryMagneticCardById(pMagneticCardId);
    }

    public MagneticCard queryMagneticCardByCardNo(String pCardNo) {
        return getMagneticCardRepository().queryMagneticCardByCardNo(pCardNo);
    }

    public boolean updateMagneticCard(MagneticCard pMagneticCard) {
        return getMagneticCardRepository().updateMagneticCard(pMagneticCard);
    }

    public boolean updatePersonMagneticCardRelationship(PersonCarMagneticRelationship pPersonCarMagneticRelationship) {
        return getMagneticCardRepository().updatePersonMagneticCardRelationship(pPersonCarMagneticRelationship);
    }

    public MagneticCardRepository getMagneticCardRepository() {
        return mMagneticCardRepository;
    }

    public void setMagneticCardRepository(MagneticCardRepository pMagneticCardRepository) {
        mMagneticCardRepository = pMagneticCardRepository;
    }
}
