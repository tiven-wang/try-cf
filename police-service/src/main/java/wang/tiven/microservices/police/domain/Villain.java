package wang.tiven.microservices.police.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class Villain {
	private String name;

	@Id
    @GeneratedValue
    private Long id;

	@JsonIgnore
	@ManyToOne
	private PoliceOffice policeOffice;

	private boolean catched;
	
	public Villain() {
	}
	
	public Villain(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PoliceOffice getPoliceOffice() {
		return policeOffice;
	}

	public void setPoliceOffice(PoliceOffice policeOffice) {
		this.policeOffice = policeOffice;
	}

	public boolean isCatched() {
		return catched;
	}

	public void setCatched(boolean catched) {
		this.catched = catched;
	}
}
