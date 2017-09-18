package wang.tiven.trycf.config;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Tenant identifier resolver implementation
 * 
 * @author <a href="mailto:i.tiven.wang@gmail.com">Tiven Wang</a>
 *
 */
public class TenantIdentifierResolverImpl implements CurrentTenantIdentifierResolver {

	@Autowired
	private TenantProvider tenantProvider;
	
	@Override
	public String resolveCurrentTenantIdentifier() {
		return tenantProvider.getTenantId();
	}

	@Override
	public boolean validateExistingCurrentSessions() {
		return false;
	}

}
