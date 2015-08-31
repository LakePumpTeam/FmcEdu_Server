package com.fmc.edu.web.filter;

import com.fmc.edu.manager.MyAccountManager;
import com.fmc.edu.model.profile.BaseProfile;
import com.fmc.edu.util.RepositoryUtils;
import com.fmc.edu.util.StringUtils;
import org.apache.log4j.MDC;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by Yove on 9/1/2015.
 */
public class Log4jMdcFilter implements Filter {

	public static final String SESSION_ID = "sessionId";

	public static final String PROFILE_ID = "profileId";

	@Override
	public void init(final FilterConfig pFilterConfig) throws ServletException {

	}

	@Override
	public void doFilter(final ServletRequest pRequest, final ServletResponse pResponse, final FilterChain pChain) throws IOException,
			ServletException {
		if (pRequest instanceof HttpServletRequest) {
			HttpServletRequest httpServletRequest = (HttpServletRequest) pRequest;
			MDC.put(SESSION_ID, httpServletRequest.getSession().getId());
			String profileId = pRequest.getParameter("userId");
			if (StringUtils.isNoneBlank(profileId)) {
				MDC.put(PROFILE_ID, profileId);
			} else {
				BaseProfile userProfile = (BaseProfile) httpServletRequest.getSession().getAttribute(MyAccountManager.CURRENT_SESSION_USER_KEY);
				if (userProfile != null && RepositoryUtils.idIsValid(userProfile.getId())) {
					MDC.put(PROFILE_ID, userProfile.getId());
				}
			}
		}
		pChain.doFilter(pRequest, pResponse);
	}

	@Override
	public void destroy() {
		MDC.remove(SESSION_ID);
		MDC.remove(PROFILE_ID);
	}
}
