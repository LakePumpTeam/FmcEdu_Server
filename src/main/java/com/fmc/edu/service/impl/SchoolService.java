package com.fmc.edu.service.impl;

import com.fmc.edu.repository.ISchoolRepository;
import com.fmc.edu.util.pagenation.Pagination;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by silly on 2015/5/10.
 */
@Service("schoolService")
public class SchoolService {
    @Resource(name="schoolRepository")
    private ISchoolRepository mSchoolRepository;

    public Map<String,Object> querySchoolsPage(Pagination pPagination, int pCityId, String pKey) {
      return getSchoolRepository().querySchoolsPage(pPagination,pCityId,pKey);
    }

    public Map<String,Object> queryClassesPage(Pagination pPagination, int pSchoolId, String pKey) {
     return getSchoolRepository().queryClassesPage(pPagination,pSchoolId,pKey);
    }

    public List<Map<String, String>> queryHeadmasterPage(int pClassId, final int pSchoolId) {
     return getSchoolRepository().queryHeadmasterPage(pClassId,pSchoolId);
    }

    public ISchoolRepository getSchoolRepository() {
        return mSchoolRepository;
    }

    public void setSchoolRepository(ISchoolRepository schoolRepository) {
        this.mSchoolRepository = schoolRepository;
    }
}
