package wang.tiven.trycf.config;

import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.jta.JtaTransactionManager;

@Configuration
@EnableJpaRepositories(basePackages = "wang.tiven.trycf.repository")
@EntityScan(basePackages = "wang.tiven.trycf.model")
public class MultitenantHibernateJpaAutoConfiguration extends HibernateJpaAutoConfiguration {
	private Log logger = LogFactory.getLog(MultitenantHibernateJpaAutoConfiguration.class);

	public MultitenantHibernateJpaAutoConfiguration(DataSource[] dataSource, JpaProperties jpaProperties,
			ObjectProvider<JtaTransactionManager> jtaTransactionManager,
			ObjectProvider<TransactionManagerCustomizers> transactionManagerCustomizers) {
		super(dataSource[0], jpaProperties, jtaTransactionManager, transactionManagerCustomizers);
	}
	
	@Override
	protected void customizeVendorProperties(Map<String, Object> vendorProperties) {
		super.customizeVendorProperties(vendorProperties);
		vendorProperties.put("hibernate.multiTenancy", "DATABASE");
		vendorProperties.put("hibernate.multi_tenant_connection_provider", multitenantConnectionProvider());
		vendorProperties.put("hibernate.tenant_identifier_resolver", multitenantIdentifierResolver());
	}

	@Bean
	public CurrentTenantIdentifierResolver multitenantIdentifierResolver() {
		return new TenantIdentifierResolverImpl();
	}

	@Bean
	public MultiTenantConnectionProvider multitenantConnectionProvider() {
		return new CloudDataSourceMultiTenantConnectionProviderImpl();
	}

}
