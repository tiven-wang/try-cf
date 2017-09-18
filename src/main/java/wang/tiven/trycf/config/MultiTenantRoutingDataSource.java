package wang.tiven.trycf.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class MultiTenantRoutingDataSource extends AbstractRoutingDataSource {
	
	@Autowired
	private TenantProvider tenantProvider;
	
	@Override
	protected Object determineCurrentLookupKey() {
		return tenantProvider.getTenantId();
	}

}
