package ca.mcgill.ecse321.cooperator.model;

import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Id;
import java.sql.Date;

@Entity
public class Coop{
   private Student student;
   
   @ManyToOne(optional=false)
   public Student getStudent() {
      return this.student;
   }
   
   public void setStudent(Student student) {
      this.student = student;
   }
   
   private Employer employer;
   
   @ManyToOne(optional=false)
   public Employer getEmployer() {
      return this.employer;
   }
   
   public void setEmployer(Employer employer) {
      this.employer = employer;
   }
   
   private Integer id;

public void setId(Integer value) {
    this.id = value;
}
@Id
public Integer getId() {
    return this.id;
}
private String title;

public void setTitle(String value) {
    this.title = value;
}
@NotNull
@NotEmpty
public String getTitle() {
    return this.title;
}
private Date startDate;

public void setStartDate(Date value) {
    this.startDate = value;
}
public Date getStartDate() {
    return this.startDate;
}
private Date endDate;

public void setEndDate(Date value) {
    this.endDate = value;
}
public Date getEndDate() {
    return this.endDate;
}
private Integer status;

public void setStatus(Integer value) {
    this.status = value;
}
public Integer getStatus() {
    return this.status;
}
private Integer salaryPerHour;

public void setSalaryPerHour(Integer value) {
    this.salaryPerHour = value;
}
public Integer getSalaryPerHour() {
    return this.salaryPerHour;
}
private Integer hoursPerWeek;

public void setHoursPerWeek(Integer value) {
    this.hoursPerWeek = value;
}
public Integer getHoursPerWeek() {
    return this.hoursPerWeek;
}
private String address;

public void setAddress(String value) {
    this.address = value;
}
public String getAddress() {
    return this.address;
}
}
