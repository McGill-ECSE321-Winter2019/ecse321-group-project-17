package ca.mcgill.ecse321.cooperator.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

@Entity
public class Administrator extends Profile {
	private Integer id;

	public void setId(Integer value) {
		this.id = value;
	}

	@GeneratedValue()
	public Integer getId() {
		return this.id;
	}

}
