package ca.mcgill.ecse321.cooperator.service;

import java.io.File;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.cooperator.dao.AdministratorRepository;
import ca.mcgill.ecse321.cooperator.dao.CoopRepository;
import ca.mcgill.ecse321.cooperator.dao.EmployerRepository;
import ca.mcgill.ecse321.cooperator.dao.NotificationRepository;
import ca.mcgill.ecse321.cooperator.dao.ProfileRepository;
import ca.mcgill.ecse321.cooperator.dao.ReportRepository;
import ca.mcgill.ecse321.cooperator.dao.StudentRepository;
import ca.mcgill.ecse321.cooperator.dto.CoopStatisticsDto;
import ca.mcgill.ecse321.cooperator.dto.ReportStatisticsDto;
import ca.mcgill.ecse321.cooperator.model.Administrator;
import ca.mcgill.ecse321.cooperator.model.Coop;
import ca.mcgill.ecse321.cooperator.model.CoopStatus;
import ca.mcgill.ecse321.cooperator.model.Employer;
import ca.mcgill.ecse321.cooperator.model.Notification;
import ca.mcgill.ecse321.cooperator.model.Report;
import ca.mcgill.ecse321.cooperator.model.ReportStatus;
import ca.mcgill.ecse321.cooperator.model.ReportType;
import ca.mcgill.ecse321.cooperator.model.Student;

/**
 * This class contains the database service methods. 
 * 
 * @author Albert Kragl
 * @author Emma Eagles
 * @author Elliot Bourachot
 * @author Matthew Kourlas
 * @author Susan Matuszewski
 */
@Service
public class CooperatorService {

	@Autowired(required = true)
	CoopRepository coopRepository;
	@Autowired(required = true)
	EmployerRepository employerRepository;
	@Autowired(required = true)
	ReportRepository reportRepository;
	@Autowired(required = true)
	StudentRepository studentRepository;
	@Autowired(required = true)
	ProfileRepository profileRepository;
	@Autowired(required = true)
	NotificationRepository notificationRepository;
	@Autowired(required = true)
	AdministratorRepository administratorRepository;

	/**
	 * Creates and adds a new Student object to the database. 
	 * 
	 * @param email
	 * @param name
	 * @param password
	 * @param phone
	 * @param id
	 * @return Student object
	 */
	@Transactional
	public Student createStudent(String email, String name, String password, String phone, Integer id) {
		Student p = new Student();
		String error = "";
		if (name == null || name.trim().length() == 0) {
			error = "Student name cannot be empty! ";
		}
		if (email == null || email.trim().length() == 0) {
			error = error + "Email cannot be empty! ";
		}
		if (password == null || password.trim().length() == 0) {
			error = error + "Password cannot be empty! ";
		}
		if (phone == null || phone.trim().length() == 0) {
			error = error + "Phone cannot be empty! ";
		}
		if (id < 0) {
			error = error + "ID is invalid!";
		}
		if (error.length() != 0) {
			throw new IllegalArgumentException(error);
		}
		p.setEmail(email);
		p.setName(name);
		p.setPassword(password);
		p.setPhone(phone);
		p.setId(id);
		studentRepository.save(p);
		return p;
	}

	/**
	 * Creates and adds an Employer object to the database. 
	 * 
	 * @param email
	 * @param name
	 * @param password
	 * @param phone
	 * @param company
	 * @return Employer object 
	 */
	@Transactional
	public Employer createEmployer(String email, String name, String password, String phone, String company) {
		Employer e = new Employer();

		String error = "";
		if (name == null || name.trim().length() == 0) {
			error = "Employer name cannot be empty! ";
		}
		if (email == null || email.trim().length() == 0) {
			error = error + "Email cannot be empty! ";
		}
		if (password == null || password.trim().length() == 0) {
			error = error + "Password cannot be empty! ";
		}
		if (phone == null || phone.trim().length() == 0) {
			error = error + "Phone cannot be empty! ";
		}
		if (company == null || phone.trim().length() == 0) {
			error = error + "Company cannot be empty! ";
		}
		if (error.length() != 0) {
			throw new IllegalArgumentException(error);
		}
		e.setEmail(email);
		e.setName(name);
		e.setPassword(password);
		e.setPhone(phone);
		e.setCompany(company);
		employerRepository.save(e);
		return e;
	}

