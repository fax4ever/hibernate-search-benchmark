package org.hibernate.performance.search.model.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.performance.search.model.entity.BusinessUnit;
import org.hibernate.performance.search.model.entity.Company;

public final class CompanyFactory {

	public static final int UNIT_PER_COMPANY = 10;

	private CompanyFactory() {
	}

	public static Company createCompanyAndUnits(int companyId) {
		Company company = new Company( companyId, "Company" + companyId );
		company.setDescription( "This is a real description for the company " + companyId );
		List<BusinessUnit> units = new ArrayList<>( UNIT_PER_COMPANY );

		for ( int i = 0; i < UNIT_PER_COMPANY; i++ ) {
			int buId = UNIT_PER_COMPANY * companyId + i;
			units.add( new BusinessUnit( buId, "Unit" + buId, company ) );
		}
		company.setBusinessUnits( units );
		return company;
	}

}
