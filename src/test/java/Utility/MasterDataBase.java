package Utility;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import HelperClass.ReadPropertyFile;
import Validation.QueryMasterDB;

public class MasterDataBase {

	public static Connection connectionMasterDB=null;
	public static Statement statementMasterDB=null;
	public static String sTNS = null;
	public static String sURL = null;
	public static String sUsername = null;
	public static String sPassword = null;
	public static QueryMasterDB queryMasterDB =null;
	public static ReadPropertyFile readPropertyFile;
	
	public static Connection GetMasterDBconnectionDetails(HelperClass.Enums.MasterDbConnect dbName) throws SQLException, ClassNotFoundException, IOException
	{
		readPropertyFile = new ReadPropertyFile();
		switch (dbName) {
		case TS1DMP:
			sTNS=readPropertyFile.GetMasterDBtnsURL();
			sURL=readPropertyFile.GetMasterDBconnectionURL() + sTNS + readPropertyFile.GetEnvironmentMasterDB();
			sUsername=readPropertyFile.GetUserNameMasterDB();
			sPassword=readPropertyFile.GetPasswordMasterDB();
			break;
		case TS3DMP:
			sTNS=readPropertyFile.GetMasterDBtnsURL();
			sURL=readPropertyFile.GetMasterDBconnectionURL() + sTNS + readPropertyFile.GetEnvironmentMasterDB();
			sUsername=readPropertyFile.GetUserNameMasterDB();
			sPassword=readPropertyFile.GetPasswordMasterDB();
			break;
		case TS3CG1:
			sTNS=readPropertyFile.GetMasterDBtnsURL() + "TS1CG1";
			sURL=readPropertyFile.GetMasterDBconnectionURL() + sTNS;
			sUsername="APPSRO";
			sPassword="read0nly";
			break;
		case DMSTG:
			sTNS=readPropertyFile.GetMasterDBtnsURL() + "DMSTG_RO";
			sURL=readPropertyFile.GetMasterDBconnectionURL() + sTNS;
			sUsername="XXAPPSRO";
			sPassword="TCZa7W_1P";
			break;
		default:
			System.out.println("No Master DataBase Connected");
			break;
		}
		Class.forName(readPropertyFile.GetMasterDBdriverRegistration());
		connectionMasterDB = DriverManager.getConnection(sURL,sUsername,sPassword);
		return connectionMasterDB;
	}
	
	public static Statement GetMasterDBStatement(HelperClass.Enums.MasterDbConnect dbName) throws SQLException, ClassNotFoundException, IOException
	{
		statementMasterDB = GetMasterDBconnectionDetails(dbName).createStatement();
		return statementMasterDB;
	}
	
	public static void KillMasterDBdriverInstance() throws SQLException
	{
		if(statementMasterDB!=null)
			statementMasterDB.close();
		
		if(connectionMasterDB!=null)
			connectionMasterDB.close();
		
	}
	
	public static void ConnectToMasterDB() throws SQLException, IOException, ClassNotFoundException
	{
		readPropertyFile = new ReadPropertyFile();
		if(readPropertyFile.GetEnvironmentMasterDB().equalsIgnoreCase("TS1DMP"))
		{	statementMasterDB = GetMasterDBStatement(HelperClass.Enums.MasterDbConnect.TS1DMP);
			queryMasterDB =new QueryMasterDB(statementMasterDB);
		}
		else if(readPropertyFile.GetEnvironmentMasterDB().equalsIgnoreCase("TS3DMP"))
		{	statementMasterDB = GetMasterDBStatement(HelperClass.Enums.MasterDbConnect.TS3DMP);
			queryMasterDB =new QueryMasterDB(statementMasterDB);
		}
		else if(readPropertyFile.GetEnvironmentMasterDB().equalsIgnoreCase("DMSTG"))
		{	statementMasterDB = GetMasterDBStatement(HelperClass.Enums.MasterDbConnect.DMSTG);
			queryMasterDB =new QueryMasterDB(statementMasterDB);
		}
		else if(readPropertyFile.GetEnvironmentMasterDB().equalsIgnoreCase("TS3CG1"))
		{	statementMasterDB = GetMasterDBStatement(HelperClass.Enums.MasterDbConnect.TS3CG1);
			queryMasterDB =new QueryMasterDB(statementMasterDB);	
		}
		else
			System.out.println("Invalid ENVIRONMENT Name Selected For Master DB Connection");
	}
}


