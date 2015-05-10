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

    public Map<String,Object> querySchoolsPage(Pagination pPagination, int pCityId, String pKey) {
        return getSchoolService().querySchoolsPage(pPagination,pCityId,pKey);
    }

    public Map<String,Object> queryClassesPage(Pagination pPagination, int pSchoolId, String pKey) {
        return getSchoolService().queryClassesPage(pPagination,pSchoolId,pKey);
    }

    public List<Map<String, String>> queryHeadmasterPage(int pClassId, final int pSchoolId) {
        return getSchoolService().queryHeadmasterPage(pClassId,pSchoolId);
    }
    public SchoolService getSchoolService() {
        return mSchoolService;
    }

    public void setSchoolService(SchoolService pSchoolService) {
        this.mSchoolService = pSchoolService;
    }
}
