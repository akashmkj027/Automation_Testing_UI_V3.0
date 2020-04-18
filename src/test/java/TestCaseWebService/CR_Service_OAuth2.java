package TestCaseWebService;

import static io.restassured.RestAssured.given;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import java.util.ArrayList;
import org.testng.annotations.Test;
import io.restassured.RestAssured;


public class CR_Service_OAuth2 {

	@Test
	public void ServiceCRT()
	{
		ArrayList<String> sPartyID = new ArrayList<String>();
		ArrayList<String> sPartyName = new ArrayList<String>();
		RestAssured.baseURI="https://ys3orgsearch-stage.cloudapps.cisco.com";

		Response resp = 
				given().
						log().all().and().
						auth().oauth2(GetAccessToken()).
						header("content_type", "text/plain; charset=ISO-8859-1").and().
						body("{\"applicationArea\":{\"sender\":{\"creationDateTime\":\"2019-11-24T23:10:56Z\",\"bodID\":7128,\"referenceID\":\"WWSP-EDMS\",\"componentID\":\"ESALES\"}},\"dataArea\":{\"get\":{\"startIndex\":0,\"pageSize\":200},\"searchCriteria\":{\"inputLocale\":\"en-US\",\"outputLocale\":\"en-US\",\"searchType\":\"SEARCH\"},\"searchCondition\":{\"partyName\":\"google\",\"countryCode\":\"TR\"},\"filterCriteria\":{\"filter\":\"ALL\"}}}").
				when().
						post("/orgsearchapp/services/OrgSearchService/orgRestSearchV2").
				then().
						assertThat().statusCode(200).and().
						extract().response();
		System.out.println("Response: \n" + resp.asString());
		
		JsonPath jsonPath = new JsonPath(resp.asString());
		for (int i=0; i<jsonPath.getInt("dataArea.parties.size()"); i++)
		{
			sPartyID.add(jsonPath.getString("dataArea.parties[" + i+ "].partyID"));
			sPartyName.add(jsonPath.getString("dataArea.parties[" + i+ "].partyName"));
		}
		for(int i=0;i<jsonPath.getInt("dataArea.parties.size()");i++)
		{
			System.out.println("PartyID: "+ sPartyID.get(i) + "    PartyName: " + sPartyName.get(i));
		}
		
	}
	
	public String GetAccessToken()
	{
		Response resp = 
		given().
				queryParam("client_id", "icwpricing").
				queryParam("client_secret", "1cwpr1c1ng").
				queryParam("grant_type", "client_credentials").
		when().
				post("https://cloudsso-test.cisco.com/as/token.oauth2").
		then().extract().response();
		
		JsonPath jsonPath = new JsonPath(resp.asString());
		System.out.println(jsonPath.getString("access_token"));
		return jsonPath.getString("access_token");			 
	}
}

