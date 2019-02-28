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
import ca.mcgill.ecse321.cooperator.service.CooperatorService;
import org.mockito.invocation.InvocationOnMock;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
	private static final String ADMIN_KEY = "admin@test.com";
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
	}
	
	@Before
	public void setupMock() {
		admin = mock(Administrator.class);
	}

	@Test
	public void testMockPersonCreation() {
		assertNotNull(admin);
	}

	@Test
	public void testPersonQueryFound() {
	  assertEquals(ADMIN_KEY, service.getAdmin(ADMIN_KEY).getEmail());
	}
	
}

