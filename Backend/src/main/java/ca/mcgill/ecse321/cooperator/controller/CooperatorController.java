package ca.mcgill.ecse321.cooperator.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.cooperator.dto.AdminDto;
import ca.mcgill.ecse321.cooperator.dto.CoopDto;
import ca.mcgill.ecse321.cooperator.dto.EmployerDto;
import ca.mcgill.ecse321.cooperator.dto.ReportDto;
import ca.mcgill.ecse321.cooperator.dto.NotificationDto;
import ca.mcgill.ecse321.cooperator.dto.ProfileDto;
import ca.mcgill.ecse321.cooperator.dto.StudentDto;
import ca.mcgill.ecse321.cooperator.model.Administrator;
import ca.mcgill.ecse321.cooperator.model.Coop;
import ca.mcgill.ecse321.cooperator.model.Employer;
import ca.mcgill.ecse321.cooperator.model.Report;
import ca.mcgill.ecse321.cooperator.model.Notification;
import ca.mcgill.ecse321.cooperator.model.Profile;
import ca.mcgill.ecse321.cooperator.model.Student;
import ca.mcgill.ecse321.cooperator.service.CooperatorService;

@CrossOrigin(origins = "*")
@RestController
public class CooperatorController {

	private CooperatorService service = new CooperatorService();
	
	@PostMapping(value = { "/student/{email}/{password}/{name}/{phone}/{studentId}", "/student/{email}/{password}/{name}/{phone}/{studentId}/" })
	public StudentDto createStudent(@PathVariable("email") String email, @PathVariable String password, @PathVariable String name, 
			@PathVariable String phone, @PathVariable Integer studentId) {
		Student student = service.createStudent(email, name, password, phone, studentId);
		return convertToDto(student);
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
	
	@GetMapping(value = { "/coops", "/coops/" })
	public List<CoopDto> getAllEvents() {
		List<CoopDto> coopDtos = new ArrayList<>();
		for (Coop coop : service.getAllCoops()) {
			coopDtos.add(convertToDto(coop));
		}
		return coopDtos;
	}

	
	@GetMapping(value = { "/reports/coop/student/{email}", "/reports/coop/student/{email}" })
	public List<ReportDto> getAllReportsofStudent(@PathVariable("email") StudentDto sDto){
		Student s = convertToDomainObject(sDto);
		List<ReportDto> reportDtos;
		List<Report> reports = null;
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
		List<Report> reports = c.getReport();
		reportDtos = convertToDto(reports);
		return reportDtos;
	}
 	
	private AdminDto convertToDto(Administrator a) {
		if (a == null) {
			throw new IllegalArgumentException("There is no such Admin!");
		}
		AdminDto adminDto = new AdminDto(a.getEmail(), a.getPassword(), a.getName(), a.getId(), a.getPhone(),
				createNotificationDtosForProfile(a), createNotificationDtosForProfile(a));
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
		CoopDto coopDto = new CoopDto(c.getId(),c.getTitle(),c.getStudent(), c.getEmployer(), c.getReport(), c.getStartDate(), c.getEndDate(), 
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
		EmployerDto employerDto = new EmployerDto(e.getEmail(), e.getPassword(), e.getName(), e.getId(), e.getPhone(), 
				createCoopDtosForEmployer(e), createNotificationDtosForProfile(e));
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
	
	private List<ReportDto> convertToDto(List<Report> r) {
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
		NotificationDto notificationDto = new NotificationDto(n.getId(), n.getText(), convertToDto(n.getProfile()), 
				convertToDto(n.getProfile1()));
		return notificationDto;
	}
	
	private ProfileDto convertToDto(Profile p) {
		if (p == null) {
			throw new IllegalArgumentException("There is no such Profile!");
		}
		ProfileDto profileDto = new ProfileDto(p.getEmail(), p.getPassword(), p.getName(), p.getPhone(), 
				createNotificationDtosForProfile(p), createNotificationDtosForProfile(p));
		return profileDto;
	}
	
	private Profile convertToDomainObject(ProfileDto pDto) {
		List<Profile> all = service.getAllProfiles();
		for (Profile p : all) {
			if (p.getName().equals(pDto.getName())) {
				return p;
			}
		}
		return null;
	}
	
	private StudentDto convertToDto(Student s) {
		if (s == null) {
			throw new IllegalArgumentException("There is no such Student!");
		}
		StudentDto studentDto = new StudentDto(s.getEmail(), s.getPassword(), s.getName(), s.getId(), s.getPhone(), 
				createCoopDtosForStudent(s), createNotificationDtosForProfile(s));
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
	
	
	private Set<NotificationDto> createNotificationDtosForProfile(Profile p) {
		Set<Notification> notificationsForProfile = service.getNotifications(p);
		Set<NotificationDto> notifications = new HashSet<>();
		for (Notification notification : notificationsForProfile) {
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
		List<Report> reportsForCoop = c.getReport();
		List<ReportDto> reports = new ArrayList<>();
		for (Report report : reportsForCoop){
			reports.add(convertToDto(report));
		}
		return reports;
	}
	

}