	/**
	 * Creates and adds an Administrator object to the database. 
	 * 
	 * @param email
	 * @param name
	 * @param password
	 * @param phone
	 * @return Adminstrator object 
	 */
	@Transactional
	public Administrator createAdmin(String email, String name, String password, String phone) {
		Administrator a = new Administrator();

		String error = "";
		if (name == null || name.trim().length() == 0) {
			error = "Administrator name cannot be empty! ";
		}
		if (email == null || email.trim().length() == 0) {
			error = error + "Email cannot be empty! ";
		}
		if (password == null || password.trim().length() == 0) {
			error = error + "Password cannot be empty! ";
		}
		if (phone == null || phone.trim().length() == 0) {
			error = error + "Phone cannot be empty! ";
		}
		if (error.length() != 0) {
			throw new IllegalArgumentException(error);
		}
		a.setEmail(email);
		a.setName(name);
		a.setPassword(password);
		a.setPhone(phone);
		administratorRepository.save(a);
		return a;
	}

	/**
	 * Creates and adds a Coop object to the database. 
	 * 
	 * @param student
	 * @param employer
	 * @param title
	 * @param startDate
	 * @param endDate
	 * @param status
	 * @param salaryPerHour
	 * @param hoursPerWeek
	 * @param address
	 * @return Coop object 
	 */
	@Transactional
	public Coop createCoop(Student student, Employer employer, String title, Date startDate, Date endDate,
			CoopStatus status, Integer salaryPerHour, Integer hoursPerWeek, String address) {
		String error = "";

		if (student == null) {
			error = error + "Student is null! ";
		}
		if (employer == null) {
			error = error + "Employer is null!";
		}
		if (error.length() != 0) {
			throw new IllegalArgumentException(error);
		}
		error = "";
		if (title == null || title.trim().length() == 0) {
			error = error + "Coop title cannot be empty! ";
		}
		if (startDate == null) {
			error = error + "Coop start date cannot be empty! ";
		}
		if (endDate == null) {
			error = error + "Coop end date cannot be empty! ";
		}
		if (status == null) {
			error = error + "Coop status invalid! ";
		}
		if (salaryPerHour <= 0 || salaryPerHour == null) {
			error = error + "Salary per hour is invalid! ";
		}
		if (hoursPerWeek <= 0 || hoursPerWeek == null) {
			error = error + "Hours per week is invalid! ";
		}

		if (address == null || address.trim().length() == 0) {
			error = error + "Address cannot be empty!";
		}

		if (error.length() != 0) {
			throw new IllegalArgumentException(error);
		}
		error = "";
		if (startDate.after(endDate)) {
			throw new IllegalArgumentException("Coop end time cannot be before Coop start time!");
		}
		Coop c = new Coop();
		c.setEmployer(employer);
		c.setStudent(student);
		c.setTitle(title);
		c.setStartDate(startDate);
		c.setEndDate(endDate);
		c.setStatus(status);
		c.setSalaryPerHour(salaryPerHour);
		c.setHoursPerWeek(hoursPerWeek);
		c.setAddress(address);
		
		coopRepository.save(c);
		//create mandatory reports
		Set<Report> mandatory = new HashSet<>();
		mandatory.add(createReport(c, startDate, ReportStatus.Unsubmitted, ReportType.Contract, null));
		mandatory.add(createReport(c, startDate, ReportStatus.Unsubmitted, ReportType.EmployerEval, null));		
		mandatory.add(createReport(c, startDate, ReportStatus.Unsubmitted, ReportType.StudentEval, null));
		
		c.setReport(mandatory);		
		reportRepository.saveAll(mandatory);
		coopRepository.save(c);
		
		return c;
	}

