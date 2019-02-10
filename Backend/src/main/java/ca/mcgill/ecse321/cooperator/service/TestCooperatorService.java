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
import ca.mcgill.ecse321.cooperator.model.Profile;

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
	public void testCreateProfile() {
		assertEquals(0, cs.getAllProfiles().size());

		String email = "paul.hooley@gmail.com";
		String name = "Paul Hooley";
		String password = "frisbyislife";
		String phone = "6047862815";

		try {
			cs.createProfile(email, name, password, phone);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		List<Profile> allPersons = cs.getAllProfiles();

		assertEquals(1, allPersons.size());
		assertEquals(name, allPersons.get(0).getName());
	}

	/*
	@Test
	public void testCreatePersonNull() {
		assertEquals(0, erc.getAllPersons().size());
		
		String name = null;
		String error = null;

		try {
			erc.createPerson(name);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		// check error
		assertEquals("Person name cannot be empty!", error);

		// check no change in memory
		assertEquals(0, erc.getAllPersons().size());

	}

	@Test
	public void testCreatePersonEmpty() {
		assertEquals(0, erc.getAllPersons().size());

		String name = "";
		String error = null;

		try {
			erc.createPerson(name);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		// check error
		assertEquals("Person name cannot be empty!", error);

		// check no change in memory
		assertEquals(0, erc.getAllPersons().size());

	}

	@Test
	public void testCreatePersonSpaces() {
		assertEquals(0, erc.getAllPersons().size());

		String name = " ";
		String error = null;
	
		try {
			erc.createPerson(name);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		// check error
		assertEquals("Person name cannot be empty!", error);

		// check no change in memory
		assertEquals(0, erc.getAllPersons().size());

	}

	@Test
	public void testCreateEvent() {
		assertEquals(0, erc.getAllEvents().size());
		
		String name = "Soccer Game";
		Calendar c = Calendar.getInstance();
		c.set(2017, Calendar.MARCH, 16, 9, 0, 0);
		Date eventDate = new Date(c.getTimeInMillis());
		LocalTime startTime = LocalTime.parse("09:00");
		c.set(2017, Calendar.MARCH, 16, 10, 30, 0);
		LocalTime endTime = LocalTime.parse("10:30");

		try {
			erc.createEvent(name, eventDate, Time.valueOf(startTime) , Time.valueOf(endTime));
		} catch (IllegalArgumentException e) {
			fail();
		}

		checkResultEvent(name, eventDate, startTime, endTime);
	}

	private void checkResultEvent(String name, Date eventDate, LocalTime startTime, LocalTime endTime) {
		assertEquals(0, erc.getAllPersons().size());
		assertEquals(1, erc.getAllEvents().size());
		assertEquals(name, erc.getAllEvents().get(0).getName());
		assertEquals(eventDate.toString(), erc.getAllEvents().get(0).getDate().toString());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		assertEquals(startTime.format(formatter).toString(), erc.getAllEvents().get(0).getStartTime().toString());
		assertEquals(endTime.format(formatter).toString(), erc.getAllEvents().get(0).getEndTime().toString());
		assertEquals(0, erc.getAllRegistrations().size());
	}

	
	@Test
	public void testRegister() {
		assertEquals(0, erc.getAllRegistrations().size());

		String nameP = "Oscar";
		
		Person person = erc.createPerson(nameP);
		
		assertEquals(1, erc.getAllPersons().size());

		String nameE = "Soccer Game";
		Calendar c = Calendar.getInstance();
		c.set(2017, Calendar.MARCH, 16, 9, 0, 0);
		Date eventDate = new Date(c.getTimeInMillis());
		Time startTime = new Time(c.getTimeInMillis());
		c.set(2017, Calendar.MARCH, 16, 10, 30, 0);
		Time endTime = new Time(c.getTimeInMillis());
		Event event = erc.createEvent(nameE, eventDate, startTime, endTime);
		assertEquals(1, erc.getAllEvents().size());

		try {
			erc.register(person, event);
		} catch (IllegalArgumentException e) {
			fail();
		}

		checkResultRegister(nameP, nameE, eventDate, startTime, endTime);
	}

	private void checkResultRegister(String nameP, String nameE, Date eventDate, Time startTime, Time endTime) {
		assertEquals(1, erc.getAllPersons().size());
		assertEquals(nameP, erc.getAllPersons().get(0).getName());
		assertEquals(1, erc.getAllEvents().size());
		assertEquals(nameE, erc.getAllEvents().get(0).getName());
		assertEquals(eventDate.toString(), erc.getAllEvents().get(0).getDate().toString());
		assertEquals(startTime.toString(), erc.getAllEvents().get(0).getStartTime().toString());
		assertEquals(endTime.toString(), erc.getAllEvents().get(0).getEndTime().toString());
		assertEquals(1, erc.getAllRegistrations().size());
		// Need to assert by ID (in this case: name)
		assertEquals(erc.getAllEvents().get(0).getName(), erc.getAllRegistrations().get(0).getEvent().getName());
		// Need to assert by ID (in this case: name)
		assertEquals(erc.getAllPersons().get(0).getName(), erc.getAllRegistrations().get(0).getPerson().getName());
	}


	@Test
	public void testCreateEventNull() {
		assertEquals(0, erc.getAllRegistrations().size());

		String name = null;
		Date eventDate = null;
		Time startTime = null;
		Time endTime = null;

		String error = null;
		try {
			erc.createEvent(name, eventDate, startTime, endTime);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		// check error
		assertEquals(
				"Event name cannot be empty! Event date cannot be empty! Event start time cannot be empty! Event end time cannot be empty!",
				error);
		// check model in memory
		assertEquals(0, erc.getAllEvents().size());
	}

	@Test
	public void testCreateEventEmpty() {
		assertEquals(0, erc.getAllEvents().size());

		String name = "";
		Calendar c = Calendar.getInstance();
		c.set(2017, Calendar.FEBRUARY, 16, 10, 00, 0);
		Date eventDate = new Date(c.getTimeInMillis());
		LocalTime startTime = LocalTime.parse("10:00");
		c.set(2017, Calendar.FEBRUARY, 16, 11, 30, 0);
		LocalTime endTime = LocalTime.parse("11:30");

		String error = null;
		try {
			erc.createEvent(name, eventDate, Time.valueOf(startTime), Time.valueOf(endTime));
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		// check error
		assertEquals("Event name cannot be empty!", error);
		// check model in memory
		assertEquals(0, erc.getAllEvents().size());
	}

	@Test
	public void testCreateEventSpaces() {
		assertEquals(0, erc.getAllEvents().size());

		String name = " ";
		Calendar c = Calendar.getInstance();
		c.set(2016, Calendar.OCTOBER, 16, 9, 00, 0);
		Date eventDate = new Date(c.getTimeInMillis());
		LocalTime startTime = LocalTime.parse("09:00");
		c.set(2016, Calendar.OCTOBER, 16, 10, 30, 0);
		LocalTime endTime = LocalTime.parse("10:30");

		String error = null;
		try {
			erc.createEvent(name, eventDate, Time.valueOf(startTime), Time.valueOf(endTime));
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		// check error
		assertEquals("Event name cannot be empty!", error);
		// check model in memory
		assertEquals(0, erc.getAllEvents().size());

	}

	@Test
	public void testCreateEventEndTimeBeforeStartTime() {
		assertEquals(0, erc.getAllEvents().size());

		String name = "Soccer Game";
		Calendar c = Calendar.getInstance();
		c.set(2016, Calendar.OCTOBER, 16, 9, 00, 0);
		Date eventDate = new Date(c.getTimeInMillis());
		LocalTime startTime = LocalTime.parse("09:00");
		c.set(2016, Calendar.OCTOBER, 16, 8, 59, 59);
		LocalTime endTime = LocalTime.parse("08:59");

		String error = null;
		try {
			erc.createEvent(name, eventDate, Time.valueOf(startTime), Time.valueOf(endTime));
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		// check error
		assertEquals("Event end time cannot be before event start time!", error);

		// check model in memory
		assertEquals(0, erc.getAllEvents().size());

	}

	@Test
	public void testRegisterNull() {
		assertEquals(0, erc.getAllRegistrations().size());

		Person Person = null;
		assertEquals(0, erc.getAllPersons().size());

		Event event = null;
		assertEquals(0, erc.getAllEvents().size());

		String error = null;
		try {
			erc.register(Person, event);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		// check error
		assertEquals("Person needs to be selected for registration! Event needs to be selected for registration!",
				error);

		// check model in memory
		assertEquals(0, erc.getAllRegistrations().size());
		assertEquals(0, erc.getAllPersons().size());
		assertEquals(0, erc.getAllEvents().size());

	}

	@Test
	public void testRegisterPersonAndEventDoNotExist() {
		assertEquals(0, erc.getAllRegistrations().size());

		String nameP = "Oscar";
		Person person = new Person();
		person.setName(nameP);
		assertEquals(0, erc.getAllPersons().size());

		String nameE = "Soccer Game";
		Calendar c = Calendar.getInstance();
		c.set(2016, Calendar.OCTOBER, 16, 9, 00, 0);
		Date eventDate = new Date(c.getTimeInMillis());
		Time startTime = new Time(c.getTimeInMillis());
		c.set(2016, Calendar.OCTOBER, 16, 10, 30, 0);
		Time endTime = new Time(c.getTimeInMillis());
		Event event = new Event();
		event.setName(nameE);
		event.setDate(eventDate);
		event.setStartTime(startTime);
		event.setEndTime(endTime);
		assertEquals(0, erc.getAllEvents().size());

		String error = null;
		try {
			erc.register(person, event);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		// check error
		assertEquals("Person does not exist! Event does not exist!", error);

		// check model in memory
		assertEquals(0, erc.getAllRegistrations().size());
		assertEquals(0, erc.getAllPersons().size());
		assertEquals(0, erc.getAllEvents().size());

	}
	*/


}