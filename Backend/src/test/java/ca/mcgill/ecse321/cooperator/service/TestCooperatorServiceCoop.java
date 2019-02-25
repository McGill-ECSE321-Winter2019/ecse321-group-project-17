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
import org.springframework.test.context.junit4.SpringRunner;

import ca.mcgill.ecse321.cooperator.dao.AdministratorRepository;
import ca.mcgill.ecse321.cooperator.dao.CoopRepository;
import ca.mcgill.ecse321.cooperator.dao.EmployerRepository;
import ca.mcgill.ecse321.cooperator.dao.ReportRepository;
import ca.mcgill.ecse321.cooperator.dao.NotificationRepository;
import ca.mcgill.ecse321.cooperator.dao.ProfileRepository;
import ca.mcgill.ecse321.cooperator.dao.StudentRepository;
import ca.mcgill.ecse321.cooperator.model.Coop;
import ca.mcgill.ecse321.cooperator.model.Employer;
import ca.mcgill.ecse321.cooperator.model.Student;

@RunWith(SpringRunner.class)
@SpringBootTest
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
	
	public void testCreateCoop() {
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

		String address = "address";

		
		try {
			cs.createCoop(s, emp, title, id, startDate, endDate, status, salaryPerHour, hoursPerWeek, address);
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
		Integer status = 0;
		Integer salaryPerHour = 19;
		Integer hoursPerWeek = 40;
		Integer id = 34;
		String error = null;
		String address = "address";
		
		try {
			cs.createCoop(null, null, title, id, startDate, endDate, status, salaryPerHour, hoursPerWeek, address);
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
		int idE = 31231234;
		Employer emp;
		
		emp = cs.createEmployer(emailE, nameE, passwordE, phoneE, idE);

		String title = "";
		Date startDate = null;
		Date endDate = null;
		Integer status = 0;
		Integer salaryPerHour = 19;
		Integer hoursPerWeek = 40;
		Integer id = 23;
		String address = "";


		String error = null;
		try {
			cs.createCoop(stu, emp, title, id, startDate, endDate, status, salaryPerHour, hoursPerWeek, address);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		// check error

		assertEquals("Coop title cannot be empty! Address cannot be empty!", error);

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
		String address = "    ";

		String error = null;
		try {
			cs.createCoop(stu, emp, title, id, startDate, endDate, status, salaryPerHour, hoursPerWeek, address);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		// check error
		assertEquals("Coop title cannot be empty! Address cannot be empty!", error);
		// check model in memory
		assertEquals(0, cs.getAllCoops().size());

	}
	
	@Test
	public void testcreateCoopEndDateBeforeStartDate() {
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
		String address = "address";
		String error = null;
		
		try {
			cs.createCoop(stu, emp, title, id, startDate, endDate, status, salaryPerHour, hoursPerWeek, address);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		// check error
		assertEquals("Coop end time cannot be before Coop start time!", error);

		// check model in memory
		assertEquals(0, cs.getAllCoops().size());
		
	}
		
}
