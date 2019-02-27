package ca.mcgill.ecse321.cooperator.dto;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ca.mcgill.ecse321.cooperator.dao.CoopRepository;
import ca.mcgill.ecse321.cooperator.dao.ReportRepository;
import ca.mcgill.ecse321.cooperator.model.Report;

public class ReportStatisticsDto {
	@Autowired
	ReportRepository reportRepository;
	
	// filters
	private String startTerm;
	private String endTerm;
	private Integer coopNumber;
	
	// reports status
	private Integer unsubmittedReports;
	private Integer submittedReports;
	private Integer lateReports;
	private Integer reviewedReports;
	
	// reports type
	private Integer contractReports;
	private Integer technicalReports;
	private Integer studentEvalReports;
	private Integer employerEvalReports;
	private Integer twoWeekReports;
	
	private Integer totalReports;

	@SuppressWarnings("unchecked")
	public ReportStatisticsDto() {
		this("","",0,0,0,0,0,0,0,0,0,0,0);
	}
	
	public ReportStatisticsDto(String startTerm, String endTerm, Integer coopNumber, Integer unsubmittedReports,
			Integer submittedReports, Integer lateReports, Integer reviewedReports, Integer contractReports,
			Integer technicalReports, Integer studentEvalReports, Integer employerEvalReports, Integer twoWeekReports,
			Integer totalReports) {
		super();
		this.startTerm = startTerm;
		this.endTerm = endTerm;
		this.coopNumber = coopNumber;
		this.unsubmittedReports = unsubmittedReports;
		this.submittedReports = submittedReports;
		this.lateReports = lateReports;
		this.reviewedReports = reviewedReports;
		this.contractReports = contractReports;
		this.technicalReports = technicalReports;
		this.studentEvalReports = studentEvalReports;
		this.employerEvalReports = employerEvalReports;
		this.twoWeekReports = twoWeekReports;
		this.totalReports = totalReports;
	}


	/*
	 * Generates statistics. Can filter by a range of terms (i.e Winter2018 to Fall2019) and/or a specific report (i.e only students doing there 1st report)
	 */
	public ReportStatisticsDto generateAllReportStatistics(String startTerm, String endTerm, Integer coopNumber) {
		ReportStatisticsDto rsd = new ReportStatisticsDto();
		rsd.startTerm = startTerm;
		rsd.endTerm = endTerm;
		rsd.coopNumber = coopNumber;
		Iterable<Report> reports = reportRepository.findAll();
		
		List<Report> filter1 = new ArrayList<Report>();
		// filter out anything before startTerm
		String startSeason = extractSeason(startTerm);
		Integer startYear = extractYear(startTerm);
		if (startSeason != "" && startYear != 0) { 
			Date startDate = getStartDate(startSeason, startYear);
			for(Report report : reports) {
				if(report.getCoop().getStartDate().after(startDate)) { // if the report start after the start of the term
					filter1.add(report);
				}
			}
		}
		
		List<Report> filter2 = new ArrayList<Report>();
		// filter out anything after the endTerm
		String endSeason = extractSeason(endTerm);
		Integer endYear = extractYear(endTerm);
		if (endSeason != "" && endYear != 0) { 
			Date endDate = getEndDate(endSeason, endYear);
			for(Report report : filter1) {
				if(report.getCoop().getStartDate().before(endDate)) { // if the report starts before the end of the term
					filter2.add(report);
				}
			}
		}
		
		List<Report> filter3 = new ArrayList<Report>();
		// filter out students who aren't on their [reportNumber] report
		if (coopNumber != 0) {
			for(Report report: filter2) {
				if(report.getCoop().getStudent().getCoopsCompleted() == coopNumber-1) { // if the student is on there [reportNumber] report
					filter3.add(report);
				}
			}
		}
		
		// fill statistics
		for (Report report: filter3) {
			rsd.totalReports++;
			switch(report.getStatus()) {
			case Unsubmitted:
				rsd.unsubmittedReports++;
				break;
			case Submitted:
				rsd.submittedReports++;
				break;
			case Late:
				rsd.lateReports++;
				break;
			case Reviewed:
				rsd.reviewedReports++;
			default:
				break;
			}
			switch(report.getType()) {
			case Contract:
				rsd.contractReports++;
				break;
			case Technical:
				rsd.technicalReports++;
				break;
			case TwoWeek:
				rsd.twoWeekReports++;
				break;
			case StudentEval:
				rsd.studentEvalReports++;
				break;
			case EmployerEval:
				rsd.employerEvalReports++;
			default:
				break;
			}
		}
		return rsd;
	}

	
	private Date getStartDate(String season, Integer year) {
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
	
	private Date getEndDate(String season, Integer year) {
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
	private String extractSeason(String term) {
		String stringOnly = term.replaceAll("[0-9]", "");
		if (stringOnly.matches("[Ww]inter") || stringOnly.matches("[Ss]ummer") || stringOnly.matches("[Ff]all")) {
			return stringOnly.toLowerCase();
		}
		return "";
	}
	
	// returns Integer like 20XX or 0
	private Integer extractYear(String term) {
		String numberOnly = term.replaceAll("[^0-9]", "");
		if(numberOnly.matches("20[0-9][0-9]")) {
			return Integer.valueOf(numberOnly);
		}
		return 0;
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

	public Integer getUnsubmittedReports() {
		return unsubmittedReports;
	}

	public void setUnsubmittedReports(Integer unsubmittedReports) {
		this.unsubmittedReports = unsubmittedReports;
	}

	public Integer getSubmittedReports() {
		return submittedReports;
	}

	public void setSubmittedReports(Integer submittedReports) {
		this.submittedReports = submittedReports;
	}

	public Integer getLateReports() {
		return lateReports;
	}

	public void setLateReports(Integer lateReports) {
		this.lateReports = lateReports;
	}

	public Integer getReviewedReports() {
		return reviewedReports;
	}

	public void setReviewedReports(Integer reviewedReports) {
		this.reviewedReports = reviewedReports;
	}

	public Integer getContractReports() {
		return contractReports;
	}

	public void setContractReports(Integer contractReports) {
		this.contractReports = contractReports;
	}

	public Integer getTechnicalReports() {
		return technicalReports;
	}

	public void setTechnicalReports(Integer technicalReports) {
		this.technicalReports = technicalReports;
	}

	public Integer getStudentEvalReports() {
		return studentEvalReports;
	}

	public void setStudentEvalReports(Integer studentEvalReports) {
		this.studentEvalReports = studentEvalReports;
	}

	public Integer getEmployerEvalReports() {
		return employerEvalReports;
	}

	public void setEmployerEvalReports(Integer employerEvalReports) {
		this.employerEvalReports = employerEvalReports;
	}

	public Integer getTwoWeekReports() {
		return twoWeekReports;
	}

	public void setTwoWeekReports(Integer twoWeekReports) {
		this.twoWeekReports = twoWeekReports;
	}

	public Integer getTotalReports() {
		return totalReports;
	}

	public void setTotalReports(Integer totalReports) {
		this.totalReports = totalReports;
	}

	
	
}
