package stepDefinations;

import java.io.IOException;

import io.cucumber.java.Before;

public class Hooks {
	
	@Before("@DeletePlace")
	public void beforeScenario() throws IOException {
		
		//code that'll give place id and will execute only when place id is null
		MyStepDefinations m = new MyStepDefinations();
		if(MyStepDefinations.place_id==null) {

			m.add_place_payload_with_("Shetty", "French", "Asia");
			m.user_calls_with_http_request("AddPlaceAPI", "POST");
			m.verify_place_Id_created_maps_to_using("Shetty", "GET");
			
		}
		
	}

}
