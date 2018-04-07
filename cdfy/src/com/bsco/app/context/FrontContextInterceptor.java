package com.bsco.app.context;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.resource.DefaultServletHttpRequestHandler;

import com.bsco.app.model.Users;
import com.bsco.app.parameter.Constants;
import com.bsco.app.service.SystemInfoService;
import com.bsco.app.service.UserService;
import com.bsco.framework.util.AuthCodeUtils;
import com.bsco.framework.util.CookieUtils;
import com.bsco.framework.util.RequestUtils;
import com.bsco.app.annotation.Authority;

/**
 * 
 * 包括登录信息
 * 
 */
public class FrontContextInterceptor extends HandlerInterceptorAdapter {

	private static final String USERID_IN_COOKIE = "USERID_IN_COOKIE";
	private static final String USER_IN_REQUEST = "USER";
	
	private static final Logger logger = LoggerFactory
			.getLogger(FrontContextInterceptor.class);

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

	private void noright(HttpServletRequest request,
			HttpServletResponse response) {
		String requestType = request.getHeader("X-Requested-With");
		if ("XMLHttpRequest".equals(requestType)) {
			
		} else {
			response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
			String newLocn = request.getContextPath() + "/noright.html";
			response.setHeader("Location", newLocn);
		}
	}

	public String getWork(Date dt) {
		String[] weekDays = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;
		return weekDays[w];
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
		
		boolean checkRight = false;
		String[] roles = new String[] {};
		
		if (!(handler instanceof DefaultServletHttpRequestHandler)) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Object bean = handlerMethod.getBean();
			Method method = handlerMethod.getMethod();
			Authority authority = method.getAnnotation(Authority.class);
			if (authority == null) {
				authority = bean.getClass().getAnnotation(Authority.class);
			}
			if (authority != null) {
				
				checkRight = true;
				roles = authority.roles();
			}
		}
		
		Serializable userId = null;
		String authcode = request.getParameter(Constants.AUTHCODE);
		if (StringUtils.isNotEmpty(authcode)) {
			String[] enccode = AuthCodeUtils.decryptAuthCode(authcode);
			userId = Integer.valueOf(enccode[0]);
		} else {
			HttpSession session = request.getSession();
			userId = (Serializable)session.getAttribute(Constants.USERID_IN_SESSION);
		}
		Users member = null;
		// 判断登录是否有效
		if (checkLogin) {
			if(userId == null) {
				login(request, response);
				return false;
			}
			member = userService.findById(userId);
			if (member == null) {
				login(request, response);
				return false;
			}
		}
		if (member != null) {
			
			if ( checkRight) {
				for(int i=0;i<roles.length;i++){
					if(roles[i].equals(member.getRole())){
						
					}else{
						login(request, response);
						return false;
					}
				}
		}
			String enccode = AuthCodeUtils.encryptAuthCode(member.getId());
			request.setAttribute(Constants.AUTHCODE, enccode);
			request.setAttribute(Constants.USER_IN_REQUEST, member);
			request.setAttribute(Constants.SYSTEM_INFO, systemInfoService.getSystemInfoById(1));
		}
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
		if(checkLogin && request.getMethod().equals("GET")) {
			Map<String, String> map = RequestUtils.getQueryParams(request);
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
		String param = RequestUtils.getQueryString(request);
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Method method = handlerMethod.getMethod();
		// 登录验证的范围内
		boolean checkLogin = true;
		for (String url : excludeUrls) {
			if (uri.matches(url)) {
				checkLogin = false;
				break;
			}
		}
		if (ex != null) {
			// logger.error("", ex);
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
	@Autowired
	private UserService userService;
	
	@Autowired
	private SystemInfoService systemInfoService;
	
	private List<String> excludeUrls = new ArrayList<String>();
	public static String loginUrl;
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