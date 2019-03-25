package ca.mcgill.ecse321.cooperator.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.cooperator.dto.AdminDto;
import ca.mcgill.ecse321.cooperator.dto.CoopDto;
import ca.mcgill.ecse321.cooperator.dto.CoopProgressDto;
import ca.mcgill.ecse321.cooperator.dto.CoopStatisticsDto;
import ca.mcgill.ecse321.cooperator.dto.EmployerDto;
import ca.mcgill.ecse321.cooperator.dto.ReportDto;
import ca.mcgill.ecse321.cooperator.dto.ReportStatisticsDto;
import ca.mcgill.ecse321.cooperator.dto.NotificationDto;
import ca.mcgill.ecse321.cooperator.dto.StudentDto;
import ca.mcgill.ecse321.cooperator.model.Administrator;
import ca.mcgill.ecse321.cooperator.model.Coop;
import ca.mcgill.ecse321.cooperator.model.CoopStatus;
import ca.mcgill.ecse321.cooperator.model.Employer;
import ca.mcgill.ecse321.cooperator.model.Report;
import ca.mcgill.ecse321.cooperator.model.ReportStatus;
import ca.mcgill.ecse321.cooperator.model.ReportType;
import ca.mcgill.ecse321.cooperator.model.Notification;
import ca.mcgill.ecse321.cooperator.model.Student;
import ca.mcgill.ecse321.cooperator.service.CooperatorService;

@CrossOrigin(origins = "*")
@RestController
public class CooperatorController {

	@Autowired 
	private CooperatorService service;
	
	/*
	 * POST METHODS
	 * 
	 */
	
	// CREATE METHODS
	
	@PostMapping("/student/create")
	public StudentDto createStudent(@RequestParam("email") String email, @RequestParam String password, @RequestParam String name, 
			@RequestParam String phone, @RequestParam Integer studentId) {
		Student student = service.createStudent(email, name, password, phone, studentId);
		return convertToDto(student);
	}
	
	@PostMapping("/employer/create")
	public EmployerDto createEmployer(@RequestParam("email") String email, @RequestParam String password, @RequestParam String name, 
			@RequestParam String phone, @RequestParam String company) {
		Employer empl = service.createEmployer(email, name, password, phone, company);
		return convertToDto(empl);
	}
	
	@PostMapping("/admin/create")
	public AdminDto createAdmin(@RequestParam("email") String email, @RequestParam String password, @RequestParam String name, 
			@RequestParam String phone) {
		Administrator admin = service.createAdmin(email, name, password, phone);
		return convertToDto(admin);
	}
	
	@PostMapping("/coop/create")
	public CoopDto createCoop(@RequestParam String title, @RequestParam String stuEmail, 
			@RequestParam String empEmail, @RequestParam String start, @RequestParam String end, 
			@RequestParam CoopStatus status, @RequestParam Integer salaryPerHour, @RequestParam Integer hoursPerWeek, 
			@RequestParam String address) {

		Student stu = service.getStudent(stuEmail);
		Employer emp = service.getEmployer(empEmail);
		Date startDate = Date.valueOf(start);
		Date endDate = Date.valueOf(end);
		
		Coop coop = service.createCoop(stu, emp, title, startDate, endDate, status, salaryPerHour, hoursPerWeek, address);
		return convertToDto(coop);
	} 
	
	//create single notification for either employer or student
	@PostMapping("/notification/create")
	public NotificationDto createNotif(@RequestParam String text, @RequestParam String senderEmail, 
			@RequestParam (required = false) String stuEmail, @RequestParam (required = false) String empEmail) {
		Administrator a = service.getAdmin(senderEmail);
		Student s = null;
		Employer e = null;
		if(stuEmail != null) {
			s = service.getStudent(stuEmail);
		}
		if(empEmail != null) {
			e = service.getEmployer(empEmail);
		}

		Notification notif = service.createNotification(text, a, s, e);
		return convertToDto(notif);
	}
	
	//create a mass notification for multiple students and employers
	@PostMapping("/notification/create-many")
	public List <NotificationDto> createManyNotif(@RequestParam String text, 
			@RequestParam String senderEmail, @RequestParam (required = false) List <String> stuEmail, 
			@RequestParam (required = false) List <String> empEmail) {
		
		List <NotificationDto> notifDtos = new ArrayList<>();
		Administrator a = service.getAdmin(senderEmail);

		if (stuEmail != null) {
			for (String studentEmail : stuEmail) {
				Student s = null;
				Employer e = null;
				if(studentEmail != null) {
					s = service.getStudent(studentEmail);
				}
				notifDtos.add(convertToDto(service.createNotification(text, a, s, e)));
			}
		}
		if (empEmail != null) {
			for (String employerEmail : empEmail) {
				Student s = null;
				Employer e = null;
				if(employerEmail != null) {
					e = service.getEmployer(employerEmail);
				}
				notifDtos.add(convertToDto(service.createNotification(text, a, s, e)));
			}
		}
		
		return notifDtos;
	}
	

