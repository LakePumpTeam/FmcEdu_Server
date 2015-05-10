package com.fmc.edu.web.controller;

import com.fmc.edu.constant.GlobalConstant;
import com.fmc.edu.constant.SessionConstant;
import com.fmc.edu.manager.ProfileManager;
import com.fmc.edu.manager.SchoolManager;
import com.fmc.edu.model.address.Address;
import com.fmc.edu.model.student.Student;
import com.fmc.edu.util.ValidationUtils;
import com.fmc.edu.web.RequestParameterBuilder;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yove on 5/4/2015.
 */
@Controller
@RequestMapping("/profile")
public class ProfileController extends BaseController {
    private static final Logger LOG = Logger.getLogger(ProfileController.class);

    private static final String ERROR_INVALID_PHONE = "Sorry, the phone number is invalid.";
    private static final String ERROR_SESSION_EXPIRED = "Sorry, the session has expired.";
    private static final String ERROR_PASSWORD_CONFIRM = "Sorry, the password isn't match the confirmation.";


    @Resource(name = "profileManager")
    private ProfileManager mProfileManager;

    @Resource(name = "schoolManager")
    private SchoolManager mSchoolManager;

    @Resource(name = "requestParameterBuilder")
    private RequestParameterBuilder mParameterBuilder;

    @Autowired
    private DataSourceTransactionManager mTransactionManager;

    @RequestMapping(value = ("/requestPhoneIdentify" + GlobalConstant.URL_SUFFIX))
    @ResponseBody
    public String requestPhoneIdentify(final HttpServletRequest pRequest, final HttpServletResponse pResponse, final String cellPhone) throws IOException {
        LOG.debug("Obtain encoded cellphone:" + cellPhone);
        String phone = decodeInput(cellPhone);
        LOG.debug("Decoded cellphone:" + phone);

        // output error if phone number is blank
        if (cellPhone == null || ValidationUtils.isValidPhoneNumber(phone)) {
            return generateJsonOutput(Boolean.FALSE, null, ERROR_INVALID_PHONE);
        }
        String identifyCode = getProfileManager().registerTempParent(phone);
        boolean success = false;
        if (StringUtils.isNotBlank(identifyCode)) {
            success = true;
            pRequest.getSession().setAttribute(SessionConstant.SESSION_KEY_PHONE, phone);
            // save temp profile bean to session
        }
        // request identify failed if identify is blank
        // TODO should not return code, return for test
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("identifyCode", identifyCode);
        return generateJsonOutput(success, dataMap, null);
    }

    @RequestMapping(value = "/requestRegisterConfirm" + GlobalConstant.URL_SUFFIX)
    @ResponseBody
    public String requestRegisterConfirm(final HttpServletRequest pRequest, final HttpServletResponse pResponse, String cellPhone, final String authCode,
                                         String password, String confirmPassword) throws IOException {
        String identifyingCode = decodeInput(authCode);
        String phoneNumber = decodeInput(cellPhone);
        String passwordDecode = decodeInput(password);
        if (StringUtils.isBlank(phoneNumber)) {
            phoneNumber = (String) pRequest.getSession().getAttribute(SessionConstant.SESSION_KEY_PHONE);
        }
        if (StringUtils.isBlank(phoneNumber)) {
            return generateJsonOutput(Boolean.FALSE, null, ERROR_SESSION_EXPIRED);
        }
        if (!StringUtils.endsWith(password, confirmPassword)) {
            return generateJsonOutput(Boolean.FALSE, null, ERROR_PASSWORD_CONFIRM);
        }
        boolean success = getProfileManager().verifyTempParentIdentifyingCode(phoneNumber, passwordDecode, identifyingCode);
        //TODO Should response error message when status is not 0.
        return generateJsonOutput(success, null, null);
    }

    @RequestMapping(value = "/requestPhoneIdentifyReply" + GlobalConstant.URL_SUFFIX)
    @ResponseBody
    public String requestRegisterParentProfile(HttpServletRequest pRequest, final HttpServletResponse pResponse, final String cellphone) throws IOException {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = getTransactionManager().getTransaction(def);
        boolean success = true;
        try {
            String phone = decodeInput(cellphone);
            Address address = getParameterBuilder().buildAddress(pRequest);

            Student student = getParameterBuilder().buildStudent(pRequest);

        } catch (Exception e) {
            status.setRollbackOnly();
        } finally {
            getTransactionManager().commit(status);
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("issuccess", success);
            return generateJsonOutput(success, dataMap, null);
        }
    }

    public ProfileManager getProfileManager() {
        return mProfileManager;
    }

    public void setProfileManager(final ProfileManager pProfileManager) {
        mProfileManager = pProfileManager;
    }

    public SchoolManager getSchoolManager() {
        return mSchoolManager;
    }

    public void setSchoolManager(final SchoolManager pSchoolManager) {
        mSchoolManager = pSchoolManager;
    }

    public RequestParameterBuilder getParameterBuilder() {
        return mParameterBuilder;
    }

    public void setParameterBuilder(final RequestParameterBuilder pParameterBuilder) {
        mParameterBuilder = pParameterBuilder;
    }

    public DataSourceTransactionManager getTransactionManager() {
        return mTransactionManager;
    }

    public void setTransactionManager(final DataSourceTransactionManager pTransactionManager) {
        mTransactionManager = pTransactionManager;
    }
}
