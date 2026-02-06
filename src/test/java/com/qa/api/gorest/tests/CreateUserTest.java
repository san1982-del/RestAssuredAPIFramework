package com.qa.api.gorest.tests;

import java.io.File;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AppConstants;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;
import com.qa.api.pojo.User;
import com.qa.api.utils.ExcelUtil;
import com.qa.api.utils.JsonUtils;
import com.qa.api.utils.StringUtils;


import io.restassured.http.ContentType;
import io.restassured.response.Response;


public class CreateUserTest extends BaseTest {
	
		
	private String tokenID;
	
	@BeforeClass
	public void setupTokenID() {
		tokenID = "ae3625d28c1096b6a83ac4fa8c5cb7eafae8da4a61ba81041aa7a0f995cd7719";
		ConfigManager.set("bearertoken", tokenID);
	}
	
	@DataProvider
	public Object[][] getData() {
		return new Object[][] {
			{"naveen","male","active"},
			{"laura","female","inactive"},
			{"austin","male","active"}
		};
	}
	
	@DataProvider
	public Object [][] getExcelData() {
		
		return ExcelUtil.readData(AppConstants.CREATE_USER_SHEET_NAME);
	}
	
	
	@Test(dataProvider="getExcelData")
	public void createAUsertest(String name, String gender, String status) {
		
		User user = new User(null, name, StringUtils.getRandomEmailID() , gender, status);
		
		Response response = restClient.post(BASE_URL_GOREST, GOREST_USERS_END_POINT, user, null, null, AuthType.BEARER_TOKEN, ContentType.JSON );
		Assert.assertEquals(response.jsonPath().getString("name"), name);
		Assert.assertNotNull(response.jsonPath().getString("id"));
		
		String userID =  response.jsonPath().getString("id");
		
		Response getResponse = restClient.get(BASE_URL_GOREST, GOREST_USERS_END_POINT + "/" + userID, null, null, AuthType.BEARER_TOKEN, ContentType.ANY);
		JsonUtils.desrialize(getResponse, User.class);
	
		
		//System.out.println(userData.getName());
	}
	
	
	@Test(enabled=false)
	public void createAUserWithFiletest() {
		
		File file = new File("./src/test/resources/jsons/user.json");
		
		Response response = restClient.post(BASE_URL_GOREST, GOREST_USERS_END_POINT, file, null, null, AuthType.BEARER_TOKEN, ContentType.JSON );
		Assert.assertEquals(response.jsonPath().getString("name"), "tims");
		Assert.assertNotNull(response.jsonPath().getString("id"));
		
	}
	
	@Test
	public void getALLAUsertest() {
				
		Response getResponse = restClient.get(BASE_URL_GOREST, GOREST_USERS_END_POINT, null, null, AuthType.BEARER_TOKEN, ContentType.ANY);
		User[] AlluserData = JsonUtils.desrialize(getResponse, User[].class);
		
		for (User eachUser : AlluserData) {
			
			System.out.println(eachUser.getId());
			System.out.println(eachUser.getName());
			System.out.println(eachUser.getGender());
			System.out.println(eachUser.getStatus());
			
		}
	
		
		//System.out.println(userData.getName());
	}
	

	


}
