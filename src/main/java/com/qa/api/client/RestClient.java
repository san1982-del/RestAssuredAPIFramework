package com.qa.api.client;

import java.io.File;
import java.util.Base64;
import java.util.Map;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.api.constants.AuthType;
import com.qa.api.exceptions.APIException;
import com.qa.api.manager.ConfigManager;
import static io.restassured.RestAssured.expect;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import static org.hamcrest.Matchers.*;

public class RestClient {
	
	//Define Response Specs:
	private ResponseSpecification responseSpec200  = expect().statusCode(200);
	private ResponseSpecification responseSpec201  = expect().statusCode(201);
	private ResponseSpecification responseSpec204  = expect().statusCode(204);
	private ResponseSpecification responseSpec400  = expect().statusCode(400);
	private ResponseSpecification responseSpec404  = expect().statusCode(404);
	private ResponseSpecification responseSpec200or201  = expect().statusCode(anyOf(equalTo(200),equalTo(201)));
	private ResponseSpecification responseSpec200or404  = expect().statusCode(anyOf(equalTo(200),equalTo(404)));
	
	
	/**
	 * This method is used to define the Request with below parameters.
	 * @param baseUrl
	 * @param authType
	 * @param contentType
	 * @return it returns the loaded Request with above param values.
	 */
	private RequestSpecification setupRequest(String baseUrl, AuthType authType, ContentType contentType) {
		
		ChainTestListener.log("Base URL "+baseUrl);
		ChainTestListener.log("Authorization Type "+authType);
		RequestSpecification request = RestAssured.given().log().all()
													.baseUri(baseUrl)
													.contentType(contentType)
													.accept(contentType);
		
		switch (authType) {
		case BEARER_TOKEN:
			request.header("Authorization", "Bearer "+ ConfigManager.get("bearertoken"));
			break;
		case BASIC_AUTH:
			request.header("Authorization", "Basic "+ generaetBasicAuthToken());
			break;
		case API_KEY:
			request.header("x-api-key", "api key");
			break;
		case NO_AUTH:
			System.out.println("Auth is not required...");
			break;
		default:
			System.out.println("This Auth is not supported...Please pass the right AuthType...");
			throw new APIException("====Invalid Auth===");
			
		}
		
		return request;
		
	}
	
	
	/**
	 * This method is to gnerate the basicAuth Token. Credentials are passed from the config.properties file.
	 * @return it return a string which is generated using Base64 encoder.
	 */
	public String generaetBasicAuthToken() {
		
		String credentials = ConfigManager.get("basicauthusername") + ":" + ConfigManager.get("basicauthpassword");
		return Base64.getEncoder().encodeToString(credentials.getBytes());		
		
	}
	

	/**
	 * This method is to apply path and Query parameters in the Request
	 * @param request
	 * @param queryParams
	 * @param pathParams
	 */
	private void applyParams(RequestSpecification request, Map<String, String>queryParams, Map<String,String>pathParams) {
		ChainTestListener.log("Query Param: "+ queryParams);
		ChainTestListener.log("Path Param: "+ pathParams);
		if (queryParams!=null) {
			
			request.queryParams(queryParams);
		}
		if (pathParams!=null) {
			
			request.pathParams(pathParams);
		}	
		
	}
	
	
	/**
	 * This method define the customized get API call.
	 * @param baseUrl	
	 * @param endPoint
	 * @param queryParams
	 * @param pathParams
	 * @param authType
	 * @param contentType
	 */
	public Response get(String baseUrl, String endPoint,Map<String, String>queryParams, Map<String,String>pathParams, AuthType authType, ContentType contentType) {
		ChainTestListener.log("APi Endpoint "+endPoint);
		RequestSpecification request = setupRequest(baseUrl, authType, contentType);
		applyParams(request, queryParams, pathParams);
		Response response = request.get(endPoint).then().spec(responseSpec200or404).extract().response();
		response.prettyPrint();
		return response;
		
	}
	
	/**
	 * Post method in which we have passed pojo class as body.
	 * @param <T> supported with pojo class and string
	 * @param baseUrl
	 * @param endPoint
	 * @param body
	 * @param queryParams
	 * @param pathParams
	 * @param authType
	 * @param contentType
	 * @return
	 */
	public <T>Response post(String baseUrl, String endPoint,T body, Map<String, String>queryParams, Map<String,String>pathParams, AuthType authType, ContentType contentType) 
	{		
			ChainTestListener.log("Payload "+body);
			RequestSpecification request = setupRequest(baseUrl, authType, contentType);
			applyParams(request, queryParams, pathParams);
			Response response = request.body(body).post(endPoint).then().spec(responseSpec200or201).extract().response();
			response.prettyPrint();
			return response;
			
		}
	
	
	/**
	 * This is post overloaded method. Here we are passing file as body.
	 * @param baseUrl
	 * @param endPoint
	 * @param file
	 * @param queryParams
	 * @param pathParams
	 * @param authType
	 * @param contentType
	 * @return
	 */
	public Response post(String baseUrl, String endPoint, File file, Map<String, String>queryParams, Map<String,String>pathParams, AuthType authType, ContentType contentType) 
	
	{
		
		RequestSpecification request = setupRequest(baseUrl, authType, contentType);
		applyParams(request, queryParams, pathParams);
		Response response = request.body(file).post(endPoint).then().spec(responseSpec200or201).extract().response();
		response.prettyPrint();
		return response;
		
	}
	
	/**
	 * post overloaded method for OAUTH2.0
	 * @param baseUrl
	 * @param endPoint
	 * @param clientID
	 * @param clientSecret
	 * @param grantType
	 * @param authType
	 * @param contentType
	 * @return response
	 */
	public Response post(String baseUrl, String endPoint, String clientID , String clientSecret, String grantType, ContentType contentType) 
	
	{
		
		Response response = RestAssured.given().log().all().accept(contentType)
					.formParam("grant_type", grantType)
					.formParam("client_id", clientID)
					.formParam("client_secret", clientSecret)
					.when()
						.post(baseUrl+endPoint);
		response.prettyPrint();
		return response;
		
	}
	
	//put:
	
	public <T>Response put(String baseUrl, String endPoint,T body, Map<String, String>queryParams, Map<String,String>pathParams, AuthType authType, ContentType contentType) 
	{	
			RequestSpecification request = setupRequest(baseUrl, authType, contentType);
			applyParams(request, queryParams, pathParams);
			Response response = request.body(body).put(endPoint).then().spec(responseSpec200).extract().response();
			response.prettyPrint();
			return response;
			
		}
	
	//patch:
	
	public <T>Response patch(String baseUrl, String endPoint,T body, Map<String, String>queryParams, Map<String,String>pathParams, AuthType authType, ContentType contentType) 
	{	
			RequestSpecification request = setupRequest(baseUrl, authType, contentType);
			applyParams(request, queryParams, pathParams);
			Response response = request.body(body).patch(endPoint).then().spec(responseSpec200).extract().response();
			response.prettyPrint();
			return response;
			
		}
	
	//delete:
	
	public <T>Response delete(String baseUrl, String endPoint, Map<String, String>queryParams, Map<String,String>pathParams, AuthType authType, ContentType contentType) 
	{	
			RequestSpecification request = setupRequest(baseUrl, authType, contentType);
			applyParams(request, queryParams, pathParams);
			Response response = request.delete(endPoint).then().spec(responseSpec204).extract().response();
			response.prettyPrint();
			return response;
			
		}

}
