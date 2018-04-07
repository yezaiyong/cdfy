/**
 * 
 */
package com.bsco.framework.tag;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang.StringUtils;
import org.hibernate.jdbc.Work;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import com.bsco.framework.dao.HibernateDao;
import com.bsco.framework.util.SaveExcelUtil;

/**
 * @author jack.li
 * 
 */
public class ExportTag extends BodyTagSupport {

	private static final Logger logger = LoggerFactory.getLogger(ExportTag.class);
	private static final long serialVersionUID = -3007463448279167711L;
	/**
	 * 
	 */
	private String sql;
	private String hql;
	private int rowPerPage=500;
	private String sheetName;
	private String columns;
	private String fileName;

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public String getHql() {
		return hql;
	}

	public void setHql(String hql) {
		this.hql = hql;
	}

	public int getRowPerPage() {
		return rowPerPage;
	}

	public void setRowPerPage(int rowPerPage) {
		this.rowPerPage = rowPerPage;
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public String getColumns() {
		return columns;
	}

	public void setColumns(String columns) {
		this.columns = columns;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int doStartTag() throws JspException {
		try {
			HttpServletResponse response = (HttpServletResponse)pageContext.getResponse();
			ApplicationContext appCtx = ContextLoader.getCurrentWebApplicationContext();
			HibernateDao hibernateDao = (HibernateDao) appCtx.getBean("hibernateDao");
			response.reset();
			response.setContentType("application/vnd.ms-excel");
			if(StringUtils.isEmpty(fileName)) {
				fileName = String.valueOf(System.currentTimeMillis());
			} else {
				fileName = URLEncoder.encode(fileName,"UTF-8");
			}
			response.setHeader("Content-disposition", "attachment; filename="+fileName+".xls");
			final OutputStream out = response.getOutputStream();
			if(StringUtils.isNotEmpty(sql)) {
				hibernateDao.currentSession().doWork(new Work() {
					public void execute(Connection connection) throws SQLException {
						SaveExcelUtil.export(connection, sql, sheetName, StringUtils.split(columns, ","), rowPerPage, out);
					}
				});
				
			} else {
				SaveExcelUtil.export(hibernateDao.currentSession(), hql, sheetName, StringUtils.split(columns, ","), rowPerPage, out);
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		return SKIP_BODY;
	}

	public int doEndTag() throws JspException {
		return SKIP_PAGE;
	}

}
