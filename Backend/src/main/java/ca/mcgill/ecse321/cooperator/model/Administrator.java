package ca.mcgill.ecse321.cooperator.model;

import javax.persistence.Entity;
import java.util.Set;
import javax.persistence.OneToMany;

@Entity
public class Administrator extends Profile{
   private Integer id;

public void setId(Integer value) {
    this.id = value;
}
public Integer getId() {
    return this.id;
}
   private Set<Notification> sent;
   
   @OneToMany(mappedBy="sender" )
   public Set<Notification> getSent() {
      return this.sent;
   }
   
   public void setSent(Set<Notification> sents) {
      this.sent = sents;
   }
   
   }
