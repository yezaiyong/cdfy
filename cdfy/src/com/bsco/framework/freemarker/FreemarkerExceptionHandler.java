/**
 * 
 */
package com.bsco.framework.freemarker;

import java.io.IOException;
import java.io.Writer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import freemarker.core.Environment;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

/**
 * @author jack.li
 * 
 */
public class FreemarkerExceptionHandler implements TemplateExceptionHandler {

	private static final Log log = LogFactory
			.getLog(FreemarkerExceptionHandler.class);

	public void handleTemplateException(TemplateException te, Environment env,
			Writer out) throws TemplateException {
		try {
			out.write("[Error: " + te.getMessage() + "]");
			//log.warn("[Freemarker Error: " + te.getMessage() + "]");
			log.warn(te);
		} catch (IOException e) {
			log.warn(e.getMessage());
			throw new TemplateException(
					"Failed to print error message. Cause: " + e, env);
		}
	}

}
