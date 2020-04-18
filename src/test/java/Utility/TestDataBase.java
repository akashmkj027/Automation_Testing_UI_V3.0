package Utility;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class TestDataBase extends Common{
	 

	 public static Statement statement=null;
	 public static ResultSet resultSet =null;
	 
	 
	 public static void KillMySQLDriverInstance()
	 {
		 try {
			Common.TerminateDriverInstanceDB(statement);
			Common.TerminateDataBaseResultSetInstance(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	 }
	 
	 public static void ConnectToTestDB()
	 {
		 statement = Common.ConnectToTestDataBase(statement);
	 }

	public static ArrayList<String> FetchUsernameAndPassword(String testCaseName) throws SQLException
	{
		ArrayList<String> dataList = new ArrayList<String>();
		resultSet = statement.executeQuery("SELECT Username,Password FROM automationtestingdbedms.edms_login_data_table where TestCaseNumber='" + testCaseName + "'");
		while(resultSet.next())
		 {
			dataList.add(resultSet.getString("Username"));
			dataList.add(resultSet.getString("Password"));
		 }
		return dataList;
	}
	
	public static ArrayList<String> FetchSearchCustomerData(String testCaseNumber) throws SQLException 
	{
		ArrayList<String> dataList = new ArrayList<String>();
		resultSet = statement.executeQuery("SELECT SearchData,MiscellaneousData,MasterTableQuerries FROM automationtestingdbedms.edms_search_customer_data where TestCaseNumber='" + testCaseNumber + "'");
		while(resultSet.next())
		 {
			dataList.add(resultSet.getString("SearchData"));
			dataList.add(resultSet.getString("MiscellaneousData"));
			dataList.add(resultSet.getString("MasterTableQuerries"));
		 }
		return dataList;
	}
	
	
}
