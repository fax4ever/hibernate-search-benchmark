package org.hibernate.search.benchmark.lucene;

import java.util.Properties;

import org.hibernate.search.benchmark.model.application.BackendType;
import org.hibernate.search.benchmark.model.application.IndexingType;
import org.hibernate.search.benchmark.model.application.ModelService;
import org.hibernate.search.benchmark.tck.MassIndexingPerformanceTest;

public class LuceneMassIndexingPerformanceTest extends MassIndexingPerformanceTest {

	@Override
	protected Properties manualProperties(ModelService modelService) {
		return modelService.properties( BackendType.LUCENE, IndexingType.MANUAL );
	}
}
