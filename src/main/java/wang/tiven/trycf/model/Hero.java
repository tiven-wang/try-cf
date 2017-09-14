package wang.tiven.trycf.model;

import java.math.BigInteger;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

@Document(collection = "#{tenantProvider.getTenantId()}_heros")
public class Hero {
	
	@Id
    @JsonSerialize(using = ToStringSerializer.class)
    private BigInteger id;

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
