package Utility;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import HelperClass.SmartLogger;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;

public class UtilitiesRestAPI {

	public static String sRequestBody = null;
	public static String sMajorHardwareATO = null;
	public static String sMajorSoftwareATO = null;
	
	public static String GenerateRandomUUID() throws Exception
	{
		{ 
			UUID uuid = UUID.randomUUID(); 
			return uuid.toString();
	    } 
	}
	
	public static void LogAllResponseHeaders(Response resp)
	{
		Headers allHeaders = resp.getHeaders();
		SmartLogger.PrintInfo("-----------------------  BELOW ARE ALL HEADER(S) FOR RESPONSE    -------------------\n");
		 for(Header header : allHeaders)
		 {
		 SmartLogger.PrintInfo((header.getName() + ":   " + header.getValue()));
		 }
	}
	
	public static void LogAllAccessoriesInfoForResponse(Response resp)
	{
		SmartLogger.PrintDebug("Response Status Code: " + resp.getStatusCode());
		SmartLogger.PrintDebug("Response Status Line: " + resp.getStatusLine());
		SmartLogger.PrintDebug("Response Time Taken: " + resp.getTimeIn(TimeUnit.SECONDS));
	}
	
	public static void PrintResponseCookies(Response resp, String sCookieName)
	{
		SmartLogger.PrintInfo(resp.getCookie(sCookieName));
	}
	
	public static String GenerateListPriceCatalogServiceRequest(String sCreatedBy, String sPriceListId, String sCodeType, String[] sMajorHWAto, String[] sMajorSWAto) throws Exception
	{
		sMajorHardwareATO="\"";
		sMajorSoftwareATO="\"";
		for(int i=0; i<sMajorHWAto.length;i++)
		{
			if(i==sMajorHWAto.length-1)
				sMajorHardwareATO = sMajorHardwareATO + sMajorHWAto[i] + "\"";
			else
				sMajorHardwareATO = sMajorHardwareATO + sMajorHWAto[i] + "\",\"";
		}
		
		for(int i=0; i<sMajorSWAto.length;i++)
		{
			if(i==sMajorSWAto.length-1)
				sMajorSoftwareATO = sMajorSoftwareATO + sMajorSWAto[i] + "\"";
			else
				sMajorSoftwareATO = sMajorSoftwareATO + sMajorSWAto[i] + "\",\"";
		}
		sRequestBody = "{\"uuid\":\"" + GenerateRandomUUID() + "\",\"createdDateTime\":\"2019-12-02T05:30:03.176Z\",\"createdBy\":\"" + sCreatedBy + "\",\"priceListId\":\"" + sPriceListId + "\",\"codeType\":\"" + sCodeType + "\",\"majorHWAto\":[" + sMajorHardwareATO + "],\"majorSWAto\":[" + sMajorSoftwareATO + "]}";
		SmartLogger.PrintInfo("JSON Request: " + sRequestBody);
		return sRequestBody;
	}
	
	
}
