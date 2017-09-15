package wang.tiven.trycf.config;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;

public class TenantIdentifierResolverImpl implements CurrentTenantIdentifierResolver {

	@Override
	public String resolveCurrentTenantIdentifier() {
		return "tenant1";
	}

	@Override
	public boolean validateExistingCurrentSessions() {
		// TODO Auto-generated method stub
		return false;
	}

}
