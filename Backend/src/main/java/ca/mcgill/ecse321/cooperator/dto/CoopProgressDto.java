package ca.mcgill.ecse321.cooperator.dto;

import java.sql.Date;
import java.util.Set;

import ca.mcgill.ecse321.cooperator.model.CoopStatus;

public class CoopProgressDto {
	
	private Set<ReportDto> reports;
	private CoopStatus coopStatus;
	private Date coopStart;
	private Date coopEnd;

	public CoopProgressDto() {
		this(null, CoopStatus.NotStarted, null, null);
	}
	
	public CoopProgressDto(Set<ReportDto> reports, CoopStatus coopStatus, Date coopStart, Date coopEnd) {
		this.reports = reports;
		this.coopStatus = coopStatus;
		this.coopStart = coopStart;
		this.coopEnd = coopEnd;
	}
	
	public Set<ReportDto> getReports(){
		return reports;
	}

	public void setReports(Set<ReportDto> reports) {
		this.reports = reports;
	}
	
	public CoopStatus getCoopStatus() {
		return coopStatus;
	}

	public void setCoopStatus(CoopStatus coopStatus) {
		this.coopStatus = coopStatus;
	}

	public Date getCoopStart() {
		return coopStart;
	}

	public void setCoopStart(Date coopStart) {
		this.coopStart = coopStart;
	}

	public Date getCoopEnd() {
		return coopEnd;
	}

	public void setCoopEnd(Date coopEnd) {
		this.coopEnd = coopEnd;
	}
}
