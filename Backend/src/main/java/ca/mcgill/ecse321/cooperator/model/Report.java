package ca.mcgill.ecse321.cooperator.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;
import java.io.File;
import javax.persistence.ManyToOne;

@Entity
public class Report{
   private Integer id;

public void setId(Integer value) {
    this.id = value;
}
@Id
public Integer getId() {
    return this.id;
}
private Date dueDate;

public void setDueDate(Date value) {
    this.dueDate = value;
}
public Date getDueDate() {
    return this.dueDate;
}
private ReportStatus status;

public void setStatus(ReportStatus value) {
    this.status = value;
}
public ReportStatus getStatus() {
    return this.status;
}
private ReportType type;

public void setType(ReportType value) {
    this.type = value;
}
public ReportType getType() {
    return this.type;
}
private File file;

public void setFile(File value) {
    this.file = value;
}
public File getFile() {
    return this.file;
}
   private Coop coop;
   
   @ManyToOne(optional=false)
   public Coop getCoop() {
      return this.coop;
   }
   
   public void setCoop(Coop coop) {
      this.coop = coop;
   }
   
   }
