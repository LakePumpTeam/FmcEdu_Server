package com.fmc.edu.manager;

import com.fmc.edu.service.impl.SchoolService;
import com.fmc.edu.util.pagenation.Pagination;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Yove on 5/8/2015.
 */
@Service(value = "schoolManager")
public class SchoolManager {
    @Resource(name = "schoolService")
    private SchoolService mSchoolService;

    public List<Map<String, String>> querySchoolsPage(Pagination pPagination, int pCityId, String pKey) {
        return getSchoolService().querySchoolsPage(pPagination,pCityId,pKey);
    }

    public List<Map<String, String>> queryClassesPage(Pagination pPagination, int pSchoolId, String pKey) {
        return getSchoolService().queryClassesPage(pPagination,pSchoolId,pKey);
    }

    public List<Map<String, String>> queryHeadmasterPage(Pagination pPagination, int pClassId, final int pSchoolId, String pKey) {
        return getSchoolService().queryHeadmasterPage(pPagination,pClassId,pSchoolId,pKey);
    }
    public SchoolService getSchoolService() {
        return mSchoolService;
    }

    public void setSchoolService(SchoolService pSchoolService) {
        this.mSchoolService = pSchoolService;
    }
}
