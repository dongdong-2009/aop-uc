/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
/*     */package org.springframework.security.web.authentication.logout;

/*     */
/*     */import java.io.IOException;
/*     */
import java.util.Arrays;
/*     */
import java.util.List;
/*     */
import javax.servlet.FilterChain;
/*     */
import javax.servlet.ServletException;
/*     */
import javax.servlet.ServletRequest;
/*     */
import javax.servlet.ServletResponse;
/*     */
import javax.servlet.http.HttpServletRequest;
/*     */
import javax.servlet.http.HttpServletResponse;
/*     */
import org.apache.commons.logging.Log;
/*     */
import org.springframework.security.core.Authentication;
/*     */
import org.springframework.security.core.context.SecurityContext;
/*     */
import org.springframework.security.core.context.SecurityContextHolder;
/*     */
import org.springframework.security.web.util.UrlUtils;
/*     */
import org.springframework.util.Assert;
/*     */
import org.springframework.util.StringUtils;
/*     */
import org.springframework.web.filter.GenericFilterBean;

/*     */
/*     */public class LogoutFilter extends GenericFilterBean
/*     */{
	/*  52 */private String filterProcessesUrl = "/j_spring_security_logout";
	/*     */private List<LogoutHandler> handlers;
	/*     */private LogoutSuccessHandler logoutSuccessHandler;

	/*     */
	/*     */public LogoutFilter(LogoutSuccessHandler logoutSuccessHandler,
			LogoutHandler[] handlers)
	/*     */{
		/*  64 */Assert.notEmpty(handlers, "LogoutHandlers are required");
		/*  65 */this.handlers = Arrays.asList(handlers);
		/*  66 */Assert.notNull(logoutSuccessHandler,
				"logoutSuccessHandler cannot be null");
		/*  67 */this.logoutSuccessHandler = logoutSuccessHandler;
		/*     */}

	/*     */
	/*     */public LogoutFilter(String logoutSuccessUrl,
			LogoutHandler[] handlers) {
		/*  71 */Assert.notEmpty(handlers, "LogoutHandlers are required");
		/*  72 */this.handlers = Arrays.asList(handlers);
		/*  73 */Assert.isTrue((!(StringUtils.hasLength(logoutSuccessUrl)))
				|| (UrlUtils.isValidRedirectUrl(logoutSuccessUrl)),
				logoutSuccessUrl + " isn't a valid redirect URL");
		/*     */
		/*  75 */SimpleUrlLogoutSuccessHandler urlLogoutSuccessHandler = new SimpleUrlLogoutSuccessHandler();
		/*  76 */if (StringUtils.hasText(logoutSuccessUrl)) {
			/*  77 */urlLogoutSuccessHandler
					.setDefaultTargetUrl(logoutSuccessUrl);
			/*     */}
		/*  79 */this.logoutSuccessHandler = urlLogoutSuccessHandler;
		/*     */}

	/*     */
	/*     */public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain)
	/*     */throws IOException, ServletException
	/*     */{
		/*  86 */HttpServletRequest request = (HttpServletRequest) req;
		/*  87 */HttpServletResponse response = (HttpServletResponse) res;
		/*     */
		/*  89 */if (requiresLogout(request, response)) {
			/*  90 */Authentication auth = SecurityContextHolder.getContext()
					.getAuthentication();
			/*     */
			/*  92 */if (this.logger.isDebugEnabled()) {
				/*  93 */this.logger.debug("Logging out user '" + auth
						+ "' and transferring to logout destination");
				/*     */}
			/*     */
			/*  96 */for (LogoutHandler handler : this.handlers) {
				/*  97 */handler.logout(request, response, auth);
				/*     */}
			/*     */
			/* 100 */this.logoutSuccessHandler.onLogoutSuccess(request,
					response, auth);
			/*     */
			/* 102 */return;
			/*     */}
		/*     */
		/* 105 */chain.doFilter(request, response);
		/*     */}

	/*     */
	/*     */protected boolean requiresLogout(HttpServletRequest request,
			HttpServletResponse response)
	/*     */{
		/* 117 */String uri = request.getRequestURI();
		/* 118 */int pathParamIndex = uri.indexOf(59);
		/*     */
		/* 120 */if (pathParamIndex > 0)
		/*     */{
			/* 122 */uri = uri.substring(0, pathParamIndex);
			/*     */}
		/*     */
		/* 125 */int queryParamIndex = uri.indexOf(63);
		/*     */
		/* 127 */if (queryParamIndex > 0)
		/*     */{
			/* 129 */uri = uri.substring(0, queryParamIndex);
			/*     */}
		/*     */
		/* 132 */if ("".equals(request.getContextPath())) {
			/* 133 */return uri.endsWith(this.filterProcessesUrl);
			/*     */}
		/*     */
		/* 136 */return uri.endsWith(request.getContextPath()
				+ this.filterProcessesUrl);
		/*     */}

	/*     */
	/*     */public void setFilterProcessesUrl(String filterProcessesUrl) {
		/* 140 */Assert.isTrue(
				UrlUtils.isValidRedirectUrl(filterProcessesUrl),
				filterProcessesUrl + " isn't a valid value for"
						+ " 'filterProcessesUrl'");
		/*     */
		/* 142 */this.filterProcessesUrl = filterProcessesUrl;
		/*     */}

	/*     */
	/*     */protected String getFilterProcessesUrl() {
		/* 146 */return this.filterProcessesUrl;
		/*     */}
	/*     */
}