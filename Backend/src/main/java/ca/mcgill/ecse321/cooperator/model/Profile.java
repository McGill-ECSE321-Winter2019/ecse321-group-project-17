package ca.mcgill.ecse321.cooperator.model;

import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.persistence.Entity;
import java.util.Set;
import javax.persistence.OneToMany;
import javax.persistence.Id;

@Entity
public abstract class Profile{
   /*private Set<Notification> received;
   
   @OneToMany
   public Set<Notification> getReceived() {
      return this.received;
   }
   
   public void setReceived(Set<Notification> receiveds) {
      this.received = receiveds;
   }
   
   private Set<Notification> sent;
   
   @OneToMany(mappedBy="sender" )
   public Set<Notification> getSent() {
      return this.sent;
   }
   
   public void setSent(Set<Notification> sents) {
      this.sent = sents;
   }
   */
   private String email;

public void setEmail(String value) {
    this.email = value;
}
@Id
public String getEmail() {
    return this.email;
}
private String name;

public void setName(String value) {
   this.name = value;
}

public String getName() {
   return this.name;
}

private String password;

public void setPassword(String value) {
    this.password = value;
}
@NotNull
@NotEmpty
public String getPassword() {
    return this.password;
}
   private String phone;
   
   public void setPhone(String value) {
      this.phone = value;
   }
   
   public String getPhone() {
      return this.phone;
   }
   
   }
