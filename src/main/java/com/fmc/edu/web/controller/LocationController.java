package com.fmc.edu.web.controller;

import com.fmc.edu.constant.GlobalConstant;
import com.fmc.edu.manager.LocationManager;
import com.fmc.edu.manager.ResourceManager;
import com.fmc.edu.util.RepositoryUtils;
import com.fmc.edu.util.pagenation.Pagination;
import com.fmc.edu.web.ResponseBean;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by Yove on 5/6/2015.
 */
@Controller
@RequestMapping("/location")
public class LocationController extends BaseController {
    private static final Logger LOG = Logger.getLogger(LocationController.class);

    @Resource(name = "locationManager")
    private LocationManager mLocationManager;

    @RequestMapping(value = ("/requestProv" + GlobalConstant.URL_SUFFIX))
    @ResponseBody
    public String requestProv(final HttpServletRequest pRequest, final HttpServletResponse pResponse, String filterKey) {
        ResponseBean responseBean = new ResponseBean(pRequest);
        try {
            preRequestProv(pRequest, responseBean);
            if (!responseBean.isSuccess()) {
                return responseBean.toString();
            }

            String key = filterKey;
            Pagination pagination = buildPagination(pRequest);
            Map<String, Object> dataList = getLocationManager().queryProvincePage(pagination, key);
            responseBean.addData(dataList);
        } catch (IOException e) {
            responseBean.addErrorMsg(e);
            LOG.error(e.getMessage(), e);
        } catch (Exception ex) {
            responseBean.addErrorMsg(ex);
        }
        return responseBean.toString();
    }

    protected void preRequestProv(final HttpServletRequest pRequest, ResponseBean responseBean) {
        validatePaginationParameters(pRequest, responseBean);
    }

    @RequestMapping(value = ("/requestCities" + GlobalConstant.URL_SUFFIX))
    @ResponseBody
    public String requestCityPage(final HttpServletRequest pRequest, final HttpServletResponse pResponse, String filterKey) {
        ResponseBean responseBean = new ResponseBean(pRequest);
        try {
            preRequestCityPage(pRequest, responseBean);

            if (!responseBean.isSuccess()) {
                return responseBean.toString();
            }

            String key = filterKey;
            String provinceId = pRequest.getParameter("provId");
            Pagination pagination = buildPagination(pRequest);
            Map<String, Object> dataList = getLocationManager().queryCityPage(pagination, Integer.valueOf(provinceId), key);
            responseBean.addData(dataList);
        } catch (IOException e) {
            responseBean.addErrorMsg(e);
            LOG.error(e.getMessage(), e);
        } catch (Exception ex) {
            responseBean.addErrorMsg(ex);
        }

        return responseBean.toString();
    }

    protected void preRequestCityPage(final HttpServletRequest pRequest, ResponseBean responseBean) {
        String provId = pRequest.getParameter("provId");
        if (!RepositoryUtils.idIsValid(provId)) {
            responseBean.addBusinessMsg(ResourceManager.VALIDATION_LOCATION_PROVINCE_ID_ERROR, provId);
        }
        validatePaginationParameters(pRequest, responseBean);
    }

    public LocationManager getLocationManager() {
        return mLocationManager;
    }

    public void setLocationManager(final LocationManager pLocationManager) {
        mLocationManager = pLocationManager;
    }
}
