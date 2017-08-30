package wang.tiven.microservices.police.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class PoliceOffice {
	private String officeName;

    @Id
    @GeneratedValue
    private Long id;

    private String href;

    private String description;
    
    private String label;

    PoliceOffice() {
    }

    public PoliceOffice(String officeName, String href,
                    String description, String label) {
        this.officeName = officeName;
        this.href = href;
        this.description = description;
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public String getOfficeName() {
		return officeName;
	}

	public Long getId() {
        return id;
    }

    public String getHref() {
        return href;
    }

    public String getDescription() {
        return description;
    }

    
}
