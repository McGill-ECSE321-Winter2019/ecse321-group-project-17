package ca.mcgill.ecse321.cooperator.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
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
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestPropertySource(locations="classpath:application-test.properties")
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

	@Before
	@After
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
		String phoneA = "6047862815";
		Administrator a;

		a = cs.createAdmin(emailA, nameA, passwordA, phoneA);

		String emailE = "emma.eagles@mail.mcgill.ca";
		String nameE = "Emma Eagles";
		String passwordE = "12341234";
		String phoneE = "254334";
		String companyE = "Lightspeed";
		Employer emp;

		emp = cs.createEmployer(emailE, nameE, passwordE, phoneE, companyE);

		String text = "this is a notification";
		String error = null;

		try {
			cs.createNotification(text, a, null, emp);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(error);

		assertEquals(1, cs.getAllNotifications().size());
		assertEquals(text, cs.getAllNotifications().get(0).getText());

		// Check to see if associated to employer
		assertEquals(1, cs.getNotificationsEmp(emp).size());

		// Check to see if associated to admin
		assertEquals(1, cs.getNotificationsAdm(a).size());

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
		String phoneA = "6047862815";
		Administrator adm;

		adm = cs.createAdmin(emailA, nameA, passwordA, phoneA);

		String text = "this is a notification";
		String error = null;

		try {
			cs.createNotification(text, adm, stu, null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(error);

		assertEquals(1, cs.getAllNotifications().size());
		assertEquals(text, cs.getAllNotifications().get(0).getText());

		// Check to see if associated to admin
		assertEquals(1, cs.getNotificationsAdm(adm).size());

		// Check to see if associated to student
		assertEquals(1, cs.getNotificationsStu(stu).size());
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
		String phoneA = "6047862815";
		Administrator adm;

		adm = cs.createAdmin(emailA, nameA, passwordA, phoneA);

		String emailE = "emma.eagles@mail.mcgill.ca";
		String nameE = "Emma Eagles";
		String passwordE = "12341234";
		String phoneE = "254334";
		String companyE = "Lightspeed";
		Employer emp;

		emp = cs.createEmployer(emailE, nameE, passwordE, phoneE, companyE);

		String text = "this is a notification";
		String error = null;
		Notification n;

		try {
			n = cs.createNotification(text, adm, stu, emp);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(error);

		assertEquals(1, cs.getAllNotifications().size());
		assertEquals(text, cs.getAllNotifications().get(0).getText());

		// Check to see if associated to employer
		assertEquals(1, cs.getNotificationsEmp(emp).size());

		// Check to see if associated to admin
		assertEquals(1, cs.getNotificationsAdm(adm).size());

		// Check to see if associated to student
		assertEquals(1, cs.getNotificationsStu(stu).size());
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
		String companyE = "Lightspeed";
		Employer emp;

		emp = cs.createEmployer(emailE, nameE, passwordE, phoneE, companyE);

		String text = "   ";
		String error = null;

		try {
			cs.createNotification(text, null, stu, emp);
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
		String phoneA = "6047862815";
		Administrator adm;

		adm = cs.createAdmin(emailA, nameA, passwordA, phoneA);

		String text = "this is a notification";
		String error = null;

		try {
			cs.createNotification(text, adm, null, null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertEquals("Notification needs at least one recipient! ", error);
		assertEquals(0, cs.getAllNotifications().size());
	}

	@Test
	public void testCreateNotificationNegative() {
		assertEquals(0, cs.getAllNotifications().size());

		String text = "    ";
		String error = null;

		try {
			cs.createNotification(text, null, null, null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		// check error
		assertEquals("Administrator is null! Notification needs at least one recipient! Text is invalid!", error);

		// check no change in memory
		assertEquals(0, cs.getAllNotifications().size());
	}

	@Test
	public void testGetNotifNullProfile() {
		assertEquals(0, cs.getAllNotifications().size());

		String error = "";
		// check employer
		try {
			cs.getNotificationsEmp(null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		// check error
		assertEquals("Profile cannot be null!", error);

		// check student
		try {
			cs.getNotificationsStu(null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		// check error
		assertEquals("Profile cannot be null!", error);

		// check admin
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
