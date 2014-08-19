/**
 * 
 */
package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @author Dinesh Appavoo
 *
 */
public class DataConnection {


	static Connection connection = null;

	public static void main(String[] args) {

		Connection con = DataConnection.getConnection();
		System.out.println("Connection : "+con);


	}

	public static Connection getConnection()	
	{
		try {
			Connection con=null;
			String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
			Class.forName(driverName);
			String url = "jdbc:sqlserver://;databaseName=;integratedSecurity=true";
			connection = DriverManager.getConnection(url);
			System.out.println("Successfully Connected to the SQL Server database!");
			return con;
		}
		catch (ClassNotFoundException e) {

			System.out.println("Could not find the database driver " + e.getMessage());
			return null;
		}
		catch(Exception e){

			System.out.println("Connection Error: "+e);
			return null;
		}
	}

	public String getXMlFileName(String sObjectID)
	{
		String sXMlFileName="";
		Connection con=null;
		Statement stt=null;
		try {


			con=DataConnection.getConnection();

			String query="select XML_File_Name from QConfig where Q_Name="+"\'"+sObjectID+"\'";	

			stt= con.createStatement();
			stt.execute(query);
			ResultSet rs = stt.getResultSet();
			while((rs!=null) && (rs.next()))
			{
				sXMlFileName=rs.getString(1);
			}

			return sXMlFileName;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}finally{
			try {
				con.close();
				stt.close();

			}catch (SQLException e) {
				e.printStackTrace();
			}

		}
	}

}
