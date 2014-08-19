/**
 * 
 */
package test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author B030513
 *
 */
public class DMOperations {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

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
