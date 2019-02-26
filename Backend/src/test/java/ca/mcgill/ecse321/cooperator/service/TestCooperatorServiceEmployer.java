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
import ca.mcgill.ecse321.cooperator.dao.ReportRepository;
import ca.mcgill.ecse321.cooperator.dao.NotificationRepository;
import ca.mcgill.ecse321.cooperator.dao.ProfileRepository;
import ca.mcgill.ecse321.cooperator.dao.StudentRepository;
import ca.mcgill.ecse321.cooperator.model.Employer;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestCooperatorServiceEmployer {
	@Autowired
	private CooperatorService cs;
	
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
		System.out.println("from student");
		reportRepository.deleteAll();
		notificationRepository.deleteAll();
		coopRepository.deleteAll();
		studentRepository.deleteAll();
		administratorRepository.deleteAll();
		employerRepository.deleteAll();
		profileRepository.deleteAll();
	}
	
	@Test
	public void testCreateEmployer() {
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
	public void testCreateEmployerNegative() {
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
