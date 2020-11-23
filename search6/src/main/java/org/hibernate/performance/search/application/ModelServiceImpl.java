package org.hibernate.performance.search.application;

import java.util.Properties;

import org.hibernate.search.mapper.orm.cfg.HibernateOrmMapperSettings;
import org.hibernate.search.mapper.orm.schema.management.SchemaManagementStrategyName;

public class ModelServiceImpl implements ModelService {

	@Override
	public Properties properties() {
		Properties config = new Properties();
		config.put( HibernateOrmMapperSettings.SCHEMA_MANAGEMENT_STRATEGY,
				SchemaManagementStrategyName.DROP_AND_CREATE_AND_DROP );
		config.put( HibernateOrmMapperSettings.MAPPING_CONFIGURER, new SearchProgrammaticMapping() );
		return config;
	}

	@Override
	public void start() {

	}

	@Override
	public void indexing() {

	}

	@Override
	public void search() {

	}

	@Override
	public void stop() {

	}
}
