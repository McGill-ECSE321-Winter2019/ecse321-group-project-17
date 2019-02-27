package ca.mcgill.ecse321.cooperator.dto;

public class ReportStatisticsDto {
	
	// filters
	private Integer currentyear; // current term of the students (i.e. Year 0, Year 1, Year 2, etc...)
	private Integer gradyear;	 // year in which the students graduate(i.e 2020)
	
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
		this(0,0,0,0,0,0,0,0,0,0,0,0);
	}
	
	public ReportStatisticsDto(Integer currentyear, Integer gradyear, Integer unsubmittedReports,
			Integer submittedReports, Integer lateReports, Integer reviewedReports, Integer contractReports,
			Integer technicalReports, Integer studentEvalReports, Integer employerEvalReports, Integer twoWeekReports,
			Integer totalReports) {
		super();
		this.currentyear = currentyear;
		this.gradyear = gradyear;
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
	
	public ReportStatisticsDto generateAllReportStatistics(Integer currentyear, Integer gradyear) {
		ReportStatisticsDto rsd = new ReportStatisticsDto();
		rsd.currentyear = currentyear;
		rsd.gradyear = gradyear;
		//fill statistics
		return rsd;
	}

	public Integer getCurrentyear() {
		return currentyear;
	}

	public void setCurrentyear(Integer currentyear) {
		this.currentyear = currentyear;
	}

	public Integer getGradyear() {
		return gradyear;
	}

	public void setGradyear(Integer gradyear) {
		this.gradyear = gradyear;
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
