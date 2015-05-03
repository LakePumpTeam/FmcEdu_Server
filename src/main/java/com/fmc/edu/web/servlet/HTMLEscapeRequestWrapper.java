package com.fmc.edu.web.servlet;

import org.apache.commons.lang.StringEscapeUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * Created by Yove on 5/1/2015.
 */
public class HTMLEscapeRequestWrapper extends HttpServletRequestWrapper {

	public HTMLEscapeRequestWrapper(HttpServletRequest pRequest) {
		super(pRequest);
	}


	@Override
	public String getParameter(String pName) {
		return StringEscapeUtils.escapeHtml(this.getRequest().getParameter(pName));
	}
}
