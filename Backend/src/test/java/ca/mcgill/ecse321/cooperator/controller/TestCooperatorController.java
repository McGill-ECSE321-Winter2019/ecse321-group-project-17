package ca.mcgill.ecse321.cooperator.controller;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import ca.mcgill.ecse321.cooperator.dto.AdminDto;
import ca.mcgill.ecse321.cooperator.dto.CoopDto;
import ca.mcgill.ecse321.cooperator.dto.EmployerDto;
import ca.mcgill.ecse321.cooperator.dto.NotificationDto;
import ca.mcgill.ecse321.cooperator.dto.ReportDto;
import ca.mcgill.ecse321.cooperator.dto.StudentDto;
import ca.mcgill.ecse321.cooperator.model.CoopStatus;
import ca.mcgill.ecse321.cooperator.model.ReportStatus;
import ca.mcgill.ecse321.cooperator.model.ReportType;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TestCooperatorController {

	@LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
    
    private static final AdminDto testAdmin = new AdminDto("admin@mcgill.ca", "pw1", "Admin Person", 1, "5141111111");
    private static final EmployerDto testEmployer = new EmployerDto("employer@company.ca", "pw2", "Employer Person", 2, "5142222222");
    private static final StudentDto testStudent0 = new StudentDto("student@mcgill.ca", "pw3", "Student Person", 260260260, "5143333333");
    private static final StudentDto testStudent1 = new StudentDto("student1@mcgill.ca", "pw2", "Student1", 260222222, "5142222222");
    private static final StudentDto testStudent2 = new StudentDto("student2@mcgill.ca", "pw3", "Student2", 260333333, "5143333333");
    private static final StudentDto testStudent3 = new StudentDto("student3@mcgill.ca", "pw4", "Student3", 260444444, "5144444444");
    @SuppressWarnings("deprecation")
	private static final CoopDto testCoop = new CoopDto(123, "SWE Intern", testStudent0, testEmployer, new Date(2019, 01, 01), new Date(2019, 05, 01),
			CoopStatus.NotStarted, 20, 40, "Montreal");
    private static final NotificationDto testNotification = new NotificationDto(1234, "This is a test notification", testAdmin, testStudent0, testEmployer);
    @SuppressWarnings("deprecation")
	private static final ReportDto testReport = new ReportDto(456, testCoop, new Date(2019, 05, 01), ReportStatus.Submitted, ReportType.Contract);
    
    @Test
    public void testAdminCreate() {
    	AdminDto response = this.restTemplate.postForObject("http://localhost:" + port +
    			"/admin/create?email=admin@mcgill.ca&password=pw1&name=Admin+Person&phone=5141111111&adminId=1",
    			null, AdminDto.class);
    	
    	AdminDto expected = testAdmin;
    	
    	assertThat(response.equals(expected));
    }
    
    @Test
    public void testEmployerCreate() {
    	EmployerDto response = this.restTemplate.postForObject("http://localhost:" + port +
    			"/employer/create?email=employer@company.ca&password=pw2&name=Employer+Person&phone=5142222222&emplId=2",
    			null, EmployerDto.class);
    	
    	EmployerDto expected = testEmployer;
    	
    	assertThat(response.equals(expected));
    }
    
    @Test
    public void testStudentCreate() {
    	StudentDto response = this.restTemplate.postForObject("http://localhost:" + port +
    			"/student/create?email=student@mcgill.ca&password=pw3&name=Student+Person&phone=5143333333&studentId=260260260",
    			null, StudentDto.class);
    	
    	StudentDto expected = testStudent0;
    	
    	assertThat(response.equals(expected));
    }
    
    @Test
    public void testNotifCreate() {
    	
    	//add admin, employer, and student to database and check
    	testAdminCreate();
    	testEmployerCreate();
    	testStudentCreate();
    	
    	NotificationDto response = this.restTemplate.postForObject("http://localhost:" + port +
    			"/notification/create?id=1234&text=This+is+a+test+notification&senderEmail=admin@mcgill.ca&stuEmail=student@mcgill.ca&empEmail=employer@company.ca",
    			null, NotificationDto.class);
    	
    	NotificationDto expected = testNotification;
    	
    	assertThat(response.equals(expected));
    }
    
    @Test
    public void testManyNotifCreate() {
    	
    	//add admin to database and check
    	testAdminCreate();
    	
    	Set<StudentDto> students = new HashSet<StudentDto>();
    	students.add(testStudent1);
    	students.add(testStudent2);
    	students.add(testStudent3);
    	
    	//add each student to the database and check
    	StudentDto responseStu;
    	for(StudentDto student : students) {
    		responseStu = this.restTemplate.postForObject("http://localhost:" + port +
        			"/student/create?email=" + student.getEmail() + "&password=" + student.getPassword() +
        			"&name=" + student.getName() + "&phone=" + student.getPhone() + "&studentId=" + student.getID(),
        			null, StudentDto.class);
    		assertThat(responseStu.equals(student));
    	}
    	
    	String url = "http://localhost:" + port + "/notification/createMany?id=5678"
    			+ "&text=This+is+a+mass+test+notification&senderEmail=admin@mcgill.ca"
    			+ "&stuEmail=student1@mcgill.ca,student2@mcgill.ca,student3@mcgill.ca";

    	//response in JSON
    	String response =  restTemplate.postForObject(url, null, String.class);
    	
    	//expected JSON
    	String expected = "[{\"id\":5678,\"text\":\"This is a mass test notification\",\"sender\":{\"email\":\"admin@mcgill.ca\","
    			+ "\"password\":\"pw1\",\"name\":\"Admin Person\",\"phone\":\"5141111111\",\"id\":1},\"student\":{\"email\":"
    			+ "\"student1@mcgill.ca\",\"password\":\"pw2\",\"name\":\"Student1\",\"id\":260222222,\"phone\":\"5142222222\"},"
    			+ "\"employer\":null},{\"id\":5679,\"text\":\"This is a mass test notification\",\"sender\":{\"email\":\"admin@mcgill.ca\","
    			+ "\"password\":\"pw1\",\"name\":\"Admin Person\",\"phone\":\"5141111111\",\"id\":1},\"student\":{\"email\":"
    			+ "\"student2@mcgill.ca\",\"password\":\"pw3\",\"name\":\"Student2\",\"id\":260333333,\"phone\":\"5143333333\"},"
    			+ "\"employer\":null},{\"id\":5680,\"text\":\"This is a mass test notification\",\"sender\":{\"email\":\"admin@mcgill.ca\","
    			+ "\"password\":\"pw1\",\"name\":\"Admin Person\",\"phone\":\"5141111111\",\"id\":1},\"student\":{\"email\":"
    			+ "\"student3@mcgill.ca\",\"password\":\"pw4\",\"name\":\"Student3\",\"id\":260444444,\"phone\":\"5144444444\"},"
    			+ "\"employer\":null}]";
    	
    	assertThat(response.contentEquals(expected));
    }
    
    @Test
    public void testCoopCreate() {
    	
    	//add employer and student to database and check
    	testEmployerCreate();
    	testStudentCreate();

    	String url = "http://localhost:" + port + "/coop/create?id=123&title=SWE+Intern&stuEmail=student@mcgill.ca"
    			+ "&empEmail=employer@company.ca&start=2019-01-01&end=2019-05-01&status=NotStarted&salaryPerHour=20"
    			+ "&hoursPerWeek=40&address=Montreal";
    	
    	CoopDto response = this.restTemplate.postForObject(url, null, CoopDto.class);
    	
		CoopDto expected = testCoop;
    	
    	assertThat(response.equals(expected));
    }
    
    @Test
    public void testReportCreate() {
    	
    	//add admin, employer, student, and coop to the database and check
    	testCoopCreate();
    	
    	ReportDto response = this.restTemplate.postForObject("http://localhost:" + port +
    			"/report/create?id=456&coopId=123&date=2019-05-01&status=Submitted&type=Contract",
    			null, ReportDto.class);
    	
		ReportDto expected = testReport;
    	
    	assertThat(response.equals(expected));
    }
}