	@PostMapping("/report/create")
	public ReportDto createReport(@RequestParam Integer coopId, @RequestParam Date date, 
			@RequestParam ReportStatus status, @RequestParam ReportType type) {
		Coop c = service.getCoop(coopId);
		Report report = service.createReport(c, date, status, type);
		return convertToDto(report);
	}
	
	/*
	 * PUT METHODS
	 * 
	 */
	
	@PutMapping("/report/update")
	public ReportDto updateReportStatus(@RequestParam Integer id, @RequestParam ReportStatus status){
		Report r = service.getReport(id);
		r = service.updateReport(r, status);
		ReportDto rDto = convertToDto(r);
		return rDto;
	}
	
	@PutMapping("/coop/update")
	public CoopDto updateCoopStatus(@RequestParam Integer id, @RequestParam CoopStatus status){
		Coop c = service.getCoop(id);
		c = service.updateCoopStatus(c, status);
		CoopDto cDto = convertToDto(c);
		return cDto;
	}
	
	/*
	 * DELETE METHODS
	 * 
	 */
	
	@DeleteMapping( "/report/delete")
	public void deleteReport(@RequestParam Integer id) {
		Report r = service.getReport(id);
		service.deleteReport(r);
	}
	
	@DeleteMapping( "/coop/delete")
	public void deleteCoop(@RequestParam Integer id) {
		service.deleteCoop(id);
	}

	@DeleteMapping( "/student/delete")
	public void deleteStudent(@RequestParam String email) {
		service.deleteStudent(email);
	}
	/*
	 * GET METHODS
	 * 
	 */
	
	// STATISTICS METHODS
	
	@GetMapping(value = { "/statistics/coop/{startTerm}/{endTerm}/{coopNumber}", "/statistics/coop/{startTerm}/{endTerm}/{coopNumber}/" })
	public CoopStatisticsDto getCoopStatistics(@PathVariable("startTerm") String startTerm, @PathVariable("endTerm") String endTerm, 
			@PathVariable("coopNumber") Integer coopNumber) {
		CoopStatisticsDto coopStatistics = service.generateAllCoopStatistics(startTerm, endTerm, coopNumber);
		return coopStatistics;
	}
	
	@GetMapping(value = { "/statistics/report/{startTerm}/{endTerm}/{coopNumber}", "/statistics/report/{startTerm}/{endTerm}/{coopNumber}/" })
	public ReportStatisticsDto getReportStatistics(@PathVariable("startTerm") String startTerm, @PathVariable("endTerm") String endTerm, 
			@PathVariable("coopNumber") Integer coopNumber) {
		ReportStatisticsDto reportStatisticsDto = service.generateAllReportStatistics(startTerm, endTerm, coopNumber);
		return reportStatisticsDto;
	}
	
	@GetMapping(value = { "/progress/coop/{id}", "/progress/coop/{id}/" })
	public CoopProgressDto getCoopProgress(@PathVariable("id") Integer id) {
		Coop c = service.getCoop(id);
		return convertToCoopProgressDto(c);
	}
	
	@GetMapping(value = { "/student/{email}", "/student/{email}/" })
	public StudentDto getStudent(@PathVariable("email") String email) {
		if(service.getAllStudents().size()!=0) {
			Student stu = service.getStudent(email);
			return convertToDto(stu);
		}
		return null;
	}
	
	@GetMapping(value = { "/employer/{email}", "/employer/{email}/" })
	public EmployerDto getEmployer(@PathVariable("email") String email) {
		Employer empl = service.getEmployer(email);
		return convertToDto(empl);
	}
	
	@GetMapping(value = { "/admin/{email}", "/admin/{email}/" })
	public AdminDto getAdmin(@PathVariable("email") String email) {
		Administrator admin = service.getAdmin(email);
		return convertToDto(admin);
	}
	
	@GetMapping(value = { "/coop/{id}", "/coop/{id}/" })
	public CoopDto getCoop(@PathVariable("id") Integer id) {
		Coop c = service.getCoop(id);
		return convertToDto(c);
	}
	
	@GetMapping(value = { "/students", "/students/" })
	public List<StudentDto> getAllStudents() {
		List<StudentDto> studentDtos = new ArrayList<>();
		for (Student student : service.getAllStudents()) {
			studentDtos.add(convertToDto(student));
		}
		return studentDtos;
	}
	
