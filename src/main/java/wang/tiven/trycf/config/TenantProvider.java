package wang.tiven.trycf.config;

import org.springframework.stereotype.Component;

@Component
public class TenantProvider {

	public String getTenantId() {
		// TODO get tenant identifier from security context
		return "my_elephantsql";
	}
}
