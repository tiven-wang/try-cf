package wang.tiven.trycf.config;

import java.net.UnknownHostException;
import java.util.List;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.cloud.app.ApplicationInstanceInfo;
import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.cloud.config.java.ServiceScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.PersistenceExceptionTranslator;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.CustomConversions;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.mongodb.DB;

import wang.tiven.trycf.mongo.MultiTenantMongoDbFactory;


@Configuration
@ServiceScan
@Profile("cloud")
public class CloudConfiguration extends AbstractCloudConfig {

	private final MongoProperties properties;
	
	public CloudConfiguration(MongoProperties properties) {
		this.properties = properties;
	}
	
	@Bean
    public ApplicationInstanceInfo applicationInfo() {
        return cloud().getApplicationInstanceInfo();
    }
	
	@Bean
	public MultiTenantMongoDbFactory multiTenantMongoDbFactory(List<MongoDbFactory> mongoDbFactories, TenantProvider tenantProvider) {
		return new MultiTenantMongoDbFactory(mongoDbFactories, tenantProvider);
	}
	
	@Bean
	@ConditionalOnMissingBean
	public MongoTemplate mongoTemplate(MultiTenantMongoDbFactory multiTenantMongoDbFactory,
			MongoConverter converter) throws UnknownHostException {
		return new MongoTemplate(multiTenantMongoDbFactory, converter);
	}

	@Bean
	@ConditionalOnMissingBean(MongoConverter.class)
	public MappingMongoConverter mappingMongoConverter(List<MongoDbFactory> factories,
			MongoMappingContext context, BeanFactory beanFactory,
			CustomConversions conversions) {
		DbRefResolver dbRefResolver = new DefaultDbRefResolver(factories.get(0));
		MappingMongoConverter mappingConverter = new MappingMongoConverter(dbRefResolver,
				context);
		mappingConverter.setCustomConversions(conversions);
		return mappingConverter;
	}
	
	@Bean
	@ConditionalOnMissingBean
	public GridFsTemplate gridFsTemplate(MultiTenantMongoDbFactory multiTenantMongoDbFactory,
			MongoTemplate mongoTemplate) {
		return new GridFsTemplate(
				new GridFsMongoDbFactory(multiTenantMongoDbFactory, this.properties),
				mongoTemplate.getConverter());
	}
	
	/**
	 * {@link MongoDbFactory} decorator to respect
	 * {@link MongoProperties#getGridFsDatabase()} if set.
	 */
	private static class GridFsMongoDbFactory implements MongoDbFactory {

		private final MongoDbFactory mongoDbFactory;

		private final MongoProperties properties;

		GridFsMongoDbFactory(MongoDbFactory mongoDbFactory, MongoProperties properties) {
			Assert.notNull(mongoDbFactory, "MongoDbFactory must not be null");
			Assert.notNull(properties, "Properties must not be null");
			this.mongoDbFactory = mongoDbFactory;
			this.properties = properties;
		}

		@Override
		public DB getDb() throws DataAccessException {
			// TODO how to config separate gridfs database for every tenant
			String gridFsDatabase = this.properties.getGridFsDatabase();
			if (StringUtils.hasText(gridFsDatabase)) {
				return this.mongoDbFactory.getDb(gridFsDatabase);
			}
			return this.mongoDbFactory.getDb();
		}

		@Override
		public DB getDb(String dbName) throws DataAccessException {
			return this.mongoDbFactory.getDb(dbName);
		}

		@Override
		public PersistenceExceptionTranslator getExceptionTranslator() {
			return this.mongoDbFactory.getExceptionTranslator();
		}

	}
}