	@GetMapping(value = { "/admins", "/admins/" })
	public List<AdminDto> getAllAdmins() {
		List<AdminDto> adminDtos = new ArrayList<>();
		for (Administrator admin : service.getAllAdministrators()) {
			adminDtos.add(convertToDto(admin));
		}
		return adminDtos;
	}
	
	@GetMapping(value = { "/employers", "/employers/" })
	public List<EmployerDto> getAllEmployers() {
		List<EmployerDto> employerDtos = new ArrayList<>();
		for (Employer empl : service.getAllEmployers()) {
			employerDtos.add(convertToDto(empl));
		}
		return employerDtos;
	}
	
	@GetMapping(value = { "/coops-by-status/{status}", "/coops-by-status/{status}/" })
	public List<CoopDto> getCoopByStatus(@PathVariable("status") CoopStatus status) {
		List<Coop> c = service.getCoopsByStatus(status);
		List<CoopDto> cDto = new ArrayList<>();
		for(Coop coop : c) {
			cDto.add(convertToDto(coop));
		}
		return cDto;
	} 
	
	@GetMapping(value = { "/coops-by-company/{company}", "/coops-by-company/{company}/" })
	public List<CoopDto> getCoopByCompany(@PathVariable("company") String company) {
		List<Coop> c = service.getCoopsOfCompany(company);
		List<CoopDto> cDto = new ArrayList<>();
		for(Coop coop : c) {
			cDto.add(convertToDto(coop));
		}
		return cDto;
	}
	
	@GetMapping(value = { "/employers-by-company/{company}", "/coops-by-company/{company}/" })
	public List<EmployerDto> getEmployersByCompany(@PathVariable("company") String company) {
		List<Employer> e = service.getEmployersOfCompany(company);
		List<EmployerDto> employerDtos = new ArrayList<>();
		for (Employer empl : service.getAllEmployers()) {
			employerDtos.add(convertToDto(empl));
		}
		return employerDtos;
	}
	
	@GetMapping(value = { "/reports-by-status/{status}", "/reports-by-status/{status}/" })
	public List<ReportDto> getReportByStatus(@PathVariable("status") ReportStatus status) {
		List<Report> r = service.getReportByStatus(status);
		List<ReportDto> rDto = new ArrayList<>();
		for(Report report : r) {
			rDto.add(convertToDto(report));
		}
		return rDto;
	} 
	
	@GetMapping(value = { "/reports-by-type/{type}", "/reports-by-type/{type}/" })
	public List<ReportDto> getReportByType(@PathVariable("type") ReportType type) {
		List<Report> r = service.getReportByType(type);
		List<ReportDto> rDto = new ArrayList<>();
		for(Report report : r) {
			rDto.add(convertToDto(report));
		}
		return rDto;
	} 
	
	@GetMapping(value = { "/coops", "/coops/" })
	public List<CoopDto> getAllCoops() {
		List<CoopDto> coopDtos = new ArrayList<>();
		for (Coop coop : service.getAllCoops()) {
			coopDtos.add(convertToDto(coop));
		}
		return coopDtos;
	}
	
	@GetMapping(value = { "student/coops/{email}", "student/coops/{email}/" })
	public List<CoopDto> getAllCoopsForStudent(@PathVariable("email") String email) {
		List<CoopDto> coopDtos = new ArrayList<>();
		Student s = service.getStudent(email);
		
		for (Coop coop : s.getCoop()) {
			coopDtos.add(convertToDto(coop));
		}
		return coopDtos;
	}
	
	@GetMapping(value = { "employer/coops/{email}", "employer/coops/{email}/" })
	public List<CoopDto> getAllCoopsForEmployer(@PathVariable("email") String email) {
		List<CoopDto> coopDtos = new ArrayList<>();
		Employer e = service.getEmployer(email);
		
		for (Coop coop : e.getCoop()) {
			coopDtos.add(convertToDto(coop));
		}
		return coopDtos;
	}
	
	@GetMapping(value = { "/reports/student/{email}", "/reports/student/{email}/" })
	public Set<ReportDto> getAllReportsofStudent(@PathVariable("email") String email){
		Student s = service.getStudent(email);
		Set<ReportDto> reportDtos;
		Set<Report> reports = new HashSet<>();
		for(Coop c : service.getCoopforStudent(s)) {
			reports.addAll(c.getReport());
		}
		reportDtos = convertToDto(reports);
		return reportDtos;
	}
	
	@GetMapping(value = { "/problem-students", "/problem-students/" })
	public List<StudentDto> getProblematicStudents(){
		List<StudentDto> s = new ArrayList<>();
		for(Report r : service.getReportByStatus(ReportStatus.Late)) {
			r.getCoop().getStudent();
			s.add(convertToDto(r.getCoop().getStudent()));
		}
		for(Coop c : service.getCoopsByStatus(CoopStatus.Incomplete)) {
			s.add(convertToDto(c.getStudent()));
		}
		return s;
	}
	
