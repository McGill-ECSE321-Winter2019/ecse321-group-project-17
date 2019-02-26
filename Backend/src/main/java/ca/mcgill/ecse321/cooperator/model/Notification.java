package ca.mcgill.ecse321.cooperator.model;

import javax.validation.constraints.NotNull;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Id;

@Entity
public class Notification{
   private Profile profile;
   
   @ManyToOne(optional=false)
   public Profile getProfile() {
      return this.profile;
   }
   
   public void setProfile(Profile profile) {
      this.profile = profile;
   }
   
   private Profile profile1;
   
   @ManyToOne(optional=false)
   public Profile getProfile1() {
      return this.profile1;
   }
   
   public void setProfile1(Profile profile1) {
      this.profile1 = profile1;
   }
   
   private Integer id;

public void setId(Integer value) {
    this.id = value;
}
@Id
public Integer getId() {
    return this.id;
}
private String text;

public void setText(String value) {
    this.text = value;
}
@NotNull
public String getText() {
    return this.text;
}
}