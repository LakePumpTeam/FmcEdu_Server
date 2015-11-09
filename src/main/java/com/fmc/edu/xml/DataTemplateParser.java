package com.fmc.edu.xml;

import com.fmc.edu.xml.model.RParent;
import com.fmc.edu.xml.model.RSchool;
import com.fmc.edu.xml.model.RStudent;
import com.fmc.edu.xml.model.RTeacher;
import com.fmc.edu.xml.parser.ParentParser;
import com.fmc.edu.xml.parser.SchoolParser;
import com.fmc.edu.xml.parser.StudentParser;
import com.fmc.edu.xml.parser.TeacherParser;
import org.apache.log4j.Logger;
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

	private static final Logger LOGGER = Logger.getLogger(DataTemplateParser.class);

	private static final String M_SCHOOL_SHEET_NAME = "school";
	private static final String M_MASTER_TEACHER_SHEET_NAME = "master_teacher";
	private static final String M_TEACHER_SHEET_NAME = "teacher";
	private static final String M_PARENT_SHEET_NAME = "parent";
	private static final String M_STUDENT_SHEET_NAME = "student";

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
			LOGGER.error("Parse date occur error.", e);
		}
		return dataHolder;
	}
}
