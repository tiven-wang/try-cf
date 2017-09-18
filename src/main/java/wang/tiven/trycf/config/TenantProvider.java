package wang.tiven.trycf.config;

import org.springframework.stereotype.Component;

@Component
public class TenantProvider {

	public String getTenantId() {
		return "pg-wechat-service";
	}
}
