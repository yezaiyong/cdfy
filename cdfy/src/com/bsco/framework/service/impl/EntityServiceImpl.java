/**
 * 
 */
package com.bsco.framework.service.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.Filter;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.bsco.framework.bean.Page;
import com.bsco.framework.dao.HibernateDao;
import com.bsco.framework.service.EntityService;

/**
 * @author jack.li
 * 
 */
@SuppressWarnings(value = { "rawtypes", "unchecked" })
@Transactional(rollbackFor = Exception.class)
public class EntityServiceImpl<PojoType> implements EntityService<PojoType>,
		InitializingBean {

	@Autowired(required = false)
	private HibernateDao hibernateEntityDao;

	public void afterPropertiesSet() throws Exception {
		if (hibernateEntityDao != null) {
			Type type = getClass().getGenericSuperclass();
			if (type instanceof ParameterizedType) {
				ParameterizedType pt = (ParameterizedType) type;
				Type[] types = pt.getActualTypeArguments();
				Class<PojoType> clazz = (Class<PojoType>) types[0];
				hibernateEntityDao.setPojoClass(clazz);
			}
		}
	}

	public Filter enableFilter(String name) {
		return hibernateEntityDao.currentSession().enableFilter(name);
	}

	// -----------------------HIBERNATE---------------------------
	public void flush() {
		hibernateEntityDao.flush();
	}

	public void evict(PojoType obj) {
		hibernateEntityDao.evict(obj);
	}

	public Serializable save(PojoType pojo) {
		return hibernateEntityDao.save(pojo);
	}

	public Serializable saveObject(Object pojo) {
		return hibernateEntityDao.save(pojo);
	}

	public void saveOrUpdateAll(Collection<PojoType> pojos) {
		hibernateEntityDao.saveOrUpdateAll(pojos);
	}

	public void saveAll(Collection pojos) {
		hibernateEntityDao.saveAll(pojos);
	}

	public void saveOrUpdate(PojoType pojo) {
		hibernateEntityDao.saveOrUpdate(pojo);
	}

	public void delete(Serializable id) {
		hibernateEntityDao.delete(id);
	}

	public void delete(Serializable[] ids) {
		if (ids == null)
			return;
		for (int i = 0; i < ids.length; i++) {
			hibernateEntityDao.delete(ids[i]);
		}
	}

	public void delete(PojoType pojo) {
		hibernateEntityDao.delete(pojo);
	}

	public void deleteAll(Collection<PojoType> pojos) {
		hibernateEntityDao.deleteAll(pojos);
	}

	public void update(PojoType pojo) {
		hibernateEntityDao.update(pojo);
	}

	public int update(String hql, Object... obj) {
		return hibernateEntityDao.bulkUpdate(hql, obj);
	}

	public PojoType findById(Serializable id) {
		return (PojoType) hibernateEntityDao.get(id);
	}

	public Object findById(Class clazz, Serializable id) {
		return hibernateEntityDao.get(clazz, id);
	}

	public Object findById(String clazz, Serializable id) {
		return hibernateEntityDao.get(clazz, id);
	}

	public Object queryForObject(DetachedCriteria detachedCriteria) {
		return hibernateEntityDao.getObject(detachedCriteria);
	}

	public Object queryForObject(String hql, Object... args) {
		return hibernateEntityDao.getObject(hql, args);
	}

	public List<PojoType> getAll() {
		return hibernateEntityDao.getAll();
	}

	public PojoType queryForObjectByField(String fieldName, Object obj) {
		return (PojoType) hibernateEntityDao.getObjectBy(fieldName, obj);
	}

	public PojoType queryForObjectByFields(Map<String, Object> map) {
		return (PojoType) hibernateEntityDao.getObjectBy(map);
	}

	public List<PojoType> getByField(String fieldName, Object obj) {
		return hibernateEntityDao.getBy(fieldName, obj);
	}

	public List<PojoType> getByIds(Serializable[] ids) {
		return hibernateEntityDao.getByIds(ids);
	}

	public List find(Map<String, Object> map) {
		return hibernateEntityDao.find(map);
	}

	public List find(DetachedCriteria detachedCriteria) {
		return hibernateEntityDao.find(detachedCriteria);
	}

	public List find(DetachedCriteria detachedCriteria, int maxRow) {
		return hibernateEntityDao.find(detachedCriteria, maxRow);
	}

	public List find(String hql, Object... objs) {
		return hibernateEntityDao.find(hql, objs);
	}

	public List findByRange(DetachedCriteria detachedCriteria, int page,
			int size) {
		return hibernateEntityDao.findByRange(detachedCriteria, page, size);
	}

	public List findByRange(String hql, int page, int size, Object... args) {
		return hibernateEntityDao.findByRange(hql, page, size, args);
	}

	public Page pagedQuery(Page page) {
		return hibernateEntityDao.pagedQuery(page);
	}

	public Page pagedQuery(String fieldName, Object obj, Page page) {
		return hibernateEntityDao.pagedQuery(fieldName, obj, page);
	}

	public Page pagedQuery(Map<String, Object> map, Page page) {
		return hibernateEntityDao.pagedQuery(map, page);
	}

	public Page pagedQuery(String hql, Page page, Object... objs) {
		return hibernateEntityDao.pagedQuery(hql, page, objs);
	}

	public Page pagedQuery(Page pager, DetachedCriteria detachedCriteria) {
		return hibernateEntityDao.pagedQuery(pager, detachedCriteria);
	}

	public Long getCountBy(String fieldName, Object obj) {
		return hibernateEntityDao.getCountBy(fieldName, obj);
	}

	public Long getTotalCount() {
		return hibernateEntityDao.getTotalCount();
	}

	public Long getCount(String hql, Object... objs) {
		return hibernateEntityDao.getCount(hql, objs);
	}

	public Long getCount(DetachedCriteria detachedCriteria) {
		return hibernateEntityDao.getCount(detachedCriteria);
	}

	public Long getCount(String hql) {
		return hibernateEntityDao.getCount(hql);
	}

	public Long getCount(Map<String, Object> map) {
		return hibernateEntityDao.getCount(map);
	}

	public SQLQuery createSQLQuery(String sql) {
		return hibernateEntityDao.createSQLQuery(sql);
	}

	public Query createQuery(String hql) {
		return hibernateEntityDao.createQuery(hql);
	}

	public void executeProcedure(String procedure) throws SQLException {
		hibernateEntityDao.executeProcedure(procedure);
	}

	public HibernateDao getEntityDao() {
		return hibernateEntityDao;
	}

	public int updateBySql(String sql, Object... args) {
		return hibernateEntityDao.executeUpdate(sql, args);
	}

	public List<?> queryForListBySql(String sql, Object... params) {
		Query query = hibernateEntityDao.createSQLQuery(sql);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		for (int i = 0; i < params.length; i++) {
			query.setParameter(i, params[i]);
		}
		return query.list();
	}

	public List<?> queryForListBySql(String sql, Class<?> cls, Object... params) {
		Query query = hibernateEntityDao.createSQLQuery(sql);
		query.setResultTransformer(Transformers.aliasToBean(cls));
		for (int i = 0; i < params.length; i++) {
			query.setParameter(i, params[i]);
		}
		return query.list();
	}

	public List<?> queryForListBySql(String sql, Class<?> cls, int pageNo,
			int pageSize, Object... params) {
		Query query = hibernateEntityDao.createSQLQuery(sql);
		query.setResultTransformer(Transformers.aliasToBean(cls));
		for (int i = 0; i < params.length; i++) {
			query.setParameter(i, params[i]);
		}
		int start = (pageNo - 1) * pageSize;
		query.setFirstResult(start);
		query.setMaxResults(pageSize);
		return query.list();
	}

	public List<?> queryForListBySql(String sql, int pageNo, int pageSize,
			Object... params) {
		Query query = hibernateEntityDao.createSQLQuery(sql);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		for (int i = 0; i < params.length; i++) {
			query.setParameter(i, params[i]);
		}
		int start = (pageNo - 1) * pageSize;
		query.setFirstResult(start);
		query.setMaxResults(pageSize);
		return query.list();
	}

	public Page queryForListBySql(String sql, Class<?> cls, Page page,
			Object... params) {
		String countSql = "SELECT COUNT(*) CNT FROM (" + sql + ") A";
		Number count = (Number) queryForObjectBySql(countSql, params);
		page.setList(queryForListBySql(sql, cls, page.getPageNo(),
				page.getPageSize(), params));
		page.setTotalCount(count.intValue());
		return page;
	}

	public Page queryForListBySql(String sql, Page page, Object... params) {
		String countSql = "SELECT COUNT(*) CNT FROM (" + sql + ") A";
		Number count = (Number) queryForObjectBySql(countSql, params);
		page.setList(queryForListBySql(sql, page.getPageNo(),
				page.getPageSize(), params));
		page.setTotalCount(count.intValue());
		return page;
	}

	public Object queryForObjectBySql(String sql, Class<?> cls,
			Object... params) {
		Query query = hibernateEntityDao.createSQLQuery(sql);
		query.setResultTransformer(Transformers.aliasToBean(cls));
		for (int i = 0; i < params.length; i++) {
			query.setParameter(i, params[i]);
		}
		return query.list();
	}

	public Object queryForObjectBySql(String sql, Object... params) {
		Query query = hibernateEntityDao.createSQLQuery(sql);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		for (int i = 0; i < params.length; i++) {
			query.setParameter(i, params[i]);
		}
		System.out.println(query.uniqueResult());
		return query.uniqueResult();
	}

}
