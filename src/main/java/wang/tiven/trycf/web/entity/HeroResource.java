package wang.tiven.trycf.web.entity;

import org.springframework.hateoas.ResourceSupport;

public class HeroResource extends ResourceSupport {
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
