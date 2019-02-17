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
import ca.mcgill.ecse321.cooperator.dao.FileRepository;
import ca.mcgill.ecse321.cooperator.dao.NotificationRepository;
import ca.mcgill.ecse321.cooperator.dao.ProfileRepository;
import ca.mcgill.ecse321.cooperator.dao.StudentRepository;
import ca.mcgill.ecse321.cooperator.model.Administrator;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestCooperatorServiceAdmin {
	@Autowired
	protected CooperatorService cs;
	
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

	@Before @After
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
	public void testCreateAdmin() {
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
	
	

}