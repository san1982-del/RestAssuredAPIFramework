package com.qa.api.gorest.tests;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class GetUserTest extends BaseTest {
	
	
	@Test
	public void getAllUsersTest() {
		
		Response response = restClient.get(BASE_URL_GOREST, GOREST_USERS_END_POINT, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertTrue(response.statusLine().contains("OK"));
		
	}
	
	
	@Test
	public void getAllUsersWithQueryParamTest() {
		
		Map<String, String> queryParams = new HashMap<String, String>();
		queryParams.put("name","sandeep");
		Response response = restClient.get(BASE_URL_GOREST, GOREST_USERS_END_POINT, queryParams, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertTrue(response.statusLine().contains("OK"));
		
	}
	
	@Test(enabled=false)
	public void singleUserTest() {
		
		String userID = "8354061";
		Response response = restClient.get(BASE_URL_GOREST, GOREST_USERS_END_POINT+"/"+userID, null, null , AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertTrue(response.statusLine().contains("OK"));
		Assert.assertEquals(response.jsonPath().getString("id"), userID);
		
		
	}
	
	
	
	
	
	
	

}
