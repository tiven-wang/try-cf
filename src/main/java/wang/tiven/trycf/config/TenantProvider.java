package wang.tiven.trycf.config;

import org.springframework.stereotype.Component;

@Component("tenantProvider")
public class TenantProvider {

  public String getTenantId() {
    return "test";
  }
}
