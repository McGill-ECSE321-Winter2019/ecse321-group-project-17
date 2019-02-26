package ca.mcgill.ecse321.cooperator.dto;

import java.sql.Date;

import ca.mcgill.ecse321.cooperator.model.ReportStatus;
import ca.mcgill.ecse321.cooperator.model.ReportType;

public class ReportDto {
	private Integer id;
	private CoopDto coop;
	private Date dueDate;
	private ReportStatus status;
	private ReportType type;
	
	public ReportDto() {
		
	}
	
	@SuppressWarnings("unchecked")
	public ReportDto(Integer id) {
		this(id, null, null, null, null);
	}
	
	public ReportDto(Integer id, CoopDto coop, Date date, ReportStatus status, ReportType type) {
		this.id = id;
		this.coop = coop;
		this.dueDate = date;
		this.status = status;
		this.type = type;
	}
	
	public Integer getID() {
		return id;
	}
	 
	public void setID(Integer id) {
		this.id = id;
	}
	
	public CoopDto getCoop() {
		return coop;
	}
	
	public void setCoop(CoopDto coop) {
		this.coop = coop;
	}
	

}