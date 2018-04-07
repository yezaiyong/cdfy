package com.bsco.framework.dao;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;

import com.bsco.framework.bean.Page;

/**
 * 公共的dao接口
 * 
 * @author jack.li
 */
public interface HibernateDao {
	
	public void setPojoClass(Class pojoClass);
	
	public void flush();
	
	public void evict(Object obj);
	
	public Serializable save(Object pojo);

	public void saveAll(Collection pojos);
	
	public void saveOrUpdateAll(Collection pojos);

	public void saveOrUpdate(Object pojo);

	public void delete(Serializable id);

	public void delete(Object pojo);

	public void deleteAll(Collection pojos);

	public void update(Object pojo);

	public Object get(Serializable id);
	
	public Object load(Serializable id);
	
	public Object get(Class clazz, Serializable id);
	
	public Object get(String clazz, Serializable id);

	public List getAll();

	public List getBy(String fieldName, Object obj);
	
	public List getByIds(Serializable[] ids);
	
	public Object getObject(DetachedCriteria detachedCriteria);
	
	public Object getObject(String hql, Object...args);
	
	public Object getObjectBy(String fieldName, Object obj);
	
	public Object getObjectBy(Map<String, Object> map);

	public List find(Map<String, Object> map);

	public List find(String hql, Object... args);
	
	public List find(DetachedCriteria detachedCriteria);
	
	public List find(DetachedCriteria detachedCriteria, int maxRow);
	
	public List findByRange(String hql, int page, int size, Object... args);
	
	public List findByRange(DetachedCriteria detachedCriteria, int page, int size);
	
	public List findBySql(String sql, Object... args);
	
	public Map findObjectBySql(String sql, Object... objs);
	
	public Page pagedQuery(Page page);
	
	public Page pagedQuery(String fieldName, Object obj, Page page);
	
	public Page pagedQuery(Map<String, Object> map, Page page);
	
	public Page pagedQuery(String hql, Page page, Object... objs);
	
	public Page pagedQuery(Page pager, DetachedCriteria detachedCriteria);
	
	public Page pageQueryBySQL(String sql, Page page);
	
	public Page pageQueryBySQL(String sql, Page page, Object... args);

	public SQLQuery createSQLQuery(String sql);
	
	public Query createQuery(String hql);
	
	public int bulkUpdate(String hql);
	
	public int bulkUpdate(String hql, Object... args);
	
	public Long getTotalCount();
	
	public Long getCountBy(String fieldName, Object obj);

	public Long getCount(String hql, Object... args);
	
	public Long getCount(DetachedCriteria detachedCriteria);
	
	public Long getCount(Map<String, Object> map);
	
	public void executeProcedure(String procedure) throws SQLException;
	
	public Session currentSession();
	
	public SessionFactory getHibernateSessionFactory();
	
	public int executeUpdate(String sql, Object... args);
}