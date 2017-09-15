package wang.tiven.trycf.config;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class MultiTenantRoutingDataSource extends AbstractRoutingDataSource {
	
	public MultiTenantRoutingDataSource(Map<String, DataSource> dataSources) {
		this.setTargetDataSources((Map) dataSources);
	}
	
	@Override
	protected Object determineCurrentLookupKey() {
		return "my_elephantsql";
	}

}
