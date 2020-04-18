package TestCaseWebService;

import static io.restassured.RestAssured.given;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.testng.ITestResult;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import HelperClass.SmartLogger;
import HelperClass.SmartReporter;
import HelperClass.TestBase;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class AccupriceL2N extends TestBase{
	
	public static String sLineID=null;
	public static String sLineStatusCode=null;
	public static String sLineItemName=null;
	public static String sLineDiscountID=null;
	public static String sLineStandardDiscountPercentage=null;
	public static String sLineNonstandardDiscountPercentage=null;
	public static String sLineBillingAmount=null;

	@BeforeMethod
	public void SetPropertyForWebDriver(ITestResult result) throws Exception
	{	
		String description = result.getMethod().getDescription();
		SmartReporter.ExtentReportNewTest(description);
		SmartLogger.InitiateSmartLogging();
		SmartLogger.PrintInfo("Initiating TestCase " + description);	
	}

	
	@Test(testName ="TC006", description="TC006: Validating Accuprice transaction")
	public static void CreatePlaceAndPrintResponse() throws IOException
	{
		FileInputStream RequestBody = new FileInputStream(new File(".\\Resources\\TestDataFiles\\L2N_EXTENSION_Sub180424_req.json"));
		RestAssured.baseURI="https://ccw-stage-int.cisco.com";
		Response resp=
				given().
						log().all().and().
						basePath("/wstg/accuprice/services/rest/getListToNet").and().
						header("Content-Type","application/json").and().
						auth().preemptive().basic("icwpricing.gen", "1cwpr1c1ng").and().
						body(RequestBody).
				when().
						post().
				then().
						log().all().and().
						assertThat().statusCode(200).header("content-type", "application/json;charset=UTF-8").and().
						extract().response();		
		SmartLogger.PrintInfo("Accuprice Response: \n" + resp.asString());
		JsonPath jsonpath = new JsonPath(resp.asString());

		int iLinesize = jsonpath.getInt("lines[0].lines.size()");
		for(int i=0; i<iLinesize; i++)
		{
			sLineID = jsonpath.getString("lines[0].lines[" + i +"].lineId");
			sLineStatusCode = jsonpath.getString("lines[0].lines[" + i +"].status.code");
			sLineItemName = jsonpath.getString("lines[0].lines[" + i +"].itemName");
			sLineDiscountID = jsonpath.getString("lines[0].lines[" + i +"].discountValues.discountId");
			sLineStandardDiscountPercentage = jsonpath.getString("lines[0].lines[" + i +"].standardDiscount.percentage");
			sLineNonstandardDiscountPercentage = jsonpath.getString("lines[0].lines[" + i +"].nonStandardDiscount.percentage");
			sLineBillingAmount = jsonpath.getString("lines[0].lines[" + i +"].billingAmount");
			
			System.out.println("LineID: " + sLineID);
			System.out.println("Status Code: "  + sLineStatusCode);
			System.out.println("Item Name: " + sLineItemName);
			System.out.println("Discount ID: " + sLineDiscountID);
			System.out.println("Standard Discount Percentage: " + sLineStandardDiscountPercentage);
			System.out.println("Non Standard Discount Percentage: " + sLineNonstandardDiscountPercentage);
			System.out.println("Final Billing Amount: " + sLineBillingAmount);
			System.out.println("---------------------------------------------------------------------------");
		}
	}
}
