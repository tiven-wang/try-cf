package wang.tiven.trycf.mongo;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.PersistenceExceptionTranslator;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoExceptionTranslator;

import com.mongodb.DB;

import wang.tiven.trycf.config.TenantProvider;

/**
 * Multi-Tenant MongoDbFactory implementation
 * 
 * @author Tiven Wang
 *
 */
public class MultiTenantMongoDbFactory implements MongoDbFactory {
	private Log logger = LogFactory.getLog(MultiTenantMongoDbFactory.class);
	
	private final HashMap<String, MongoDbFactory> mongoDbFactories = new HashMap<String, MongoDbFactory>();
	private final PersistenceExceptionTranslator exceptionTranslator;
	
	private TenantProvider tenantProvider;
	
	public MultiTenantMongoDbFactory(List<MongoDbFactory> mongoDbFactories, TenantProvider tenantProvider) {
		for(MongoDbFactory mongoDbFactory : mongoDbFactories) {
			logger.debug("Put the mongoDbFactory: " + mongoDbFactory.getDb().getName());
			this.addMongoDbFactory(mongoDbFactory.getDb().getName(), mongoDbFactory);
		}
		this.tenantProvider = tenantProvider;
		this.exceptionTranslator = new MongoExceptionTranslator();
	}

	@Override
	public DB getDb() throws DataAccessException {
		return mongoDbFactories.get(tenantProvider.getTenantId()).getDb();
	}

	@Override
	public DB getDb(String dbName) throws DataAccessException {
		return mongoDbFactories.get(tenantProvider.getTenantId()).getDb(dbName);
	}

	@Override
	public PersistenceExceptionTranslator getExceptionTranslator() {
		return this.exceptionTranslator;
	}

	/**
	 * Add a MongoDbFactory for a tenant
	 * 
	 * @param tenant
	 * @param mongoDbFactory
	 */
	public void addMongoDbFactory(String tenant, MongoDbFactory mongoDbFactory) {
		this.mongoDbFactories.put(tenant, mongoDbFactory);
	}
}
