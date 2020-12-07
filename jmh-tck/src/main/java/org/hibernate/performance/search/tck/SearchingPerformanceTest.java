package org.hibernate.performance.search.tck;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.performance.search.model.application.DomainDataFiller;
import org.hibernate.performance.search.model.application.HibernateORMHelper;
import org.hibernate.performance.search.model.application.ModelService;
import org.hibernate.performance.search.model.entity.BusinessUnit;
import org.hibernate.performance.search.model.entity.Company;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.infra.Blackhole;

@State(Scope.Benchmark)
public class SearchingPerformanceTest {

	private final ModelService modelService;
	private final SessionFactory sessionFactory;

	public SearchingPerformanceTest() {
		modelService = TckBackendHelperFactory.getModelService();
		sessionFactory = HibernateORMHelper.buildSessionFactory( TckBackendHelperFactory.manualProperties() );
	}

	@Setup(Level.Trial)
	public void setup() throws Exception {
		new DomainDataFiller( sessionFactory ).fillData( 0 );
		try ( Session session = ( sessionFactory.openSession() ) ) {
			modelService.massIndexing( session );
		}
	}

	@TearDown(Level.Trial)
	public void tearDown() {
		if ( sessionFactory != null ) {
			sessionFactory.close();
		}
	}

	@Benchmark
	public void company(Blackhole blackhole) {
		try ( Session session = ( sessionFactory.openSession() ) ) {
			// match
			List<Company> companies = modelService.search( session, Company.class, "legalName", "Company0" );
			blackhole.consume( companies );

			// no match
			companies = modelService.search( session, Company.class, "legalName", "CompanyX" );
			blackhole.consume( companies );

			// nested match
			companies = modelService.search( session, Company.class, "businessUnits.name", "Unit7" );
			blackhole.consume( companies );
		}
	}

	@Benchmark
	public void businessUnit(Blackhole blackhole) {
		try ( Session session = ( sessionFactory.openSession() ) ) {
			// match
			List<BusinessUnit> businessUnits = modelService.search( session, BusinessUnit.class, "name", "Unit7" );
			blackhole.consume( businessUnits );

			// no match
			businessUnits = modelService.search( session, BusinessUnit.class, "name", "UnitX" );
			blackhole.consume( businessUnits );

			// nested match
			businessUnits = modelService.search( session, BusinessUnit.class, "owner.legalName", "Company0" );
			blackhole.consume( businessUnits );
		}
	}
}
