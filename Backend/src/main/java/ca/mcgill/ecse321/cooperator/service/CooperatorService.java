package ca.mcgill.ecse321.cooperator.service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.cooperator.dao.AdministratorRepository;
import ca.mcgill.ecse321.cooperator.dao.CoopRepository;
import ca.mcgill.ecse321.cooperator.dao.EmployerRepository;
import ca.mcgill.ecse321.cooperator.dao.FileRepository;
import ca.mcgill.ecse321.cooperator.dao.NotificationRepository;
import ca.mcgill.ecse321.cooperator.dao.ProfileRepository;
import ca.mcgill.ecse321.cooperator.dao.StudentRepository;
import ca.mcgill.ecse321.model.Administrator;
import ca.mcgill.ecse321.model.Coop;
import ca.mcgill.ecse321.model.Employer;
import ca.mcgill.ecse321.model.File;
import ca.mcgill.ecse321.model.Notification;
import ca.mcgill.ecse321.model.Profile;
import ca.mcgill.ecse321.model.Student;

@Service
public class CooperatorService {

	@Autowired
	CoopRepository coopRepository;
	@Autowired
	EmployerRepository employerRepository;
	@Autowired
	FileRepository fileRepository;
	@Autowired
	StudentRepository studentRepository;
	@Autowired
	ProfileRepository profileRepository;
	@Autowired
	NotificationRepository notificationRepository;
	@Autowired
	AdministratorRepository administratorRepository;

	@Transactional
	public Profile createProfile(String email, String name, String password, String phone) {
		Profile p = new Profile();
		p.setEmail(email);
		p.setName(name);
		p.setPassword(password);
		p.setPhone(phone);
		profileRepository.save(p);
		return p;
	}
	
	@Transactional 
	public Profile getProfile(String email) {
		Profile p = profileRepository.findProfileByEmail(email);
		return p;
	}
	
	@Transactional 
	public Coop createCoop(Integer id, String title, Date startDate, Date endDate, Integer status, Integer salaryPerHour, Integer hoursPerWeek, Employer employer) {
		Coop c = new Coop();
		c.setId(id);
		c.setTitle(title);
		c.setStartDate(startDate);
		c.setEndDate(endDate);
		c.setStatus(status);
		c.setSalarayPerHour(salaryPerHour);
		c.setHoursPerWeek(hoursPerWeek);
		c.setEmployer(employer);
		coopRepository.save(c);
		return c;
	}
	
	@Transactional 
	public Coop getCoop(Employer employer) {
		Coop c = coopRepository.findCoopByEmployer(employer);
		return c;
	}
	
	@Transactional 
	public Student createStudent(Integer id, Integer status) {
		Student s = new Student();
		//s.setId(id);
		s.setStatus(status);
		studentRepository.save(s);
		return s;
	}
	
	@Transactional 
	public Student getStudent(String name) {
		Student s = studentRepository.findStudentByName(name);
		return s;
	}
	
	@Transactional 
	public Employer createEmployer(Integer id) {
		Employer e = new Employer();
		//e.setId(id);
		employerRepository.save(e);
		return e;
	}
	
	@Transactional 
	public Employer getEmployer(String name) {
		Employer e = employerRepository.findEmployerByName(name);
		return e;
	}
	
	@Transactional 
	public Administrator createAdministrator(Integer id) {
		Administrator a = new Administrator();
		//a.setId(id);
		administratorRepository.save(a);
		return a;
	}
	
	@Transactional 
	public Optional<Administrator> getAdministrator(Integer id) {
		Optional<Administrator> a = administratorRepository.findById(id);
		return a;
	}
	
	@Transactional  
	public Notification createNotification(Integer id, String text) {
		Notification n = new Notification();
		n.setId(id);
		n.setText(text);
		notificationRepository.save(n);
		return n;
	}
	
	@Transactional  
	public List<Notification> getNotifications(Profile profile) {
		List<Notification> n = notificationRepository.findByProfile(profile);
		return n;
	}
	
	@Transactional  
	public File createFile(Integer id) {
		File f = new File();
		f.setId(id);
		fileRepository.save(f);
		return f;
	}
	
	@Transactional  
	public Optional<File> getFile(Integer id) {
		Optional<File> f = fileRepository.findById(id);
		return f;
	}
	
}