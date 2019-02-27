package ca.mcgill.ecse321.cooperator.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

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
import ca.mcgill.ecse321.cooperator.dao.NotificationRepository;
import ca.mcgill.ecse321.cooperator.dao.ProfileRepository;
import ca.mcgill.ecse321.cooperator.dao.ReportRepository;
import ca.mcgill.ecse321.cooperator.dao.StudentRepository;
import ca.mcgill.ecse321.cooperator.model.Student;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestCooperatorServiceStudent {
	
	@Autowired
	private CooperatorService cs;
	
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
	public void testCreateStudent() {
		assertEquals(0, cs.getNumberofProfiles());

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
		assertEquals(password, cs.getStudent(email).getPassword());
		
		//assertEquals("Paul Hooley", studentRepository.findStudentByName(name));
	}
	
	@Test
	public void testCreateStudentNull() {
		assertEquals(0, cs.getNumberofProfiles());
		
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
		assertEquals(0, cs.getNumberofProfiles());
		
	}
	
	@Test
	public void testCreateStudentEmpty() {
		assertEquals(0, cs.getNumberofProfiles());

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
		assertEquals(0, cs.getNumberofProfiles());
		

	}
	
	@Test
	public void testCreateStudentSpaces() {
		assertEquals(0, cs.getNumberofProfiles());

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
		assertEquals(0, cs.getNumberofProfiles());
		assertEquals(0, cs.getAllStudents().size());
		
		try {
			cs.getStudent("");
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Student email cannot be empty!", error);


	}

}
