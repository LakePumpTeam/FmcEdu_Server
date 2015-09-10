package com.fmc.edu.xml.parser;

import com.fmc.edu.xml.model.RStudent;
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
public class StudentParser implements IXmlParser<RStudent> {

    public List<RStudent> parse(Sheet mSheet) {
        if (mSheet == null || !(mSheet instanceof XSSFSheet)) {
            return Collections.EMPTY_LIST;
        }
        XSSFSheet xssfSheet = (XSSFSheet) mSheet;
        Iterator<Row> rowIterator = xssfSheet.iterator();
        List<RStudent> students = new ArrayList<RStudent>(xssfSheet.getLastRowNum() - xssfSheet.getFirstRowNum());
        RStudent eachStudent;
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
            if (eachRow.getCell(0).getStringCellValue() == null || "".equalsIgnoreCase(eachRow.getCell(0).getStringCellValue())) {
                break;
            }
            eachStudent = new RStudent();

            eachStudent.setGrade(eachRow.getCell(0).getStringCellValue());
            eachStudent.setCls(eachRow.getCell(1).getStringCellValue());
            eachStudent.setMasterTeacherName(eachRow.getCell(2).getStringCellValue());
            eachStudent.setStudentSerialNumber(eachRow.getCell(3).getStringCellValue());
            eachStudent.setName(eachRow.getCell(4).getStringCellValue());
            eachStudent.setSex(eachRow.getCell(5).getStringCellValue());
            eachStudent.setBirthDay(eachRow.getCell(6).getStringCellValue());
            students.add(eachStudent);
        }
        return students;
    }
}
