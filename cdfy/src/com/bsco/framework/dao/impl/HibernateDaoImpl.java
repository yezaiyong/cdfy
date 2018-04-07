package com.bsco.framework.dao.impl;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.jdbc.Work;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.bsco.framework.bean.Page;
import com.bsco.framework.bean.Page.OrderType;
import com.bsco.framework.dao.HibernateDao;

/**
 * 公共dao实现类 * 
 * 
 * @author jack.li
 */
@Repository
@Scope(value = "prototype")
public class HibernateDaoImpl extends HibernateDaoSupport implements HibernateDao {

	private String pojoName;
	private Class pojoClass;
	
	@Autowired
	public void setParentSessionFactory(SessionFactory sessionFactory) {
		this.setSessionFactory(sessionFactory);
	}

	public void setPojoClass(Class clazz) {
		this.pojoName = clazz.getCanonicalName();
		this.pojoClass = clazz;
	}
	
	public void flush() {
		currentSession().flush();
	}
	
	public void evict(Object obj) {
		currentSession().evict(obj);
	}
	
	public Serializable save(Object pojo) {
		return currentSession().save(pojo);
	}
	
	public void saveOrUpdateAll(Collection pojos) {
		int i = 0;
		for(Object obj : pojos) {
			currentSession().saveOrUpdate(obj);
			// 每50提交
			if (i++ % 50 == 0) {
				currentSession().flush();
				currentSession().clear();
			}
		}
		currentSession().flush();
		currentSession().clear();
	}
	
	public void saveAll(Collection pojos) {
		int i = 0;
		for(Object obj : pojos) {
			currentSession().save(obj);
			// 每50提交
			if (i++ % 50 == 0) {
				currentSession().flush();
				currentSession().clear();
			}
		}
		currentSession().flush();
		currentSession().clear();
	}

	public void saveOrUpdate(Object pojo) {
		currentSession().saveOrUpdate(pojo);
	}

	public void delete(Serializable id) {
		delete(get(id));
	}

	public void delete(Object pojo) {
		currentSession().delete(pojo);
	}

	public void deleteAll(Collection pojos) {
		int i = 0;
		for(Object obj : pojos) {
			currentSession().delete(obj);
			// 每50提交
			if (i++ % 50 == 0) {
				currentSession().flush();
				currentSession().clear();
			}
		}
	}

	public void update(Object pojo) {
		currentSession().update(pojo);
	}

	public int bulkUpdate(String hql) {
		return createQuery(hql).executeUpdate();
	}

	public int bulkUpdate(String hql, Object... args) {
		Query query = createQuery(hql);
		for (int i = 0; i < args.length; i++) {
			query.setParameter(i, args[i]);
		}
		return query.executeUpdate();
	}

	public Object get(Serializable id) {
		return currentSession().get(pojoClass, id);
	}
	
	public Object load(Serializable id) {
		return currentSession().load(pojoClass, id);
	}
	
	public Object get(@SuppressWarnings("rawtypes") Class clazz, Serializable id) {
		return currentSession().get(clazz, id);
	}
	
	public Object get(String clazz, Serializable id) {
		return currentSession().get(clazz, id);
	}

	public List getAll() {
		return find("from " + pojoName);
	}

	public List getBy(String fieldName, Object obj) {
		String hql = "from " + pojoName + " obj where obj." + fieldName + "=?";
		return find(hql, obj);
	}

	public List getByIds(Serializable[] ids) {
		String hql = "from " + pojoName + " obj where obj." + getSessionFactory().getClassMetadata(
				pojoClass).getIdentifierPropertyName() + " in (:ids)";
		Query query = currentSession().createQuery(hql);
		query.setParameterList("ids", ids);
		return query.list();
	}

	public List find(Map<String, Object> map) {
		String hql = "from " + pojoName + " obj where 1=1";
		Object[] objs = new Object[map.size()];
		int i = 0;
		for (String key : map.keySet()) {
			hql = hql + " and obj." + key + "=?";
			objs[i++] = map.get(key);
		}
		return find(hql, objs);
	}

	public List find(DetachedCriteria detachedCriteria) {
		Criteria criteria = detachedCriteria.getExecutableCriteria(currentSession());
		criteria.setCacheable(true);
		return criteria.list();
	}

	public List find(DetachedCriteria detachedCriteria, int maxRow) {
		Criteria criteria = detachedCriteria.getExecutableCriteria(currentSession());
		criteria.setFirstResult(0);
		criteria.setMaxResults(maxRow);
		criteria.setCacheable(true);
		return criteria.list();
	}
	
	public List findByRange(DetachedCriteria detachedCriteria, int page, int size) {
		int first = (page-1) * size;
		Criteria criteria = detachedCriteria.getExecutableCriteria(currentSession());
		criteria.setFirstResult(first);
		criteria.setMaxResults(size);
		criteria.setCacheable(true);
		return criteria.list();
	}
	
