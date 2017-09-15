package wang.tiven.trycf.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.jta.JtaTransactionManager;

@Configuration
@EnableJpaRepositories(basePackages = "wang.tiven.trycf.repository")
@EntityScan(basePackages = "wang.tiven.trycf.model")
public class MultitenantHibernateJpaAutoConfiguration extends HibernateJpaAutoConfiguration {
	private Log logger = LogFactory.getLog(MultitenantHibernateJpaAutoConfiguration.class);

	public MultitenantHibernateJpaAutoConfiguration(MultiTenantRoutingDataSource dataSource, JpaProperties jpaProperties,
			ObjectProvider<JtaTransactionManager> jtaTransactionManager,
			ObjectProvider<TransactionManagerCustomizers> transactionManagerCustomizers) {
		super(dataSource, jpaProperties, jtaTransactionManager, transactionManagerCustomizers);
	}
	
}