	/**
	 * Returns the Coop with specified ID from the database. 
	 * 
	 * @param id
	 * @return Coop object 
	 */
	@Transactional
	public Coop getCoop(Integer id) {
		Coop c = coopRepository.findCoopById(id);
		return c;
	}

	/**
	 * Returns all Coops with specified status from the database. 
	 * 
	 * @param status
	 * @return List of Coop objects
	 */
	@Transactional
	public List<Coop> getCoopsByStatus(CoopStatus status) {
		List<Coop> coop = toList(coopRepository.findCoopByStatus(status));

		return coop;
	}

	/**
	 * Returns all Reports with specified type from the database. 
	 * 
	 * @param type
	 * @return List of Report objects
	 */
	@Transactional
	public List<Report> getReportByType(ReportType type) {
		List<Report> report = toList(reportRepository.findByType(type));

		return report;
	}

	/**
	 * Returns all Reports with specified status from the database.
	 * 
	 * @param status
	 * @return List of Report objects
	 */
	@Transactional
	public List<Report> getReportByStatus(ReportStatus status) {
		List<Report> report = toList(reportRepository.findByStatus(status));

		return report;
	}

	/**
	 * Returns all Coops in the database. 
	 * 
	 * @return List of Coop objects
	 */
	@Transactional
	public List<Coop> getAllCoops() {
		return toList(coopRepository.findAll());
	}

	/**
	 * Returns all Coops associated with the specified company from the database. 
	 * 
	 * @param company
	 * @return List of Coop objects
	 */
	@Transactional
	public List<Coop> getCoopsOfCompany(String company) {
		List<Coop> all = toList(coopRepository.findAll());
		for (Coop c : all) {
			if (c.getEmployer().getCompany() != company) {
				all.remove(c);
			}
		}
		return all;
	}

	/**
	 * Deletes the specified Report from the database.
	 * 
	 * @param r
	 */
	@Transactional
	public void deleteReport(Report r) {
		Coop c = r.getCoop();
		Set<Report> all = c.getReport();
		all.remove(r);
		c.setReport(all);
		coopRepository.save(c);
		reportRepository.delete(r);
	}
	
	/**
	 * Updates the specified Report's status in the database. 
	 * 
	 * @param r
	 * @param s
	 * @return Report object
	 */
	@Transactional
	public Report updateReportStatus(Report r, ReportStatus s) {
		r.setStatus(s);
		reportRepository.save(r);
		return r;
	}
	
	/**
	 * Updates the specified Report's type in the database. 
	 * 
	 * @param r
	 * @param t
	 * @return Report object
	 */
	@Transactional
	public Report updateReportType(Report r, ReportType t) {
		r.setType(t);
		reportRepository.save(r);
		return r;
	}
	
	/**
	 * Updates the specified Report's due date in the database. 
	 * 
	 * @param r
	 * @param d
	 * @return Report object
	 */
	@Transactional
	public Report updateReportDate(Report r, Date d) {
		r.setDueDate(d);
		reportRepository.save(r);
		return r;
	}
	
	/**
	 * Updates the specified Coop's status in the database. 
	 * 
	 * @param c
	 * @param s
	 * @return Coop object 
	 */
	@Transactional
	public Coop updateCoopStatus(Coop c, CoopStatus s) {
		c.setStatus(s);
		coopRepository.save(c);
		return c;
	}
	
	/**
	 * Updates the specified Coop's title in the database. 
	 *  
	 * @param s
	 * @return Set of Coop objects
	 */
	@Transactional
	public Coop updateCoopTitle(Coop c, String s) {
		c.setTitle(s);
		coopRepository.save(c);
		return c;
	}
	
	/**
	 * Updates the specified Coop's start date in the database. 
	 * 
	 * @param c
	 * @param d
	 * @return
	 */
	@Transactional
	public Coop updateCoopStart(Coop c, Date d) {
		c.setStartDate(d);
		coopRepository.save(c);
		return c;
	}
	
