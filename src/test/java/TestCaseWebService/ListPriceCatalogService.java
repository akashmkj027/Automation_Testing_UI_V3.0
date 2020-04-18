package TestCaseWebService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.testng.ITestResult;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import HelperClass.ReadPropertyFile;
import HelperClass.SmartLogger;
import HelperClass.SmartReporter;
import HelperClass.TestBase;
import Utility.UtilitiesRestAPI;
import Validation.AssertionsRestAPI;

import static io.restassured.RestAssured.given;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.RestAssured;

public class ListPriceCatalogService extends TestBase{

	public String sRequestBody = null;
	public static String sCreatedBy = null;
	public static String sPriceListId = null;
	public static String sCodeType = null;
	public static String sMajorHardwareATO = null;
	public static String sMajorSoftwareATO = null;
	public static String[] sMajorHWAto = null;
	public static String[] sMajorSWAto = null;
	
	@BeforeMethod
	public void SetPropertyForWebDriver(ITestResult result) throws Exception
	{	
		String description = result.getMethod().getDescription();
		SmartReporter.ExtentReportNewTest(description);
		SmartLogger.InitiateSmartLogging();
		SmartLogger.PrintInfo("Initiating TestCase " + description);	
	}
	
	
	@Test(testName ="TC007", description="TC007: Validating Mandatory Attached Subscription of LPC Catalog Service")
	public void ListPricecatalogService() throws Exception
	{
		sCreatedBy = "amakia";
		sPriceListId = "13022";
		sCodeType = "PriceListIdentifier";
		sMajorHardwareATO = "C9300-24U-A|C9200L-24T-4X-A";
		sMajorSoftwareATO = "CAT-CDNA-P";
		sRequestBody = UtilitiesRestAPI.GenerateListPriceCatalogServiceRequest(sCreatedBy, sPriceListId, sCodeType, sMajorHardwareATO.split("\\|"), sMajorSoftwareATO.split("\\|"));
		RestAssured.baseURI="https://ccw-int.cisco.com";
		Response resp = 
				given().
						header("content-type","application/json").and().
						header("x-mashery-handshake","ewogICJhY2Nlc3NfdG9rZW5fdWlkIjogImFtYW5ha2lhIgp9Cg==").and().
						body(sRequestBody).
				when().
						post("/lpcatalogservices/mandatoryAttachMapping/mappingDetails").
				then().
						assertThat().statusCode(200).and().
						extract().response();
		UtilitiesRestAPI.LogAllResponseHeaders(resp);
		UtilitiesRestAPI.LogAllAccessoriesInfoForResponse(resp);
		SmartLogger.PrintInfo("JSON Response: \n" + resp.asString());
		if(AssertionsRestAPI.AssertListPriceCatalogServiceResponse(resp))
			SmartLogger.PrintPassStepLog("Assertion Passed");
		else
			SmartLogger.PrintFailStepLog("Assertion failed");	
	}
}
