package com.fmc.edu.xml.parser;

import com.fmc.edu.xml.model.RTeacher;
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
        RTeacher eachTeacher;
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
            eachTeacher = new RTeacher();
            eachTeacher.setMasterTeacher(mMasterTeacherParser);
            eachTeacher.setGrade(eachRow.getCell(0).getStringCellValue());
            eachTeacher.setCls(eachRow.getCell(1).getStringCellValue());
            eachTeacher.setName(eachRow.getCell(2).getStringCellValue());
            eachTeacher.setSex(eachRow.getCell(3).getStringCellValue());
            eachTeacher.setPhone(eachRow.getCell(4).getStringCellValue());
            eachTeacher.setCourse(eachRow.getCell(5).getStringCellValue());
            eachTeacher.setBirthday(eachRow.getCell(6).getStringCellValue());
            eachTeacher.setResume(eachRow.getCell(7).getStringCellValue());

            teachers.add(eachTeacher);
        }
        return teachers;
    }
}
