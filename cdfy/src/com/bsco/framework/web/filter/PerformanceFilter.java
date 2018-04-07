package com.bsco.framework.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import com.bsco.framework.util.FilterConfigUtils;
import com.bsco.framework.util.RequestUtils;

/**
 * 性能日志filter。
 * web.xml配置文件格式如下：
 * <pre>
 * <![CDATA[
 *  <filter>
 *  <filter-name>PerformanceFilter</filter-name>
 *  <filter-class>com.daqsoft.framework.filter.PerformanceFilter</filter-class>
 *  <init-param>
 *  <param-name>threshold</param-name>
 *  <param-value>3000</param-value>
 *  </init-param>
 *  </filter>
 * </pre>
 * 其中<code>threshold</code>参数表明超时阈值，如果处理的总时间超过该值，则filter会以warning的方式记录该次操作。
 */
public class PerformanceFilter extends OncePerRequestFilter implements Filter {
	private int threshold = 3000;
	private boolean includeQueryString = false;
	private static final Logger log = LoggerFactory
			.getLogger(PerformanceFilter.class);

	public void destroy() {
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		StatusExposingServletResponse responseWrapper = new StatusExposingServletResponse(
				(HttpServletResponse) response);
		responseWrapper.setHeader("Expires","0");
		responseWrapper.setHeader("Cache-Control","no-store");
		responseWrapper.setHeader("Pragrma","no-cache");
		responseWrapper.setDateHeader("Expires",0);
		Throwable failed = null;
		long start = System.currentTimeMillis();
		CommonRequestWrapper requestWrapper = new CommonRequestWrapper(request);
		try {
			chain.doFilter(requestWrapper, responseWrapper);
		} catch (Throwable e) {
			failed = e;
			e.printStackTrace();
		} finally {
			String requestString = dumpRequest(requestWrapper);
			long duration = System.currentTimeMillis() - start;
			if (failed != null) {
				log.error("[" + requestString + ",F," + duration + "ms,"
						+ responseWrapper.getStatus() + "]");
			} else if (duration > threshold) {
				log.warn("[" + requestString + ",Y," + duration + "ms,"
						+ responseWrapper.getStatus() + "]");
			} else {
				log.info("[" + requestString + ",Y," + duration + "ms,"
						+ responseWrapper.getStatus() + "]");
			}
		}
	}

	@SuppressWarnings("unused")
	private static void rethrowThrowable(Throwable failed) throws Error,
			IOException, ServletException, Exception {
		if (failed != null) {
			if (failed instanceof Error) {
				throw (Error) failed;
			} else if (failed instanceof RuntimeException) {
				throw (RuntimeException) failed;
			} else if (failed instanceof IOException) {
				throw (IOException) failed;
			} else if (failed instanceof ServletException) {
				throw (ServletException) failed;
			} else if (failed instanceof Exception) {
				throw (Exception) failed;
			}
		}
	}

	protected void initFilterBean() throws ServletException {
		this.threshold = FilterConfigUtils.getIntParameter(getFilterConfig(),
				"threshold", threshold);
		this.includeQueryString = FilterConfigUtils.getBooleanParameter(
				getFilterConfig(), "includeQueryString", includeQueryString);
		log.info("PerformanceFilter started with threshold:" + threshold
				+ "ms includeQueryString:" + includeQueryString);
	}

	/**
	 * 取得request的内容(HTTP方法, URI)
	 * 
	 * @param request
	 *            HTTP请求
	 * 
	 * @return 字符串
	 */
	protected String dumpRequest(ServletRequest request) {
		HttpServletRequest req = (HttpServletRequest) request;
		StringBuffer buffer = new StringBuffer(RequestUtils.getIpAddr(req));
		buffer.append(" ").append(req.getMethod()).append(" ").append(req.getScheme()+"://")
				.append(req.getHeader("host")+req.getRequestURI());
		if (includeQueryString) {
			String queryString = RequestUtils.getQueryString(req);
			if (StringUtils.isNotBlank(queryString)) {
				buffer.append("?").append(queryString);
			}
		}
		return buffer.toString();
	}

}
