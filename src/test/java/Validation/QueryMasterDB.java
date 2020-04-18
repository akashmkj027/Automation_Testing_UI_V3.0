package Validation;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class QueryMasterDB {

	public static ResultSet resultSetMasterDB=null;
	public static Statement statement=null;
	public static ArrayList<String> dataList = new ArrayList<String>();
	
	public QueryMasterDB(Statement smt) throws IOException, SQLException
	{
		statement = smt;
	}
	
	public static ResultSet ExecuteQueryOnMasterDB(String sQuery) throws SQLException
	{
		    resultSetMasterDB = statement.executeQuery(sQuery);
			return resultSetMasterDB;
	}
	
	public static ArrayList<String> FetchBeGeoIDdetailsFromMasterTable(String sQuery) throws SQLException
	{
		resultSetMasterDB = ExecuteQueryOnMasterDB(sQuery);
		while(resultSetMasterDB.next())
		 {
			dataList.add(resultSetMasterDB.getString("BE_GEO_NAME"));
			dataList.add(resultSetMasterDB.getString("GEO_CD"));
			dataList.add(resultSetMasterDB.getString("PGTMV_BEGEO_ID"));
			dataList.add(resultSetMasterDB.getString("PGTMV_BE_ID"));
		 }
		return dataList;
	}
}
	

