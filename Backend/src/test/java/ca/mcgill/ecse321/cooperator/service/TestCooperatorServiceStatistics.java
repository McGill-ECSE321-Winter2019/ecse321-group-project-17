package ca.mcgill.ecse321.cooperator.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

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
import ca.mcgill.ecse321.cooperator.dao.NotificationRepository;
import ca.mcgill.ecse321.cooperator.dao.ProfileRepository;
import ca.mcgill.ecse321.cooperator.dao.ReportRepository;
import ca.mcgill.ecse321.cooperator.dao.StudentRepository;
import ca.mcgill.ecse321.cooperator.dto.CoopStatisticsDto;
import ca.mcgill.ecse321.cooperator.dto.ReportStatisticsDto;
import ca.mcgill.ecse321.cooperator.model.Coop;
import ca.mcgill.ecse321.cooperator.model.CoopStatus;
import ca.mcgill.ecse321.cooperator.model.Employer;
import ca.mcgill.ecse321.cooperator.model.ReportStatus;
import ca.mcgill.ecse321.cooperator.model.ReportType;
import ca.mcgill.ecse321.cooperator.model.Student;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestCooperatorServiceStatistics {
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
	public void testCompletedCoopUnsubmittedContractReportStatistics() {
		assertEquals(true, cs.getAllReports().isEmpty());

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
		String companyE = "Lightspeed";
		Employer emp;
		emp = cs.createEmployer(emailE, nameE, passwordE, phoneE, companyE, idE);
		String title = "Developer";
		Date startDate = Date.valueOf("2019-01-01");
		Date endDate = Date.valueOf("2019-04-29");
		CoopStatus status = CoopStatus.Completed;
		Integer salaryPerHour = 19;
		Integer hoursPerWeek = 40;
		Integer idC = 45;
		String address = "address";
		Coop c;
		c = cs.createCoop(s, emp, title, idC, startDate, endDate, status, salaryPerHour, hoursPerWeek, address);
		Integer id = 1;
		Date date = Date.valueOf("2019-03-30");
		cs.createReport(id, c, date, ReportStatus.Unsubmitted, ReportType.Contract);
	
		ReportStatisticsDto rsd = cs.generateAllReportStatistics("Winter2019", "Summer2019", 0);

		CoopStatisticsDto csd = cs.generateAllCoopStatistics("Winter2019", "Winter2019", 0);
		
		assertEquals("Winter2019", csd.getStartTerm());
		assertEquals("Winter2019", csd.getEndTerm());
		
		assertEquals(1, (int)csd.getCompletedCoops());
		assertEquals(0, (int)csd.getInProgressCoops());
		assertEquals(0, (int)csd.getNotStartedCoops());
		
		assertEquals(1, (int)csd.getTotalCoops());
		assertEquals(0, (int)csd.getCoopNumber());

		assertEquals(1, (int)rsd.getUnsubmittedReports());
		assertEquals(0, (int)rsd.getSubmittedReports());
		assertEquals(0, (int)rsd.getLateReports());
		assertEquals(0, (int)rsd.getReviewedReports());

		assertEquals(1, (int)rsd.getContractReports());
		assertEquals(0, (int)rsd.getTechnicalReports());
		assertEquals(0, (int)rsd.getStudentEvalReports());
		assertEquals(0, (int)rsd.getEmployerEvalReports());
		assertEquals(0, (int)rsd.getTwoWeekReports());
		
		assertEquals(1, (int)rsd.getTotalReports());
	}
	
	@Test
	public void testInProgressCoopSubmittedTechnicalReportStatistics() {
		assertEquals(true, cs.getAllReports().isEmpty());

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
		String companyE = "Lightspeed";
		Employer emp;
		emp = cs.createEmployer(emailE, nameE, passwordE, phoneE, companyE, idE);
		String title = "Developer";
		Date startDate = Date.valueOf("2019-01-01");
		Date endDate = Date.valueOf("2019-04-29");
		CoopStatus status = CoopStatus.InProgress;
		Integer salaryPerHour = 19;
		Integer hoursPerWeek = 40;
		Integer idC = 45;
		String address = "address";
		Coop c;
		c = cs.createCoop(s, emp, title, idC, startDate, endDate, status, salaryPerHour, hoursPerWeek, address);
		Integer id = 1;
		Date date = Date.valueOf("2019-03-30");
		cs.createReport(id, c, date, ReportStatus.Submitted, ReportType.Technical);
	
		ReportStatisticsDto rsd = cs.generateAllReportStatistics("Winter2019", "Summer2019", 0);

		CoopStatisticsDto csd = cs.generateAllCoopStatistics("Winter2019", "Winter2019", 0);
		
		assertEquals("Winter2019", csd.getStartTerm());
		assertEquals("Winter2019", csd.getEndTerm());
		
		assertEquals(0, (int)csd.getCompletedCoops());
		assertEquals(1, (int)csd.getInProgressCoops());
		assertEquals(0, (int)csd.getNotStartedCoops());
		
		assertEquals(1, (int)csd.getTotalCoops());
		assertEquals(0, (int)csd.getCoopNumber());

		assertEquals(0, (int)rsd.getUnsubmittedReports());
		assertEquals(1, (int)rsd.getSubmittedReports());
		assertEquals(0, (int)rsd.getLateReports());
		assertEquals(0, (int)rsd.getReviewedReports());

		assertEquals(0, (int)rsd.getContractReports());
		assertEquals(1, (int)rsd.getTechnicalReports());
		assertEquals(0, (int)rsd.getStudentEvalReports());
		assertEquals(0, (int)rsd.getEmployerEvalReports());
		assertEquals(0, (int)rsd.getTwoWeekReports());
		
		assertEquals(1, (int)rsd.getTotalReports());
	}
	
	@Test
	public void testNotStartedCoopLateStudentEvalReportStatistics() {
		assertEquals(true, cs.getAllReports().isEmpty());

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
		String companyE = "Lightspeed";
		int idE = 31231234;
		Employer emp;
		emp = cs.createEmployer(emailE, nameE, passwordE, phoneE, companyE, idE);
		String title = "Developer";
		Date startDate = Date.valueOf("2019-01-01");
		Date endDate = Date.valueOf("2019-04-29");
		CoopStatus status = CoopStatus.NotStarted;
		Integer salaryPerHour = 19;
		Integer hoursPerWeek = 40;
		Integer idC = 45;
		String address = "address";
		Coop c;
		c = cs.createCoop(s, emp, title, idC, startDate, endDate, status, salaryPerHour, hoursPerWeek, address);
		Integer id = 1;
		Date date = Date.valueOf("2019-03-30");
		cs.createReport(id, c, date, ReportStatus.Late, ReportType.StudentEval);
	
		ReportStatisticsDto rsd = cs.generateAllReportStatistics("Winter2019", "Summer2019", 0);

		CoopStatisticsDto csd = cs.generateAllCoopStatistics("Winter2019", "Winter2019", 0);
		
		assertEquals("Winter2019", csd.getStartTerm());
		assertEquals("Winter2019", csd.getEndTerm());
		
		assertEquals(0, (int)csd.getCompletedCoops());
		assertEquals(0, (int)csd.getInProgressCoops());
		assertEquals(1, (int)csd.getNotStartedCoops());
		
		assertEquals(1, (int)csd.getTotalCoops());
		assertEquals(0, (int)csd.getCoopNumber());

		assertEquals(0, (int)rsd.getUnsubmittedReports());
		assertEquals(0, (int)rsd.getSubmittedReports());
		assertEquals(1, (int)rsd.getLateReports());
		assertEquals(0, (int)rsd.getReviewedReports());

		assertEquals(0, (int)rsd.getContractReports());
		assertEquals(0, (int)rsd.getTechnicalReports());
		assertEquals(1, (int)rsd.getStudentEvalReports());
		assertEquals(0, (int)rsd.getEmployerEvalReports());
		assertEquals(0, (int)rsd.getTwoWeekReports());
		
		assertEquals(1, (int)rsd.getTotalReports());
	}

	@Test
	public void testInProgressCoopReviewedEmployerEvalReportStatistics() {
		assertEquals(true, cs.getAllReports().isEmpty());

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
		String companyE = "Lightspeed";
		Employer emp;
		emp = cs.createEmployer(emailE, nameE, passwordE, phoneE, companyE, idE);
		String title = "Developer";
		Date startDate = Date.valueOf("2019-01-01");
		Date endDate = Date.valueOf("2019-04-29");
		CoopStatus status = CoopStatus.InProgress;
		Integer salaryPerHour = 19;
		Integer hoursPerWeek = 40;
		Integer idC = 45;
		String address = "address";
		Coop c;
		c = cs.createCoop(s, emp, title, idC, startDate, endDate, status, salaryPerHour, hoursPerWeek, address);
		Integer id = 1;
		Date date = Date.valueOf("2019-03-30");
		cs.createReport(id, c, date, ReportStatus.Reviewed, ReportType.EmployerEval);
	
		ReportStatisticsDto rsd = cs.generateAllReportStatistics("Winter2019", "Summer2019", 0);

		CoopStatisticsDto csd = cs.generateAllCoopStatistics("Winter2019", "Winter2019", 0);
		
		assertEquals("Winter2019", csd.getStartTerm());
		assertEquals("Winter2019", csd.getEndTerm());
		
		assertEquals(0, (int)csd.getCompletedCoops());
		assertEquals(1, (int)csd.getInProgressCoops());
		assertEquals(0, (int)csd.getNotStartedCoops());
		
		assertEquals(1, (int)csd.getTotalCoops());
		assertEquals(0, (int)csd.getCoopNumber());

		assertEquals(0, (int)rsd.getUnsubmittedReports());
		assertEquals(0, (int)rsd.getSubmittedReports());
		assertEquals(0, (int)rsd.getLateReports());
		assertEquals(1, (int)rsd.getReviewedReports());

		assertEquals(0, (int)rsd.getContractReports());
		assertEquals(0, (int)rsd.getTechnicalReports());
		assertEquals(0, (int)rsd.getStudentEvalReports());
		assertEquals(1, (int)rsd.getEmployerEvalReports());
		assertEquals(0, (int)rsd.getTwoWeekReports());
		
		assertEquals(1, (int)rsd.getTotalReports());
	}
	

	@Test
	public void testInProgressCoopReviewedTwoWeekReportStatistics() {
		assertEquals(true, cs.getAllReports().isEmpty());

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
		String companyE = "Lightspeed";
		Employer emp;
		emp = cs.createEmployer(emailE, nameE, passwordE, phoneE, companyE, idE);
		String title = "Developer";
		Date startDate = Date.valueOf("2019-01-01");
		Date endDate = Date.valueOf("2019-04-29");
		CoopStatus status = CoopStatus.InProgress;
		Integer salaryPerHour = 19;
		Integer hoursPerWeek = 40;
		Integer idC = 45;
		String address = "address";
		Coop c;
		c = cs.createCoop(s, emp, title, idC, startDate, endDate, status, salaryPerHour, hoursPerWeek, address);
		Integer id = 1;
		Date date = Date.valueOf("2019-03-30");
		cs.createReport(id, c, date, ReportStatus.Reviewed, ReportType.TwoWeek);
	
		ReportStatisticsDto rsd = cs.generateAllReportStatistics("Winter2019", "Summer2019", 0);

		CoopStatisticsDto csd = cs.generateAllCoopStatistics("Winter2019", "Winter2019", 0);
		
		assertEquals("Winter2019", csd.getStartTerm());
		assertEquals("Winter2019", csd.getEndTerm());
		
		assertEquals(0, (int)csd.getCompletedCoops());
		assertEquals(1, (int)csd.getInProgressCoops());
		assertEquals(0, (int)csd.getNotStartedCoops());
		
		assertEquals(1, (int)csd.getTotalCoops());
		assertEquals(0, (int)csd.getCoopNumber());

		assertEquals(0, (int)rsd.getUnsubmittedReports());
		assertEquals(0, (int)rsd.getSubmittedReports());
		assertEquals(0, (int)rsd.getLateReports());
		assertEquals(1, (int)rsd.getReviewedReports());

		assertEquals(0, (int)rsd.getContractReports());
		assertEquals(0, (int)rsd.getTechnicalReports());
		assertEquals(0, (int)rsd.getStudentEvalReports());
		assertEquals(0, (int)rsd.getEmployerEvalReports());
		assertEquals(1, (int)rsd.getTwoWeekReports());
		
		assertEquals(1, (int)rsd.getTotalReports());
	}
	
	@Test
	public void testCoopAndReportStatisticsWithPastCoop() {
		assertEquals(true, cs.getAllReports().isEmpty());

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
		String companyE = "Lightspeed";
		String phoneE = "254334";
		int idE = 31231234;
		Employer emp;
		emp = cs.createEmployer(emailE, nameE, passwordE, phoneE, companyE, idE);
		String title = "Developer";
		Date startDate = Date.valueOf("2018-01-01");
		Date endDate = Date.valueOf("2018-04-29");
		CoopStatus status = CoopStatus.InProgress;
		Integer salaryPerHour = 19;
		Integer hoursPerWeek = 40;
		Integer idC = 45;
		String address = "address";
		Coop c;
		c = cs.createCoop(s, emp, title, idC, startDate, endDate, status, salaryPerHour, hoursPerWeek, address);
		Integer id = 1;
		Date date = Date.valueOf("2019-03-30");
		cs.createReport(id, c, date, ReportStatus.Reviewed, ReportType.TwoWeek);
	
		ReportStatisticsDto rsd = cs.generateAllReportStatistics("Winter2019", "Summer2019", 0);

		CoopStatisticsDto csd = cs.generateAllCoopStatistics("Winter2019", "Winter2019", 0);
		
		assertEquals("Winter2019", csd.getStartTerm());
		assertEquals("Winter2019", csd.getEndTerm());
		
		assertEquals(0, (int)csd.getCompletedCoops());
		assertEquals(0, (int)csd.getInProgressCoops());
		assertEquals(0, (int)csd.getNotStartedCoops());
		
		assertEquals(0, (int)csd.getTotalCoops());
		assertEquals(0, (int)csd.getCoopNumber());

		assertEquals(0, (int)rsd.getUnsubmittedReports());
		assertEquals(0, (int)rsd.getSubmittedReports());
		assertEquals(0, (int)rsd.getLateReports());
		assertEquals(0, (int)rsd.getReviewedReports());

		assertEquals(0, (int)rsd.getContractReports());
		assertEquals(0, (int)rsd.getTechnicalReports());
		assertEquals(0, (int)rsd.getStudentEvalReports());
		assertEquals(0, (int)rsd.getEmployerEvalReports());
		assertEquals(0, (int)rsd.getTwoWeekReports());
		
		assertEquals(0, (int)rsd.getTotalReports());
	}

	@Test
	public void testCoopAndReportStatisticsWithFutureCoop() {
		assertEquals(true, cs.getAllReports().isEmpty());

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
		String companyE = "Lightspeed";
		Employer emp;
		emp = cs.createEmployer(emailE, nameE, passwordE, phoneE, companyE, idE);
		String title = "Developer";
		Date startDate = Date.valueOf("2020-01-01");
		Date endDate = Date.valueOf("2020-04-29");
		CoopStatus status = CoopStatus.InProgress;
		Integer salaryPerHour = 19;
		Integer hoursPerWeek = 40;
		Integer idC = 45;
		String address = "address";
		Coop c;
		c = cs.createCoop(s, emp, title, idC, startDate, endDate, status, salaryPerHour, hoursPerWeek, address);
		Integer id = 1;
		Date date = Date.valueOf("2019-03-30");
		cs.createReport(id, c, date, ReportStatus.Reviewed, ReportType.TwoWeek);
	
		ReportStatisticsDto rsd = cs.generateAllReportStatistics("Winter2019", "Summer2019", 0);

		CoopStatisticsDto csd = cs.generateAllCoopStatistics("Winter2019", "Winter2019", 0);
		
		assertEquals("Winter2019", csd.getStartTerm());
		assertEquals("Winter2019", csd.getEndTerm());
		
		assertEquals(0, (int)csd.getCompletedCoops());
		assertEquals(0, (int)csd.getInProgressCoops());
		assertEquals(0, (int)csd.getNotStartedCoops());
		
		assertEquals(0, (int)csd.getTotalCoops());
		assertEquals(0, (int)csd.getCoopNumber());

		assertEquals(0, (int)rsd.getUnsubmittedReports());
		assertEquals(0, (int)rsd.getSubmittedReports());
		assertEquals(0, (int)rsd.getLateReports());
		assertEquals(0, (int)rsd.getReviewedReports());

		assertEquals(0, (int)rsd.getContractReports());
		assertEquals(0, (int)rsd.getTechnicalReports());
		assertEquals(0, (int)rsd.getStudentEvalReports());
		assertEquals(0, (int)rsd.getEmployerEvalReports());
		assertEquals(0, (int)rsd.getTwoWeekReports());
		
		assertEquals(0, (int)rsd.getTotalReports());
	}
	
	@Test
	public void testCoopAndReportStatisticsWithCorrectCoopNumber() {
		assertEquals(true, cs.getAllReports().isEmpty());

		String emailS = "paul.hooley@gmail.com";
		String nameS = "qwefqwefq";
		String passwordS = "frisbyislife";
		int idS = 3;
		String phoneS = "6047862815";
		Student s;
		s = cs.createStudent(emailS, nameS, passwordS, phoneS, idS);
		s.setCoopsCompleted(1);
		String emailE = "emma.eagles@mail.mcgill.ca";
		String nameE = "Emma Eagles";
		String passwordE = "12341234";
		String companyE = "Lightspeed";
		String phoneE = "254334";
		int idE = 31231234;
		Employer emp;
		emp = cs.createEmployer(emailE, nameE, passwordE, phoneE, companyE, idE);
		String title = "Developer";
		Date startDate = Date.valueOf("2019-01-01");
		Date endDate = Date.valueOf("2019-04-29");
		CoopStatus status = CoopStatus.Completed;
		Integer salaryPerHour = 19;
		Integer hoursPerWeek = 40;
		Integer idC = 45;
		String address = "address";
		Coop c;
		c = cs.createCoop(s, emp, title, idC, startDate, endDate, status, salaryPerHour, hoursPerWeek, address);
		Integer id = 1;
		Date date = Date.valueOf("2019-03-30");
		cs.createReport(id, c, date, ReportStatus.Reviewed, ReportType.TwoWeek);
	
		ReportStatisticsDto rsd = cs.generateAllReportStatistics("Winter2019", "Summer2019", 1);

		CoopStatisticsDto csd = cs.generateAllCoopStatistics("Winter2019", "Winter2019", 1);
		
		assertEquals("Winter2019", csd.getStartTerm());
		assertEquals("Winter2019", csd.getEndTerm());
		
		assertEquals(1, (int)csd.getCompletedCoops());
		assertEquals(0, (int)csd.getInProgressCoops());
		assertEquals(0, (int)csd.getNotStartedCoops());
		
		assertEquals(1, (int)csd.getTotalCoops());
		assertEquals(1, (int)csd.getCoopNumber());

		assertEquals(0, (int)rsd.getUnsubmittedReports());
		assertEquals(0, (int)rsd.getSubmittedReports());
		assertEquals(0, (int)rsd.getLateReports());
		assertEquals(1, (int)rsd.getReviewedReports());

		assertEquals(0, (int)rsd.getContractReports());
		assertEquals(0, (int)rsd.getTechnicalReports());
		assertEquals(0, (int)rsd.getStudentEvalReports());
		assertEquals(0, (int)rsd.getEmployerEvalReports());
		assertEquals(1, (int)rsd.getTwoWeekReports());
		
		assertEquals(1, (int)rsd.getTotalReports());
	}
	
	@Test
	public void testCoopAndReportStatisticsWithIncorrectCoopNumber() {
		assertEquals(true, cs.getAllReports().isEmpty());

		String emailS = "paul.hooley@gmail.com";
		String nameS = "qwefqwefq";
		String passwordS = "frisbyislife";
		int idS = 3;
		String phoneS = "6047862815";
		Student s;
		s = cs.createStudent(emailS, nameS, passwordS, phoneS, idS);
		s.setCoopsCompleted(1);
		String emailE = "emma.eagles@mail.mcgill.ca";
		String nameE = "Emma Eagles";
		String passwordE = "12341234";
		String companyE = "Lightspeed";
		String phoneE = "254334";
		int idE = 31231234;
		Employer emp;
		emp = cs.createEmployer(emailE, nameE, passwordE, phoneE, companyE, idE);
		String title = "Developer";
		Date startDate = Date.valueOf("2019-01-01");
		Date endDate = Date.valueOf("2019-04-29");
		CoopStatus status = CoopStatus.Completed;
		Integer salaryPerHour = 19;
		Integer hoursPerWeek = 40;
		Integer idC = 45;
		String address = "address";
		Coop c;
		c = cs.createCoop(s, emp, title, idC, startDate, endDate, status, salaryPerHour, hoursPerWeek, address);
		Integer id = 1;
		Date date = Date.valueOf("2019-03-30");
		cs.createReport(id, c, date, ReportStatus.Reviewed, ReportType.TwoWeek);
	
		ReportStatisticsDto rsd = cs.generateAllReportStatistics("Winter2019", "Summer2019", 2);

		CoopStatisticsDto csd = cs.generateAllCoopStatistics("Winter2019", "Winter2019", 2);
		
		assertEquals("Winter2019", csd.getStartTerm());
		assertEquals("Winter2019", csd.getEndTerm());
		
		assertEquals(0, (int)csd.getCompletedCoops());
		assertEquals(0, (int)csd.getInProgressCoops());
		assertEquals(0, (int)csd.getNotStartedCoops());
		
		assertEquals(0, (int)csd.getTotalCoops());
		assertEquals(2, (int)csd.getCoopNumber());

		assertEquals(0, (int)rsd.getUnsubmittedReports());
		assertEquals(0, (int)rsd.getSubmittedReports());
		assertEquals(0, (int)rsd.getLateReports());
		assertEquals(0, (int)rsd.getReviewedReports());

		assertEquals(0, (int)rsd.getContractReports());
		assertEquals(0, (int)rsd.getTechnicalReports());
		assertEquals(0, (int)rsd.getStudentEvalReports());
		assertEquals(0, (int)rsd.getEmployerEvalReports());
		assertEquals(0, (int)rsd.getTwoWeekReports());
		
		assertEquals(0, (int)rsd.getTotalReports());
	}
	
}
