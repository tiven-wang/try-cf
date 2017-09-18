package wang.tiven.trycf.config;

import org.springframework.stereotype.Component;

/**
 * Tenant Provider to be design
 * 
 * @author <a href="mailto:i.tiven.wang@gmail.com">Tiven Wang</a>
 * 
 */
@Component
public class TenantProvider {

	public String getTenantId() {
		// TODO get tenant identifier from security context
		return "my_elephantsql";
	}
}