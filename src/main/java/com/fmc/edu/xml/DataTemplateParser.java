package com.fmc.edu.xml;

import com.fmc.edu.xml.model.RParent;
import com.fmc.edu.xml.model.RSchool;
import com.fmc.edu.xml.model.RStudent;
import com.fmc.edu.xml.model.RTeacher;
import com.fmc.edu.xml.parser.ParentParser;
import com.fmc.edu.xml.parser.SchoolParser;
import com.fmc.edu.xml.parser.StudentParser;
import com.fmc.edu.xml.parser.TeacherParser;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by dylanyu on 9/8/2015.
 */
@Component("dataTemplateParser")
public class DataTemplateParser {
    private static final String M_SCHOOL_SHEET_NAME = "学校信息";
    private static final String M_MASTER_TEACHER_SHEET_NAME = "班主任信息收集（重要）";
    private static final String M_TEACHER_SHEET_NAME = "任课老师老师信息收集";
    private static final String M_PARENT_SHEET_NAME = "家长信息收集（重要）";
    private static final String M_STUDENT_SHEET_NAME = "学生信息收集（重要）";

    public DataHolder parser(InputStream pInputStream) {
        if (pInputStream == null) {
            return null;
        }
        DataHolder dataHolder = new DataHolder();
        try {
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(pInputStream);
            int sheetCount = xssfWorkbook.getNumberOfSheets();
            for (int idx = 0; idx < sheetCount; idx++) {
                if (xssfWorkbook.getSheetName(idx).trim().equalsIgnoreCase(M_SCHOOL_SHEET_NAME)) {
                    List<RSchool> schools = new SchoolParser().parse(xssfWorkbook.getSheetAt(idx));
                    dataHolder.setSchools(schools);
                } else if (xssfWorkbook.getSheetName(idx).trim().equalsIgnoreCase(M_MASTER_TEACHER_SHEET_NAME)) {
                    List<RTeacher> teachers = new TeacherParser(true).parse(xssfWorkbook.getSheetAt(idx));
                    dataHolder.setTeachers(teachers);
                } else if (xssfWorkbook.getSheetName(idx).trim().equalsIgnoreCase(M_TEACHER_SHEET_NAME)) {
                    List<RTeacher> teachers = new TeacherParser(false).parse(xssfWorkbook.getSheetAt(idx));
                    dataHolder.setTeachers(teachers);
                } else if (xssfWorkbook.getSheetName(idx).trim().equalsIgnoreCase(M_PARENT_SHEET_NAME)) {
                    List<RParent> parents = new ParentParser().parse(xssfWorkbook.getSheetAt(idx));
                    dataHolder.setParents(parents);
                } else if (xssfWorkbook.getSheetName(idx).trim().equalsIgnoreCase(M_STUDENT_SHEET_NAME)) {
                    List<RStudent> students = new StudentParser().parse(xssfWorkbook.getSheetAt(idx));
                    dataHolder.setStudents(students);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataHolder;
    }
}
