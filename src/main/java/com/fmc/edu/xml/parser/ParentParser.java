package com.fmc.edu.xml.parser;

import com.fmc.edu.xml.model.RParent;
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
public class ParentParser implements IXmlParser<RParent> {

	private static final Logger LOGGER = Logger.getLogger(ParentParser.class);

	public List<RParent> parse(Sheet mSheet) {
		if (mSheet == null || !(mSheet instanceof XSSFSheet)) {
			return Collections.EMPTY_LIST;
		}
		XSSFSheet xssfSheet = (XSSFSheet) mSheet;
		Iterator<Row> rowIterator = xssfSheet.iterator();
		List<RParent> parents = new ArrayList<RParent>(xssfSheet.getLastRowNum() - xssfSheet.getFirstRowNum());
		RParent parent;
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
			if (eachRow.getCell(0).getStringCellValue() == null || "".equalsIgnoreCase(eachRow.getCell(0).getStringCellValue())) {
				break;
			}
			parent = new RParent();

			parent.setStudentSerialNumber(eachRow.getCell(0).getStringCellValue());
			parent.setStudentName(eachRow.getCell(1).getStringCellValue());
			parent.setName(eachRow.getCell(2).getStringCellValue());
			parent.setPhone(eachRow.getCell(3).getStringCellValue());
			parent.setRelationship(eachRow.getCell(4).getStringCellValue());

			if (StringUtils.isNotBlank(parent.getPhone())) {
				String phone = parent.getPhone().trim();
				parent.setPhone(phone);
				if (phone.length() != 11) {
					LOGGER.error("Invalid phone number: " + parent.getPhone() + " for parent: " + parent.getName());
					if (phone.length() > 11) {
						parent.setPhone(parent.getPhone().trim().substring(0, 11));
					}
				}
			}
			parents.add(parent);
		}
		return parents;
	}
}
