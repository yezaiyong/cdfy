/**
 * 
 */
package com.bsco.framework.exception.resolver;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class DefaultExceptionResolver implements HandlerExceptionResolver {

	private static final Logger logger = LoggerFactory.getLogger(DefaultExceptionResolver.class);
	
	private String view;
	private String messageProperty;
	
	public String getView() {
		return view;
	}

	public void setView(String view) {
		this.view = view;
	}

	public String getMessageProperty() {
		return messageProperty;
	}

	public void setMessageProperty(String messageProperty) {
		this.messageProperty = messageProperty;
	}

	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object obj, Exception exception) {
		try {
			response.sendError(404);
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.error(exception.getMessage(), exception);
		/*
		ModelAndView modelAndView = new ModelAndView(view);
		//logger.error(exception.getMessage());
		modelAndView.addObject(messageProperty, exception.getMessage());
		*/
		return null;
	}

}
