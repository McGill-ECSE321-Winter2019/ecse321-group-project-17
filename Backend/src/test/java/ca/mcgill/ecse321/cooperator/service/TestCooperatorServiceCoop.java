package ca.mcgill.ecse321.cooperator.service;

import static org.junit.Assert.assertEquals;

import java.sql.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import ca.mcgill.ecse321.cooperator.dao.AdministratorRepository;
import ca.mcgill.ecse321.cooperator.dao.CoopRepository;
import ca.mcgill.ecse321.cooperator.dao.EmployerRepository;
import ca.mcgill.ecse321.cooperator.dao.ReportRepository;
import ca.mcgill.ecse321.cooperator.dao.NotificationRepository;
import ca.mcgill.ecse321.cooperator.dao.ProfileRepository;
import ca.mcgill.ecse321.cooperator.dao.StudentRepository;
import ca.mcgill.ecse321.cooperator.model.Coop;
import ca.mcgill.ecse321.cooperator.model.CoopStatus;
import ca.mcgill.ecse321.cooperator.model.Employer;
import ca.mcgill.ecse321.cooperator.model.Student;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode=DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TestCooperatorServiceCoop {
	@Autowired
	protected CooperatorService cs;
	
	@Autowired
	private AdministratorRepository administratorRepository;
	@Autowired
	private CoopRepository coopRepository;
	@Autowired
	private EmployerRepository employerRepository;
	@Autowired
	private ReportRepository reportRepository;
	@Autowired
	private NotificationRepository notificationRepository;
	@Autowired
	private ProfileRepository profileRepository;
	@Autowired
	private StudentRepository studentRepository;

	@Before @After
	public void clearDatabase() {
		reportRepository.deleteAll();
		notificationRepository.deleteAll();
		coopRepository.deleteAll();
		studentRepository.deleteAll();
		administratorRepository.deleteAll();
		employerRepository.deleteAll();
		profileRepository.deleteAll();
	}
	
	@Test
	public void testCreateCoop() {
		assertEquals(0, cs.getAllCoops().size());		
		assertEquals(0, cs.getNumberofProfiles());
	
		String emailS = "paul.hooley@gmail.com";
		String nameS = "qwefqwefq";
		String passwordS = "frisbyislife";
		int idS = 3;
		String phoneS = "6047862815";
		Student stu;
		
		stu = cs.createStudent(emailS, nameS, passwordS, phoneS, idS);
		
		String emailE = "emma.eagles@mail.mcgill.ca";
		String nameE = "Emma Eagles";
		String passwordE = "12341234";
		String phoneE = "254334";
		String companyE = "Lightspeed";
		Employer emp;
		
		emp = cs.createEmployer(emailE, nameE, passwordE, phoneE, companyE);
		
		if (emp == null) {
			System.out.println("hi");
		}
		else {
			System.out.println("bye");
		}
		
		Integer idE = emp.getId();
		
		String title = "Developer";
		Date startDate = Date.valueOf("2019-01-01");
		Date endDate = Date.valueOf("2019-04-30");
		CoopStatus status = CoopStatus.NotStarted;
		Integer salaryPerHour = 19;
		Integer hoursPerWeek = 40;
		String error = "";
		String address = "address";

		Coop c = null;
		try {
			c = cs.createCoop(stu, emp, title, startDate, endDate, status, salaryPerHour, hoursPerWeek, address);
		} catch(Exception e) {
			error = e.getMessage();
		}
		
		int id = c.getId();
		//int idE = emp.getId();
		
		assertEquals(1, cs.getAllCoops().size());
		assertEquals(title, cs.getCoop(id).getTitle());
		assertEquals(1, cs.getCoopforStudent(stu).size());
		assertEquals(address, cs.getCoop(id).getAddress());
		assertEquals(1, cs.getCoopsByStatus(CoopStatus.NotStarted).size());
		checkResultCoop(idS, idE, title, startDate, endDate, status, salaryPerHour, hoursPerWeek);
	}
	
	private void checkResultCoop(Integer studentID, Integer employerID, String title,
			Date startDate, Date endDate, CoopStatus status, Integer salaryPerHour, Integer hoursPerWeek) {
		List<Student> allStudents = cs.getAllStudents();
		List<Employer> allEmployers = cs.getAllEmployers();
		List<Coop> allCoops = cs.getAllCoops();
		
		assertEquals(1, allStudents.size());
		assertEquals(studentID, allStudents.get(0).getId());
		
		assertEquals(1, allEmployers.size());
		assertEquals(employerID, allEmployers.get(0).getId());		
		assertEquals(1, allCoops.size());
		assertEquals(title, allCoops.get(0).getTitle());
		assertEquals(startDate, allCoops.get(0).getStartDate());
		assertEquals(endDate, allCoops.get(0).getEndDate());
		assertEquals(status, allCoops.get(0).getStatus());
		assertEquals(salaryPerHour, allCoops.get(0).getSalaryPerHour());
		assertEquals(hoursPerWeek, allCoops.get(0).getHoursPerWeek());
		
		assertEquals(0, cs.getAllReports().size()); 
		
	}
	
	@Test
	public void testcreateCoopNullStudentEmployer() {
		assertEquals(0, cs.getAllCoops().size());

		String title = "Developer";
		Date startDate = Date.valueOf("2019-01-01");
		Date endDate = Date.valueOf("2019-04-30");
		CoopStatus status = CoopStatus.NotStarted;
		Integer salaryPerHour = 19;
		Integer hoursPerWeek = 40;
		String error = null;
		String address = "address";
		
		try {
			cs.createCoop(null, null, title, startDate, endDate, status, salaryPerHour, hoursPerWeek, address);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		// check error
		assertEquals("Student is null! Employer is null!", error);
		// check model in memory
		assertEquals(0, cs.getAllCoops().size());
	}
	
	@Test
	public void testcreateCoopEmpty() {
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
		String companyE = "Lightspeed";
		Employer emp;
		
		emp = cs.createEmployer(emailE, nameE, passwordE, phoneE, companyE);

		String title = "";
		Date startDate = null;
		Date endDate = null;
		CoopStatus status = null;
		Integer salaryPerHour = -19;
		Integer hoursPerWeek = -40;
		String address = "";


		String error = null;
		try {
			cs.createCoop(stu, emp, title, startDate, endDate, status, salaryPerHour, hoursPerWeek, address);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		// check error
		assertEquals("Coop title cannot be empty! Coop start date cannot be empty! Coop end date cannot be empty! Coop status invalid! Salary per hour is invalid! Hours per week is invalid! Address cannot be empty!", error);

		// check model in memory
		assertEquals(0, cs.getAllCoops().size());
	}
	
	@Test
	public void testCreateCoopSpaces() {
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
		String companyE = "CSA";
		String phoneE = "6047862815";
		Employer emp;
		
		emp = cs.createEmployer(emailE, nameE, passwordE, phoneE, companyE);
		
		String title = "   ";
		Date startDate = Date.valueOf("2019-01-01");
		Date endDate = Date.valueOf("2019-04-30");
		CoopStatus status = CoopStatus.NotStarted;
		Integer salaryPerHour = 19;
		Integer hoursPerWeek = 40;
		String address = "    ";

		String error = null;
		try {
			cs.createCoop(stu, emp, title, startDate, endDate, status, salaryPerHour, hoursPerWeek, address);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		// check error
		assertEquals("Coop title cannot be empty! Address cannot be empty!", error);
		// check model in memory
		assertEquals(0, cs.getAllCoops().size());

	}
	
	@Test
	public void testCreateCoopEndDateBeforeStartDate() {
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
		String companyE = "CSA";
		String phoneE = "6047862815";
		Employer emp;
		
		emp = cs.createEmployer(emailE, nameE, passwordE, phoneE, companyE);

		String title = "NASA";
		Date startDate = Date.valueOf("2019-04-30");
		Date endDate = Date.valueOf("2019-01-01");
		CoopStatus status = CoopStatus.NotStarted;
		Integer salaryPerHour = 19;
		Integer hoursPerWeek = 40;
		String address = "address";
		String error = null;
		
		try {
			cs.createCoop(stu, emp, title, startDate, endDate, status, salaryPerHour, hoursPerWeek, address);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		// check error
		assertEquals("Coop end time cannot be before Coop start time!", error);

		// check model in memory
		assertEquals(0, cs.getAllCoops().size());
		
	}
	
	@Test
	public void testGetCoopNull() {
		assertEquals(0, cs.getAllCoops().size());
		String error = "";
		
		try {
			cs.getCoopforStudent(null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		// check error
		assertEquals("Student is null!", error);

		// check model in memory
		assertEquals(0, cs.getAllCoops().size());
		
	}
		
}
