package com.fmc.edu.xml.parser;

import org.apache.poi.ss.usermodel.Sheet;

import java.util.List;

/**
 * Created by dylanyu on 9/8/2015.
 */
public interface IXmlParser<T> {
    List<T> parse(Sheet mSheet);
}
