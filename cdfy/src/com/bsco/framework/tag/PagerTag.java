/**
 * 
 */
package com.bsco.framework.tag;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import com.bsco.framework.bean.Page;
import com.bsco.framework.dao.HibernateDao;

/**
 * @author jack.li
 * 
 */
public class PagerTag extends BodyTagSupport {

	private static final Logger logger = LoggerFactory.getLogger(PagerTag.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = -3007463448279167711L;
	private String sql;
	private String hql;
	private String countHql;
	private String size = "0";
	private String page = "1";
	private String id;

	public int doStartTag() throws JspException {
		Page pager = new Page();
		List list = new ArrayList();
		try {
			ApplicationContext appCtx = ContextLoader.getCurrentWebApplicationContext();
			int totalCount = 0;
			int tmppage = 0;
			try {
				tmppage = Integer.parseInt(page) < 1 ? 1 : Integer.parseInt(page);
			}catch (Exception e) {
				tmppage = 1;
			}
			int tmpsize = 15;
			try {
				tmpsize = Integer.parseInt(size);
			}catch (Exception e) {
				tmpsize = 15;
			}
			HibernateDao hibernateDao = (HibernateDao) appCtx.getBean(HibernateDao.class);
			if (StringUtils.isNotEmpty(hql)) {
				if(StringUtils.isNotEmpty(countHql)) {
					totalCount = ((Number)hibernateDao.getObject(countHql)).intValue();
				} else {
					totalCount = hibernateDao.getCount(hql).intValue();
				}
				if(totalCount > 0) {
					if(tmpsize == 0) {
						list = hibernateDao.find(hql);
					} else {
						list = hibernateDao.findByRange(hql, tmppage, tmpsize);
					}
				}
			} else if (StringUtils.isNotEmpty(sql)) {
//				JdbcDao jdbcDao = (JdbcDao) appCtx.getBean("jdbcDao");
				totalCount = ((Number)hibernateDao.createSQLQuery("SELECT COUNT(*) FROM (" + sql + ") t").uniqueResult()).intValue();
				/*
				JdbcDao jdbcDao = (JdbcDao) appCtx.getBean("jdbcDao");
				totalCount = ((Number)jdbcDao.queryForObject("SELECT COUNT(*) FROM (" + sql + ") t", new RowMapper<Number>() {
					public Number mapRow(ResultSet arg0, int arg1)
							throws SQLException {
						return arg0.getLong(1);
					}
				})).intValue();
				*/
				Query query = hibernateDao.createSQLQuery(sql).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
				query.setFirstResult((tmppage - 1) * tmpsize);
				query.setMaxResults(tmpsize);
				if(totalCount > 0) {
					list = query.list();
				}
			}
			pager.setPageNo(tmppage);
			pager.setPageSize(tmpsize);
			pager.setTotalCount(totalCount);
		} catch (Exception e) {
			logger.error("", e);
		}
		pager.setList(list);
		pageContext.setAttribute(id, pager);
		return SKIP_BODY;
	}

	// 此方法被绕过不会被执行	public void doInitBody() throws JspException {
		super.doInitBody();
	}

	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHql() {
		return hql;
	}

	public void setHql(String hql) {
		this.hql = hql;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

}
