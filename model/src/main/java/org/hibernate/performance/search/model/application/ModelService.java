package org.hibernate.performance.search.model.application;

import java.util.List;
import java.util.Properties;

import org.hibernate.Session;

public interface ModelService {

	enum Kind {
		LUCENE_AUTOMATIC_INDEXING(true, false),
		LUCENE_MANUAL_INDEXING(true, true),
		ELASTICSEARCH_AUTOMATIC_INDEXING(false, false),
		ELASTICSEARCH_MANUAL_INDEXING(false, true);

		private boolean lucene;
		private boolean manual;

		Kind(boolean lucene, boolean manual) {
			this.lucene = lucene;
			this.manual = manual;
		}

		public boolean isLucene() {
			return lucene;
		}

		public boolean isManual() {
			return manual;
		}
	}

	Properties properties(Kind kind);

	<E> List<E> search(Session session, Class<E> entityClass);

	<E> List<E> search(Session session, Class<E> entityClass, String fieldName, Object value);

	long count(Session session, Class<?> entityClass, String fieldName, Object value);

	<E> List<E> range(Session session, Class<E> entityClass, String fieldName, Object start, Object end);

	<E> List<E> rangeOrderBy(Session session, Class<E> entityClass, String fieldName, Object start, Object end);

	List<Object> projectId(Session session, Class<?> entityClass, String fieldName, Object value);

	void massIndexing(Session session) throws InterruptedException;

}
