package com.bsco.framework.hibernate;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.exception.JDBCExceptionHelper;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.id.PersistentIdentifierGenerator;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RedisIDGenerator implements IdentifierGenerator, Configurable {

	private static final Logger log = LoggerFactory
			.getLogger(RedisIDGenerator.class);
	private String table;
	private String sql;
	private static final String sequenceName = "HIBERNATE_SEQUENCE";

	public Serializable generate(SessionImplementor session, Object arg1)
			throws HibernateException {
		// return RedisUtil.genIdentify(table.getBytes()).toString();
		try {
			PreparedStatement st = session.getBatcher().prepareSelectStatement(
					sql);
			try {
				ResultSet rs = st.executeQuery();
				try {
					rs.next();
					Serializable result = (Serializable) rs.getObject(1);
					log.debug("Sequence identifier generated: " + result);
					return result.toString();
				} finally {
					rs.close();
				}
			} finally {
				session.getBatcher().closeStatement(st);
			}
		} catch (SQLException sqle) {
			throw JDBCExceptionHelper.convert(session.getFactory()
					.getSQLExceptionConverter(), sqle,
					"could not get next sequence value", sql);
		}
	}

	public void configure(Type type, Properties params, Dialect dialect)
			throws MappingException {
		this.table = params.getProperty(PersistentIdentifierGenerator.TABLE);
		this.sql = dialect.getSequenceNextValString(sequenceName);
	}

}
