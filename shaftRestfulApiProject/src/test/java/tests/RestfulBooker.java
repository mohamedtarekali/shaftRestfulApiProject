package tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.shaft.api.RestActions;
import com.shaft.driver.DriverFactory;
import com.shaft.tools.io.JSONFileManager;
import com.shaft.validation.Validations;

import io.restassured.response.Response;
import shaftRestfulApiProject.shaftRestfulApiProject.RestfulBookerApi;
import shaftRestfulApiProject.shaftRestfulApiProject.RestfulBookerApiBooking;

public class RestfulBooker {
	private RestActions apiObject;
	private JSONFileManager jsonReader;
	
	@BeforeClass
    public void beforeClass() {
		jsonReader = new JSONFileManager(System.getProperty("testDataFolderPath") + "/authData.json");
		apiObject = DriverFactory.getAPIDriver(RestfulBookerApi.BASE_URL);
		new RestfulBookerApi(apiObject).login(jsonReader.getTestData("username"), jsonReader.getTestData("password"));
	}
	
	@Test
	public void getBookingIds () {
		new RestfulBookerApiBooking(apiObject).getBookingIds();
	}
	
	@Test
	public void getSpecificBookingId() {
		new RestfulBookerApiBooking(apiObject).getSpecificBookingId("Susan", "Wilson");
	}
	
	@Test
	public void getBooking() {
		new RestfulBookerApiBooking(apiObject).getBooking("7");
	}
	
	@Test
	public void createBooking() {
		Response createBookingRes = new RestfulBookerApiBooking(apiObject).createBooking("mohamed", "tarek", 1000, true, "2022-06-01", "2022-06-01", "iceCream");
		
		//String bookingId = RestActions.getResponseJSONValue(createBookingRes, "bookingid");
		String firstName = RestActions.getResponseJSONValue(createBookingRes, "booking.firstname");
		String lastName = RestActions.getResponseJSONValue(createBookingRes, "booking.lastname");
		String checkIn = RestActions.getResponseJSONValue(createBookingRes, "booking.bookingdates.checkin");
		String checkOut = RestActions.getResponseJSONValue(createBookingRes, "booking.bookingdates.checkout");
		String additionalNeeds = RestActions.getResponseJSONValue(createBookingRes, "booking.additionalneeds");
		
		Validations.verifyThat().object(firstName).isEqualTo("mohamed").perform();
		Validations.verifyThat().response(createBookingRes).extractedJsonValue("booking.firstname").isEqualTo("mohamed").perform();
		
		Validations.verifyThat().object(lastName).isEqualTo("tarek").perform();
		Validations.verifyThat().response(createBookingRes).extractedJsonValue("booking.lastname").isEqualTo("tarek").perform();
		
		Validations.verifyThat().object(checkIn).isEqualTo("2022-06-01").perform();
		Validations.verifyThat().response(createBookingRes).extractedJsonValue("booking.bookingdates.checkin").isEqualTo("2022-06-01").perform();
		
		Validations.verifyThat().object(checkOut).isEqualTo("2022-06-01").perform();
		Validations.verifyThat().response(createBookingRes).extractedJsonValue("booking.bookingdates.checkout").isEqualTo("2022-06-01").perform();
		
		Validations.verifyThat().object(additionalNeeds).isEqualTo("iceCream").perform();
		Validations.verifyThat().response(createBookingRes).extractedJsonValue("booking.additionalneeds").isEqualTo("iceCream").perform();
	}
	
	@Test(dependsOnMethods = { "createBooking" })
	public void deleteBooking() {
		Response getBookingId  = new RestfulBookerApiBooking(apiObject).getSpecificBookingId("mohamed", "tarek");
		String bookingId = RestActions.getResponseJSONValue(getBookingId, "bookingid[0]");
		
		Response deleteBooking  = new RestfulBookerApiBooking(apiObject).deleteBooking(bookingId);
		String deleteBookingBody = RestActions.getResponseBody(deleteBooking);
		
		Validations.verifyThat().object(deleteBookingBody).isEqualTo("Created").perform();
	}
}
