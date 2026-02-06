package com.qa.api.base;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import com.qa.api.client.RestClient;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;

//@Listeners(ChainTestListener.class)
public class BaseTest {
	
	protected RestClient restClient;
	
	//********API BASE_URLS********//
	
	protected final static String BASE_URL_GOREST = "https://gorest.co.in";
	protected final static String BASE_URL_CONTACTS = "https://thinking-tester-contact-list.herokuapp.com";
	protected final static String BASE_URL_BASIC_AUTH = "https://the-internet.herokuapp.com";
	protected final static String BASE_URL_PRODUCTS = "https://fakestoreapi.com";
	protected final static String BASE_URL_OAUTH2_AMADEUS = "https://test.api.amadeus.com";
	
	//********API BASE_URLS********//
	
	protected final static String GOREST_USERS_END_POINT = "/public/v2/users";
	protected final static String CONTACTS_LOGIN_END_POINT = "/users/login";
	protected final static String GET_CONTACTS_END_POINT = "/contacts";
	protected final static String BASIC_AUTH_ENDPOINT = "/basic_auth";
	protected final static String PRODUCTS_ENDPOINT = "/products";
	protected final static String AMADEUS_OATUH2_TOKEN_ENDPOINT = "/v1/security/oauth2/token";
	protected final static String AMADEUS_FLIGHT_DEST_ENDPOINT = "/v1/shopping/flight-destinations";
	
	
	@BeforeSuite
	public void setupAllureReport() {
		
		RestAssured.filters(new AllureRestAssured());
	}
	
	@BeforeTest
	public void setup() {
		
		restClient = new RestClient();
		
	}
	
	
	
	
	

	

}
