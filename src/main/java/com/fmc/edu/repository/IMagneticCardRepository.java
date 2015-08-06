package com.fmc.edu.repository;

import com.fmc.edu.model.clockin.MagneticCard;
import com.fmc.edu.model.relationship.PersonCarMagneticRelationship;

import java.util.List;

/**
 * Created by Yu on 2015/7/23.
 */
public interface IMagneticCardRepository {
    String QUERY_MAGNETIC_BY_STUDENT_ID_FOR_PANRENT = "com.fmc.edu.magnetic.card.queryMagneticByStudentIdForParent";

    List<MagneticCard> queryMagneticByStudentIdForParent(int pStudentId);

    String QUERY_MAGNETIC_BY_STUDENT_ID_FOR_STUDENTS = "com.fmc.edu.magnetic.card.queryMagneticByStudentIdForStudents";

    List<MagneticCard> queryMagneticByStudentIdForStudents(List<Integer> pStudentIds);

    String QUERY_PERSON_MAGNETIC_CARD_RELATIONSHIP = "com.fmc.edu.magnetic.card.queryPersonMagneticCardRelationship";

    PersonCarMagneticRelationship queryPersonMagneticCardRelationship(int pMagneticCardId);

    String QUERY_MAGNETIC_CARD_BY_ID = "com.fmc.edu.magnetic.card.queryMagneticCardById";

    MagneticCard queryMagneticCardById(int pMagneticCardId);

    String UPDATE_MAGETIC_CARD = "com.fmc.edu.magnetic.card.updateMagneticCard";

    boolean updateMagneticCard(MagneticCard pMagneticCard);

    String UPDATE_PERSION_MAGNETIC_CARD_RELATIONSHIP = "com.fmc.edu.magnetic.card.updatePersonMagneticCardRelationship";

    boolean updatePersonMagneticCardRelationship(PersonCarMagneticRelationship pPersonCarMagneticRelationship);
}
