package inputvalidation;

import java.sql.*;

//no need for buffer overflow
//no need for scanner
//one run == one transaction

public class assignment2_cxr4596 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			// Store necessary values
//			String connectionUrl = "jdbc:mysql://localhost:8080;databaseName=secureprogramming;user=class;password=CSE5382";
			// Declare the JDBC objects.  
			Connection con = null;
			Statement stmt = null;
			ResultSet rs = null;

			try {
				// Establish the connection.  
//				Class.forName("com.mysql.jdbc.Driver");  
//				con = DriverManager.getConnection(connectionUrl);
				System.out.println("Echo:\t\t" + args[0] + "\n");
			}
			// Handle any errors that may have occurred.  
			catch (Exception e) {  
				e.printStackTrace();  
			}  
			finally {  
				if (rs != null) try { rs.close(); } catch(Exception e) {}  
				if (stmt != null) try { stmt.close(); } catch(Exception e) {}  
				if (con != null) try { con.close(); } catch(Exception e) {}
			}
	}

}