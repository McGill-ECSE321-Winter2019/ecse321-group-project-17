package ca.mcgill.ecse321.cooperator.model;

import javax.persistence.Entity;
import java.util.Set;
import javax.persistence.OneToMany;

@Entity
public class Employer extends Profile{
   private Set<Coop> coop;
   
   @OneToMany(mappedBy="employer" )
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
   private Set<Notification> received;
   
   @OneToMany(mappedBy="employer" )
   public Set<Notification> getReceived() {
      return this.received;
   }
   
   public void setReceived(Set<Notification> receiveds) {
      this.received = receiveds;
   }
   
   }
