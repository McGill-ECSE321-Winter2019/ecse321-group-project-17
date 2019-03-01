package ca.mcgill.ecse321.cooperator.dto;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ca.mcgill.ecse321.cooperator.dao.CoopRepository;
import ca.mcgill.ecse321.cooperator.model.Coop;

public class CoopStatisticsDto {
	
	// filters 
	private String startTerm; 	// format: {Winter,Fall,Summer}{Year}, if empty don't apply
	private String endTerm;		// format: {Winter,Fall,Summer}{Year}, if empty don't apply
	private Integer coopNumber; // format: [1;max[, if 0 don't apply
	
	// coops
	private Integer notStartedCoops;
	private Integer inProgressCoops;
	private Integer completedCoops;
	private Integer totalCoops;
	
	@SuppressWarnings("unchecked")
	public CoopStatisticsDto() {
		this("","",0,0,0,0,0);
	}
	
	public CoopStatisticsDto(String startTerm, String endTerm, Integer coopNumber,
			Integer notStartedCoops, Integer inProgressCoops, Integer completedCoops, Integer totalCoops) {
		super();
		this.startTerm = startTerm;
		this.endTerm = endTerm;
		this.coopNumber = coopNumber;
		this.notStartedCoops = notStartedCoops;
		this.inProgressCoops = inProgressCoops;
		this.completedCoops = completedCoops;
		this.totalCoops = totalCoops;
	}
	
	public Date getStartDate(String season, Integer year) {
		int day = 1;
		int month = 0;
		switch(season) {
		case "winter":
			month = 1;
			break;
		case "summer":
			month = 5;
			break;
		case "fall":
			month = 9;
			break;
		}
		return new Date(year, month, day);
	}
	
	public Date getEndDate(String season, Integer year) {
		int month = 0, day = 0;
		switch(season) {
		case "winter":
			month = 4; day = 30; 
			break;
		case "summer":
			month = 8; day = 31;
			break;
		case "fall":
			month = 12; day = 31;
			break;
		}
		return new Date(year, month, day);
	}
	
	// returns "winter", "summer", "fall", or "" 
	public String extractSeason(String term) {
		String stringOnly = term.replaceAll("[0-9]", "");
		if (stringOnly.matches("[Ww]inter") || stringOnly.matches("[Ss]ummer") || stringOnly.matches("[Ff]all")) {
			return stringOnly.toLowerCase();
		}
		return "";
	}
	
	// returns Integer like 20XX or 0
	public Integer extractYear(String term) {
		String numberOnly = term.replaceAll("[^0-9]", "");
		if(numberOnly.matches("20[0-9][0-9]")) {
			return Integer.valueOf(numberOnly);
		}
		return 0;
	}
	
	// Start date: 1st January; End date: 31st December
	public boolean inYear(Date date, Integer year) {
		boolean isBefore = date.before(new Date(year, 12, 31));
		boolean isAfter = date.after(new Date(year, 1, 1));
		return (isBefore && isAfter);
	}
	
	// Start date: 1st September; End date: 30th August
	public boolean inSchoolYear(Date date, Integer year) {
		boolean isBefore = date.before(new Date(year, 8, 30));
		boolean isAfter = date.after(new Date(year, 9, 01));
		return (isBefore && isAfter);
	}

	public String getStartTerm() {
		return startTerm;
	}


	public void setStartTerm(String startTerm) {
		this.startTerm = startTerm;
	}


	public String getEndTerm() {
		return endTerm;
	}


	public void setEndTerm(String endTerm) {
		this.endTerm = endTerm;
	}


	public Integer getCoopNumber() {
		return coopNumber;
	}


	public void setCoopNumber(Integer coopNumber) {
		this.coopNumber = coopNumber;
	}


	public Integer getNotStartedCoops() {
		return notStartedCoops;
	}


	public void setNotStartedCoops(Integer notStartedCoops) {
		this.notStartedCoops = notStartedCoops;
	}


	public Integer getInProgressCoops() {
		return inProgressCoops;
	}


	public void setInProgressCoops(Integer inProgressCoops) {
		this.inProgressCoops = inProgressCoops;
	}


	public Integer getCompletedCoops() {
		return completedCoops;
	}


	public void setCompletedCoops(Integer completedCoops) {
		this.completedCoops = completedCoops;
	}


	public Integer getTotalCoops() {
		return totalCoops;
	}


	public void setTotalCoops(Integer totalCoops) {
		this.totalCoops = totalCoops;
	}

}
