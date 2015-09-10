package com.fmc.edu.xml;

import com.fmc.edu.xml.model.RParent;
import com.fmc.edu.xml.model.RSchool;
import com.fmc.edu.xml.model.RStudent;
import com.fmc.edu.xml.model.RTeacher;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dylanyu on 9/8/2015.
 */
public class DataHolder {
    private List<RStudent> mStudents;
    private List<RParent> mParents;
    private List<RTeacher> mTeachers;
    private List<RSchool> mSchools;

    public DataHolder() {
        mStudents = new ArrayList<RStudent>();
        mParents = new ArrayList<RParent>();
        mTeachers = new ArrayList<RTeacher>();
        mSchools = new ArrayList<RSchool>();
    }

    public List<RStudent> getStudents() {
        return mStudents;
    }

    public void setStudents(List<RStudent> pStudents) {
        mStudents.addAll(pStudents);
    }

    public List<RParent> getParents() {
        return mParents;
    }

    public void setParents(List<RParent> pParents) {
        mParents.addAll(pParents);
    }

    public List<RTeacher> getTeachers() {
        return mTeachers;
    }

    public void setTeachers(List<RTeacher> pTeachers) {
        mTeachers.addAll(pTeachers);
    }

    public List<RSchool> getSchools() {
        return mSchools;
    }

    public void setSchools(List<RSchool> pSchools) {
        mSchools.addAll(pSchools);
    }
}
