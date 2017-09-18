package wang.tiven.trycf.config;

import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Cloud DataSource based multi-tenant connection provider implementation
 * 
 * @author <a href="mailto:i.tiven.wang@gmail.com">Tiven Wang</a>
 *
 */
public class CloudDataSourceMultiTenantConnectionProviderImpl extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl {
	private Log logger = LogFactory.getLog(CloudDataSourceMultiTenantConnectionProviderImpl.class);
	
	private static final long serialVersionUID = 6086628073272413281L;
	
	@Autowired
	private Map<String, DataSource> dataSources;
	
	@Override
	protected DataSource selectAnyDataSource() {
		return dataSources.values().iterator().next();
	}

	@Override
	protected DataSource selectDataSource(String tenantIdentifier) {
		return dataSources.get(tenantIdentifier);
	}

}
