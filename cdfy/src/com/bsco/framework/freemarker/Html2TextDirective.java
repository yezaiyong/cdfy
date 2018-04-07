/**
 * 
 */
package com.bsco.framework.freemarker;

import java.io.IOException;
import java.util.Map;

import com.bsco.framework.util.StrUtils;

import freemarker.core.Environment;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * @author jack.li
 * 
 */
public class Html2TextDirective implements TemplateDirectiveModel {

	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		SimpleScalar htmlParam = (SimpleScalar)params.get("html");
		if(htmlParam != null) {
			String html = htmlParam.getAsString();
			SimpleScalar scalar = (SimpleScalar)params.get("length");
			if(scalar != null) {
				int len = Integer.parseInt(scalar.getAsString());
				env.getOut().write(StrUtils.htmlCut(html, len, "..."));
			} else {
				env.getOut().write(StrUtils.html2Text(html));
			}
		}
	}

}
