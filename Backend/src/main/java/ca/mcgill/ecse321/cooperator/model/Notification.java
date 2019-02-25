package ca.mcgill.ecse321.cooperator.model;

import javax.validation.constraints.NotNull;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Id;

@Entity
public class Notification{
   private Profile sender;
   
   @ManyToOne(optional=false)
   public Profile getSender() {
      return this.sender;
   }
   
   public void setSender(Profile profile) {
      this.sender = profile;
   }
   
   private Profile receiver;
   
   @ManyToOne(optional=false)
   public Profile receiver() {
      return this.receiver;
   }
   
   public void setReceiver(Profile profile2) {
      this.receiver = profile2;
   }
   
   public Profile getReceiver() {
	     return this.receiver;
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
