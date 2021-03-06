package ca.mcgill.ecse321.cooperator.dto;

import java.sql.Date;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import ca.mcgill.ecse321.cooperator.model.CoopStatus;

public class CoopDto {

	private Integer id; // primary key
	private String title; // not null, not empty
	private StudentDto student;
	private EmployerDto employer;
	private Set<ReportDto> report;
	private Date startDate;
	private Date endDate;
	private CoopStatus status;
	private Integer salaryPerHour;
	private Integer hoursPerWeek;
	private String address;

	public CoopDto() {
	}

	public CoopDto(Integer id) {
		this(id, "title "+id, null, null, null, null, CoopStatus.NotStarted, 0, 0, "", Collections.EMPTY_SET);
	}
	
	public CoopDto(Integer id, String title, StudentDto student, EmployerDto employer, Date startDate, Date endDate, 
			CoopStatus status, Integer salaryPerHour, Integer hoursPerWeek, String address, Set<ReportDto> r) {
		this.student = student;
		this.employer = employer;
		this.report = r;
		this.id = id;
		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
		this.status = status;
		this.salaryPerHour = salaryPerHour;
		this.hoursPerWeek = hoursPerWeek;
		this.address = address;
	}

	public StudentDto getStudent() {
		return student;
	}

	public void setStudentDto(StudentDto student) {
		this.student = student;
	}

	public EmployerDto getEmployer() {
		return employer;
	}

	public void setEmployer(EmployerDto employer) {
		this.employer = employer;
	}

	public Set<ReportDto> getReports() {
		return report;
	}

	public void setReport(Set<ReportDto> report) {
		this.report = report;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public CoopStatus getStatus() {
		return status;
	}

	public void setStatus(CoopStatus status) {
		this.status = status;
	}

	public Integer getSalaryPerHour() {
		return salaryPerHour;
	}

	public void setSalaryPerHour(Integer salaryPerHour) {
		this.salaryPerHour = salaryPerHour;
	}

	public Integer getHoursPerWeek() {
		return hoursPerWeek;
	}

	public void setHoursPerWeek(Integer hoursPerWeek) {
		this.hoursPerWeek = hoursPerWeek;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
