/**
 * 
 */
package com.bsco.framework.tag;

import java.util.ArrayList;
import java.util.Iterator;
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

import com.bsco.framework.dao.HibernateDao;

/**
 * @author jack.li
 * 
 */
public class ListTag extends BodyTagSupport {

	private static final Logger logger = LoggerFactory.getLogger(ListTag.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = -3007463448279167711L;
	private String sql;
	private String hql;
	private int row;
	private String id;
	private String index = "index";
	private Iterator<?> iter;
	private int i = 0;

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}
	
	public int doStartTag() throws JspException {
		try {
			List list = new ArrayList();
			ApplicationContext appCtx = ContextLoader.getCurrentWebApplicationContext();
			HibernateDao hibernateDao = (HibernateDao) appCtx.getBean(HibernateDao.class);
			if (StringUtils.isNotEmpty(hql)) {
				if (row > 0) {
					list = hibernateDao.findByRange(hql, 1, row);
				} else {
					list = hibernateDao.find(hql);
				}
			} else if (StringUtils.isNotEmpty(sql)){
//				JdbcDao jdbcDao = (JdbcDao) appCtx.getBean("jdbcDao");
				Query query = hibernateDao.createSQLQuery(sql).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
				if(row > 0) {
					query.setFirstResult(0);
					query.setMaxResults(row);
				}
				list = query.list();
			}
			// 表示如果未找到指定集合，则不用处理标签体，直接调用doEndTag()方法。
			int size = list.size();
			iter = list.iterator();
			if(iter.hasNext()) {
				pageContext.setAttribute(id, iter.next());
			}
			pageContext.setAttribute(id + "_" + index, i++);
			pageContext.setAttribute(id + "_size", size);
			if (list.isEmpty())
				return SKIP_BODY;
			// 表示在现有的输出流对象中处理标签体，但绕过setBodyContent()和doInitBody()方法
			// 这里一定要返回EVAL_BODY_INCLUDE，否则标签体的内容不会在网页上输出显示			return EVAL_BODY_INCLUDE;
		} catch (Exception e) {
			logger.error("", e);
		}
		return SKIP_BODY;
	}

	// 此方法被绕过不会被执行	public void doInitBody() throws JspException {
		super.doInitBody();
	}

	public int doAfterBody() throws JspException {
		if (iter.hasNext()) {
			pageContext.setAttribute(id, iter.next());
			pageContext.setAttribute(id + "_" + index, i++);
			return EVAL_BODY_AGAIN;// 如果集合中还有对像，则循环执行标签体
		}
		return SKIP_BODY;// 迭代完集合后，跳过标签体，调用doEndTag()方法。	}

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

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

}
