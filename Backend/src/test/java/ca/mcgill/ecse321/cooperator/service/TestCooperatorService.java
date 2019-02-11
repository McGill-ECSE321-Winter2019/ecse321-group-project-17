package ca.mcgill.ecse321.cooperator.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

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
import ca.mcgill.ecse321.cooperator.dao.FileRepository;
import ca.mcgill.ecse321.cooperator.dao.NotificationRepository;
import ca.mcgill.ecse321.cooperator.dao.ProfileRepository;
import ca.mcgill.ecse321.cooperator.dao.StudentRepository;
import ca.mcgill.ecse321.cooperator.model.Administrator;
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
	@After
	public void clearDatabase() {
		fileRepository.deleteAll();
		notificationRepository.deleteAll();
		coopRepository.deleteAll();
		studentRepository.deleteAll();
		administratorRepository.deleteAll();
		employerRepository.deleteAll();
		profileRepository.deleteAll();
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

		List<Student> allStudents = cs.getAllStudents();

		assertEquals(1, allStudents.size());
		assertEquals(name, allStudents.get(0).getName());
		
		//assertEquals("Paul Hooley", studentRepository.findStudentByName(name));
	}

	
	@Test
	public void testCreateStudentNull() {
		clearDatabase();
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
		assertEquals("Student name cannot be empty! Email cannot be empty! "
				+"Password cannot be empty! Phone cannot be empty! ", error);

		// check no change in memory
		assertEquals(0, cs.getAllProfiles().size());
		
	}

	@Test
	public void testCreateStudentEmpty() {
		clearDatabase();
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
		assertEquals("Student name cannot be empty! Email cannot be empty! "
				+"Password cannot be empty! Phone cannot be empty! ", error);


		// check no change in memory
		assertEquals(0, cs.getAllProfiles().size());

	}

	@Test
	public void testCreateStudentSpaces() {
		clearDatabase();
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
		assertEquals("Student name cannot be empty! Email cannot be empty! "
				+"Password cannot be empty! Phone cannot be empty! ID is invalid!", error);

		// check no change in memory
		assertEquals(0, cs.getAllProfiles().size());

	}
	
	@Test
	public void testCreateEmployer() {
		clearDatabase();
		assertEquals(0, cs.getAllProfiles().size());

		String email = "paul.hooley@gmail.com";
		String name = "Paul Hooley";
		String password = "frisbyislife";
		int id = 3;
		String phone = "6047862815";

		try {
			cs.createEmployer(email, name, password, phone, id);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		List<Employer> allEmployers = cs.getAllEmployers();

		assertEquals(1, allEmployers.size());
		assertEquals(name, allEmployers.get(0).getName());
		 
		assertEquals("Paul Hooley", employerRepository.findEmployerByName(name).getName());
		assertEquals(null, employerRepository.findEmployerByName("Emma Eagles"));
		
	}

	
	@Test
	public void testCreateEmployerNull() {
		clearDatabase();
		assertEquals(0, cs.getAllProfiles().size());
		
		String email = null;
		String name = null;
		String password = null;
		String phone = null;
		int id = 0;
		String error = null;

		try {
			cs.createEmployer(email, name, password, phone, id);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		// check error
		assertEquals("Employer name cannot be empty! Email cannot be empty! "
				+"Password cannot be empty! Phone cannot be empty! ", error);

		// check no change in memory
		assertEquals(0, cs.getAllProfiles().size());

	}

	@Test
	public void testCreateEmployerEmpty() {
		clearDatabase();
		assertEquals(0, cs.getAllProfiles().size());

		String email = "";
		String name = "";
		String password = "";
		String phone = "";
		int id = 0;
		String error = null;

		try {
			cs.createEmployer(email, name, password, phone, id);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		// check error
		assertEquals("Employer name cannot be empty! Email cannot be empty! "
				+"Password cannot be empty! Phone cannot be empty! ", error);


		// check no change in memory
		assertEquals(0, cs.getAllProfiles().size());

	}

	@Test
	public void testCreateEmployerSpaces() {
		clearDatabase();
		assertEquals(0, cs.getAllProfiles().size());

		String email = " ";
		String name = " ";
		String password = " ";
		String phone = " ";
		int id = -1;
		String error = null;
	
		try {
			cs.createEmployer(email, name, password, phone, id);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		// check error
		assertEquals("Employer name cannot be empty! Email cannot be empty! "
				+"Password cannot be empty! Phone cannot be empty! ID is invalid!", error);

		// check no change in memory
		assertEquals(0, cs.getAllProfiles().size());
	}
	
	@Test
	public void testCreateAdmin() {
		clearDatabase();
		assertEquals(0, cs.getAllProfiles().size());

		String email = "paul.hooley@gmail.com";
		String name = "Paul Hooley";
		String password = "frisbyislife";
		int id = 3;
		String phone = "6047862815";

		try {
			cs.createAdmin(email, name, password, phone, id);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		List<Administrator> allAdmins = cs.getAllAdministrators();

		assertEquals(1, allAdmins.size());
		assertEquals(name, allAdmins.get(0).getName());
		
		assertEquals("Paul Hooley", administratorRepository.findAdministratorByName(name).getName());
		assertEquals(null, administratorRepository.findAdministratorByName("Albert Kragl"));
		
	}
	
	@Test
	public void testCreateAdminNull() {
		clearDatabase();
		assertEquals(0, cs.getAllProfiles().size());
		
		String email = null;
		String name = null;
		String password = null;
		String phone = null;
		int id = 1;
		String error = null;

		try {
			cs.createAdmin(email, name, password, phone, id);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		// check error
		assertEquals("Administrator name cannot be empty! Email cannot be empty! "
				+"Password cannot be empty! Phone cannot be empty! ", error);

		// check no change in memory
		assertEquals(0, cs.getAllProfiles().size());

	}

	@Test
	public void testCreateAdminEmpty() {
		clearDatabase();
		assertEquals(0, cs.getAllProfiles().size());

		String email = "";
		String name = "";
		String password = "";
		String phone = "";
		int id = 0;
		String error = null;

		try {
			cs.createAdmin(email, name, password, phone, id);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		// check error
		assertEquals("Administrator name cannot be empty! Email cannot be empty! "
				+"Password cannot be empty! Phone cannot be empty! ", error);

		// check no change in memory
		assertEquals(0, cs.getAllProfiles().size());

	}

	@Test
	public void testCreateAdminSpaces() {
		clearDatabase();
		assertEquals(0, cs.getAllProfiles().size());

		String email = " ";
		String name = " ";
		String password = " ";
		String phone = " ";
		int id = -1;
		String error = null;
	
		try {
			cs.createAdmin(email, name, password, phone, id);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		// check error
		assertEquals("Administrator name cannot be empty! Email cannot be empty! "
				+"Password cannot be empty! Phone cannot be empty! ID is invalid!", error);

		// check no change in memory
		assertEquals(0, cs.getAllProfiles().size());

	}
	
	@Test
	public void testCreateCoop() {
		clearDatabase();
		assertEquals(0, cs.getAllCoops().size());		
		assertEquals(0, cs.getAllProfiles().size());
	
		String emailS = "paul.hooley@gmail.com";
		String nameS = "qwefqwefq";
		String passwordS = "frisbyislife";
		int idS = 3;
		String phoneS = "6047862815";
		Student s;
		
		s = cs.createStudent(emailS, nameS, passwordS, phoneS, idS);
		
		String emailE = "emma.eagles@mail.mcgill.ca";
		String nameE = "Emma Eagles";
		String passwordE = "12341234";
		String phoneE = "254334";
		int idE = 31231234;
		Employer emp;
		String error = "";
		
		emp = cs.createEmployer(emailE, nameE, passwordE, phoneE, idE);
		
		String title = "Developer";
		Date startDate = Date.valueOf("2019-01-01");
		Date endDate = Date.valueOf("2019-04-30");
		Integer status = 0;
		Integer salaryPerHour = 19;
		Integer hoursPerWeek = 40;
		Integer id = 45;
		
		try {
			cs.createCoop(s, emp, title, id, startDate, endDate, status, salaryPerHour, hoursPerWeek);
		} catch(Exception e) {
			error = e.getMessage();
		}
		
		assertEquals(1, cs.getAllCoops().size());
		checkResultCoop(idS, idE, title, startDate, endDate, status, salaryPerHour, hoursPerWeek);
	}

	private void checkResultCoop(Integer studentID, Integer employerID, String title,
			Date startDate, Date endDate, Integer status, Integer salaryPerHour, Integer hoursPerWeek) {
		List<Student> allStudents = cs.getAllStudents();
		List<Employer> allEmployers = cs.getAllEmployers();
		List<Coop> allCoops = cs.getAllCoops();
		
		assertEquals(1, allStudents.size());
//		assertEquals(studentID, allStudents.get(0).getId());
		
		assertEquals(1, allEmployers.size());
//		assertEquals(employerID, allEmployers.get(0).getId());
//		
		assertEquals(1, allCoops.size());
		assertEquals(title, allCoops.get(0).getTitle());
		assertEquals(startDate, allCoops.get(0).getStartDate());
		assertEquals(endDate, allCoops.get(0).getEndDate());
		assertEquals(status, allCoops.get(0).getStatus());
		assertEquals(salaryPerHour, allCoops.get(0).getSalarayPerHour());
		assertEquals(hoursPerWeek, allCoops.get(0).getHoursPerWeek());
		
		assertEquals(0, cs.getAllFiles().size()); 
		
	}

	@Test
	public void testcreateCoopNullStudentEmployer() {
		clearDatabase();
		assertEquals(0, cs.getAllCoops().size());

		String title = "Developer";
		Date startDate = Date.valueOf("2019-01-01");
		Date endDate = Date.valueOf("2019-04-30");
		Integer status = 0;
		Integer salaryPerHour = 19;
		Integer hoursPerWeek = 40;
		Integer id = 34;
		String error = null;
		
		try {
			cs.createCoop(null, null, title, id, startDate, endDate, status, salaryPerHour, hoursPerWeek);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		// check error
		assertEquals(
				"Student is null! Employer is null!", error);
		// check model in memory
		assertEquals(0, cs.getAllCoops().size());
	}

	@Test
	public void testcreateCoopEmpty() {
		clearDatabase();
		assertEquals(0, cs.getAllCoops().size());

		String emailS = "paul.hooley@gmail.com";
		String nameS = "Paul Hooley";
		String passwordS = "frisbyislife";
		int idS = 3;
		String phoneS = "6047862815";
		Student stu;
		
		stu = cs.createStudent(emailS, nameS, passwordS, phoneS, idS);
		
		String emailE = "emma.eagles@mail.mcgill.ca ";
		String nameE = "Emma Eagles";
		String passwordE = "12341234";
		String phoneE = "4563";
		int idE = 31231234;
		Employer emp;
		
		emp = cs.createEmployer(emailE, nameE, passwordE, phoneE, idE);

		String title = "";
		Date startDate = Date.valueOf("2019-01-01");
		Date endDate = Date.valueOf("2019-04-30");
		Integer status = 0;
		Integer salaryPerHour = 19;
		Integer hoursPerWeek = 40;
		Integer id = 23;

		String error = null;
		try {
			cs.createCoop(stu, emp, title, id, startDate, endDate, status, salaryPerHour, hoursPerWeek);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		// check error
		assertEquals("Coop title cannot be empty! ", error);
		// check model in memory
		assertEquals(0, cs.getAllCoops().size());
	}

	@Test
	public void testCreateCoopSpaces() {
		clearDatabase();
		assertEquals(0, cs.getAllCoops().size());
		
		String emailS = "emma.eagles@mail.mcgill.ca ";
		String nameS = "Emma Eagles";
		String passwordS = "12341234";
		String phoneS = "23452452";
		int idS = 31231234;
		Student stu;
		
		stu = cs.createStudent(emailS, nameS, passwordS, phoneS, idS);
		
		String emailE = "paul.hooley@gmail.com";
		String nameE = "Paul Hooley";
		String passwordE = "frisbyislife";
		int idE = 3;
		String phoneE = "6047862815";
		Employer emp;
		
		emp = cs.createEmployer(emailE, nameE, passwordE, phoneE, idE);
		
		String title = "   ";
		Date startDate = Date.valueOf("2019-01-01");
		Date endDate = Date.valueOf("2019-04-30");
		Integer status = 0;
		Integer salaryPerHour = 19;
		Integer hoursPerWeek = 40;
		Integer id = 67;

		String error = null;
		try {
			cs.createCoop(stu, emp, title, id, startDate, endDate, status, salaryPerHour, hoursPerWeek);
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
		clearDatabase();
		assertEquals(0, cs.getAllCoops().size());
		
		String emailS = "emma.eagles@mail.mcgill.ca ";
		String nameS = "Emma Eagles";
		String passwordS = "12341234";
		String phoneS = "50409342345";
		int idS = 31231234;
		Student stu;
		
		stu = cs.createStudent(emailS, nameS, passwordS, phoneS, idS);
		
		String emailE = "paul.hooley@gmail.com";
		String nameE = "Paul Hooley";
		String passwordE = "frisbyislife";
		int idE = 3;
		String phoneE = "6047862815";
		Employer emp;
		
		emp = cs.createEmployer(emailE, nameE, passwordE, phoneE, idE);

		String title = "NASA";
		Date startDate = Date.valueOf("2019-04-30");
		Date endDate = Date.valueOf("2019-01-01");
		Integer status = 0;
		Integer salaryPerHour = 19;
		Integer hoursPerWeek = 40;
		Integer id = 47;

		String error = null;
		try {
			cs.createCoop(stu, emp, title, id, startDate, endDate, status, salaryPerHour, hoursPerWeek);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		// check error
		assertEquals("Coop end time cannot be before Coop start time!", error);

		// check model in memory
		assertEquals(0, cs.getAllCoops().size());
		
	}

	@Test
	public void testCreateProfileNoPhone() {
		clearDatabase();
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
		assertEquals("Phone cannot be empty! ", error);

		// check no change in memory
		assertEquals(0, cs.getAllProfiles().size());
	}
	@Test
	public void testCreateProfileNoEmail() {
		clearDatabase();
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
	public void testCreateFile() {
		clearDatabase();
		assertEquals(0, cs.getAllFiles().size());
		
		String emailS = "paul.hooley@gmail.com";
		String nameS = "qwefqwefq";
		String passwordS = "frisbyislife";
		int idS = 3;
		String phoneS = "6047862815";
		Student s;
		
		s = cs.createStudent(emailS, nameS, passwordS, phoneS, idS);
		
		String emailE = "emma.eagles@mail.mcgill.ca";
		String nameE = "Emma Eagles";
		String passwordE = "12341234";
		String phoneE = "254334";
		int idE = 31231234;
		Employer emp;
		
		emp = cs.createEmployer(emailE, nameE, passwordE, phoneE, idE);
		
		String title = "Developer";
		Date startDate = Date.valueOf("2019-01-01");
		Date endDate = Date.valueOf("2019-04-30");
		Integer status = 0;
		Integer salaryPerHour = 19;
		Integer hoursPerWeek = 40;
		Integer idC = 45;
		Coop c;

		c = cs.createCoop(s, emp, title, idC, startDate, endDate, status, salaryPerHour, hoursPerWeek);

		int id = 1;
		String error = null;
	
		try {
			cs.createFile(id, c);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertEquals(null, error);

		assertEquals(1, cs.getAllFiles().size());
	}
	
	@Test
	public void testCreateFileNegative() {
		clearDatabase();
		assertEquals(0, cs.getAllFiles().size());

		int id = -1;
		String error = null;
	
		try {
			cs.createFile(id, null);
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
		clearDatabase();
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
		clearDatabase();
		assertEquals(0, cs.getAllEmployers().size());

		String email = "emma.eagles@mail.mcgill.ca";
		String name = "Emma Eagles";
		String password = "12341234";
		String phone = "5061231234";
		int id = -9;
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