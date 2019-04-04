package ca.mcgill.ecse321.cooperator.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import ca.mcgill.ecse321.cooperator.dao.AdministratorRepository;
import ca.mcgill.ecse321.cooperator.dao.CoopRepository;
import ca.mcgill.ecse321.cooperator.dao.EmployerRepository;
import ca.mcgill.ecse321.cooperator.dao.NotificationRepository;
import ca.mcgill.ecse321.cooperator.dao.ProfileRepository;
import ca.mcgill.ecse321.cooperator.dao.ReportRepository;
import ca.mcgill.ecse321.cooperator.dao.StudentRepository;
import ca.mcgill.ecse321.cooperator.service.CooperatorService;

//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
//@TestPropertySource(locations="classpath:application-test.properties")
// @DirtiesContext(classMode=DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TestCooperatorController {

	@LocalServerPort
	private int port;

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

	// HTTP POST request
	private String sendPost(String url) throws Exception {
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// add request header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

		String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		return response.toString();
	}

	private List<String> parseResponse(String resp) {
		String delimiters = "(\")*[{}:,](\")*";

		// analyzing the string
		String[] tokensVal = resp.split(delimiters);

		List<String> result = new ArrayList<String>();
		for (String token : tokensVal) {
			result.add(token);
		}
		result.remove(0); // remove first element because it will be a space
		return result;

	}

	private String getParameter(String parameter, List<String> list) {
		for (int index = 0; index < list.size(); index++) {
			if (list.get(index).equalsIgnoreCase(parameter))
				return list.get(index + 1);
		}
		return null;
	}

	/*
	 * @Before @After public void clearDatabase() { reportRepository.deleteAll();
	 * notificationRepository.deleteAll(); coopRepository.deleteAll();
	 * studentRepository.deleteAll(); administratorRepository.deleteAll();
	 * employerRepository.deleteAll(); profileRepository.deleteAll(); }
	 */

	/*
	@Test
	public void testAdminCreate() {
		String email = "admin@gmail.com";
		String password = "hello";
		String name = "sally";
		String phone = "6132143253";
		String url = "http://localhost:" + port + "/admin/create?email=" + email + "&password=" + password + "&name="
				+ name + "&phone=" + phone;

		try {
			String response = sendPost(url);

			List<String> responseList = parseResponse(response);
			String responseEmail = getParameter("email", responseList);
			// Integer expectedTermsRemaining = 0;

			// compare response from request and expected response
			assertEquals(responseEmail, email);
			assertNotNull(response);
			// deleteAdmin(email);
			administratorRepository.deleteAll();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		}

	}

	@Test
	public void testStudentCreate() {
		String email = "student@gmail.com";
		String password = "hello";
		String name = "travis";
		String phone = "6132143255";
		Integer id = 260719281;
		String url = "http://localhost:" + port + "/student/create?email=" + email + "&password=" + password + "&name="
				+ name + "&phone=" + phone + "&studentId=" + id;

		try {
			String response = sendPost(url);

			List<String> responseList = parseResponse(response);
			String responseEmail = getParameter("email", responseList);
			// Integer expectedTermsRemaining = 0;

			// compare response from request and expected response
			assertEquals(responseEmail, email);
			assertNotNull(response);
			System.out.println("hello");
			cs.deleteStudent(email);
			studentRepository.deleteAll();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		}
	}

	@Test
	public void testEmployerCreate() {
		String email = "employer@gmail.com";
		String password = "hello";
		String name = "rainbow";
		String phone = "6132143255";
		String company = "cornflakes";
		String url = "http://localhost:" + port + "/employer/create?email=" + email + "&password=" + password + "&name="
				+ name + "&phone=" + phone + "&company=" + company;

		try {
			String response = sendPost(url);

			List<String> responseList = parseResponse(response);
			String responseEmail = getParameter("email", responseList);
			// Integer expectedTermsRemaining = 0;

			// compare response from request and expected response
			assertEquals(responseEmail, email);
			assertNotNull(response);
			cs.deleteEmployer(email);
			employerRepository.deleteAll();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		}
	}

	@Test
	public void testNotifCreate() {
		String emailA = "admin@gmail.com";
		String passwordA = "hello";
		String nameA = "sally";
		String phoneA = "6132143253";

		try {
			cs.createAdmin(emailA, nameA, passwordA, phoneA);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		String emailE = "employer@gmail.com";
		String passwordE = "hello";
		String nameE = "rainbow";
		String phoneE = "6132143255";
		String companyE = "cornflakes";

		try {
			cs.createEmployer(emailE, nameE, passwordE, phoneE, companyE);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		String emailS = "student@gmail.com";
		String passwordS = "hello";
		String nameS = "travis";
		String phoneS = "6132143255";
		Integer idS = 260719281;

		try {
			cs.createStudent(emailS, nameS, passwordS, phoneS, idS);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		String text = "This+is+a+notification";
		String url = "http://localhost:" + port + "/notification/create?text=" + text + "&senderEmail=" + emailA
				+ "&stuEmail=" + emailS; // + "&empEmail=" + emailE;

		JSONObject jobj = null;
		try {
			String response = sendPost(url);
			System.out.println(response);
			jobj = new JSONObject(response);

			String responseSenderEmail = jobj.getJSONObject("sender").getString("email");
			String responseStuEmail = jobj.getJSONObject("student").getString("email");
			// String responseEmpEmail = jobj.getJSONObject("employer").getString("email");
			Integer id = Integer.valueOf(jobj.getString("id"));

			List<String> responseList = parseResponse(response);
			// System.out.println(responseList);
			String responseText = getParameter("text", responseList);

			// compare response from request and expected response
			assertEquals(responseText, "This is a notification");
			assertEquals(responseSenderEmail, emailA);
			assertEquals(responseStuEmail, emailS);
			// assertEquals(responseEmpEmail, emailE);
			assertNotNull(response);

			cs.deleteNotif(id);
			employerRepository.deleteAll();
			studentRepository.deleteAll();
			administratorRepository.deleteAll();
			// employerRepository.deleteAll();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		}
	}

	/*
	 * @LocalServerPort private int port;
	 * 
	 * @Autowired private TestRestTemplate restTemplate;
	 * 
	 * private static final AdminDto testAdmin = new AdminDto("admin@mcgill.ca",
	 * "pw1", "Admin Person", 1, "5141111111"); private static final EmployerDto
	 * testEmployer = new EmployerDto("employer@company.ca", "pw2",
	 * "Employer Person", 2, "5142222222", "company"); private static final
	 * StudentDto testStudent0 = new StudentDto("student@mcgill.ca", "pw3",
	 * "Student Person", 260260260, "5143333333"); private static final StudentDto
	 * testStudent1 = new StudentDto("student1@mcgill.ca", "pw2", "Student1",
	 * 260222222, "5142222222"); private static final StudentDto testStudent2 = new
	 * StudentDto("student2@mcgill.ca", "pw3", "Student2", 260333333, "5143333333");
	 * private static final StudentDto testStudent3 = new
	 * StudentDto("student3@mcgill.ca", "pw4", "Student3", 260444444, "5144444444");
	 * 
	 * @SuppressWarnings("deprecation") private static final CoopDto testCoop = new
	 * CoopDto(123, "SWE Intern", testStudent0, testEmployer, new Date(2019, 01,
	 * 01), new Date(2019, 05, 01), CoopStatus.NotStarted, 20, 40, "Montreal");
	 * private static final NotificationDto testNotification = new
	 * NotificationDto(1234, "This is a test notification", testAdmin, testStudent0,
	 * testEmployer);
	 * 
	 * @SuppressWarnings("deprecation") private static final ReportDto testReport =
	 * new ReportDto(456, testCoop, new Date(2019, 05, 01), ReportStatus.Submitted,
	 * ReportType.Contract);
	 * 
	 * /*@Test public void testAdminCreate() { AdminDto response =
	 * this.restTemplate.postForObject("http://localhost:" + port +
	 * "/admin/create?email=admin@mcgill.ca&password=pw1&name=Admin+Person&phone=5141111111&adminId=1",
	 * null, AdminDto.class);
	 * 
	 * AdminDto expected = testAdmin;
	 * 
	 * assertThat(response.equals(expected)); }
	 * 
	 * @Test public void testEmployerCreate() { EmployerDto response =
	 * this.restTemplate.postForObject("http://localhost:" + port +
	 * "/employer/create?email=employer@company.ca&password=pw2&name=Employer+Person&phone=5142222222&&company=company&emplId=2",
	 * null, EmployerDto.class);
	 * 
	 * EmployerDto expected = testEmployer;
	 * 
	 * assertThat(response.equals(expected)); }
	 * 
	 * @Test public void testStudentCreate() { StudentDto response =
	 * this.restTemplate.postForObject("http://localhost:" + port +
	 * "/student/create?email=student@mcgill.ca&password=pw3&name=Student+Person&phone=5143333333&studentId=260260260",
	 * null, StudentDto.class);
	 * 
	 * StudentDto expected = testStudent0;
	 * 
	 * assertThat(response.equals(expected)); }
	 * 
	 * @Test public void testNotifCreate() {
	 * 
	 * //add admin, employer, and student to database and check testAdminCreate();
	 * testEmployerCreate(); testStudentCreate();
	 * 
	 * NotificationDto response =
	 * this.restTemplate.postForObject("http://localhost:" + port +
	 * "/notification/create?id=1234&text=This+is+a+test+notification&senderEmail=admin@mcgill.ca&stuEmail=student@mcgill.ca&empEmail=employer@company.ca",
	 * null, NotificationDto.class);
	 * 
	 * NotificationDto expected = testNotification;
	 * 
	 * assertThat(response.equals(expected)); }
	 * 
	 * @Test public void testManyNotifCreate() {
	 * 
	 * //add admin to database and check testAdminCreate();
	 * 
	 * Set<StudentDto> students = new HashSet<StudentDto>();
	 * students.add(testStudent1); students.add(testStudent2);
	 * students.add(testStudent3);
	 * 
	 * //add each student to the database and check StudentDto responseStu;
	 * for(StudentDto student : students) { responseStu =
	 * this.restTemplate.postForObject("http://localhost:" + port +
	 * "/student/create?email=" + student.getEmail() + "&password=" +
	 * student.getPassword() + "&name=" + student.getName() + "&phone=" +
	 * student.getPhone() + "&studentId=" + student.getID(), null,
	 * StudentDto.class); assertThat(responseStu.equals(student)); }
	 * 
	 * String url = "http://localhost:" + port + "/notification/createMany?id=5678"
	 * + "&text=This+is+a+mass+test+notification&senderEmail=admin@mcgill.ca" +
	 * "&stuEmail=student1@mcgill.ca,student2@mcgill.ca,student3@mcgill.ca";
	 * 
	 * //response in JSON String response = restTemplate.postForObject(url, null,
	 * String.class);
	 * 
	 * //expected JSON String expected =
	 * "[{\"id\":5678,\"text\":\"This is a mass test notification\",\"sender\":{\"email\":\"admin@mcgill.ca\","
	 * +
	 * "\"password\":\"pw1\",\"name\":\"Admin Person\",\"phone\":\"5141111111\",\"id\":1},\"student\":{\"email\":"
	 * +
	 * "\"student1@mcgill.ca\",\"password\":\"pw2\",\"name\":\"Student1\",\"id\":260222222,\"phone\":\"5142222222\"},"
	 * +
	 * "\"employer\":null},{\"id\":5679,\"text\":\"This is a mass test notification\",\"sender\":{\"email\":\"admin@mcgill.ca\","
	 * +
	 * "\"password\":\"pw1\",\"name\":\"Admin Person\",\"phone\":\"5141111111\",\"id\":1},\"student\":{\"email\":"
	 * +
	 * "\"student2@mcgill.ca\",\"password\":\"pw3\",\"name\":\"Student2\",\"id\":260333333,\"phone\":\"5143333333\"},"
	 * +
	 * "\"employer\":null},{\"id\":5680,\"text\":\"This is a mass test notification\",\"sender\":{\"email\":\"admin@mcgill.ca\","
	 * +
	 * "\"password\":\"pw1\",\"name\":\"Admin Person\",\"phone\":\"5141111111\",\"id\":1},\"student\":{\"email\":"
	 * +
	 * "\"student3@mcgill.ca\",\"password\":\"pw4\",\"name\":\"Student3\",\"id\":260444444,\"phone\":\"5144444444\"},"
	 * + "\"employer\":null}]";
	 * 
	 * assertThat(response.contentEquals(expected)); }
	 * 
	 * @Test public void testCoopCreate() {
	 * 
	 * //add employer and student to database and check testEmployerCreate();
	 * testStudentCreate();
	 * 
	 * String url = "http://localhost:" + port +
	 * "/coop/create?id=123&title=SWE+Intern&stuEmail=student@mcgill.ca" +
	 * "&empEmail=employer@company.ca&start=2019-01-01&end=2019-05-01&status=NotStarted&salaryPerHour=20"
	 * + "&hoursPerWeek=40&address=Montreal";
	 * 
	 * CoopDto response = this.restTemplate.postForObject(url, null, CoopDto.class);
	 * 
	 * CoopDto expected = testCoop;
	 * 
	 * assertThat(response.equals(expected)); }
	 * 
	 * @Test public void testReportCreate() {
	 * 
	 * //add admin, employer, student, and coop to the database and check
	 * testCoopCreate();
	 * 
	 * ReportDto response = this.restTemplate.postForObject("http://localhost:" +
	 * port +
	 * "/report/create?id=456&coopId=123&date=2019-05-01&status=Submitted&type=Contract",
	 * null, ReportDto.class);
	 * 
	 * ReportDto expected = testReport;
	 * 
	 * assertThat(response.equals(expected)); }
	 */
}
