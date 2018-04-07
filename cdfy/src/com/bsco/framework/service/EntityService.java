package com.bsco.framework.service;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.Filter;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.DetachedCriteria;

import com.bsco.framework.bean.Page;
import com.bsco.framework.dao.HibernateDao;

public interface EntityService<PojoType> {

	public Filter enableFilter(String name);
	public void flush();
	public void evict(PojoType obj);
	public Serializable save(PojoType pojo);
	public Serializable saveObject(Object pojo);
	public void saveOrUpdateAll(Collection<PojoType> pojos);
	public void saveAll(Collection pojos);
	public void saveOrUpdate(PojoType pojo);
	public void delete(Serializable id);
	public void delete(Serializable[] ids);
	public void delete(PojoType pojo);
	public void deleteAll(Collection<PojoType> pojos);
	public void update(PojoType pojo);
	public int update(String hql, Object... obj);
	public PojoType findById(Serializable id);
	public Object findById(Class clazz, Serializable id);
	public Object findById(String clazz, Serializable id);
	public Object queryForObject(DetachedCriteria detachedCriteria);
	public Object queryForObject(String hql, Object...args);
	public PojoType queryForObjectByField(String fieldName, Object obj);
	public PojoType queryForObjectByFields(Map<String, Object> map);
	public List<PojoType> getAll();
	public List<PojoType> getByField(String fieldName, Object obj);
	public List<PojoType> getByIds(Serializable[] ids);
	public List find(Map<String, Object> map);
	public List find(DetachedCriteria detachedCriteria);
	public List find(DetachedCriteria detachedCriteria, int maxRow);
	public List find(String hql, Object... objs);
	public List findByRange(String hql, int page, int size, Object... args);
	public List findByRange(DetachedCriteria detachedCriteria, int page, int size);
	public Page pagedQuery(Page page);
	public Page pagedQuery(String fieldName, Object obj, Page page);
	public Page pagedQuery(Map<String, Object> map, Page page);
	public Page pagedQuery(String hql, Page page, Object... objs);
	public Page pagedQuery(Page pager, DetachedCriteria detachedCriteria);
	public Long getTotalCount();
	public Long getCountBy(String fieldName, Object obj);
	public Long getCount(String hql);
	public Long getCount(String hql, Object... objs);
	public Long getCount(Map<String, Object> map);
	public Long getCount(DetachedCriteria detachedCriteria);
	public SQLQuery createSQLQuery(String sql);
	public Query createQuery(String hql);
	public void executeProcedure(String procedure) throws SQLException;
	public HibernateDao getEntityDao();
	
	public Object queryForObjectBySql(String sql, Class<?> cls, Object... params);
	public Object queryForObjectBySql(String sql, Object... params);
	public List<?> queryForListBySql(String sql, Object... params);
	public List<?> queryForListBySql(String sql, Class<?> cls, Object... params);
	public List<?> queryForListBySql(String sql, Class<?> cls, int pageNo, int pageSize, Object... params);
	public List<?> queryForListBySql(String sql, int pageNo, int pageSize, Object... params);
	public Page queryForListBySql(String sql, Class<?> cls, Page page, Object... params);
	public Page queryForListBySql(String sql, Page page, Object... params);
	public int updateBySql(String sql, Object... args);
}