	public List findByRange(String hql, int page, int size, Object... args) {
		Query query = currentSession().createQuery(hql);
		for (int i = 0; i < args.length; i++) {
			query.setParameter(i, args[i]);
		}
		query.setCacheable(true);
		query.setFirstResult((page - 1) * size);
		query.setMaxResults(size);
		return query.list();
	}

	public List find(String hql, Object... args) {
		Query query = currentSession().createQuery(hql);
		for (int i = 0; i < args.length; i++) {
			query.setParameter(i, args[i]);
		}
		query.setCacheable(true);
		return query.list();
	}

	public Object getObject(DetachedCriteria detachedCriteria) {
		Criteria criteria = detachedCriteria
				.getExecutableCriteria(currentSession());
		criteria.setCacheable(true);
		return criteria.uniqueResult();
	}

	public Object getObject(String hql, Object... args) {
		Query query = currentSession().createQuery(hql);
		for (int i = 0; i < args.length; i++) {
			query.setParameter(i, args[i]);
		}
		query.setCacheable(true);
		return query.uniqueResult();
	}
	
	public Object getObjectBy(String fieldName, Object obj) {
		String hql = "from " + pojoName + " obj where obj." + fieldName + "=?";
		Query query = currentSession().createQuery(hql);
		query.setParameter(0, obj);
		return query.uniqueResult();
	}
	
	public Object getObjectBy(Map<String, Object> map) {
		String hql = "from " + pojoName + " obj where 1=1";
		Object[] objs = new Object[map.size()];
		int i = 0;
		for (String key : map.keySet()) {
			hql = hql + " and obj." + key + "=?";
			objs[i++] = map.get(key);
		}
		return getObject(hql, objs);
	}

	public Long getCount(String hql, Object... args) {
		String[] sections = hql.split("\\s");
		String first = sections[0].toUpperCase();
		hql = "SELECT COUNT(*) " + ("FROM".equals(first) ? hql : ("FROM ("+hql+")"));
		Query query = currentSession().createQuery(hql);
		for (int i = 0; i < args.length; i++) {
			query.setParameter(i, args[i]);
		}
		query.setCacheable(true);
		return ((Number) query.uniqueResult()).longValue();
	}

	public Long getCount(Map<String, Object> map) {
		String hql = "FROM " + pojoName + " obj where 1=1";
		Object[] objs = new Object[map.size()];
		int i = 0;
		for (String key : map.keySet()) {
			hql = hql + "  and obj." + key + "=?";
			objs[i++] = map.get(key);
		}
		return getCount(hql, objs);
	}

	public Long getCount(DetachedCriteria detachedCriteria) {
		Criteria criteria = detachedCriteria
				.getExecutableCriteria(currentSession());
		criteria.setCacheable(true);
		int totalCount = (Integer) criteria.setProjection(
				Projections.rowCount()).uniqueResult();
		return new Long(totalCount);
	}

	public Long getCountBy(String fieldName, Object obj) {
		String hql = "from " + pojoName + " obj where obj." + fieldName + "=?";
		return getCount(hql, obj);
	}

	public Long getTotalCount() {
		String hql = "from " + pojoName + " obj";
		return getCount(hql);
	}

	public Page pagedQuery(Page page) {
		String hql = "FROM " + pojoName + " obj";
		return (Page) getPage(hql, page);
	}

	public Page pagedQuery(String fieldName, Object obj, Page page) {
		String hql = "FROM " + pojoName + " obj WHERE obj." + fieldName + "=?";
		return (Page) getPage(hql, page, obj);
	}

	public Page pagedQuery(Map<String, Object> map, Page page) {
		String hql = "FROM " + pojoName + " obj WHERE 1=1";
		Object[] objs = new Object[map.size()];
		int i = 0;
		for (String key : map.keySet()) {
			Object obj = map.get(key);
			hql = hql + "  and obj." + key + "=?";
			objs[i++] = obj;
		}
		return (Page) getPage(hql, page, objs);
	}

	public Page pagedQuery(String hql, Page page, Object... objs) {
		return (Page) getPage(hql, page, objs);
	}
	
	public Page pageQueryBySQL(String sql, Page page) {
		int pageNo = page.getPageNo() < 1 ? 1 : page.getPageNo();
		StringBuffer countSQL = new StringBuffer("SELECT COUNT(*) FROM (");
		countSQL.append(sql);
		countSQL.append(") a");
		Long total = ((Number)currentSession().createSQLQuery(countSQL.toString()).uniqueResult()).longValue();
		long totalPage = total % page.getPageSize() == 0 ? total/page.getPageSize() : (total/page.getPageSize()+1);
		totalPage = totalPage < 1 ? 1 : totalPage;
		if(total > 0) {
			Query query = currentSession().createSQLQuery(sql);
			query.setFirstResult((pageNo-1)*page.getPageSize());
			query.setMaxResults(page.getPageSize());
			query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List<Map<String, Object>> result = query.list();
			page.setList(result);
		}
		page.setTotalCount(total);
		return page;
	}
	
