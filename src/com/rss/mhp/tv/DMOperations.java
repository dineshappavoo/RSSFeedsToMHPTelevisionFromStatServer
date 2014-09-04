/**
 * 
 */
package com.rss.mhp.tv;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @author Dinesh Appavoo
 *
 */
public class DMOperations {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public static ArrayList<String> getXMlFileName()
	{
		ArrayList<String> sXMlFileNames;
		Connection con=null;
		Statement stt=null;
		try {


			con=DataConnection.getConnection();

			String query="select XML_File_Name from QConfig";	

			stt= con.createStatement();
			stt.execute(query);
			ResultSet rs = stt.getResultSet();
			sXMlFileNames=new ArrayList<String>();
			while((rs!=null) && (rs.next()))
			{
				sXMlFileNames.add(rs.getString(1));
			}

			return sXMlFileNames;

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
