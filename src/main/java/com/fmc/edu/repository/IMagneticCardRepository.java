package com.fmc.edu.repository;

import com.fmc.edu.model.clockin.MagneticCard;
import com.fmc.edu.model.relationship.PersonCarMagneticRelationship;

import java.util.List;

/**
 * Created by Yu on 2015/7/23.
 */
public interface IMagneticCardRepository {

    String QUERY_MAGNETIC_CARDS_BY_STUDENT_ID = "com.fmc.edu.magnetic.card.queryMagneticCardsByStudentId";

    List<MagneticCard> queryMagneticCardsByStudentId(List<Integer> pStudentIds);

    String QUERY_PERSON_MAGNETIC_CARD_RELATIONSHIP = "com.fmc.edu.magnetic.card.queryPersonMagneticCardRelationship";

    PersonCarMagneticRelationship queryPersonMagneticCardRelationship(int pMagneticCardId);

    String QUERY_MAGNETIC_CARD_BY_ID = "com.fmc.edu.magnetic.card.queryMagneticCardById";

    MagneticCard queryMagneticCardById(int pMagneticCardId);

    String QUERY_MAGNETIC_CARD_BY_CARD_NO = "com.fmc.edu.magnetic.card.queryMagneticCardByCardNo";

    MagneticCard queryMagneticCardByCardNo(String pCardNo);

    String UPDATE_MAGETIC_CARD = "com.fmc.edu.magnetic.card.updateMagneticCard";

    boolean updateMagneticCard(MagneticCard pMagneticCard);

    String UPDATE_PERSION_MAGNETIC_CARD_RELATIONSHIP = "com.fmc.edu.magnetic.card.updatePersonMagneticCardRelationship";

    boolean updatePersonMagneticCardRelationship(PersonCarMagneticRelationship pPersonCarMagneticRelationship);
}
