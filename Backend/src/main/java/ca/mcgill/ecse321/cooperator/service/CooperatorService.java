package ca.mcgill.ecse321.cooperator.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.cooperator.dao.AdministratorRepository;
import ca.mcgill.ecse321.cooperator.dao.CoopRepository;
import ca.mcgill.ecse321.cooperator.dao.EmployerRepository;
import ca.mcgill.ecse321.cooperator.dao.ReportRepository;
import ca.mcgill.ecse321.cooperator.dao.NotificationRepository;
import ca.mcgill.ecse321.cooperator.dao.ProfileRepository;
import ca.mcgill.ecse321.cooperator.dao.StudentRepository;
import ca.mcgill.ecse321.cooperator.dto.CoopStatisticsDto;
import ca.mcgill.ecse321.cooperator.dto.ReportStatisticsDto;
import ca.mcgill.ecse321.cooperator.model.Administrator;
import ca.mcgill.ecse321.cooperator.model.Coop;
import ca.mcgill.ecse321.cooperator.model.CoopStatus;
import ca.mcgill.ecse321.cooperator.model.Employer;
import ca.mcgill.ecse321.cooperator.model.Report;
import ca.mcgill.ecse321.cooperator.model.ReportStatus;
import ca.mcgill.ecse321.cooperator.model.ReportType;
import ca.mcgill.ecse321.cooperator.model.Notification;
import ca.mcgill.ecse321.cooperator.model.Profile;
import ca.mcgill.ecse321.cooperator.model.Student;

@Service
public class CooperatorService {

	@Autowired (required = true)
	CoopRepository coopRepository;
	@Autowired (required = true)
	EmployerRepository employerRepository;
	@Autowired (required = true)
	ReportRepository reportRepository;
	@Autowired (required = true)
	StudentRepository studentRepository;
	@Autowired (required = true)
	ProfileRepository profileRepository;
	@Autowired (required = true)
	NotificationRepository notificationRepository;
	@Autowired (required = true)
	AdministratorRepository administratorRepository;