	/**
	 * Updates the specified Coop's end date in the database. 
	 * 
	 * @param c
	 * @param d
	 * @return
	 */
	@Transactional
	public Coop updateCoopEnd(Coop c, Date d) {
		c.setEndDate(d);
		coopRepository.save(c);
		return c;
	}
	
	/**
	 * Updates the specified Coop's employer in the database. 
	 * 
	 * @param c
	 * @param e
	 * @return
	 */
	@Transactional
	public Coop updateCoopEmp(Coop c, Employer e) {
		c.setEmployer(e);
		coopRepository.save(c);
		return c;
	}
	
	/**
	 * Updates the specified Coop's pay in the database. 
	 * 
	 * @param c
	 * @param p
	 * @return
	 */
	@Transactional
	public Coop updateCoopPay(Coop c, Integer p) {
		c.setSalaryPerHour(p);
		coopRepository.save(c);
		return c;
	}
	
	/**
	 * Updates the specified Coop's hours in the database. 
	 * @param c
	 * @param h
	 * @return
	 */
	@Transactional
	public Coop updateCoopHours(Coop c, Integer h) {
		c.setHoursPerWeek(h);
		coopRepository.save(c);
		return c;
	}
	
	/**
	 * Updates the specified Coop's address in the database. 
	 * @param c
	 * @param a
	 * @return
	 */
	@Transactional
	public Coop updateCoopAddress(Coop c, String a) {
		c.setAddress(a);
		coopRepository.save(c);
		return c;
	}
	
	/**
	 * Returns the set of coop objects belonging to a student
	 *  
	 * @param s
	 * @return Set of Coop objects
	 */
	@Transactional
	public Set<Coop> getCoopforStudent(Student s){
		if(s == null) {
			throw new IllegalArgumentException("Student is null!");
		}
		Set<Coop> stuCoops = coopRepository.findCoopByStudent(s);
		return stuCoops;
	}
	
	/**
	 * Returns all Coops for the specified employer in the database.
	 * 
	 * @param e
	 * @return Set of Coop objects
	 */
	@Transactional
	public Set<Coop> getCoopforEmployer(Employer e){
		if(e == null) {
			throw new IllegalArgumentException("Employer is null!");
		}
		Set<Coop> stuCoops = coopRepository.findCoopByEmployer(e);
		return stuCoops;
	}

	/**
	 * Returns the Student with specified email from the database. 
	 * 
	 * @param email
	 * @return Student object
	 */
	@Transactional
	public Student getStudent(String email) {
		if (email == null || email.trim().length() == 0) {
			throw new IllegalArgumentException("Student email cannot be empty!");
		}
		Student s = studentRepository.findStudentByEmail(email);
		return s;
	}
	
	/**
	 * Returns the Employer with specified email from the database. 
	 * 
	 * @param email
	 * @return Employer object
	 */
	@Transactional
	public Employer getEmployer(String email) {
		if (email == null || email.trim().length() == 0) {
			throw new IllegalArgumentException("Employer email cannot be empty!");
		}

		Employer e = employerRepository.findEmployerByEmail(email);
		return e;
	}

	/**
	 * Returns the Administrator with specified email from the database. 
	 * 
	 * @param email
	 * @return Administrator object
	 */
	@Transactional
	public Administrator getAdmin(String email) {
		if (email == null || email.trim().length() == 0) {
			throw new IllegalArgumentException("Administrator email cannot be empty!");
		}
		Administrator a = administratorRepository.findAdministratorByEmail(email);
		return a;
	}

	/**
	 * Returns all students in the database.
	 * 
	 * @return List of Student objects
	 */
	@Transactional
	public List<Student> getAllStudents() {
		return toList(studentRepository.findAll());
	}

	/**
	 * Returns all employers in the database.
	 * 
	 * @return List of Employer objects
	 */
	@Transactional
	public List<Employer> getAllEmployers() {
		return toList(employerRepository.findAll());
	}
	
