package ca.mcgill.ecse321.cooperator.dto;

import java.sql.Date;
import java.util.List;

import ca.mcgill.ecse321.cooperator.model.Employer;
import ca.mcgill.ecse321.cooperator.model.Report;
import ca.mcgill.ecse321.cooperator.model.Student;
import net.bytebuddy.utility.RandomString;

public class CoopDto {

	private Integer id; // primary key
	private String title; // not null, not empty
	private Student student;
	private Employer employer;
	private List<Report> report;
	private Date startDate;
	private Date endDate;
	private Integer status;
	private Integer salaryPerHour;
	private Integer hoursPerWeek;
	private String address;
	
	public CoopDto() {
	}
	
	public CoopDto(Integer id) {
		this(id, "title "+id, null, null, null, null, 0, 0, 0, "");
	}
	
	public CoopDto(Integer id, String title, Student student, Employer employer, Date startDate, Date endDate, 
			Integer status, Integer salaryPerHour, Integer hoursPerWeek, String address) {
		this.student = student;
		this.employer = employer;
		this.report = null;
		this.id = id;
		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
		this.status = status;
		this.salaryPerHour = salaryPerHour;
		this.hoursPerWeek = hoursPerWeek;
		this.address = address;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Employer getEmployer() {
		return employer;
	}

	public void setEmployer(Employer employer) {
		this.employer = employer;
	}

	public List<Report> getFile() {
		return report;
	}

	public void setReport(List<Report> report) {
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
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