	@Transactional
	public Student createStudent(String email, String name, String password, String phone, Integer id) {
		Student p = new Student();
		String error = "";
		if(name == null || name.trim().length() == 0) {
			error = "Student name cannot be empty! ";
	    }
		if(email == null || email.trim().length() == 0) {
			error =  error + "Email cannot be empty! ";
		}
		if(password == null || password.trim().length() == 0) {
			error =  error + "Password cannot be empty! ";
		}
		if(phone == null || phone.trim().length() == 0) {
			error =  error + "Phone cannot be empty! ";
		}
		if(id < 0) {
			error =  error + "ID is invalid!";
		}
		if(error.length() != 0) {
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
	
	@Transactional
	public Employer createEmployer(String email, String name, String password, String phone, Integer id) {
		Employer e = new Employer();
		
		String error = "";
		if(name == null || name.trim().length() == 0) {
			error = "Employer name cannot be empty! ";
		}
		if(email == null || email.trim().length() == 0) {
			error =  error + "Email cannot be empty! ";
		}
		if(password == null || password.trim().length() == 0) {
			error =  error + "Password cannot be empty! ";
		}
		if(phone == null || phone.trim().length() == 0) {
			error =  error + "Phone cannot be empty! ";
		}
		if(id < 0) {
			error =  error + "ID is invalid!";
		}
		if(error.length() != 0) {
			throw new IllegalArgumentException(error);
		}
		e.setEmail(email);
		e.setName(name);
		e.setPassword(password);
		e.setPhone(phone);
		e.setId(id);
		employerRepository.save(e);
		return e;
	}
	
	@Transactional
	public Administrator createAdmin(String email, String name, String password, String phone, Integer id) {
		Administrator a = new Administrator();
		
		String error = "";
		if(name == null || name.trim().length() == 0) {
			error = "Administrator name cannot be empty! ";
			}
		if(email == null || email.trim().length() == 0) {
			error =  error + "Email cannot be empty! ";
			}
		if(password == null || password.trim().length() == 0) {
			error =  error + "Password cannot be empty! ";
			}
		if(phone == null || phone.trim().length() == 0) {
			error =  error + "Phone cannot be empty! ";
			}
		if(id < 0) {
			error =  error + "ID is invalid!";
		}
		if(error.length() != 0) {
			throw new IllegalArgumentException(error);
		}
		a.setEmail(email);
		a.setName(name);
		a.setPassword(password);
		a.setPhone(phone);
		a.setId(id);
		administratorRepository.save(a);
		return a;
	}

	@Transactional 
	public Coop createCoop(Student student, Employer employer, String title, Integer id, Date startDate, Date endDate, CoopStatus status, Integer salaryPerHour, Integer hoursPerWeek, String address) {
		String error = "";

		if(student == null) {
			error = error + "Student is null! ";
		}
		if(employer == null) {
			error = error + "Employer is null!";
		}
		if(error.length()!= 0) {
			throw new IllegalArgumentException(error);
		}
		error = "";
		if(id < 0) {
			error = error + "ID is invalid! ";
		}
		if(title == null || title.trim().length() == 0) {
			error = error + "Coop title cannot be empty! ";
		}
		if(startDate == null) {
			error = error + "Coop start date cannot be empty! ";
		}
		if(endDate == null) {
			error = error + "Coop end date cannot be empty! ";
		}
		if(status == null) {
			error = error + "Coop status invalid! ";
		}
		if(salaryPerHour <= 0 || salaryPerHour == null) {
			error = error + "Salary per hour is invalid! ";
		}
		if(hoursPerWeek <= 0 || hoursPerWeek == null ) {
			error = error + "Hours per week is invalid! ";
		}

		if(address == null || address.trim().length() ==  0) {
			error = error + "Address cannot be empty!";
		}

		if(error.length()!= 0) {
			throw new IllegalArgumentException(error);
		}
		error = "";
		if(startDate.after(endDate)) {
			throw new IllegalArgumentException("Coop end time cannot be before Coop start time!");
		}
		Coop c = new Coop();
		c.setId(id);
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
		return c;
	}
	
	@Transactional 
	public Coop getCoop(Integer id) {
		Coop c = coopRepository.findCoopById(id);
		return c;
	}
	
	@Transactional 
	public List<Coop> getCoopsByStatus(CoopStatus status){
		List<Coop> coop = toList(coopRepository.findCoopByStatus(status));
		
		return coop;
	} 

	@Transactional 
	public List<Report> getReportByType(ReportType type){
		List<Report> report = toList(reportRepository.findByType(type));
		
		return report;
	}
	
	@Transactional 
	public List<Report> getReportByStatus(ReportStatus status){
		List<Report> report = toList(reportRepository.findByStatus(status));
		
		return report;
	}
	
	@Transactional
	public List<Coop> getAllCoops() {
		return toList(coopRepository.findAll());
	}
	
	@Transactional
	public Set<Coop> getCoopforStudent(Student s){
		if(s == null) {
			throw new IllegalArgumentException("Student is null!");
		}
		Set<Coop> stuCoops = coopRepository.findCoopByStudent(s);
		return stuCoops;
	}
	
	@Transactional 
	public Student getStudent(String email) {
		if(email == null || email.trim().length() == 0) {
			throw new IllegalArgumentException("Student email cannot be empty!");
		}
		Student s = studentRepository.findStudentByEmail(email);
		return s;
	}
	
	@Transactional 
	public Administrator getAdmin(String email) {
		if(email == null || email.trim().length() == 0) {
			throw new IllegalArgumentException("Administrator email cannot be empty!");
		}
		Administrator a = administratorRepository.findAdministratorByEmail(email);
		return a;
	}
	
	@Transactional
	public List<Student> getAllStudents() {
		return toList(studentRepository.findAll());
	}
	
	@Transactional 
	public Employer getEmployer(String email) {
		if(email == null || email.trim().length() == 0) {
			throw new IllegalArgumentException("Employer email cannot be empty!");
		}
		
		Employer e = employerRepository.findEmployerByEmail(email);
		return e;
	}
	
	@Transactional
	public List<Employer> getAllEmployers() {
		return toList(employerRepository.findAll());
	}
	
	@Transactional
	public int getNumberofProfiles() {
		int num = (int) studentRepository.count();
		num += (int) employerRepository.count();
		num += (int) administratorRepository.count();
		return num;
	}
	
	
	@Transactional
	public List<Administrator> getAllAdministrators() {
		return toList(administratorRepository.findAll());
	}
	
	@Transactional  
	public Notification createNotification(Integer id, String text, Administrator a, Student s, Employer e) {
		String error = "";
		if(a == null) {
			error = error + "Administrator is null! ";
		}
		if(s == null && e == null) {
			error = error + "Notification needs at least one recipient! ";
		}
		if(id == null || id < 0) {
			error = error + "ID is invalid! ";
		}
		if(text == null || text.trim().length() == 0) {
			error = error + "Text is invalid!";
		}
		if(error.length() != 0) {
			throw new IllegalArgumentException(error);
		}
		
		Notification n = new Notification();
		n.setId(id);
		n.setText(text);
		n.setSender(a);
		n.setEmployer(e);
		n.setStudent(s);
		
		/* COMMENT THESE THREE IF STATEMENTS OUT FOR RESTFUL NOTIFCATION CREATE TO WORK */
		
		if (a != null) {
			Set<Notification> notifs = a.getSent();
			if (notifs == null) notifs = new HashSet<>();
			notifs.add(n);
			a.setSent(notifs);
		}

		if (e != null) {
			Set<Notification> notifs = e.getReceived();
			if (notifs == null) notifs = new HashSet<>();
			notifs.add(n);
			e.setReceived(notifs);
		}
		if (s != null) {
			Set<Notification> notifs = s.getReceived();
			if (notifs == null) notifs = new HashSet<>();
			notifs.add(n);
			s.setReceived(notifs);
		}

		notificationRepository.save(n);
		return n;
	}
	
	public Set<Notification> getNotificationsEmp(Employer e) {
		Set <Notification> n = null;
		if(e == null) {
			throw new IllegalArgumentException("Profile cannot be null!");
		}
		else {
			n = e.getReceived() == null ? new HashSet<>() : e.getReceived();
		}
		return n;
	}
	
	@Transactional  
	public Set<Notification> getNotificationsStu(Student s) {
		Set <Notification> n = null;
		if(s == null) {
			throw new IllegalArgumentException("Profile cannot be null!");
		}
		else {
			n = s.getReceived() == null ? new HashSet<>() : s.getReceived();
		}
		return n;
	} 
	
	@Transactional  
	public Set<Notification> getNotificationsAdm(Administrator a) {
		Set <Notification> n = null;
		if(a == null) {
			throw new IllegalArgumentException("Profile cannot be null!");
		}
		else {
			n = a.getSent() == null ? new HashSet<>() : a.getSent();
		}
		return n;
	} 
	
	@Transactional
	public List<Notification> getAllNotifications() {
		return toList(notificationRepository.findAll());
	}
	
	@Transactional  
	public Report createReport(Integer id, Coop c, Date d, ReportStatus s, ReportType t) {
		String error = "";
		if(id == null || id < 0) {
			error = ("ID is invalid! ");
		}
		if(c == null) {
			error = error + "Coop is null! ";
		}
		if(d == null) {
			error = error + "Due date is invalid! ";
		}
		if(s == null ) {
			error = error + "Status is invalid! ";
		}
		if(t == null) {
			error = error + "Type is invalid!";
		}
		if(error.length() != 0) {
			throw new IllegalArgumentException(error);
		}
		
		Report r = new Report();
		r.setId(id);
		r.setCoop(c);
		r.setDueDate(d);
		r.setStatus(s);
		r.setType(t);
		reportRepository.save(r);
		return r;
	}
	
	@Transactional  
	public Report createReport(Report r) {
		String error = "";
		if(r == null) {
			error = ("Report cannot be null!");
		}
		if(error.length() != 0) {
			throw new IllegalArgumentException(error);
		}

		reportRepository.save(r);
		return r;
	}
	
	@Transactional  
	public Report getReport(Integer id) {
		Report r = reportRepository.findReportByid(id);
		return r;
	}
	
	@Transactional
	public List<Report> getAllReports() {
		return toList(reportRepository.findAll());
	}
	
	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}

	/*
	 * Generates statistics. Can filter by a range of terms (i.e Winter2018 to Fall2019) and/or a specific coop (i.e only students doing there 1st coop)
	 */
	public CoopStatisticsDto generateAllCoopStatistics(String startTerm, String endTerm, Integer coopNumber) {
		CoopStatisticsDto csd = new CoopStatisticsDto();
		csd.setStartTerm(startTerm);
		csd.setEndTerm(endTerm);
		csd.setCoopNumber(coopNumber);
		Iterable<Coop> coops = coopRepository.findAll();
		
		List<Coop> filter1 = new ArrayList<Coop>();
		// filter out anything before startTerm
		String startSeason = csd.extractSeason(startTerm);
		Integer startYear = csd.extractYear(startTerm);
		if (startSeason != "" && startYear != 0) { 
			Date startDate = csd.getStartDate(startSeason, startYear);
			for(Coop coop : coops) {
				if(coop.getStartDate().after(startDate)) { // if the coop start after the start of the term
					filter1.add(coop);
				}
			}
		}
		
		List<Coop> filter2 = new ArrayList<Coop>();
		// filter out anything after the endTerm
		String endSeason = csd.extractSeason(endTerm);
		Integer endYear = csd.extractYear(endTerm);
		if (endSeason != "" && endYear != 0) { 
			Date endDate = csd.getEndDate(endSeason, endYear);
			for(Coop coop : filter1) {
				if(coop.getStartDate().before(endDate)) { // if the coop starts before the end of the term
					filter2.add(coop);
				}
			}
		}
		
		List<Coop> filter3 = new ArrayList<Coop>();
		// filter out students who aren't on their [coopNumber] coop
		if (coopNumber != 0) {
			for(Coop coop: filter2) {
				if(coop.getStudent().getCoopsCompleted() == coopNumber-1) { // if the student is on there [coopNumber] coop
					filter3.add(coop);
				}
			}
		}
		
		// fill statistics
		for (Coop coop: filter3) {
			csd.setTotalCoops(csd.getTotalCoops()+1);
			switch(coop.getStatus()) {
			case NotStarted:
				csd.setNotStartedCoops(csd.getNotStartedCoops()+1);
				break;
			case InProgress:
				csd.setInProgressCoops(csd.getInProgressCoops()+1);
				break;
			case Completed:
				csd.setCompletedCoops(csd.getCompletedCoops()+1);
				break;
			default:
				break;
			}
		}
		return csd;
	}
	
	/*
	 * Generates statistics. Can filter by a range of terms (i.e Winter2018 to Fall2019) and/or a specific report (i.e only students doing there 1st report)
	 */
	public ReportStatisticsDto generateAllReportStatistics(String startTerm, String endTerm, Integer coopNumber) {
		ReportStatisticsDto rsd = new ReportStatisticsDto();
		rsd.setStartTerm(startTerm);
		rsd.setEndTerm(endTerm);
		rsd.setCoopNumber(coopNumber);
		Iterable<Report> reports = reportRepository.findAll();
		
		List<Report> filter1 = new ArrayList<Report>();
		// filter out anything before startTerm
		String startSeason = rsd.extractSeason(startTerm);
		Integer startYear = rsd.extractYear(startTerm);
		if (startSeason != "" && startYear != 0) { 
			Date startDate = rsd.getStartDate(startSeason, startYear);
			for(Report report : reports) {
				if(report.getCoop().getStartDate().after(startDate)) { // if the report start after the start of the term
					filter1.add(report);
				}
			}
		}
		
		List<Report> filter2 = new ArrayList<Report>();
		// filter out anything after the endTerm
		String endSeason = rsd.extractSeason(endTerm);
		Integer endYear = rsd.extractYear(endTerm);
		if (endSeason != "" && endYear != 0) { 
			Date endDate = rsd.getEndDate(endSeason, endYear);
			for(Report report : filter1) {
				if(report.getCoop().getStartDate().before(endDate)) { // if the report starts before the end of the term
					filter2.add(report);
				}
			}
		}
		
		List<Report> filter3 = new ArrayList<Report>();
		// filter out students who aren't on their [reportNumber] report
		if (coopNumber != 0) {
			for(Report report: filter2) {
				if(report.getCoop().getStudent().getCoopsCompleted() == coopNumber-1) { // if the student is on there [reportNumber] report
					filter3.add(report);
				}
			}
		}
		
		// fill statistics
		for (Report report: filter3) {
			rsd.setTotalReports(rsd.getTotalReports()+1);
			switch(report.getStatus()) {
			case Unsubmitted:
				rsd.setUnsubmittedReports(rsd.getUnsubmittedReports()+1);
				break;
			case Submitted:
				rsd.setSubmittedReports(rsd.getSubmittedReports()+1);
				break;
			case Late:
				rsd.setLateReports(rsd.getLateReports()+1);
				break;
			case Reviewed:
				rsd.setReviewedReports(rsd.getReviewedReports()+1);
			default:
				break;
			}
			switch(report.getType()) {
			case Contract:
				rsd.setContractReports(rsd.getContractReports()+1);
				break;
			case Technical:
				rsd.setTechnicalReports(rsd.getTechnicalReports()+1);
				break;
			case TwoWeek:
				rsd.setTwoWeekReports(rsd.getTwoWeekReports()+1);
				break;
			case StudentEval:
				rsd.setStudentEvalReports(rsd.getStudentEvalReports()+1);
				break;
			case EmployerEval:
				rsd.setEmployerEvalReports(rsd.getEmployerEvalReports()+1);
			default:
				break;
			}
		}
		return rsd;
	}
	
}