	/**
	 * Returns all coop corresponding to a report.
	 * 
	 * @return List of Employer objects
	 */
	@Transactional
	public Coop getCoopFromReport(Integer id) {
		Report r = reportRepository.findReportByid(id);
		Coop c = r.getCoop();
		return c;
	}

	/**
	 * Returns all employers associated with the specified company in the database. 
	 * 
	 * @param company
	 * @return List of Employer objects
	 */
	@Transactional
	public List<Employer> getEmployersOfCompany(String company) {
		return toList(employerRepository.findEmployerByCompany(company));
	}

	/**
	 * Returns the total number of profiles in the database
	 * 
	 * @return int
	 */
	@Transactional
	public int getNumberofProfiles() {
		int num = (int) studentRepository.count();
		num += (int) employerRepository.count();
		num += (int) administratorRepository.count();
		return num;
	}

	/**
	 * Returns all co-op Administrators in the database.
	 * 
	 * @return List of Administrator objects
	 */
	@Transactional
	public List<Administrator> getAllAdministrators() {
		return toList(administratorRepository.findAll());
	}

	/**
	 * Creates a notification in the database with the specified parameters. 
	 * 
	 * @param text
	 * @param a
	 * @param s
	 * @param e
	 * @return Notification object
	 */
	@Transactional
	public Notification createNotification(String text, Administrator a, Student s, Employer e) {
		String error = "";
		if (a == null) {
			error = error + "Administrator is null! ";
		}
		if (s == null && e == null) {
			error = error + "Notification needs at least one recipient! ";
		}
		if (text == null || text.trim().length() == 0) {
			error = error + "Text is invalid!";
		}
		if (error.length() != 0) {
			throw new IllegalArgumentException(error);
		}

		Notification n = new Notification();
		n.setText(text);
		n.setSender(a);
		n.setEmployer(e);
		n.setStudent(s);

		if (a != null) {
			Set<Notification> notifs = a.getSent();
			if (notifs == null)
				notifs = new HashSet<>();
			notifs.add(n);
			a.setSent(notifs);
			administratorRepository.save(a);
		}

		if (e != null) {
			Set<Notification> notifs = e.getEmployerReceived();
			if (notifs == null)
				notifs = new HashSet<>();
			notifs.add(n);
			e.setEmployerReceived(notifs);
			employerRepository.save(e);
		}
		if (s != null) {
			Set<Notification> notifs = s.getStudentReceived();
			if (notifs == null)
				notifs = new HashSet<>();
			notifs.add(n);
			s.setStudentReceived(notifs);
			studentRepository.save(s);
		}
		
		notificationRepository.save(n);
		return n;
	}

	/**
	 * Returns all notifications for the specified Employer in the database. 
	 * 
	 * @param e
	 * @return Set of Notification objects
	 */
	@Transactional
	public Set<Notification> getNotificationsEmp(Employer e) {
		Set<Notification> n = null;
		if (e == null) {
			throw new IllegalArgumentException("Profile cannot be null!");
		} else {
			n = e.getEmployerReceived() == null ? new HashSet<>() : e.getEmployerReceived();
		}
		return n;
	}

	/**
	 * Returns all notifications for the specified Student in the database. 
	 * 
	 * @param s
	 * @return Set of Notification objects
	 */
	@Transactional
	public Set<Notification> getNotificationsStu(Student s) {
		Set<Notification> n = null;
		if (s == null) {
			throw new IllegalArgumentException("Profile cannot be null!");
		} else {
			n = s.getStudentReceived() == null ? new HashSet<>() : s.getStudentReceived();
		}
		return n;
	}

	/**
	 * Returns all notifications for the specified co-op Administrator in the database. 
	 * 
	 * @param a
	 * @return Set of Notification objects
	 */
	@Transactional
	public Set<Notification> getNotificationsAdm(Administrator a) {
		Set<Notification> n = null;
		if (a == null) {
			throw new IllegalArgumentException("Profile cannot be null!");
		} else {
			n = a.getSent() == null ? new HashSet<>() : a.getSent();
		}
		return n;
	}

