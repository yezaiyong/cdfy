package com.bsco.app.context;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.bsco.framework.util.CookieUtils;
import com.bsco.framework.util.RequestUtils;

/**
 * 
 * 包括登录信息
 * 
 */
public class AdminContextInterceptor extends HandlerInterceptorAdapter {

	private static final String USERID_IN_COOKIE = "USERID_IN_COOKIE";
	private static final String USER_IN_REQUEST = "USER";
	
	private static final Logger logger = LoggerFactory
			.getLogger(AdminContextInterceptor.class);
	
	private void login(HttpServletRequest request, HttpServletResponse response) {
		CookieUtils.cancelCookie(request, response, USERID_IN_COOKIE);
		String requestType = request.getHeader("X-Requested-With");
		if ("XMLHttpRequest".equals(requestType)) {
			
		} else {
			response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
			String newLocn = request.getContextPath() + loginUrl;
			response.setHeader("Location", newLocn);
		}
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// 可以进行编码、安全控制等处理；
		String uri = RequestUtils.getURI(request);
		// 登录验证的范围内
		boolean checkLogin = true;
		for (String url : excludeUrls) {
			if (uri.matches(url)) {
				checkLogin = false;
				break;
			}
		}
		/*
		Users member = null;
		String authcode = request.getParameter(Constants.AUTHCODE);
		if (StringUtils.isNotEmpty(authcode)) {
			try {
				String[] enccode = AuthCodeUtils.decryptAuthCode(authcode);
				String userId = enccode[0];
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("userId", userId);
				map.put("status", Status.y);
				member = userService.queryForObjectByFields(map);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			authcode = CookieUtils.getValue(request, USERID_IN_COOKIE);
			if(StringUtils.isNotEmpty(authcode)) {
				String[] strs = AuthCodeUtils.decryptAuthCode(authcode);
				String userId = strs[0];
				if (StringUtils.isNotEmpty(userId)) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("userId", userId);
					map.put("status", Status.y);
					member = userService.queryForObjectByFields(map);
				}
			}
		}
		
		// 判断登录是否有效
		if (checkLogin) {
			if (member == null) {
				logger.info(uri+":未登录");
				login(request, response);
				return false;
			}
		}
		if (member != null) {
			String enccode = AuthCodeUtils.encryptAuthCode(member.getUserId());
			CookieUtils.addCookie(request, response, USERID_IN_COOKIE, enccode, null);
			request.setAttribute(Constants.AUTHCODE, enccode);
			request.setAttribute(USER_IN_REQUEST, member);
			//设置数据环境
//			WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
//			SessionFactory sessionFactory = (SessionFactory)context.getBean("sessionFactory");
//			if(request.getParameter("validdateOfProduct") != null && request.getParameter("validdateOfProduct").equals("1") ) {
//				sessionFactory.getCurrentSession().enableFilter("validdate").setParameter("currentdate", sdf.format(new Date()));
//				sessionFactory.getCurrentSession().enableFilter("stateFilter");
//				sessionFactory.getCurrentSession().enableFilter("isAuditFilter");
//			}

		} * */
//		request.setAttribute(Constants.SYS_CONFIG_KEY, Sysconfig.getAll());
		request.setAttribute("contextPath", request.getContextPath());
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler, ModelAndView mav)
			throws Exception {
		// 有机会修改ModelAndView；
		// 操作成功，重新跳转
		String uri = RequestUtils.getURI(request);
		// 登录验证的范围内
		boolean checkLogin = true;
		for (String url : excludeUrls) {
			if (uri.matches(url)) {
				checkLogin = false;
				break;
			}
		}
		if(checkLogin) {
			Map<String, String> map = RequestUtils.getRequestMap(request);
			if(!map.isEmpty() && mav != null) {
				ModelMap model = mav.getModelMap();
				model.addAllAttributes(map);
			}
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		String uri = RequestUtils.getURI(request);
		if (ex != null) {
			logger.error("", ex);
			try {
				String requestType = request.getHeader("X-Requested-With");
				if ("XMLHttpRequest".equals(requestType)) {
					response.setCharacterEncoding("UTF-8");
					response.setContentType("text/json");
					response.setDateHeader("Expires", 0);
					// response.getWriter().write("{\"error\": \"对不起，服务器异常，请稍后再试。\"}");
					response.getWriter().write(
							"{\"error\": \"" + ex.getMessage() + "\"}");
				} else {
					// response.sendRedirect("");
					response.getWriter().write(ex.getMessage());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private List<String> excludeUrls = new ArrayList<String>();
	private String loginUrl;
	private int sessionTimeout = 10;

	public void setExcludeUrls(List<String> excludeUrls) {
		this.excludeUrls = excludeUrls;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public void setSessionTimeout(int sessionTimeout) {
		this.sessionTimeout = sessionTimeout;
	}

}