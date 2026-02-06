package com.qa.api.contacts.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.client.RestClient;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;
import com.qa.api.pojo.ContactsCredentials;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class GetContactsTest extends BaseTest {
	
	private String tokenID;
	
	@BeforeMethod
	public void getToken() {
		
		ContactsCredentials creds = ContactsCredentials.builder()
									.email("sandeepdahiya@yahoo.com")
									.password("Yuvi@2014")
									.build();
		
		Response response = restClient.post(BASE_URL_CONTACTS, CONTACTS_LOGIN_END_POINT, creds, null, null, AuthType.NO_AUTH, ContentType.JSON);
		Assert.assertEquals(response.statusCode(), 200);
		tokenID = response.jsonPath().getString("token");
		System.out.println(tokenID);
		ConfigManager.set("bearertoken", tokenID);
	}
	
	
	@Test
	public void getContactsAPITest() {
		
		Response response = restClient.get(BASE_URL_CONTACTS, GET_CONTACTS_END_POINT, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		response.prettyPrint();
		//Assert.
		
		
	}

	

}