	/**
	 * Returns all Notifications in the database. 
	 * 
	 * @return List of Notification objects
	 */
	@Transactional
	public List<Notification> getAllNotifications() {
		return toList(notificationRepository.findAll());
	}

	/**
	 * Creates a report with specified parameters in the database. 
	 * 
	 * @param coop
	 * @param date
	 * @param status
	 * @param type
	 * @param file
	 * @return Report object 
	 */
	@Transactional
	public Report createReport(Coop coop, Date date, ReportStatus status, ReportType type, File file) {
		String error = "";
		if (coop == null) {
			error = error + "Coop is null! ";
		}
		if (date == null) {
			error = error + "Due date is invalid! ";
		}
		if (status == null) {
			error = error + "Status is invalid! ";
		}
		if (type == null) {
			error = error + "Type is invalid!";
		}
		if (error.length() != 0) {
			throw new IllegalArgumentException(error);
		}

		Report r = new Report();
		r.setCoop(coop);
		r.setDueDate(date);
		r.setStatus(status);
		r.setType(type);
		r.setFile(file);
		Set<Report> reports = coop.getReport();
		if (reports == null) reports = new HashSet<>();
		reports.add(r);
		coop.setReport(reports);
		reportRepository.save(r);
		return r;
	}

	/**
	 * Returns the report with specified ID in the database. 
	 * 
	 * @param id
	 * @return Report object
	 */
	@Transactional
	public Report getReport(Integer id) {
		Report r = reportRepository.findReportByid(id);
		return r;
	}

	/**
	 * Returns all reports in the database. 
	 * 
	 * @return List of Report objects
	 */
	@Transactional
	public List<Report> getAllReports() {
		return toList(reportRepository.findAll());
	}

	private <T> List<T> toList(Iterable<T> iterable) {
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}

	/**
	 * Generates statistics that can be filtered by a range of terms (i.e Winter2018 to
	 * Fall2019) and/or a specific coop (i.e only students doing their 1st coop).
	 * 
	 * @param startTerm
	 * @param endTerm
	 * @param coopNumber
	 * @return CoopStatisticsDto object
	 */
	public CoopStatisticsDto generateAllCoopStatistics(String startTerm, String endTerm, Integer coopNumber) {
		CoopStatisticsDto csd = new CoopStatisticsDto();
		csd.setStartTerm(startTerm);
		csd.setEndTerm(endTerm);
		csd.setCoopNumber(coopNumber);
		List<Coop> coops = (List<Coop>) coopRepository.findAll();
		List<Coop> toRemove = new ArrayList<Coop>();
		// Filter out anything before startTerm
		String startSeason = csd.extractSeason(startTerm);
		String startYear = csd.extractYear(startTerm);
		if (startSeason != "" && startYear != "") {
			Date startDate = csd.getStartDate(startSeason, startYear);
			for (Coop coop : coops) {
				// If the coop starts before the start of the term
				if (coop.getStartDate().before(startDate) && !coop.getStartDate().equals(startDate)) {
					toRemove.add(coop);
				}
			}
		}

		// Filter out anything after the endTerm
		String endSeason = csd.extractSeason(endTerm);
		String endYear = csd.extractYear(endTerm);
		if (endSeason != "" && endYear != "") {
			Date endDate = csd.getEndDate(endSeason, endYear);
			for (Coop coop : coops) {
				// If the coop ends after the end of the term
				if (coop.getEndDate().after(endDate) && !coop.getEndDate().equals(endDate)) {
					toRemove.add(coop);
				}
			}
		}

		// Filter out students who aren't on their [coopNumber] coop
		if (coopNumber != 0) {
			for (Coop coop : coops) {

				int completed;
				if (coop.getStudent().getCoopsCompleted() == null) {
					completed = 0;
				} else {
					completed = coop.getStudent().getCoopsCompleted();
				}

				if (!(completed == coopNumber - 1)) { // If the student is on there [coopNumber] coop
					toRemove.add(coop);
				}
			}
		}
		// Fill statistics
		coops.removeAll(toRemove);
		for (Coop coop : coops) {
			csd.setTotalCoops(csd.getTotalCoops() + 1);
			switch (coop.getStatus()) {
			case NotStarted:
				csd.setNotStartedCoops(csd.getNotStartedCoops() + 1);
				break;
			case InProgress:
				csd.setInProgressCoops(csd.getInProgressCoops() + 1);
				break;
			case Completed:
				csd.setCompletedCoops(csd.getCompletedCoops() + 1);
				break;
			default:
				break;
			}
		}
		return csd;
	}
	
