package com.fmc.edu.xml.parser;

import com.fmc.edu.xml.model.RTeacher;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by dylanyu on 9/8/2015.
 */
public class TeacherParser implements IXmlParser<RTeacher> {

	private static final Logger LOGGER = Logger.getLogger(TeacherParser.class);

	private boolean mMasterTeacherParser;

	public TeacherParser(boolean pMasterTeacherParser) {
		mMasterTeacherParser = pMasterTeacherParser;
	}

	public List<RTeacher> parse(Sheet mSheet) {
		if (mSheet == null || !(mSheet instanceof XSSFSheet)) {
			return Collections.EMPTY_LIST;
		}
		XSSFSheet xssfSheet = (XSSFSheet) mSheet;
		Iterator<Row> rowIterator = xssfSheet.iterator();
		List<RTeacher> teachers = new ArrayList<RTeacher>(xssfSheet.getLastRowNum() - xssfSheet.getFirstRowNum());
		RTeacher teacher;
		Row eachRow;
		while (rowIterator.hasNext()) {
			eachRow = rowIterator.next();
			if (eachRow == null || eachRow.getRowNum() == 0) {
				continue;
			}
			eachRow.getCell(0).setCellType(HSSFCell.CELL_TYPE_STRING);
			eachRow.getCell(1).setCellType(HSSFCell.CELL_TYPE_STRING);
			eachRow.getCell(2).setCellType(HSSFCell.CELL_TYPE_STRING);
			eachRow.getCell(3).setCellType(HSSFCell.CELL_TYPE_STRING);
			eachRow.getCell(4).setCellType(HSSFCell.CELL_TYPE_STRING);
			eachRow.getCell(5).setCellType(HSSFCell.CELL_TYPE_STRING);
			eachRow.getCell(6).setCellType(HSSFCell.CELL_TYPE_STRING);
			eachRow.getCell(7).setCellType(HSSFCell.CELL_TYPE_STRING);
			if (eachRow.getCell(0).getStringCellValue() == null || "".equalsIgnoreCase(eachRow.getCell(0).getStringCellValue())) {
				break;
			}
			teacher = new RTeacher();
			teacher.setMasterTeacher(mMasterTeacherParser);
			teacher.setGrade(eachRow.getCell(0).getStringCellValue());
			teacher.setCls(eachRow.getCell(1).getStringCellValue());
			teacher.setName(eachRow.getCell(2).getStringCellValue());
			teacher.setSex(eachRow.getCell(3).getStringCellValue());
			teacher.setPhone(eachRow.getCell(4).getStringCellValue());
			teacher.setCourse(eachRow.getCell(5).getStringCellValue());
			teacher.setBirthday(eachRow.getCell(6).getStringCellValue());
			teacher.setResume(eachRow.getCell(7).getStringCellValue());

			if (StringUtils.isNotBlank(teacher.getPhone())) {
				String phone = teacher.getPhone().trim();
				teacher.setPhone(phone);
				if (phone.length() != 11) {
					LOGGER.error("Invalid phone number: " + teacher.getPhone() + " for teacher: " + teacher.getName());
					if (phone.length() > 11) {
						teacher.setPhone(teacher.getPhone().trim().substring(0, 11));
					}
				}
			}
			teachers.add(teacher);
		}
		return teachers;
	}
}
