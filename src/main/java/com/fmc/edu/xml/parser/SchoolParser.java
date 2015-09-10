package com.fmc.edu.xml.parser;

import com.fmc.edu.xml.model.RSchool;
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
public class SchoolParser implements IXmlParser<RSchool> {

    public List<RSchool> parse(Sheet mSheet) {
        if (mSheet == null || !(mSheet instanceof XSSFSheet)) {
            return Collections.EMPTY_LIST;
        }
        XSSFSheet xssfSheet = (XSSFSheet) mSheet;
        Iterator<Row> rowIterator = xssfSheet.iterator();
        List<RSchool> schools = new ArrayList<RSchool>(xssfSheet.getLastRowNum() - xssfSheet.getFirstRowNum());
        RSchool eachSchool;
        Row eachRow;
        while (rowIterator.hasNext()) {
            eachRow = rowIterator.next();
            if (eachRow == null || eachRow.getRowNum() == 0) {
                continue;
            }
            eachRow.getCell(0).setCellType(HSSFCell.CELL_TYPE_STRING);
            eachRow.getCell(1).setCellType(HSSFCell.CELL_TYPE_STRING);
            eachRow.getCell(2).setCellType(HSSFCell.CELL_TYPE_STRING);
            if (eachRow.getCell(0).getStringCellValue() == null || "".equalsIgnoreCase(eachRow.getCell(0).getStringCellValue())) {
                break;
            }
            eachSchool = new RSchool();

            eachSchool.setProvince(eachRow.getCell(0).getStringCellValue());
            eachSchool.setCity(eachRow.getCell(1).getStringCellValue());
            eachSchool.setSchoolName(eachRow.getCell(2).getStringCellValue());
            schools.add(eachSchool);
        }
        return schools;
    }
}
