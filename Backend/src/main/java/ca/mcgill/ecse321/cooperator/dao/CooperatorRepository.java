package ca.mcgill.ecse321.cooperator.dao;

import java.sql.Date;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.model.Administrator;
import ca.mcgill.ecse321.model.Coop;
import ca.mcgill.ecse321.model.Employer;
import ca.mcgill.ecse321.model.File;
import ca.mcgill.ecse321.model.Notification;
import ca.mcgill.ecse321.model.Profile;
import ca.mcgill.ecse321.model.Student;


@Repository
public class CooperatorRepository {

	@Autowired
	EntityManager entityManager;

	@Transactional
	public Profile createProfile(String email, String name, String password, String phone) {
		Profile p = new Profile();
		p.setEmail(email);
		p.setName(name);
		p.setPassword(password);
		p.setPhone(phone);
		entityManager.persist(p);
		return p;
	}
	
	@Transactional 
	public Profile getProfile(String email) {
		Profile p = entityManager.find(Profile.class, email);
		return p;
	}
	
	@Transactional 
	public Coop createCoop(Integer id, String title, Date startDate, Date endDate, Integer status, Integer salaryPerHour, Integer hoursPerWeek) {
		Coop c = new Coop();
		c.setId(id);
		c.setTitle(title);
		c.setStartDate(startDate);
		c.setEndDate(endDate);
		c.setStatus(status);
		c.setSalarayPerHour(salaryPerHour);
		c.setHoursPerWeek(hoursPerWeek);
		entityManager.persist(c);
		return c;
	}
	
	@Transactional 
	public Coop getCoop(Integer id) {
		Coop c = entityManager.find(Coop.class, id);
		return c;
	}
	
	@Transactional 
	public Student createStudent(Integer id, Integer status) {
		Student s = new Student();
		s.setId(id);
		s.setStatus(status);
		entityManager.persist(s);
		return s;
	}
	
	@Transactional 
	public Student getStudent(Integer id) {
		Student s = entityManager.find(Student.class, id);
		return s;
	}
	
	@Transactional 
	public Employer createEmployer(Integer id) {
		Employer e = new Employer();
		e.setId(id);
		entityManager.persist(e);
		return e;
	}
	
	@Transactional 
	public Employer getEmployer(Integer id) {
		Employer e = entityManager.find(Employer.class, id);
		return e;
	}
	
	@Transactional 
	public Administrator createAdministrator(Integer id) {
		Administrator a = new Administrator();
		a.setId(id);
		entityManager.persist(a);
		return a;
	}
	
	@Transactional 
	public Administrator getAdministrator(Integer id) {
		Administrator a = entityManager.find(Administrator.class, id);
		return a;
	}
	
	@Transactional  
	public Notification createNotification(Integer id, String text) {
		Notification n = new Notification();
		n.setId(id);
		n.setText(text);
		entityManager.persist(n);
		return n;
	}
	
	@Transactional  
	public Notification getNotification(Integer id) {
		Notification n = entityManager.find(Notification.class, id);
		return n;
	}
	
	@Transactional  
	public File createFile(Integer id) {
		File f = new File();
		f.setId(id);
		entityManager.persist(f);
		return f;
	}
	
	@Transactional  
	public File getFile(Integer id) {
		File f = entityManager.find(File.class, id);
		return f;
	}
	
}