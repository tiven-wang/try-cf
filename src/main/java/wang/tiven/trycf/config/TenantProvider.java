package wang.tiven.trycf.config;

import org.springframework.stereotype.Component;

@Component("tenantProvider")
public class TenantProvider {

  public String getTenantId() {
    return "CloudFoundry_gm6kda63_5u8rmufu";
  }
}
