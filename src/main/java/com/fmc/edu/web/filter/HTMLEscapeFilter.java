package com.fmc.edu.web.filter;

import com.fmc.edu.web.servlet.HTMLEscapeRequestWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;

/**
 * Created by Yove on 5/1/2015.
 */
public class HTMLEscapeFilter implements Filter {
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequestWrapper requestWrapper = new HTMLEscapeRequestWrapper((HttpServletRequest) request);
		chain.doFilter(requestWrapper, response);
	}

	@Override
	public void destroy() {

	}
}
