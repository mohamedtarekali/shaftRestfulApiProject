package shaftRestfulApiProject.shaftRestfulApiProject;

import org.json.simple.JSONObject;

import com.shaft.api.RestActions;
import com.shaft.api.RestActions.RequestType;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class RestfulBookerApiBooking {
	 private RestActions apiObject;

	    // Services Names
	    private String booking_serviceName = "booking/";

	    // Constructor
	    public RestfulBookerApiBooking(RestActions apiObject) {
		this.apiObject = apiObject;
	    }
	    
	    public Response getBookingIds() {
	    	return apiObject.buildNewRequest(booking_serviceName, RequestType.GET).performRequest();
	    }
	    
	    public Response getSpecificBookingId(String firstName , String lastName) {
	    	return apiObject.buildNewRequest(booking_serviceName, RequestType.GET)
	    			.setUrlArguments("firstname=" + firstName +"&lastname=" + lastName)
	    			.performRequest();
	    }
	    
	    public Response getBooking(String bookingId) {
	    	return apiObject.buildNewRequest(booking_serviceName + bookingId, RequestType.GET).performRequest();
	    }
	    
	    @SuppressWarnings("unchecked")
		public Response createBooking(String firstName, String lastName, int totalPrice, boolean depositePaid,
	    	    String checkIn, String checkOut, String additionalNeeds) {
	    	
	    	JSONObject createBookingBody = new JSONObject();
	    	createBookingBody.put("firstname", firstName);
	    	createBookingBody.put("lastname", lastName);
	    	createBookingBody.put("totalprice", totalPrice);
	    	createBookingBody.put("depositpaid", depositePaid);
	    	JSONObject bookingDates = new JSONObject();
	    	bookingDates.put("checkin", checkIn);
	    	bookingDates.put("checkout", checkOut);
	    	createBookingBody.put("bookingdates", bookingDates);
	    	createBookingBody.put("additionalneeds", additionalNeeds);
	    	
	    	return apiObject.buildNewRequest(booking_serviceName, RequestType.POST)
	    			.setRequestBody(createBookingBody)
	    			.setContentType(ContentType.JSON)
	    			.performRequest();
	    }
	    
	    public Response deleteBooking(String bookingId) {
	    	return apiObject.buildNewRequest(booking_serviceName + bookingId, RequestType.DELETE)
	    			.setTargetStatusCode(RestfulBookerApi.SUCCESS_DELETE)
	    			.performRequest();
	    }

}
