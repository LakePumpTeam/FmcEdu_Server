package com.fmc.edu.manager;

import com.fmc.edu.model.clockin.MagneticCard;
import com.fmc.edu.model.relationship.PersonCarMagneticRelationship;
import com.fmc.edu.service.impl.MagneticCardService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Yu on 2015/7/23.
 */
@Service("magneticCardManager")
public class MagneticCardManager {

    @Resource(name = "magneticCardService")
    private MagneticCardService mMagneticCardService;

    public List<MagneticCard> queryMagneticCardsByStudentId(int pStudentId) {
        return getMagneticCardService().queryMagneticCardsByStudentId(pStudentId);
    }

    public List<MagneticCard> queryMagneticCardsByStudentId(List<Integer> pStudentIds) {
        return getMagneticCardService().queryMagneticCardsByStudentId(pStudentIds);
    }

    public PersonCarMagneticRelationship queryPersonMagneticCardRelationship(int pMagneticCardId) {
        return getMagneticCardService().queryPersonMagneticCardRelationship(pMagneticCardId);
    }

    public MagneticCard queryMagneticCardById(int pMagneticCardId) {
        return getMagneticCardService().queryMagneticCardById(pMagneticCardId);
    }

    public MagneticCard queryMagneticCardByCardNo(String pCardNo) {
        return getMagneticCardService().queryMagneticCardByCardNo(pCardNo);
    }

    public boolean updateMagneticCard(MagneticCard pMagneticCard) {
        return getMagneticCardService().updateMagneticCard(pMagneticCard);
    }

    public boolean updatePersonMagneticCardRelationship(PersonCarMagneticRelationship pPersonCarMagneticRelationship) {
        return getMagneticCardService().updatePersonMagneticCardRelationship(pPersonCarMagneticRelationship);
    }

    public MagneticCardService getMagneticCardService() {
        return mMagneticCardService;
    }

    public void setMagneticCardService(MagneticCardService pMagneticCardService) {
        mMagneticCardService = pMagneticCardService;
    }
}
