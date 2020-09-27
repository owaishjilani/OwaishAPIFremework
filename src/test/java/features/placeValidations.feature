Feature: Validating place APIs

@AddPlace
Scenario Outline: Verifying adding place using AddPlaceAPI
	Given Add place payload with "<name>"	"<language>"	"<address>"
	When User calls "AddPlaceAPI" with "POST" http request
	Then The API call got success with status code 200
	And "status" in response body as "OK"
	And "scope" in response body as "APP"
	And Verify place_Id created maps to "<name>" using "GetPlaceAPI"
	
Examples:
	| name		| language | address	|
	| OMJ House	| English  | Warsan	1	|
	| MJ House	| English  | Warsan 2	|
	
@DeletePlace @Regression
Scenario: Verify if deleting place functionality is working
	Given DeletePlace Payload
	When User calls "DeletePlaceAPI" with "POST" http request
	Then The API call got success with status code 200
	And "status" in response body as "OK"