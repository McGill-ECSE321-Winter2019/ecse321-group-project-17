package ca.mcgill.ecse321.cooperator.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import java.sql.Date;
import java.io.File;

@Entity
public class Report{
	
	private Coop coop;
	
	@ManyToOne(optional=false)
	public Coop getCoop() {
		return this.coop;
	}
	
	public void setCoop(Coop coop) {
		this.coop = coop;
	}
	
   private Integer id;

public void setId(Integer value) {
    this.id = value;
}

@Id
@GeneratedValue()
public Integer getId() {
    return this.id;
}
   /**
    * <pre>
    *           1..1     1..1
    * Report ------------------------> Date
    *           &lt;       dueDate
    * </pre>
    */
   private Date dueDate;
   
   public void setDueDate(Date value) {
      this.dueDate = value;
   }
   
   public Date getDueDate() {
      return this.dueDate;
   }
   
   /**
    * <pre>
    *           1..1     1..1
    * Report ------------------------> ReportStatus
    *           &lt;       status
    * </pre>
    */
   private ReportStatus status;
   
   public void setStatus(ReportStatus value) {
      this.status = value;
   }
   
   public ReportStatus getStatus() {
      return this.status;
   }
   
   /**
    * <pre>
    *           1..1     1..1
    * Report ------------------------> ReportType
    *           &lt;       type
    * </pre>
    */
   private ReportType type;
   
   public void setType(ReportType value) {
      this.type = value;
   }
   
   public ReportType getType() {
      return this.type;
   }
   
   /**
    * <pre>
    *           1..1     1..1
    * Report ------------------------> File
    *           &lt;       file
    * </pre>
    */
   private File file;
   
   public void setFile(File value) {
      this.file = value;
   }
   
   public File getFile() {
      return this.file;
   }
   
   }
