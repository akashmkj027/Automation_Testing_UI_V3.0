package Validation;

import HelperClass.SmartLogger;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class AssertionsRestAPI {

	public static boolean AssertListPriceCatalogServiceResponse(Response resp)
	{
		JsonPath jsonpath = new JsonPath(resp.asString());	
		String mappingDetailSize, mandatoryAttachedSubscriptionSize =null;
		for(int i=0; i<jsonpath.getInt("mandatoryAttachedMappingDetails.size()");i++)
		{
			mappingDetailSize = "mandatoryAttachedMappingDetails[" + i + "].mappingDetail.size()";
			for (int j=0; j<jsonpath.getInt(mappingDetailSize);j++)
			{
				mandatoryAttachedSubscriptionSize = "mandatoryAttachedMappingDetails[" + i + "].mappingDetail[" + j +"].mandatoryAttachedSubscription.size()";
				for (int k=0; k<jsonpath.getInt(mandatoryAttachedSubscriptionSize); k++)
				{
					SmartLogger.PrintInfo("Major H/W ATO: " + jsonpath.getString("mandatoryAttachedMappingDetails[" + i + "].majorHWAto") + " || Major S/W ATO: " + jsonpath.getString("mandatoryAttachedMappingDetails[" + i +"].mappingDetail[" + j + "].majorSWAto") + " || Mandatory Attached Subscription: " + jsonpath.getString("mandatoryAttachedMappingDetails[" + i +"].mappingDetail[" + j + "].mandatoryAttachedSubscription[" + k +"]") + "\n");	
				}
			}
		}
		return true;
	}
}
