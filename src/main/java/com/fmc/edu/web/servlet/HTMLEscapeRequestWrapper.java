package com.fmc.edu.web.servlet;

import org.apache.commons.lang.StringEscapeUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * Created by Yove on 5/1/2015.
 */
public class HTMLEscapeRequestWrapper extends HttpServletRequestWrapper {

	public HTMLEscapeRequestWrapper(HttpServletRequest request) {
		super(request);
	}


	@Override
	public String getParameter(String name) {
		return StringEscapeUtils.escapeHtml(this.getRequest().getParameter(name));
	}
}
