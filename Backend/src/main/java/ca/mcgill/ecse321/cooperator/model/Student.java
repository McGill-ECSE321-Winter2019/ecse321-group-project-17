package ca.mcgill.ecse321.cooperator.model;

import javax.persistence.Entity;
import java.util.Set;
import javax.persistence.OneToMany;

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
public Integer getId() {
    return this.id;
}
private Integer coopsCompleted;

public void setCoopsCompleted(Integer value) {
    this.coopsCompleted = value;
}
public Integer getCoopsCompleted() {
    return this.coopsCompleted;
}
private Boolean problematic;

public void setProblematic(Boolean value) {
    this.problematic = value;
}
public Boolean getProblematic() {
    return this.problematic;
}
   private Set<Notification> received;
   
   @OneToMany(mappedBy="student" )
   public Set<Notification> getReceived() {
      return this.received;
   }
   
   public void setReceived(Set<Notification> receiveds) {
      this.received = receiveds;
   }
   
   }