	public Page pageQueryBySQL(String sql, Page page, Object... args) {
		int pageNo = page.getPageNo() < 1 ? 1 : page.getPageNo();
		StringBuffer countSQL = new StringBuffer("SELECT COUNT(*) FROM (");
		countSQL.append(sql);
		countSQL.append(") a");
		Query countQuery = currentSession().createSQLQuery(countSQL.toString());
		for (int i = 0; i < args.length; i++) {
			countQuery.setParameter(i, args[i]);
		}
		Long total = ((Number)countQuery.uniqueResult()).longValue();
		long totalPage = total % page.getPageSize() == 0 ? total/page.getPageSize() : (total/page.getPageSize()+1);
		totalPage = totalPage < 1 ? 1 : totalPage;
		if(total > 0) {
			Query query = currentSession().createSQLQuery(sql);
			query.setFirstResult((pageNo-1)*page.getPageSize());
			query.setMaxResults(page.getPageSize());
			query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			for (int i = 0; i < args.length; i++) {
				query.setParameter(i, args[i]);
			}
			List<Map<String, Object>> result = query.list();
			page.setList(result);
		}
		page.setTotalCount(total);
		return page;
	}

	@SuppressWarnings("unchecked")
	private Page getPage(String hql, Page page, Object... objs) {
		int pageNo = page.getPageNo();
		int pageSize = page.getPageSize();
		int totalCount = getCount(hql).intValue();
		int pageCount = totalCount % pageSize == 0 ? totalCount / pageSize
				: totalCount / pageSize + 1;
		Query query = currentSession().createQuery(hql);
		for (int i = 0; i < objs.length; i++) {
			query.setParameter(i, objs[i]);
		}
		query.setFirstResult((pageNo - 1) * pageSize);
		query.setMaxResults(pageSize);
		// query.setCacheable(true);
		List<Object> pojoTypes = query.list();
		page.setList(pojoTypes);
		page.setTotalCount(totalCount);
		return page;
	}

	public Page pagedQuery(Page pager, DetachedCriteria detachedCriteria) {
		if (pager == null) {
			pager = new Page();
		}
		int pageNumber = pager.getPageNo();
		int pageSize = pager.getPageSize();
		String orderBy = pager.getOrderBy();
		OrderType orderType = pager.getOrderType();

		Criteria criteria = detachedCriteria
				.getExecutableCriteria(currentSession());
		// criteria.setCacheable(true);
		int totalCount = (Integer) criteria.setProjection(
				Projections.rowCount()).uniqueResult();
		int pageCount = totalCount % pageSize == 0 ? totalCount / pageSize
				: totalCount / pageSize + 1;
		criteria.setProjection(null);
		criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		criteria.setFirstResult((pageNumber - 1) * pageSize);
		criteria.setMaxResults(pageSize);
		if (StringUtils.isNotEmpty(orderBy) && orderType != null) {
			String propertyString = "";
			if (orderBy.contains(".")) {
				String propertyPrefix = StringUtils.substringBefore(orderBy,
						".");
				String propertySuffix = StringUtils
						.substringAfter(orderBy, ".");
				criteria.createAlias(propertyPrefix, "model");
				propertyString = "model." + propertySuffix;
			} else {
				propertyString = orderBy;
			}
			if (orderType == OrderType.asc) {
				criteria.addOrder(Order.asc(propertyString));
			} else {
				criteria.addOrder(Order.desc(propertyString));
			}
		}
//		ClassMetadata metadata = getSessionFactory()
//				.getClassMetadata(pojoClass);
//		String identifierPropertyName = metadata.getIdentifierPropertyName();
//		criteria.addOrder(Order.desc(identifierPropertyName));// 加入主键排序，避免hibernate排序分页错乱
		// criteria.setCacheable(true);
		pager.setTotalCount(totalCount);
		pager.setList(criteria.list());
		return pager;
	}

	public SQLQuery createSQLQuery(String sql) {
		return currentSession().createSQLQuery(sql);
	}

	public Query createQuery(String hql) {
		return currentSession().createQuery(hql);
	}
    
	public void executeProcedure(final String procedure) throws SQLException {
		currentSession().doWork(new Work() {
			public void execute(Connection connection) throws SQLException {
				CallableStatement cstmt = connection.prepareCall(procedure);
				cstmt.execute();
			}
		}) ;
	}
	
	public Session currentSession() {
		return getSessionFactory().getCurrentSession();
	}
	
	public SessionFactory getHibernateSessionFactory() {
		return getSessionFactory();
	}
	
	public List findBySql(String sql, Object... objs) {
		Query query = createSQLQuery(sql);
		for (int i = 0; i < objs.length; i++) {
			query.setParameter(i, objs[i]);
		}
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}
	
	public Map findObjectBySql(String sql, Object... objs) {
		Query query = createSQLQuery(sql);
		for (int i = 0; i < objs.length; i++) {
			query.setParameter(i, objs[i]);
		}
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		return (Map)query.uniqueResult();
	}

	public int executeUpdate(String sql, Object... objs) {
		Query query = createSQLQuery(sql);
		for (int i = 0; i < objs.length; i++) {
			query.setParameter(i, objs[i]);
		}
		return query.executeUpdate();
	}
}
