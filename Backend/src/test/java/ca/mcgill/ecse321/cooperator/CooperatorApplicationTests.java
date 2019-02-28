package ca.mcgill.ecse321.cooperator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ca.mcgill.ecse321.cooperator.controller.CooperatorController;
import ca.mcgill.ecse321.cooperator.dao.*;
import ca.mcgill.ecse321.cooperator.model.Administrator;
import ca.mcgill.ecse321.cooperator.model.Coop;
import ca.mcgill.ecse321.cooperator.model.Student;
import ca.mcgill.ecse321.cooperator.service.CooperatorService;
import org.mockito.invocation.InvocationOnMock;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Date;
import java.util.Set;
import java.util.HashSet;

import javax.net.ssl.HttpsURLConnection;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CooperatorApplicationTests{
		
	@Mock
	private AdministratorRepository adminDao;
	
	@Mock 
	private CoopRepository coopDao;
	
	@Mock 
	private EmployerRepository employerDao;
	
	@Mock 
	private NotificationRepository notifDao;
	
	@Mock 
	private ProfileRepository profileDao;
	
	@Mock 
	private ReportRepository reportDao;
	
	@Mock 
	private StudentRepository studentDao;
	
	@InjectMocks
	private CooperatorService service;

	@Mock
	private CooperatorService serviceMock;
	
	@InjectMocks
	private CooperatorController controller;
	
	@Test
	public void contextLoads () 
	{
	}
	
	private Administrator admin;
	private Coop coop;
	private static final String ADMIN_KEY = "admin@test.com";
	private static final Integer COOP_KEY = 1;
	private static final String NONEXISTING_KEY = "NotAAdmin";
	
	@Before
	public void setMockOutput() {
	  when(adminDao.findAdministratorByEmail(anyString())).thenAnswer( (InvocationOnMock invocation) -> {
	    if(invocation.getArgument(0).equals(ADMIN_KEY)) {
	      Administrator admin = new Administrator();
	      admin.setEmail(ADMIN_KEY);
	      return admin;
	    } else {
	      return null;
	    }
	  });
	  
	  when(coopDao.findCoopById(anyInt())).thenAnswer( (InvocationOnMock invocation) -> {
		Coop coop = new Coop();
		coop.setStartDate(new Date(2018,01,12));
		coop.setEndDate(new Date(2018,04,29));
		coop.setId(COOP_KEY);
		Student student = new Student();
		student.setName("Eliot");
		student.setEmail("eliot@test.com");
		coop.setStudent(student);
		Set<Coop> coops = new HashSet<Coop>();
		student.setCoop(coops);
		return coop;
	  });
	}
	
	@Before
	public void setupMock() {
		admin = mock(Administrator.class);
		coop = mock(Coop.class);
	}

	@Test
	public void testMockAdminCreation() {
		assertNotNull(admin);
	}

	@Test
	public void testAdminQueryFound() {
	  assertEquals(ADMIN_KEY, service.getAdmin(ADMIN_KEY).getEmail());
	}
	
	@Test
	public void testCoopAPI() throws Exception {
		String url = "http://localhost:8081/statistics/coop/Winter2018/Winter2019/1";
		String res = sendGet(url);
		System.out.println("Response: "+res);
		assertNotNull(service.getCoop(COOP_KEY));
		//assertEquals(res, service.getCoop(COOP_KEY));
	}
	
	@Test
	public void testMockCoopCreation() {
		assertNotNull(coop);
	}

	// HTTP POST request
	private String sendPost(String url) throws Exception {
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		
		//add request header
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
	
	// HTTP GET request
	private String sendGet(String url) throws Exception {
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", "Mozilla/5.0");

		int responseCode = con.getResponseCode();
		//System.out.println("\nSending 'GET' request to URL : " + url);
		//System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		return response.toString();
	}
}

