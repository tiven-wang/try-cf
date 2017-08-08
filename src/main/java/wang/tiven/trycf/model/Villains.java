package wang.tiven.trycf.model;

import java.math.BigInteger;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

@Document(collection = "villains")
public class Villains {

	@Id
    @JsonSerialize(using = ToStringSerializer.class)
    private BigInteger id;
	
	private String name;
	
	@DBRef
	private Hero catchedBy;
	
	public Villains() {
		super();
	}
	
	public Villains(String name) {
		super();
		this.name = name;
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Hero getCatchedBy() {
		return catchedBy;
	}

	public void setCatchedBy(Hero catchedBy) {
		this.catchedBy = catchedBy;
	}
}
