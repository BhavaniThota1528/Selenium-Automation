import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import static org.hamcrest.Matchers.*;

import Files.Payload;
public class AddPlace {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	//Validating add place is working as expected
	//Given---- Input details(we are chaining up query parameters, headers and body)
		//when--- submit the API(resource and http methods will come in when)
		//then-- validate the response
		
		RestAssured.baseURI= "https://rahulshettyacademy.com" ;
		String response= given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body(Payload.AddPlace("KPHB","TELUGU")).when().post("maps/api/place/add/json")
		.then().assertThat().log().all().statusCode(200).body("scope", equalTo("APP"))
		.header("server", "Apache/2.4.52 (Ubuntu)").extract().response().asString();
		
		
		System.out.println(response);
		
		JsonPath js= new JsonPath(response);
	String placeId=	js.getString("place_id");
	
	System.out.println(placeId);
		//Update Place
	
	String newAddress= "Kakinada";
	given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
	.body("{\r\n"
			+ "\"place_id\":\""+placeId+"\",\r\n"
			+ "\"address\":\""+newAddress+"\",\r\n"
			+ "\"key\":\"qaclick123\"\r\n"
			+ "}\r\n"
			+ "")
	.when().put("maps/api/place/update/json")
	.then().log().all().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"));
		
		//get place
	String getPlaceResponse= given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeId)
	.when().get("maps/api/place/get/json")
	.then().log().all().assertThat().statusCode(200).extract().response().asString();
	
	JsonPath js1= new JsonPath(getPlaceResponse);
	
	String actualAddress= js1.getString("address");
	String phonenumber= js1.getString("phone_number");
	System.out.println(actualAddress);
	System.out.println(phonenumber);
	}
}
