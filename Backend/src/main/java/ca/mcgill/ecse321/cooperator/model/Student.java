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
   private Set<Notification> studentReceived;
   
   @OneToMany(mappedBy="student" )
   public Set<Notification> getStudentReceived() {
      return this.studentReceived;
   }
   
   public void setStudentReceived(Set<Notification> studentReceiveds) {
      this.studentReceived = studentReceiveds;
   }
   
   }
