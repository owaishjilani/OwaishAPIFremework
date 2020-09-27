package stepDefinations;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.runner.RunWith;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.junit.Cucumber;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;

@RunWith(Cucumber.class)
public class MyStepDefinations extends Utils{

	RequestSpecification res;
	ResponseSpecification resspec;
	Response response;
	TestDataBuild data = new TestDataBuild();
	static String place_id;

	@Given("Add place payload with {string}	{string} {string}")
	public void add_place_payload_with_(String name, String language, String address) throws IOException {
		
		res = given().spec(requestSpecification()).body(data.addPlacePayload(name, language, address));
	}

	@When("User calls {string} with {string} http request")
	public void user_calls_with_http_request(String resource, String method) {
		
		APIResources resourceAPI = APIResources.valueOf(resource);
		System.out.println(resourceAPI.getResource());
		
		resspec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON)
		.expectContentType(ContentType.JSON).build();

		if(method.equalsIgnoreCase("POST"))
			
			response = res.when().post(resourceAPI.getResource());
		
		else if(method.equalsIgnoreCase("GET"))
			
			response = res.when().get(resourceAPI.getResource());

	}

	@Then("The API call got success with status code {int}")
	public void the_API_call_got_success_with_status_code(Integer int1) {
		
		assertEquals(response.getStatusCode(), 200);
		
	}

	@Then("{string} in response body as {string}")
	public void in_response_body_as(String KeyValue, String ExpectedValue) {
				
		assertEquals(getJsonPath(response, KeyValue).toString(), ExpectedValue);
		
	}
	
	@Then("Verify place_Id created maps to {string} using {string}")
    public void verify_place_Id_created_maps_to_using(String expectedName, String resource) throws IOException{
		
		// requestSpecification
		place_id = getJsonPath(response, "place_id");
		res = given().spec(requestSpecification()).queryParam("place_id", place_id);
		user_calls_with_http_request(resource, "GET");
		String actualName = getJsonPath(response, "name");
		assertEquals(actualName, expectedName);
		
    }
	
	@Given("DeletePlace Payload")
	public void delete_place_payload() throws IOException {

		res = given().spec(requestSpecification()).body(data.deletePlacePayload(place_id));
		
	}

}