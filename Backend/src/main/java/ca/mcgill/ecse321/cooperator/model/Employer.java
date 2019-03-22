package ca.mcgill.ecse321.cooperator.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;

import java.util.Set;
import javax.persistence.OneToMany;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;

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

@GeneratedValue()
public Integer getId() {
    return this.id;
}
private Set<Notification> employerReceived;

@OneToMany(mappedBy="employer", fetch=FetchType.EAGER)
public Set<Notification> getEmployerReceived() {
   return this.employerReceived;
}

public void setEmployerReceived(Set<Notification> employerReceiveds) {
   this.employerReceived = employerReceiveds;
}

private String company;

public void setCompany(String value) {
    this.company = value;
}
public String getCompany() {
    return this.company;
}
}