	/**
	 * Generates report statistics that can be filtered by a range of terms (i.e Winter2018 
	 * to Fall2019) and/or a specific report (i.e only students doing their 1st coop).
	 * 
	 * @param startTerm
	 * @param endTerm
	 * @param coopNumber
	 * @return ReportStatisticsDto object
	 */
	public ReportStatisticsDto generateAllReportStatistics(String startTerm, String endTerm, Integer coopNumber) {
		ReportStatisticsDto rsd = new ReportStatisticsDto();
		rsd.setStartTerm(startTerm);
		rsd.setEndTerm(endTerm);
		rsd.setCoopNumber(coopNumber);
		List<Report> reports = (List<Report>) reportRepository.findAll();

		List<Report> toRemove = new ArrayList<Report>();
		// Filter out anything before startTerm
		String startSeason = rsd.extractSeason(startTerm);
		String startYear = rsd.extractYear(startTerm);
		if (startSeason != "" && startYear != "") {
			Date startDate = rsd.getStartDate(startSeason, startYear);
			for (Report report : reports) {
				// If the coop starts before the start of the term
				if (report.getCoop().getStartDate().before(startDate)
						&& !report.getCoop().getStartDate().equals(startDate)) {
					toRemove.add(report);
				}
			}
		}

		// Filter out anything after the endTerm
		String endSeason = rsd.extractSeason(endTerm);
		String endYear = rsd.extractYear(endTerm);
		if (endSeason != "" && endYear != "") {
			Date endDate = rsd.getEndDate(endSeason, endYear);
			for (Report report : reports) {
				// If the report ends after the end of the term
				if (report.getCoop().getEndDate().after(endDate) && !report.getCoop().getEndDate().equals(endDate)) {
					toRemove.add(report);
				}
			}
		}

		// Filter out students who aren't on their [coopNumber] report
		if (coopNumber != 0) {
			for (Report report : reports) {

				int completed;
				if (report.getCoop().getStudent().getCoopsCompleted() == null) {
					completed = 0;
				} else {
					completed = report.getCoop().getStudent().getCoopsCompleted();
				}

				if (!(completed == coopNumber - 1)) { // if the student is on there [reportNumber] report
					toRemove.add(report);
				}
			}
		}

		// Fill statistics
		reports.removeAll(toRemove);
		for (Report report : reports) {
			rsd.setTotalReports(rsd.getTotalReports() + 1);
			switch (report.getStatus()) {
			case Unsubmitted:
				rsd.setUnsubmittedReports(rsd.getUnsubmittedReports() + 1);
				break;
			case Submitted:
				rsd.setSubmittedReports(rsd.getSubmittedReports() + 1);
				break;
			case Late:
				rsd.setLateReports(rsd.getLateReports() + 1);
				break;
			case Reviewed:
				rsd.setReviewedReports(rsd.getReviewedReports() + 1);
			default:
				break;
			}
			switch (report.getType()) {
			case Contract:
				rsd.setContractReports(rsd.getContractReports() + 1);
				break;
			case Technical:
				rsd.setTechnicalReports(rsd.getTechnicalReports() + 1);
				break;
			case TwoWeek:
				rsd.setTwoWeekReports(rsd.getTwoWeekReports() + 1);
				break;
			case StudentEval:
				rsd.setStudentEvalReports(rsd.getStudentEvalReports() + 1);
				break;
			case EmployerEval:
				rsd.setEmployerEvalReports(rsd.getEmployerEvalReports() + 1);
			default:
				break;
			}
		}
		return rsd;
	}

