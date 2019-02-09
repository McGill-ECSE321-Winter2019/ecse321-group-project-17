package ca.mcgill.ecse321.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Administrator extends Profile{
   private Integer id;

public void setId(Integer value) {
    this.id = value;
}
@Id
public Integer getId() {
    return this.id;
}
}
