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
import ca.mcgill.ecse321.cooperator.dao.ReportRepository;
import ca.mcgill.ecse321.cooperator.dao.NotificationRepository;
import ca.mcgill.ecse321.cooperator.dao.ProfileRepository;
import ca.mcgill.ecse321.cooperator.dao.StudentRepository;
import ca.mcgill.ecse321.cooperator.model.Coop;
import ca.mcgill.ecse321.cooperator.model.Employer;
import ca.mcgill.ecse321.cooperator.model.ReportStatus;
import ca.mcgill.ecse321.cooperator.model.ReportType;
import ca.mcgill.ecse321.cooperator.model.Student;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestCooperatorServiceReport {
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
	public void testCreateReportNull() {
		assertEquals(0, cs.getAllReports().size());

		Integer id = -1;
		String error = null;
		Date date = null;
	
		try {
			cs.createReport(id, null, date, null, null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertEquals("ID is invalid! Coop is null! Due date is invalid! Status is invalid! Type is invalid!", error);

		assertEquals(0, cs.getAllReports().size());
	}
	
	@Test
	public void testCreateReportNegative() {
		assertEquals(0, cs.getAllReports().size());

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
		String address = "address";
		Coop c;

		c = cs.createCoop(s, emp, title, idC, startDate, endDate, status, salaryPerHour, hoursPerWeek, address);
		
		int id = -1;
		Date date = Date.valueOf("2019-03-30");
		String error = null;
	
		try {
			cs.createReport(id, c, date, ReportStatus.Complete, ReportType.Contract);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		// check error
		assertEquals("ID is invalid! ", error);

		// check no change in memory
		assertEquals(0, cs.getAllReports().size());
	}
	
	@Test
	public void testCreateReport() {
		assertEquals(0, cs.getAllReports().size());
		
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
		String address = "address";
		Coop c;

		c = cs.createCoop(s, emp, title, idC, startDate, endDate, status, salaryPerHour, hoursPerWeek, address);

		Integer id = 1;
		Date date = Date.valueOf("2019-03-30");
		String error = "";
		
	
		try {
			cs.createReport(id, c, date, ReportStatus.Complete, ReportType.Contract);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertEquals("", error);

		assertEquals(1, cs.getAllReports().size());
		assertEquals(id, cs.getAllReports().get(0).getId());
	}

}
