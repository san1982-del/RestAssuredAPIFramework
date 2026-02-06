 package com.api.qa.oauth2.tests;

import java.util.HashMap;
import java.util.Map;

import org.apache.groovy.util.Maps;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class AmadeusAPIOAUTH2Test extends BaseTest {
	
	
	@BeforeMethod
	public void getAcccessToken() {
		
		Response response = restClient.post(BASE_URL_OAUTH2_AMADEUS, AMADEUS_OATUH2_TOKEN_ENDPOINT, ConfigManager.get("clientid"), ConfigManager.get("clientsecret") , 
				ConfigManager.get("granttype"), ContentType.URLENC);
		
		String accessToken = response.jsonPath().get("access_token");
		System.out.println(accessToken);
		ConfigManager.set("bearertoken", accessToken);
		
		
	}
	
	@Test
	public void AmadeusOAuth2Test() {
		
		Map<String,String> queryParams =new HashMap<String, String>();
		queryParams.put("origin", "PAR");
		queryParams.put("maxPrice", "200");
		
		Response response = restClient.get(BASE_URL_OAUTH2_AMADEUS, AMADEUS_FLIGHT_DEST_ENDPOINT, queryParams, null, AuthType.BEARER_TOKEN, ContentType.ANY);
		
	}
	
	
	
	

}
