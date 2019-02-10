package ca.mcgill.ecse321.cooperator.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ca.mcgill.ecse321.cooperator.dao.AdministratorRepository;
import ca.mcgill.ecse321.cooperator.dao.CoopRepository;
import ca.mcgill.ecse321.cooperator.dao.EmployerRepository;
import ca.mcgill.ecse321.cooperator.dao.FileRepository;
import ca.mcgill.ecse321.cooperator.dao.NotificationRepository;
import ca.mcgill.ecse321.cooperator.dao.ProfileRepository;
import ca.mcgill.ecse321.cooperator.dao.StudentRepository;
import ca.mcgill.ecse321.cooperator.model.Coop;
import ca.mcgill.ecse321.cooperator.model.Employer;
import ca.mcgill.ecse321.cooperator.model.Profile;
import ca.mcgill.ecse321.cooperator.model.Student;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestCooperatorService {

	@Autowired
	private CooperatorService cs;
	
	@Autowired
	private AdministratorRepository administratorRepository;
	@Autowired
	private CoopRepository coopRepository;
	@Autowired
	private EmployerRepository employerRepository;
	@Autowired
	private FileRepository fileRepository;
	@Autowired
	private NotificationRepository notificationRepository;
	@Autowired
	private ProfileRepository profileRepository;
	@Autowired
	private StudentRepository studentRepository;

	@Before
	public void clearDatabase() {
		coopRepository.deleteAll();
		studentRepository.deleteAll();
		administratorRepository.deleteAll();
		employerRepository.deleteAll();
		profileRepository.deleteAll();
		fileRepository.deleteAll();
		notificationRepository.deleteAll();
	}
	
	@Test
	public void testCreateStudent() {
		assertEquals(0, cs.getAllProfiles().size());

		String email = "paul.hooley@gmail.com";
		String name = "Paul Hooley";
		String password = "frisbyislife";
		int id = 3;
		String phone = "6047862815";

		try {
			cs.createStudent(email, name, password, phone, id);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		List<Profile> allProfiles = cs.getAllProfiles();

		assertEquals(1, allProfiles.size());
		assertEquals(name, allProfiles.get(0).getName());
	}

	
	@Test
	public void testCreateStudentNull() {
		assertEquals(0, cs.getAllProfiles().size());
		
		String email = null;
		String name = null;
		String password = null;
		String phone = null;
		int id = 0;
		String error = null;

		try {
			cs.createStudent
			(email, name, password, phone, id);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		// check error
		assertEquals("Profile name cannot be empty! Email cannot be empty! "
				+"Password cannot be empty! Phone cannot be empty!", error);

		// check no change in memory
		assertEquals(0, cs.getAllProfiles().size());

	}

	@Test
	public void testCreateStudentEmpty() {
		assertEquals(0, cs.getAllProfiles().size());

		String email = "";
		String name = "";
		String password = "";
		String phone = "";
		int id = 0;
		String error = null;

		try {
			cs.createStudent(email, name, password, phone, id);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		// check error
		assertEquals("Profile name cannot be empty! Email cannot be empty! "
				+"Password cannot be empty! Phone cannot be empty!", error);


		// check no change in memory
		assertEquals(0, cs.getAllProfiles().size());

	}

	@Test
	public void testCreateStudentSpaces() {
		assertEquals(0, cs.getAllProfiles().size());

		String email = " ";
		String name = " ";
		String password = " ";
		String phone = " ";
		int id = -1;
		String error = null;
	
		try {
			cs.createStudent(email, name, password, phone, id);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		// check error
		assertEquals("Profile name cannot be empty! Email cannot be empty! "
				+"Password cannot be empty! Phone cannot be empty! ID is invalid!", error);

		// check no change in memory
		assertEquals(0, cs.getAllProfiles().size());

	}

	@Test
	public void testCreateCoop() {
		assertEquals(0, cs.getAllCoops().size());
		
		Integer id = 0;
		String title = "NASA";
		Date startDate = Date.valueOf("2019-01-01");
		Date endDate = Date.valueOf("2019-04-30");
		Integer status = 0;
		Integer salaryPerHour = 19;
		Integer hoursPerWeek = 40;

		try {
			cs.createCoop(id, title, startDate, endDate, status, salaryPerHour, hoursPerWeek);
		} catch (IllegalArgumentException e) {
			fail();
		}

		checkResultCoop(id, title, startDate, endDate, status, salaryPerHour, hoursPerWeek);
	}

	private void checkResultCoop(Integer id, String title, Date startDate, Date endDate, Integer status, Integer salaryPerHour, Integer hoursPerWeek){
		assertEquals(0, cs.getAllProfiles().size());
		assertEquals(1, cs.getAllCoops().size());
		assertEquals(id, cs.getAllCoops().get(0).getId());
		assertEquals(title, cs.getAllCoops().get(0).getTitle());
		assertEquals(startDate, cs.getAllCoops().get(0).getStartDate());
		assertEquals(endDate, cs.getAllCoops().get(0).getEndDate());
		assertEquals(status, cs.getAllCoops().get(0).getStatus());
		assertEquals(salaryPerHour, cs.getAllCoops().get(0).getSalarayPerHour());
		assertEquals(hoursPerWeek, cs.getAllCoops().get(0).getHoursPerWeek());
		assertEquals(0, cs.getAllFiles().size());
	}

	
	@Test
	public void testRegister() {
		assertEquals(0, cs.getAllCoops().size());
		
		
		
		Integer studentID = 260706395;
		Integer studentStatus = 0;
		
		Student Student = cs.createStudent(studentID, studentStatus);
		
		assertEquals(1, cs.getAllProfiles().size());

		Integer employerID = 23;
		
		Employer Employer = cs.createEmployer(employerID);
		assertEquals(1, cs.getAllEmployers().size());

		try {
			cs.register(Student, Employer);
		} catch (IllegalArgumentException e) {
			fail();
		}

		checkResultCoop(studentID, studentStatus, employerID);
	}

	private void checkResultCoop(Integer studentID, Integer studentStatus, Integer employerID) {
		assertEquals(1, cs.getAllStudents().size());
		assertEquals(studentID, cs.getAllStudents().get(0).getId());
		assertEquals(1, cs.getAllEmployers().size());
		assertEquals(employerID, cs.getAllEmployers().get(0).getId());
		assertEquals(studentStatus, cs.getAllCoops().get(0).getStudent().getStatus());
	}


	@Test
	public void testcreateCoopNull() {
		assertEquals(0, cs.getAllCoops().size());

		Integer id = null;
		String title = null;
		Date startDate = null;
		Date endDate = null;
		Integer status = null;
		Integer salaryPerHour = null;
		Integer hoursPerWeek = null;

		String error = null;
		try {
			cs.createCoop(id, title, startDate, endDate, status, salaryPerHour, hoursPerWeek);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		// check error
		assertEquals(
				"Coop id cannot be empty! Coop title cannot be empty! Coop start date cannot be empty! Coop end date"
				+ " cannot be empty! Coop status cannot be empty! Coop salaryPerHour cannot be empty! Coop "
				+ "hoursPerWeek cannot be empty!",
				error);
		// check model in memory
		assertEquals(0, cs.getAllCoops().size());
	}

	@Test
	public void testcreateCoopEmpty() {
		assertEquals(0, cs.getAllCoops().size());

		Integer id = 0;
		String title = "";
		Date startDate = Date.valueOf("2019-01-01");
		Date endDate = Date.valueOf("2019-04-30");
		Integer status = 0;
		Integer salaryPerHour = 19;
		Integer hoursPerWeek = 40;

		String error = null;
		try {
			cs.createCoop(id, title, startDate, endDate, status, salaryPerHour, hoursPerWeek);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		// check error
		assertEquals("Coop title cannot be empty! ", error);
		// check model in memory
		assertEquals(0, cs.getAllCoops().size());
	}

	@Test
	public void testcreateCoopSpaces() {
		assertEquals(0, cs.getAllCoops().size());

		Integer id = 0;
		String title = " ";
		Date startDate = Date.valueOf("2019-01-01");
		Date endDate = Date.valueOf("2019-04-30");
		Integer status = 0;
		Integer salaryPerHour = 19;
		Integer hoursPerWeek = 40;

		String error = null;
		try {
			cs.createCoop(id, title, startDate, endDate, status, salaryPerHour, hoursPerWeek);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		// check error
		assertEquals("Coop title cannot be empty! ", error);
		// check model in memory
		assertEquals(0, cs.getAllCoops().size());

	}

	@Test
	public void testcreateCoopEndDateBeforeStartDate() {
		assertEquals(0, cs.getAllCoops().size());

		Integer id = 0;
		String title = "NASA";
		Date startDate = Date.valueOf("2019-04-30");
		Date endDate = Date.valueOf("2019-01-01");
		Integer status = 0;
		Integer salaryPerHour = 19;
		Integer hoursPerWeek = 40;

		String error = null;
		try {
			cs.createCoop(id, title, startDate, endDate, status, salaryPerHour, hoursPerWeek);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		// check error
		assertEquals("Coop end time cannot be before Coop start time!", error);

		// check model in memory
		assertEquals(0, cs.getAllCoops().size());
	}

	@Test
	public void testRegisterNull() {
		assertEquals(0, cs.getAllCoops().size());

		Student student = null;
		assertEquals(0, cs.getAllStudents().size());

		Employer employer = null;
		assertEquals(0, cs.getAllEmployers().size());

		String error = null;
		try {
			cs.register(student, employer);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		// check error
		assertEquals("Student needs to be selected for registration! Employer needs to be selected for registration!",
				error);

		// check model in memory
		assertEquals(0, cs.getAllCoops().size());
		assertEquals(0, cs.getAllStudents().size());
		assertEquals(0, cs.getAllEmployers().size());
	}

	@Test
	public void testRegisterProfileAndCoopDoNotExist() {
		assertEquals(0, cs.getAllCoops().size());

		String email = "paul.hooley@gmail.com";
		String name = "Paul Hooley";
		String password = "frisbyislife";
		String phone = "6047862815";
		
		Student Student = new Student();
		Student.setName(name);
		Student.setEmail(email);
		Student.setPassword(password);
		Student.setPhone(phone);
		assertEquals(0, cs.getAllStudents().size());

		Employer Employer = new Employer();
		Employer.setId(23);;
		assertEquals(0, cs.getAllEmployers().size());

		String error = null;
		try {
			cs.register(Student, Employer);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		// check error
		assertEquals("Student does not exist! Employer does not exist!", error);

		// check model in memory
		assertEquals(0, cs.getAllCoops().size());
		assertEquals(0, cs.getAllProfiles().size());
		assertEquals(0, cs.getAllCoops().size());
	}
	
	@Test
	public void testCreateProfileNoPhone() {
		assertEquals(0, cs.getAllProfiles().size());

		String email = "emma.eagles@mail.mcgill.ca ";
		String name = "Emma Eagles";
		String password = "12341234";
		String phone = " ";
		int id = 31231234;
		String error = null;
	
		try {
			cs.createStudent(email, name, password, phone, id);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		// check error
		assertEquals("Phone cannot be empty!", error);

		// check no change in memory
		assertEquals(0, cs.getAllProfiles().size());
	}
	@Test
	public void testCreateProfileNoEmail() {
		assertEquals(0, cs.getAllProfiles().size());

		String email = "";
		String name = "Emma Eagles";
		String password = "12341234";
		String phone = "5061231234";
		int id = 31231234;
		String error = null;
	
		try {
			cs.createStudent(email, name, password, phone, id);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		// check error
		assertEquals("Email cannot be empty! ", error);

		// check no change in memory
		assertEquals(0, cs.getAllProfiles().size());
	}
	@Test
	public void testCreateFileNegative() {
		assertEquals(0, cs.getAllFiles().size());

		int id = -1;
		String error = null;
	
		try {
			cs.createFile(id);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		// check error
		assertEquals("ID is invalid!", error);

		// check no change in memory
		assertEquals(0, cs.getAllFiles().size());
	}
	
	@Test
	public void testCreateNotificationNegative() {
		assertEquals(0, cs.getAllNotifications().size());

		int id = -1;
		String text = "    ";
		String error = null;
	
		try {
			cs.createNotification(id, text);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		// check error
		assertEquals("ID is invalid! Text is invalid!", error);

		// check no change in memory
		assertEquals(0, cs.getAllNotifications().size());
	}
	@Test
	public void testCreateEmployerNegative() {
		assertEquals(0, cs.getAllEmployers().size());

		String email = "";
		String name = "Emma Eagles";
		String password = "12341234";
		String phone = "5061231234";
		int id = 31231234;
		String error = null;
	
		try {
			cs.createEmployer(email, name, password, phone, id);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		// check error
		assertEquals("ID is invalid!", error);

		// check no change in memory
		assertEquals(0, cs.getAllEmployers().size());
	}
}