	/**
	 * Deletes the student with specified email from the database.
	 * 
	 * @param email
	 */
	@Transactional
	public void deleteStudent(String email) {
		Student s = studentRepository.findStudentByEmail(email);
		
		while(s.getStudentReceived().size() != 0) {
			Set<Notification> notifs = s.getStudentReceived();
			Notification n = notifs.iterator().next();
			deleteNotif(n.getId());
			notificationRepository.saveAll(notifs);
		}	
		
		while( getCoopforStudent(s).size() != 0) {
			Set<Coop> coops = getCoopforStudent(s);
			Coop c = coops.iterator().next();
			deleteCoop(c.getId());
		}
		studentRepository.delete(s);
	}

	/**
	 * Deletes the Coop with specified ID from the database. 
	 * 
	 * @param id
	 */
	@Transactional
	public void deleteCoop(Integer id) {
		Coop c = coopRepository.findCoopById(id);
		while(c.getReport().size() != 0) {
			Set<Report> reports = c.getReport();
			Report r = reports.iterator().next();
			deleteReport(r);
		}
	
		Student s = c.getStudent();
		s.getCoop().remove(c);
		Employer e = c.getEmployer();
		e.getCoop().remove(c);
		
		employerRepository.save(e);
		studentRepository.save(s);
		coopRepository.delete(c);
	}
	
	/**
	 * Deletes the Employer with specified email from the database. 
	 * 
	 * @param email
	 */
	@Transactional
	public void deleteEmployer(String email) {
		Employer e = employerRepository.findEmployerByEmail(email);
		
		while(e.getEmployerReceived().size() != 0) {
			Set<Notification> notifs = e.getEmployerReceived();
			Notification n = notifs.iterator().next();
			deleteNotif(n.getId());
			notificationRepository.saveAll(notifs);
		}	
		
		while( getCoopforEmployer(e).size() != 0) {
			Set<Coop> coops = getCoopforEmployer(e);
			Coop c = coops.iterator().next();
			deleteCoop(c.getId());
		}
		employerRepository.delete(e);
	}
	
	/**
	 * Deletes the co-op Administrator with specified email from the database. 
	 * 
	 * @param email
	 */
	@Transactional
	public void deleteAdmin(String email) {
		Administrator a = administratorRepository.findAdministratorByEmail(email);
		
		while(a.getSent().size() != 0) {
			Set<Notification> notifs = a.getSent();
			Notification n = notifs.iterator().next();
			deleteNotif(n.getId());
			notificationRepository.saveAll(notifs);
		}
		
		administratorRepository.delete(a);
	}

	/**
	 * Deletes the Notification with specified ID from the database. 
	 * 
	 * @param id
	 */
	@Transactional
	public void deleteNotif(Integer id) {
		Notification n = notificationRepository.findByid(id);
		
		// Delete from admin
		Administrator a = n.getSender();
		Set<Notification> notifsA = a.getSent();
		notifsA.remove(n);
		a.setSent(notifsA);
		administratorRepository.save(a);
		
		// Delete from student
		Student s = n.getStudent();
		if(s != null) {
			Set<Notification> notifsS = s.getStudentReceived();
			notifsS.remove(n);
			s.setStudentReceived(notifsS);
			studentRepository.save(s);
		}
				
		// Delete from employer
		Employer e = n.getEmployer();
		if(e != null) {
			Set<Notification> notifsE = e.getEmployerReceived();
			notifsE.remove(n);
			e.setEmployerReceived(notifsE);	
			employerRepository.save(e);
		}
		
		notificationRepository.deleteById(id);
	}

}