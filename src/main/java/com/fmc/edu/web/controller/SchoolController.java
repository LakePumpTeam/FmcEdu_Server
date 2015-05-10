package com.fmc.edu.web.controller;

import com.fmc.edu.manager.SchoolManager;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * Created by silly on 2015/5/10.
 */
@RequestMapping("/school")
public class SchoolController {
    @Resource(name = "schoolManager")
    private SchoolManager mSchoolManager;

    public SchoolManager getSchoolManager() {
        return mSchoolManager;
    }

    public void setSchoolManager(SchoolManager pSchoolManager) {
        this.mSchoolManager = pSchoolManager;
    }
}
