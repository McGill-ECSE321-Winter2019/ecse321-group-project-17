package ca.mcgill.ecse321.cooperator.service;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ca.mcgill.ecse321.cooperator.dao.AdministratorRepository;
import ca.mcgill.ecse321.cooperator.dao.CoopRepository;
import ca.mcgill.ecse321.cooperator.dao.EmployerRepository;
import ca.mcgill.ecse321.cooperator.dao.NotificationRepository;
import ca.mcgill.ecse321.cooperator.dao.ProfileRepository;
import ca.mcgill.ecse321.cooperator.dao.ReportRepository;
import ca.mcgill.ecse321.cooperator.dao.StudentRepository;
import ca.mcgill.ecse321.cooperator.model.Administrator;
import ca.mcgill.ecse321.cooperator.model.Employer;
import ca.mcgill.ecse321.cooperator.model.Notification;
import ca.mcgill.ecse321.cooperator.model.Student;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestCooperatorServiceNotification {
	@Autowired
	protected CooperatorService cs;
	
	@Autowired
	private AdministratorRepository administratorRepository;
	@Autowired
	private CoopRepository coopRepository;
	@Autowired
	private EmployerRepository employerRepository;
	@Autowired
	private NotificationRepository notificationRepository;
	@Autowired
	private ProfileRepository profileRepository;
	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private ReportRepository reportRepository;

	@Before @After
	public void clearDatabase() {
		notificationRepository.deleteAll();
		coopRepository.deleteAll();
		studentRepository.deleteAll();
		administratorRepository.deleteAll();
		employerRepository.deleteAll();
		profileRepository.deleteAll();
		reportRepository.deleteAll();
	}
	
	@Test
	public void testCreateNotificationEmployer() {
		assertEquals(0, cs.getAllNotifications().size());
		
		String emailA = "paul.hooley@gmail.com";
		String nameA = "qwefqwefq";
		String passwordA = "frisbyislife";
		int idA = 3;
		String phoneA = "6047862815";
		Administrator a;
		
		a = cs.createAdmin(emailA, nameA, passwordA, phoneA, idA);
		
		String emailE = "emma.eagles@mail.mcgill.ca";
		String nameE = "Emma Eagles";
		String passwordE = "12341234";
		String phoneE = "254334";
		int idE = 31231234;
		Employer emp;
		
		emp = cs.createEmployer(emailE, nameE, passwordE, phoneE, idE);
		
		Integer id = 34;
		String text = "this is a notification";
		String error = null;
	
		try {
			cs.createNotification(id, text, a, null, emp);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertEquals(null, error);
		assertEquals(1, cs.getAllNotifications().size());
		assertEquals(id, cs.getAllNotifications().get(0).getId());
		
		//Check to see if associated to employer
		Set<Notification> notif = cs.getNotificationsEmp(emp);
		assertEquals(1, notif.size());
		
		//Check to see if associated to admin
		notif = cs.getNotificationsAdm(a);
		assertEquals(1, notif.size());
		
	}
	
	@Test
	public void testCreateNotificationStudent() {
		assertEquals(0, cs.getAllNotifications().size());
		
		String emailS = "susan@gmail.com";
		String nameS = "susan";
		String passwordS = "iloveC";
		int idS = 3354;
		String phoneS = "6043242815";
		Student stu;
		stu = cs.createStudent(emailS, nameS, passwordS, phoneS, idS);
		
		String emailA = "paul.hooley@gmail.com";
		String nameA = "qwefqwefq";
		String passwordA = "frisbyislife";
		int idA = 3;
		String phoneA = "6047862815";
		Administrator adm;
		
		adm = cs.createAdmin(emailA, nameA, passwordA, phoneA, idA);
		
		Integer id = 34;
		String text = "this is a notification";
		String error = null;
	
		try {
			cs.createNotification(id, text, adm, stu, null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertEquals(null, error);
		assertEquals(1, cs.getAllNotifications().size());
		assertEquals(id, cs.getAllNotifications().get(0).getId());
		
		//Check to see if associated to employer
		Set<Notification> notif = cs.getNotificationsAdm(adm);
		assertEquals(1, notif.size());
		
		//Check to see if not associated to student
		notif = cs.getNotificationsStu(stu);
		assertEquals(0, notif.size());
	}
	
	@Test
	public void testCreateNotificationBoth() {
		assertEquals(0, cs.getAllNotifications().size());
		
		String emailS = "susan@gmail.com";
		String nameS = "susan";
		String passwordS = "iloveC";
		int idS = 3354;
		String phoneS = "6043242815";
		Student stu;
		stu = cs.createStudent(emailS, nameS, passwordS, phoneS, idS);
		
		String emailA = "paul.hooley@gmail.com";
		String nameA = "qwefqwefq";
		String passwordA = "frisbyislife";
		int idA = 3;
		String phoneA = "6047862815";
		Administrator adm;
		
		adm = cs.createAdmin(emailA, nameA, passwordA, phoneA, idA);
		
		String emailE = "emma.eagles@mail.mcgill.ca";
		String nameE = "Emma Eagles";
		String passwordE = "12341234";
		String phoneE = "254334";
		int idE = 31231234;
		Employer emp;
		
		emp = cs.createEmployer(emailE, nameE, passwordE, phoneE, idE);
		
		Integer id = 34;
		String text = "this is a notification";
		String error = null;
		Notification n;
	
		try {
			n = cs.createNotification(id, text, adm, stu, emp);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertEquals(null, error);

		assertEquals(1, cs.getAllNotifications().size());
		assertEquals(id, cs.getAllNotifications().get(0).getId());
		//Check to see if associated to employer
		Set<Notification> notif = cs.getNotificationsEmp(emp);
		assertEquals(1, notif.size());
		
		//Check to see if associated to admin
		notif = cs.getNotificationsAdm(adm);
		assertEquals(1, notif.size());
		
		//Check to see if not associated to student
		notif = cs.getNotificationsStu(stu);
		assertEquals(0, notif.size());
	}
	
	@Test
	public void testCreateNotificationAdminNull() {
		assertEquals(0, cs.getAllNotifications().size());
		
		String emailS = "susan@gmail.com";
		String nameS = "susan";
		String passwordS = "iloveC";
		int idS = 3354;
		String phoneS = "6043242815";
		Student stu;
		stu = cs.createStudent(emailS, nameS, passwordS, phoneS, idS);
		
		String emailE = "emma.eagles@mail.mcgill.ca";
		String nameE = "Emma Eagles";
		String passwordE = "12341234";
		String phoneE = "254334";
		int idE = 31231234;
		Employer emp;
		
		emp = cs.createEmployer(emailE, nameE, passwordE, phoneE, idE);
		
		Integer id = 34;
		String text = "   ";
		String error = null;
	
		try {
			cs.createNotification(id, text, null, stu, emp);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertEquals("Administrator is null! Text is invalid!", error);
		assertEquals(0, cs.getAllNotifications().size());
	}
	
	@Test
	public void testCreateNotificationBothNull() {
		assertEquals(0, cs.getAllNotifications().size());
		
		String emailA = "paul.hooley@gmail.com";
		String nameA = "qwefqwefq";
		String passwordA = "frisbyislife";
		int idA = 3;
		String phoneA = "6047862815";
		Administrator adm;
		
		adm = cs.createAdmin(emailA, nameA, passwordA, phoneA, idA);
		
		Integer id = 34;
		String text = "this is a notification";
		String error = null;
	
		try {
			cs.createNotification(id, text, adm, null, null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertEquals("Notification needs at least one recipient! ", error);
		assertEquals(0, cs.getAllNotifications().size());	
	}
	
	@Test
	public void testCreateNotificationNegative() {
		assertEquals(0, cs.getAllNotifications().size());

		int id = -1;
		String text = "    ";
		String error = null;
	
		try {
			cs.createNotification(id, text, null, null, null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		// check error
		assertEquals("Profile1 is null! Profile2 is null! ID is invalid! Text is invalid!", error);

		// check no change in memory
		assertEquals(0, cs.getAllNotifications().size());
	}
	
	@Test
	public void testGetNotifNullProfile() {
		assertEquals(0, cs.getAllNotifications().size());
	
		String error = "";
		//check employer
		try {
			cs.getNotificationsEmp(null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		// check error
		assertEquals("Profile cannot be null!", error);
		
		//check student
		try {
			cs.getNotificationsStu(null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		// check error
		assertEquals("Profile cannot be null!", error);
		
		//check admin
		try {
			cs.getNotificationsAdm(null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		// check error
		assertEquals("Profile cannot be null!", error);

		// check no change in memory
		assertEquals(0, cs.getAllNotifications().size());
	}
	
	

}
