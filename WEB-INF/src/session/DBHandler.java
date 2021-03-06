/**
 * 
 */
package session;

import java.io.IOException;
import java.sql.*; 

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author mcmorris
 *
 */
public class DBHandler {
	private static String dbUser = "mcmorris";
    	private static String dbPassword = "oracleseat5mules";
	private static String dbDriverName = "oracle.jdbc.driver.OracleDriver";
	private static String dbString = "jdbc:oracle:thin:@gwynne.cs.ualberta.ca:1521:CRS";
	private static DBHandler instance = null;

	protected DBHandler() {
		// Exists only to defeat instantiation.
	}
	public static DBHandler getInstance() {
		if(instance == null) {
			instance = new DBHandler();
		}
		return instance;
	}

	/*
	 *   Connect to the specified database
	 */
	public Connection getConnection() throws Exception {
		Class drvClass = Class.forName(dbDriverName); 
		DriverManager.registerDriver((Driver) drvClass.newInstance());
		return( DriverManager.getConnection(dbString, dbUser, dbPassword) );
	}

	/*
	 *   Start a connection for use of upload.
	 */
	public Connection startUpload() {
		conn = getConnection();
		conn.setAutoCommit(false);
		return conn;
	}

	/*
	 *   End connection for use with upload, commit transaction or rollback as needed.
	 */	
	public void endUpload(conn) {
		try {
			conn.commit();
		} catch (SQLException ex) {
			DBHandler.getInstance().safeCloseTrans(conn);
		}
	}

	/*
	 *	 Safely close a connection with running transaction.
	 */
	public void safeCloseTrans(Connection conn) {
		if (conn == null) return;
	
		try {
			if (conn != null) { 
				conn.rollback();
				conn.close();
			}
		}
		// Catch rollback / close error.
		catch (SQLException sqle) {
			out.println("<hr>" + sqle.getMessage() + "<hr>");
		}
		// Regardless of rollback, attempt close.
		finally 
		{
			safeCloseConn(conn);
		}
			
	}
	
	public void safeCloseConn(Connection conn) {
		if (conn == null) return;
		
		try {
			conn.close();
		}
		// Close can still fail, check for failure.
		catch (SQLException sqle) {
			System.out.println("<hr>" + sqle.getMessage() + "<hr>");
		}
	}
}
