package ca.mcgill.ecse321.cooperator.model;

import javax.validation.constraints.NotNull;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Id;

@Entity
public class Notification{
   private Employer employer;
   
   @ManyToOne
   public Employer getEmployer() {
      return this.employer;
   }
   
   public void setEmployer(Employer employer) {
      this.employer = employer;
   }
   
   private Administrator sender;
   
   @ManyToOne(optional=false)
   public Administrator getSender() {
      return this.sender;
   }
   
   public void setSender(Administrator sender) {
      this.sender = sender;
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
   private Student student;
   
   @ManyToOne
   public Student getStudent() {
      return this.student;
   }
   
   public void setStudent(Student student) {
      this.student = student;
   }
   
   }
