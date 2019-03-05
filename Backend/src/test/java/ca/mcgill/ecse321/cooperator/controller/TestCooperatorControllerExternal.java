package ca.mcgill.ecse321.cooperator.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class TestCooperatorControllerExternal {
		
	// HTTP GET request
	private String sendGet(String url) throws Exception {
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", "Mozilla/5.0");

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
	    result.remove(0); //remove first element because it will be a space
	    return result;
	    
	}
	
	private String getParameter(String parameter, List<String> list) {
		for(int index=0; index<list.size(); index++) {
			if(list.get(index).equalsIgnoreCase(parameter)) return list.get(index+1);
		}
		return null;
	}
		
	@Test
	public void testGetTermsRemaining() throws Exception{
		String url = "https://cooperator-backend-00.herokuapp.com/getTermsRemaining/260733168";
		
		try {
			String response = sendGet(url);
			
			List<String> responseList = parseResponse(response);
			Integer responseTermsRemaining = Integer.valueOf(getParameter("termsRemaining", responseList));
			Integer expectedTermsRemaining = 0;
			
			//compare response from request and expected response
			assertEquals(responseTermsRemaining, expectedTermsRemaining);
			assertNotNull(response);
		}
		catch (Exception e) {
			e.getMessage();
		}
	}
	
	@Test
	public void testGetTermsFinished() throws Exception{
		String url = "https://cooperator-backend-00.herokuapp.com/getTermsFinished/260733168";
		
		try {
			String response = sendGet(url);
			List<String> responseList = parseResponse(response);
			Integer responseTermsFinished = Integer.valueOf(getParameter("termsFinished", responseList));
			Integer expectedTermsFinished = 0;
			
			//compare response from request and expected response
			assertEquals(responseTermsFinished, expectedTermsFinished);
			assertNotNull(response);
		}
		catch (Exception e) {
			e.getMessage();
		}
	}
	
	@Test
	public void testGetStudent() throws Exception{
		String url = "https://cooperator-backend-00.herokuapp.com/getStudent/260733168";
		try {
			String response = sendGet(url);
			
			List<String> responseList = parseResponse(response);
			String responseName = getParameter("firstName", responseList);
			String expectedName = "saif";
			
			//compare response from request and expected response
			assertEquals(responseName,  expectedName);
			assertNotNull(response);
		}
		catch (Exception e) {
			e.getMessage();
		}
	}
	
	@Test
	public void testGetAllInternships() throws Exception{
		String url = "https://cooperator-backend-00.herokuapp.com/getAllInternships?Student=260733168";
		
		try {
			String response = sendGet(url);
		
			List<String> responseList = parseResponse(response);
			String responseCoopName = (getParameter("offers", responseList));
			String expectedCoopName = null;
			
			//compare response from request and expected response
			assertEquals(responseCoopName, expectedCoopName);
			assertNotNull(response);
		}
		catch (Exception e) {
			e.getMessage();
		}
	}
	
	
	
	
	
	

}
