package inputvalidation;

import java.sql.*;

public class assignment2_cxr4596 {

	public static void main(String[] args) {
		assignment2_cxr4596 db = new assignment2_cxr4596();
			
		//validateCommand();
			
		if(args.length == 0)
			db.help();
		else if(args[0].equalsIgnoreCase("ADD"))
			db.insert(args[1], args[2]);
		else if(args[0].equalsIgnoreCase("DEL"))
			db.delete(args[1]);
		else if(args[0].equalsIgnoreCase("LIST"))
			db.list();
		else
			System.out.println("Invalid argument: " + args[0]);
	}
	
	public void help() {
		System.out.println("Proper arguments for assignment2_cxr4596:");
		System.out.println("\tADD \"<name>\" \"<phone_number>\" - add a new unique user");
		System.out.println("\tDEL \"<phone_number>\" - remove a user based on phone number");
		System.out.println("\tDEL \"<name>\" - remove a user based on phone number");
		System.out.println("\tLIST - list all current names and numbers in the database");
    }
	
	public void list() {
        String listAll = "SELECT * FROM USERS";

        try{
        	Connection conn = this.connect();
        	PreparedStatement pstmt = conn.prepareStatement(listAll);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
	
	public void delete(String name) {
        String del = "DELETE FROM USERS WHERE NAME = ? OR PHONE = ?";

        try{
        	Connection conn = this.connect();
        	//switch here on name/phone
        	PreparedStatement del_user = conn.prepareStatement(del);
            del_user.setString(1, name);
            del_user.setString(2, name);
            del_user.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

	public void insert(String name, String phone) {
        String insert = "INSERT INTO users(name,phone) VALUES(?,?)";

        try{
        	Connection conn = this.connect();
        	PreparedStatement pstmt = conn.prepareStatement(insert);
            pstmt.setString(1, name.toUpperCase());
            pstmt.setString(2, phone);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

	private Connection connect() {
		// Store necessary values
		String connectionUrl = "jdbc:sqlite://C:/Users/IBM_ADMIN/workspace/SecureProgrammingAssignment2/src/inputvalidation/telephone.db";
		// Declare the JDBC objects.  
		Connection con = null;
		
		try {
			// Establish the connection.  
			con = DriverManager.getConnection(connectionUrl);
		}
		// Handle any errors that may have occurred.  
		catch (SQLException e) {  
			System.out.println(e.getMessage());  
		}  
		return con;
	}

}