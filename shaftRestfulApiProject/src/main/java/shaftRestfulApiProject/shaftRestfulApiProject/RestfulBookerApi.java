package shaftRestfulApiProject.shaftRestfulApiProject;

import org.json.simple.JSONObject;

import com.shaft.api.RestActions;
import com.shaft.api.RestActions.RequestType;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class RestfulBookerApi {
	private RestActions apiObject;
	
	// Base URL
    public static final String BASE_URL = "https://restful-booker.herokuapp.com/";

    // Status Codes
    public static final int SUCCESS = 200;
    public static final int SUCCESS_DELETE = 201;

    // Services Names
    private String auth_serviceName = "auth";
    
    //Constructor
    public RestfulBookerApi(RestActions apiObject) {
    	this.apiObject = apiObject;
        }
    
    
	@SuppressWarnings("unchecked")
	public void login (String userNeme , String password) {
    	JSONObject authenticationData = new JSONObject();
    	authenticationData.put("username", userNeme);
    	authenticationData.put("password", password);
    	
    	Response createToken  = apiObject.buildNewRequest(auth_serviceName, RequestType.POST)
    	.setRequestBody(authenticationData)
    	.setContentType(ContentType.JSON)
    	.performRequest();
    	
    	String token = RestActions.getResponseJSONValue(createToken , "token");
    	
    	apiObject.addHeaderVariable("Cookie", "token=" + token);
    	
    }

}
