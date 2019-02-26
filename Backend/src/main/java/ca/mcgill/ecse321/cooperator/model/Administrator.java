package ca.mcgill.ecse321.cooperator.model;

import javax.persistence.Entity;

@Entity
public class Administrator extends Profile{
   private Integer id;ty
public void setId(Integer value) {
      this.id = value;
}

public Integer getId() {
      return this.id;
}
}
