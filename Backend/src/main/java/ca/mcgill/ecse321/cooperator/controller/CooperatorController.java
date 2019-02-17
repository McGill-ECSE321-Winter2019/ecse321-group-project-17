package ca.mcgill.ecse321.cooperator.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.cooperator.dto.AdminDto;
import ca.mcgill.ecse321.cooperator.dto.CoopDto;
import ca.mcgill.ecse321.cooperator.dto.EmployerDto;
import ca.mcgill.ecse321.cooperator.dto.FileDto;
import ca.mcgill.ecse321.cooperator.dto.NotificationDto;
import ca.mcgill.ecse321.cooperator.dto.ProfileDto;
import ca.mcgill.ecse321.cooperator.dto.StudentDto;
import ca.mcgill.ecse321.cooperator.model.Administrator;
import ca.mcgill.ecse321.cooperator.model.Coop;
import ca.mcgill.ecse321.cooperator.model.Employer;
import ca.mcgill.ecse321.cooperator.model.File;
import ca.mcgill.ecse321.cooperator.model.Notification;
import ca.mcgill.ecse321.cooperator.model.Profile;
import ca.mcgill.ecse321.cooperator.model.Student;
import ca.mcgill.ecse321.cooperator.service.CooperatorService;

@CrossOrigin(origins = "*")
@RestController
public class CooperatorController {

	private CooperatorService service;
	
	@PostMapping(value = { "/student/{email}/{password}/{name}/{phone}/{studentId}", "/student/{email}/{password}/{name}/{phone}/{studentId}/" })
	public StudentDto createStudent(@PathVariable("email") String email, @RequestParam String password, @RequestParam String name, 
			@RequestParam String phone, @RequestParam Integer studentId) {
		Student student = service.createStudent(email, name, password, phone, studentId);
		return convertToDto(student);
	}
	
	@GetMapping(value = { "/events", "/events/" })
	public List<StudentDto> getAllStudents() {
		List<StudentDto> studentDtos = new ArrayList<>();
		for (Student student : service.getAllStudents()) {
			studentDtos.add(convertToDto(student));
		}
		return studentDtos;
	}
	
	// REST route for Modify File
	// Not a true update method, this will create a new file for now
	@PostMapping(value = { "/file/{fileId}/{coopId}", "/file/{fileId}/{coopId}/" })
	public FileDto createFile(@PathVariable("fileId") Integer fileId, @PathVariable("coopId") Integer coopId) {
		Coop coop = service.getCoop(coopId);
		File newFile = service.createFile(fileId, coop);
		
		return convertToDto(newFile);
	}
	
	private AdminDto convertToDto(Administrator a) {
		if (a == null) {
			throw new IllegalArgumentException("There is no such Admin!");
		}
		AdminDto adminDto = new AdminDto(a.getEmail(), a.getPassword(), a.getName(), a.getId(), a.getPhone(),
				createNotificationDtosForProfile(a), createNotificationDtosForProfile(a));
		return adminDto;
	}
	
	private CoopDto convertToDto(Coop c) {
		if (c == null) {
			throw new IllegalArgumentException("There is no such Coop!");
		}
		CoopDto coopDto = new CoopDto(c.getId(),c.getTitle(),c.getStudent(), c.getEmployer(), c.getFile(), c.getStartDate(), c.getEndDate(), 
				c.getStatus(), c.getSalaryPerHour(), c.getHoursPerWeek(), c.getAddress());
		return coopDto;
	}
	
	private EmployerDto convertToDto(Employer e) {
		if (e == null) {
			throw new IllegalArgumentException("There is no such Employer!");
		}
		EmployerDto employerDto = new EmployerDto(e.getEmail(), e.getPassword(), e.getName(), e.getId(), e.getPhone(), 
				createCoopDtosForEmployer(e), createNotificationDtosForProfile(e));
		return employerDto;
	}
	
	private FileDto convertToDto(File f) {
		if (f == null) {
			throw new IllegalArgumentException("There is no such File!");
		}
		FileDto fileDto = new FileDto(f.getId(), convertToDto(f.getCoop()));
		return fileDto;
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
	
	private StudentDto convertToDto(Student s) {
		if (s == null) {
			throw new IllegalArgumentException("There is no such Student!");
		}
		StudentDto studentDto = new StudentDto(s.getEmail(), s.getPassword(), s.getName(), s.getId(), s.getPhone(), 
				createCoopDtosForStudent(s), createNotificationDtosForProfile(s));
		return studentDto;
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

}