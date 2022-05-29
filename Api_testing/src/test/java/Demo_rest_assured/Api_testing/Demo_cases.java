package Demo_rest_assured.Api_testing;
import org.json.simple.JSONObject;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.ResponseSpecification;
import static io.restassured.RestAssured.given;

public class Demo_cases {
	ResponseSpecification spec = null;
	@BeforeClass
	public void setSpecification() {
		spec = RestAssured.expect();
		spec.contentType(ContentType.JSON);
		spec.statusCode(200);
		spec.statusLine("HTTP/1.1 200 OK");
		
		ExtentReportManager.createReport();
		
	
	}
	
	@Test
	public void getListOfUsers() {
		ExtentReportManager.test = ExtentReportManager.reports.startTest("fetch users", "Fetches userdata from endpoint");
		
		RestAssured.baseURI = "https://dummy.restapiexample.com/";
		ExtentReportManager.test.log(LogStatus.INFO, "Setting endpoint url", RestAssured.baseURI);
		
		String message = given().when().get("api/v1/employees").then().spec(spec).extract().path("message");
		
		
		System.out.println(message);
		if (message.equals("Successfully! All records has been fetched.")) {
			System.out.println("Test case passed");
		}
		else
		{
			System.out.println("Test case failed");
		}
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void CreateUser() {
		
		ExtentReportManager.test = ExtentReportManager.reports.startTest("fetch users", "Fetches userdata from endpoint");
		
		JSONObject params = new JSONObject();
		params.put("name", "Jacob");
		params.put("age", "20");
		params.put("salary", "200000");
		RestAssured.baseURI = "https://dummy.restapiexample.com/";
		
		ExtentReportManager.test.log(LogStatus.INFO, "Making a post request to ad  new user", RestAssured.baseURI);
		
		Response response = given().header("Content-Type", "application/json").body(params.toJSONString()).post("api/v1/create");
		System.out.println("The status received: " + response.asString());
		
		ExtentReportManager.test.log(LogStatus.INFO, "user created", RestAssured.baseURI);


	}
	
	@Test
	public void getSpecificUser() {
		ExtentReportManager.test = ExtentReportManager.reports.startTest("get specific user", "get specific user from endpoint");
		//ResponseSpecification spec2 = RestAssured.expect();
		//spec2.contentType(ContentType.JSON);
		//spec2.statusCode(200);
		RestAssured.baseURI = "https://dummy.restapiexample.com/";
		ExtentReportManager.test.log(LogStatus.INFO, "Expected", "got user");
		
		String message = given().when().get("api/v1/employee/5777").then().spec(spec).extract().path("message");
		System.out.println(message);
		
	}
	
	
	@Test
	public void DeleteUser() {
		ExtentReportManager.test = ExtentReportManager.reports.startTest("Delete user", "Delete userdata from endpoint");
		
		ExtentReportManager.test.log(LogStatus.INFO, "Making a delete request", "/api/v1/delete/<id>");
		RestAssured.baseURI = "https://dummy.restapiexample.com/";
		ExtentReportManager.test.log(LogStatus.INFO, "Expected", "Successfully deleted");
		Response response = given().delete("/api/v1/delete/2");
		System.out.println("The status received: " + response.asString());

	}
	
	@Test
	public void UpdateUser() {
		ExtentReportManager.test = ExtentReportManager.reports.startTest("Update user", "Update userdata");
		
		RestAssured.baseURI = "https://dummy.restapiexample.com/";
		ExtentReportManager.test.log(LogStatus.INFO, "Making a update request", "/api/v1/update/<id>");
		ExtentReportManager.test.log(LogStatus.INFO, "Expected", "Successfully updated");
		
		Response response = given().put("/api/v1/update/21");
		System.out.println("The status received: " + response.asString());
	}
	
	@AfterClass
	public void endReport() {
		
		ExtentReportManager.reports.flush();
	}
	

	
}