	@GetMapping(value = { "/reports/coop/{id}", "/reports/coop/{id}/" })
	public Set<ReportDto> getAllReportsofCoop(@PathVariable("name") CoopDto cDto){
		Coop c = convertToDomainObject(cDto);
		Set<ReportDto> reportDtos;
		Set<Report> reports = c.getReport();
		reportDtos = convertToDto(reports);
		return reportDtos;
	}
	
	@GetMapping(value = { "/report/{id}", "/report/{id}/" })
	public ReportDto getReport(@PathVariable("id") Integer id){
		Report r = service.getReport(id);
		ReportDto rDto = convertToDto(r);
		return rDto;
	}

	/*
	 * CONVERSION METHODS
	 * 
	 */
	private AdminDto convertToDto(Administrator a) {
		if (a == null) {
			throw new IllegalArgumentException("There is no such Admin!");
		}
		AdminDto adminDto = new AdminDto(a.getEmail(), a.getPassword(), a.getName(), a.getId(), a.getPhone());
		return adminDto;
	}
	
	
	private CoopDto convertToDto(Coop c) {
		if (c == null) {
			throw new IllegalArgumentException("There is no such Coop!");
		}
		CoopDto coopDto = new CoopDto(c.getId(),c.getTitle(), convertToDto(c.getStudent()), convertToDto(c.getEmployer()), c.getStartDate(), c.getEndDate(), 
				c.getStatus(), c.getSalaryPerHour(), c.getHoursPerWeek(), c.getAddress(), convertToDto(c.getReport()));
		return coopDto;
	}

	
	private Coop convertToDomainObject(CoopDto cDto) {
		List<Coop> allCoops = service.getAllCoops();
		for (Coop c : allCoops) {
			if (c.getId() == cDto.getId()) {
				return c;
			}
		}
		return null;
	}
	
	private CoopProgressDto convertToCoopProgressDto(Coop c) {
		if (c == null) {
			throw new IllegalArgumentException("There is no such Coop!");
		}
		Set<Report> coopReports = c.getReport();
		Set <ReportDto> rDtos = new HashSet<ReportDto>();
		for(Report report : coopReports) {
			rDtos.add(convertToDto(report));
		}
		CoopProgressDto coopProgressDto = new CoopProgressDto(rDtos, c.getStatus(), c.getStartDate(), c.getEndDate());
		return coopProgressDto;
	}
	
	private EmployerDto convertToDto(Employer e) {
		if (e == null) {
			throw new IllegalArgumentException("There is no such Employer!");
		}
		EmployerDto employerDto = new EmployerDto(e.getEmail(), e.getPassword(), e.getName(), e.getId(), e.getPhone(), e.getCompany());
		return employerDto;
	}
	
	private ReportDto convertToDto(Report r) {
		if (r == null) {
			throw new IllegalArgumentException("There is no such Report!");
		}
		ReportDto reportDto = new ReportDto(r.getId(), r.getDueDate(), r.getStatus(), r.getType());
		return reportDto;
	}
	
	private Set<ReportDto> convertToDto(Set<Report> r) {
		if (r == null) {
			throw new IllegalArgumentException("There is no such Report!");
		}
		Set<ReportDto> rDto = new HashSet<>();
		for(Report rep : r) {
			rDto.add(convertToDto(rep));
		}
		return (Set<ReportDto>) rDto;
	}
	
	private NotificationDto convertToDto(Notification n) {
		if (n == null) {
			throw new IllegalArgumentException("There is no such Notification!");
		}
		NotificationDto nDto = null;
		if(n.getStudent() == null) {
			nDto = new NotificationDto(n.getId(), n.getText(), convertToDto(n.getSender()), 
					null, convertToDto(n.getEmployer()));
		}
		else if(n.getEmployer() == null) {
			nDto = new NotificationDto(n.getId(), n.getText(), convertToDto(n.getSender()), 
					convertToDto(n.getStudent()), null);
		}
		else {
			nDto = new NotificationDto(n.getId(), n.getText(), convertToDto(n.getSender()), 
					convertToDto(n.getStudent()), convertToDto(n.getEmployer()));
		}
		return nDto;
	}
	
	private StudentDto convertToDto(Student s) {
		if (s == null) {
			throw new IllegalArgumentException("There is no such Student!");
		}
		StudentDto studentDto = new StudentDto(s.getEmail(), s.getPassword(), s.getName(), s.getId(), s.getPhone());
		return studentDto;
	}
	

}