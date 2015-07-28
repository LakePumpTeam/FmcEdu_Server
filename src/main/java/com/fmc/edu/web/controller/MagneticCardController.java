package com.fmc.edu.web.controller;

import com.fmc.edu.manager.MagneticCardManager;
import com.fmc.edu.manager.StudentManager;
import com.fmc.edu.model.clockin.MagneticCard;
import com.fmc.edu.model.relationship.ParentStudentRelationship;
import com.fmc.edu.model.relationship.PersonCarMagneticRelationship;
import com.fmc.edu.model.student.Student;
import com.fmc.edu.util.RepositoryUtils;
import com.fmc.edu.util.StringUtils;
import com.fmc.edu.web.ResponseBean;
import com.fmc.edu.web.ResponseBuilder;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Yu on 2015/7/23.
 */
@RequestMapping("/magneticCard")
@Controller
public class MagneticCardController extends BaseController {
    @Resource(name = "studentManager")
    private StudentManager mStudentManager;

    @Resource(name = "responseBuilder")
    private ResponseBuilder mResponseBuilder;

    @Resource(name = "magneticCardManager")
    private MagneticCardManager mMagneticCardManager;

    @RequestMapping("/retrieveAllMagneticCard")
    @ResponseBody
    public String retrieveAllMagneticCard(final HttpServletRequest pRequest, final HttpServletResponse pResponse) {
        ResponseBean responseBean = new ResponseBean(pRequest);
        String studentId = pRequest.getParameter("studentId");
        if (!RepositoryUtils.idIsValid(studentId)) {
            responseBean.addBusinessMessage("学生ID不能为空.");
            return output(responseBean);
        }

        Student student = getStudentManager().queryStudentById(Integer.valueOf(studentId));
        if (student == null) {
            responseBean.addBusinessMessage("学生不存在:" + studentId);
            return output(responseBean);
        }
        List<ParentStudentRelationship> parentStudentRelationshipList = getStudentManager().queryParentStudentRelationshipByStudentId(Integer.valueOf(studentId));
        if (CollectionUtils.isEmpty(parentStudentRelationshipList)) {
            responseBean.addBusinessMessage("未查询到家长.");
            return output(responseBean);
        }
        List<Map<String, Object>> magneticCardList = new ArrayList<Map<String, Object>>();
        responseBean.addData("magneticCards", magneticCardList);
        for (ParentStudentRelationship psr : parentStudentRelationshipList) {
            if (!responseBean.isSuccess()) {
                break;
            }
            if (psr == null) {
                continue;
            }
            getResponseBuilder().buildMagneticCardList(psr, magneticCardList, responseBean);
        }
        return output(responseBean);
    }

    @RequestMapping("/reportMagneticCardLost")
    @ResponseBody
    public String reportMagneticCardLost(final HttpServletRequest pRequest, final HttpServletResponse pResponse) {
        ResponseBean responseBean = new ResponseBean(pRequest);
        String magneticCardId = pRequest.getParameter("magneticCardId");
        String lost = pRequest.getParameter("lost");
        if (!RepositoryUtils.idIsValid(magneticCardId)) {
            responseBean.addBusinessMessage("磁卡id错误:" + magneticCardId);
            return output(responseBean);
        }

        //TODO
        if (StringUtils.isBlank(lost)) {
            lost = "true";
        }

        MagneticCard magneticCard = getMagneticCardManager().queryMagneticCardById(Integer.valueOf(magneticCardId));
        if (magneticCard == null) {
            responseBean.addBusinessMessage("磁卡无效:" + magneticCardId);
            return output(responseBean);
        }
        TransactionStatus status = ensureTransaction();
        boolean isSuccess = false;
        try {
           /* if ("true".equalsIgnoreCase(lost)) {
                magneticCard.setComments("挂失");
            } else {
                magneticCard.setComments("重新激活");
            }*/
            //  if (getMagneticCardManager().updateMagneticCard(magneticCard)) {
            PersonCarMagneticRelationship pcmr = getMagneticCardManager().queryPersonMagneticCardRelationship(magneticCard.getId());
            if (pcmr != null) {
                if ("true".equalsIgnoreCase(lost)) {
                    pcmr.setAvailable(false);
                } else {
                    pcmr.setAvailable(true);
                }
                getMagneticCardManager().updatePersonMagneticCardRelationship(pcmr);
                isSuccess = true;
            }
            //  }
            if (!isSuccess) {
                status.setRollbackOnly();
            }
        } catch (Exception e) {
            responseBean.addErrorMsg(e);
            status.setRollbackOnly();
        } finally {
            getTransactionManager().commit(status);
        }
        return output(responseBean);
    }

    public StudentManager getStudentManager() {
        return mStudentManager;
    }

    public void setStudentManager(StudentManager pStudentManager) {
        mStudentManager = pStudentManager;
    }

    public ResponseBuilder getResponseBuilder() {
        return mResponseBuilder;
    }

    public void setResponseBuilder(ResponseBuilder pResponseBuilder) {
        mResponseBuilder = pResponseBuilder;
    }

    public MagneticCardManager getMagneticCardManager() {
        return mMagneticCardManager;
    }

    public void setMagneticCardManager(MagneticCardManager pMagneticCardManager) {
        mMagneticCardManager = pMagneticCardManager;
    }
}
