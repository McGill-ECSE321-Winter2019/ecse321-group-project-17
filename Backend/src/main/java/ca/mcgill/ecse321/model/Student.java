package ca.mcgill.ecse321.model;

import javax.persistence.Entity;
import java.util.Set;
import javax.persistence.OneToMany;
import javax.persistence.Id;

@Entity
public class Student extends Profile{
   private Set<Coop> coop;
   
   @OneToMany(mappedBy="student" )
   public Set<Coop> getCoop() {
      return this.coop;
   }
   
   public void setCoop(Set<Coop> coops) {
      this.coop = coops;
   }
   
   private Integer id;

public void setId(Integer value) {
    this.id = value;
}
@Id
public Integer getId() {
    return this.id;
}
private Integer status;

public void setStatus(Integer value) {
    this.status = value;
}
public Integer getStatus() {
    return this.status;
}
}
