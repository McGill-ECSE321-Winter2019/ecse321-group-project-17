package ca.mcgill.ecse321.cooperator.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.cooperator.dto.AdminDto;
import ca.mcgill.ecse321.cooperator.dto.CoopDto;
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
	
	@PostMapping(value = { "/student/create/{email}/{password}/{name}/{phone}/{studentId}", 
						   "/student/create/{email}/{password}/{name}/{phone}/{studentId}/" })
	public StudentDto createStudent(@PathVariable("email") String email, @PathVariable String password, @PathVariable String name, 
			@PathVariable String phone, @PathVariable Integer studentId) {
		name = name.replace('_', ' '); // Name will be separated by underscore, change it to space
		Student student = service.createStudent(email, name, password, phone, studentId);
		return convertToDto(student);
	}
	
	@PostMapping(value = { "/employer/create/{email}/{password}/{name}/{phone}/{emplId}",  
			        	   "/employer/create/{email}/{password}/{name}/{phone}/{emplId}/" })
	public EmployerDto createEmployer(@PathVariable("email") String email, @PathVariable String password, @PathVariable String name, 
			@PathVariable String phone, @PathVariable Integer emplId) {
		name = name.replace('_', ' '); // Name will be separated by underscore, change it to space
		Employer empl = service.createEmployer(email, name, password, phone, emplId);
		return convertToDto(empl);
	}
	
	@PostMapping(value = { "/admin/create/{email}/{password}/{name}/{phone}/{adminId}",  
			  			   "/admin/create/{email}/{password}/{name}/{phone}/{adminId}/" })
	public AdminDto createAdmin(@PathVariable("email") String email, @PathVariable String password, @PathVariable String name, 
			@PathVariable String phone, @PathVariable Integer adminId) {
		name = name.replace('_', ' '); // Name will be separated by underscore, change it to space
		Administrator admin = service.createAdmin(email, name, password, phone, adminId);
		return convertToDto(admin);
	}
	
	@PostMapping(value = { "/coop/create/{id}/{title}/{stuEmail}/{empEmail}/{start}/{end}/{status}/{salaryPerHour}/{hoursPerWeek}/{address}",  
						   "/coop/create/{id}/{title}/{stuEmail}/{empEmail}/{start}/{end}/{status}/{salaryPerHour}/{hoursPerWeek}/{address}/" })
	public CoopDto createCoop(@PathVariable("id") Integer id, @PathVariable String title, @PathVariable String stuEmail, 
								@PathVariable String empEmail, @PathVariable String start, @PathVariable String end, 
								@PathVariable CoopStatus status, @PathVariable Integer salaryPerHour, @PathVariable Integer hoursPerWeek, 
								@PathVariable String address) {
		title = title.replace('_', ' ');
		address = address.replace('_', ' ');
		Student stu = service.getStudent(stuEmail);
		Employer emp = service.getEmployer(empEmail);
		Date startDate = Date.valueOf(start);
		Date endDate = Date.valueOf(end);
		
		Coop coop = service.createCoop(stu, emp, title, id, startDate, endDate, status, salaryPerHour, hoursPerWeek, address);
		return convertToDto(coop);
	} 
	
	
	//CAN ONLY DO THIS IF THE BACKWARDS ASSOCIATION IN CREATE NOTIFICATION IN SERVICE FILE IS COMMENTED OUT
	//THERES A COMMENT TO SHOW WHICH TO COMMENT OUT
	@PostMapping(value = { "/notification/create/{id}/{text}/{senderEmail}/{stuEmail}/{empEmail}", 
						   "/notification/create/{id}/{text}/{senderEmail}/{stuEmail}/{empEmail}/" })
	public NotificationDto createNotif(@PathVariable("id") Integer id, @PathVariable String text, 
			@PathVariable String senderEmail, @PathVariable String stuEmail, @PathVariable String empEmail) {
		Administrator a = service.getAdmin(senderEmail);
		Student s = null;
		Employer e = null;
		if(!stuEmail.equalsIgnoreCase("null")) {
			s = service.getStudent(stuEmail);
		}
		if(!empEmail.equalsIgnoreCase("null")) {
			e = service.getEmployer(empEmail);
		}
		Notification notif = service.createNotification(id, text, a, s, e);
		return convertToDto(notif);
	}
	
	@PostMapping(value = { "/report/create/{id}/{coopID}/{date}/{status}/{type}", 
	   					   "/report/create/{id}/{coopID}/{date}/{status}/{type}/" })
	public ReportDto createReport(@PathVariable("id") Integer id, @PathVariable Integer coopId, 
								  @PathVariable Date date, @PathVariable ReportStatus status, 
								  @PathVariable ReportType type) {
		Coop c = service.getCoop(coopId);
		Report report = service.createReport(id, c, date, status, type);
		return convertToDto(report);
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
	
	@GetMapping(value = { "/student/{email}", "/student/{email}/" })
	public StudentDto getStudent(@PathVariable("email") String email) {
		if(service.getAllStudents().size()!=0) {
			Student stu = service.getStudent(email);
			System.out.println("Requested student: "+stu.getEmail());
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
	
	
	/* NULL POINTER EXCEPTIONS FROM THESE TWO :(
	
	@GetMapping(value = { "/incompleteCoops", "/incompleteCoops/" })
	public List<CoopDto> getIncompleteCoop() {
		List<Coop> all = service.getIncompleteCoop();
		List<CoopDto> inc = null;
		for (Coop c : all) {
			inc.add(convertToDto(c));
		}
		return inc;
	}
	
	@GetMapping(value = { "/incompleteStu", "/incompleteStu/" })
	public List<StudentDto> getIncompleteCoopStudent() {
		Set<Student> all = service.getIncompleteCoopStudents();
		List<StudentDto> inc = null;
		for (Student s : all) {
			inc.add(convertToDto(s));
		}
		return inc;
	} */
	
	
	
/* THIS DOESNT WORK and probs wont ever work the way it should 
	@GetMapping(value = { "/profiles", "/profiles/" })
	public List<ProfileDto> getAllProfiles() {
		List<ProfileDto> profDtos = new ArrayList<>();
		List<Profile> profiles = new ArrayList<>();
		for (Employer empl : service.getAllEmployers()) {
			profiles.add(empl);
		}
		for (Student stu : service.getAllStudents()) {
			profiles.add(stu);
		}
		for (Administrator admin : service.getAllAdministrators()) {
			profiles.add(admin);
		}
		for(Profile p : profiles) {
			profDtos.add(convertToDto(p));
		}
		return profDtos;
	}
	*/
	
	@GetMapping(value = { "/coops", "/coops/" })
	public List<CoopDto> getAllCoops() {
		List<CoopDto> coopDtos = new ArrayList<>();
		for (Coop coop : service.getAllCoops()) {
			coopDtos.add(convertToDto(coop));
		}
		return coopDtos;
	}
	
	@GetMapping(value = { "/reports/student/{email}", "/reports/student/{email}" })
	public List<ReportDto> getAllReportsofStudent(@PathVariable("email") StudentDto sDto){
		Student s = convertToDomainObject(sDto);
		List<ReportDto> reportDtos;
		Set<Report> reports = new HashSet<>();
		for(Coop c : service.getCoopforStudent(s)) {
			reports.addAll(c.getReport());
		}
		reportDtos = convertToDto(reports);
		return reportDtos;
	}
	
	@GetMapping(value = { "/reports/coop/{id}", "/reports/coop/{id}" })
	public List<ReportDto> getAllReportsofCoop(@PathVariable("name") CoopDto cDto){
		Coop c = convertToDomainObject(cDto);
		List<ReportDto> reportDtos;
		Set<Report> reports = c.getReport();
		reportDtos = convertToDto(reports);
		return reportDtos;
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
	
	private Administrator convertToDomainObject(AdminDto aDto) {
		List<Administrator> allAdministrators = service.getAllAdministrators();
		for (Administrator admin : allAdministrators) {
			if (admin.getName().equals(aDto.getName())) {
				return admin;
			}
		}
		return null;
	}
	
	private CoopDto convertToDto(Coop c) {
		if (c == null) {
			throw new IllegalArgumentException("There is no such Coop!");
		}
		CoopDto coopDto = new CoopDto(c.getId(),c.getTitle(), convertToDto(c.getStudent()), convertToDto(c.getEmployer()), c.getStartDate(), c.getEndDate(), 
				c.getStatus(), c.getSalaryPerHour(), c.getHoursPerWeek(), c.getAddress());
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
	
	private EmployerDto convertToDto(Employer e) {
		if (e == null) {
			throw new IllegalArgumentException("There is no such Employer!");
		}
		EmployerDto employerDto = new EmployerDto(e.getEmail(), e.getPassword(), e.getName(), e.getId(), e.getPhone());
		return employerDto;
	}
	
	private Employer convertToDomainObject(EmployerDto eDto) {
		List<Employer> allEmployers = service.getAllEmployers();
		for (Employer empl : allEmployers) {
			if (empl.getName().equals(eDto.getName())) {
				return empl;
			}
		}
		return null;
	}
	
	private ReportDto convertToDto(Report r) {
		if (r == null) {
			throw new IllegalArgumentException("There is no such Report!");
		}
		ReportDto reportDto = new ReportDto(r.getId(), convertToDto(r.getCoop()), r.getDueDate(), r.getStatus(), r.getType());
		return reportDto;
	}
	
	private List<ReportDto> convertToDto(Set<Report> r) {
		if (r == null) {
			throw new IllegalArgumentException("There is no such Report!");
		}
		List<ReportDto> rDto = null;
		for(Report rep : r) {
			rDto.add(convertToDto(rep));
		}
		return rDto;
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
	
	private Student convertToDomainObject(StudentDto sDto) {
		List<Student> allStudents = service.getAllStudents();
		for (Student student : allStudents) {
			if (student.getName().equals(sDto.getName())) {
				return student;
			}
		}
		return null;
	}
	
	
	private Set<NotificationDto> createNotificationDtosForEmp(Employer e) {
		Set<Notification> notificationsForEmp = service.getNotificationsEmp(e);
		Set<NotificationDto> notifications = new HashSet<>();
		for (Notification notification : notificationsForEmp) {
			notifications.add(convertToDto(notification));
		}
		return notifications;
	}
	
	private Set<NotificationDto> createNotificationDtosForAdm(Administrator a) {
		Set<Notification> notificationsForAdm = service.getNotificationsAdm(a);
		Set<NotificationDto> notifications = new HashSet<>();
		for (Notification notification : notificationsForAdm) {
			notifications.add(convertToDto(notification));
		}
		return notifications;
	}
	
	private Set<NotificationDto> createNotificationDtosForStu(Student s) {
		Set<Notification> notificationsForStu = service.getNotificationsStu(s);
		Set<NotificationDto> notifications = new HashSet<>();
		for (Notification notification : notificationsForStu) {
			notifications.add(convertToDto(notification));
		}
		return notifications;
	}
	
	private Set<CoopDto> createCoopDtosForEmployer(Employer e){
		Set<Coop> coopsForEmployer = e.getCoop();
		Set<CoopDto> coops = new HashSet<>();
		for (Coop coop : coopsForEmployer) {
			coops.add(convertToDto(coop));
		}
		return coops;
	}
	
	private Set<CoopDto> createCoopDtosForStudent(Student s){
		Set<Coop> coopsForStudent = s.getCoop();
		Set<CoopDto> coops = new HashSet<>();
		for (Coop coop : coopsForStudent) {
			coops.add(convertToDto(coop));
		}
		return coops;
	}
	
	private List<ReportDto> createReportDtosForCoop(Coop c){
		Set<Report> reportsForCoop = c.getReport();
		List<ReportDto> reports = new ArrayList<>();
		for (Report report : reportsForCoop){
			reports.add(convertToDto(report));
		}
		return reports;
	}
	

}