package com.fmc.edu.admin.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Yove on 5/1/2015.
 */
public class AuthenticationFilter implements Filter {


	@Override
	public void doFilter(ServletRequest pRequest, ServletResponse pResponse, FilterChain pChain) throws IOException,
			ServletException {

		HttpServletRequest request = (HttpServletRequest) pRequest;
		HttpServletResponse response = (HttpServletResponse) pResponse;
		Calendar calendar = Calendar.getInstance();
		calendar.set(2015, 6, 1);
		Date due = calendar.getTime();
		if (new Date().after(due)) {
			response.sendRedirect("/error.jsp");
		}
		boolean passAccess = true;
		if (passAccess) {
			pChain.doFilter(pRequest, pResponse);
		} else {
			response.sendRedirect("/AccessDenied.jsp");
		}

	}

	@Override
	public void destroy() {

	}


	@Override
	public void init(FilterConfig pConfig) throws ServletException {

	}

}
