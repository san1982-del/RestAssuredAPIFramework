package com.qa.api.schema.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;
import com.qa.api.pojo.User;
import com.qa.api.utils.SchemaValidator;
import com.qa.api.utils.StringUtils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class GoRestAPISchemaValidateTest extends BaseTest {
	
	
	private String tokenID;
	
	@BeforeClass
	public void setupTokenID() {
		tokenID = "ae3625d28c1096b6a83ac4fa8c5cb7eafae8da4a61ba81041aa7a0f995cd7719";
		ConfigManager.set("bearertoken", tokenID);
	}
	
	
	@Test
	
	public void goRestSchemavalidationtest() {
		
	Response response = restClient.get(BASE_URL_GOREST, GOREST_USERS_END_POINT, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
	
	Assert.assertTrue(SchemaValidator.validateSchema(response, "schema/getuserschema.json"));
		
		
	}
	
	@Test
	public void createUserSchemavalidationtest() {
	
	User user = new User(null, "rimmy", StringUtils.getRandomEmailID() , "male", "active");
	
	Response response = restClient.post(BASE_URL_GOREST, GOREST_USERS_END_POINT, user, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
	
	Assert.assertTrue(SchemaValidator.validateSchema(response, "schema/createuserschema.json"));
		
		
	}

}
