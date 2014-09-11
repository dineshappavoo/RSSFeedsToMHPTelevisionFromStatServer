/**
 * 
 */
package com.rss.mhp.tv;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

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
	
	public static HashMap<String, String> getXMlFileName()
	{
		HashMap<String, String> hQueueXMlFileMap;
		Connection con=null;
		Statement stt=null;
		try {


			con=DataConnection.getConnection();

			String query="select * from Q_XML_Map";	

			stt= con.createStatement();
			stt.execute(query);
			ResultSet rs = stt.getResultSet();
			hQueueXMlFileMap=new HashMap<String, String>();
			while((rs!=null) && (rs.next()))
			{
				hQueueXMlFileMap.put(rs.getString(1),rs.getString(2));
			}

			return hQueueXMlFileMap;

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
