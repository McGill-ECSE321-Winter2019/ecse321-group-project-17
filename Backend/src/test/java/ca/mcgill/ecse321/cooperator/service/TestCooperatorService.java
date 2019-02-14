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

	//placeholder test so this doesn't fail
	//add tests that include mutliple classes in this file
	
	
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
		assertEquals("Phone cannot be empty! ", error);

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

}