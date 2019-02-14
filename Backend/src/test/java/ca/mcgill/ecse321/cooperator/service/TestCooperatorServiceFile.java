package ca.mcgill.ecse321.cooperator.service;

import static org.junit.Assert.assertEquals;

import java.sql.Date;

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
import ca.mcgill.ecse321.cooperator.model.Coop;
import ca.mcgill.ecse321.cooperator.model.Employer;
import ca.mcgill.ecse321.cooperator.model.Student;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestCooperatorServiceFile {
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
	public void testCreateFile() {
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

		Integer id = 1;
		String error = null;
	
		try {
			cs.createFile(id, c);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertEquals(null, error);

		assertEquals(1, cs.getAllFiles().size());
		assertEquals(id, cs.getAllFiles().get(0).getId());
	}
	
	@Test
	public void testCreateFileNegative() {
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

}
