package inputvalidation;

import java.sql.*;

public class assignment2_cxr4596 {

	public static void main(String[] args) {
		assignment2_cxr4596 db = new assignment2_cxr4596();
		
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
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
        	conn = this.connect();
        	pstmt = conn.prepareStatement(listAll);
        	rs = pstmt.executeQuery();ResultSetMetaData rsmd = rs.getMetaData();
        	int columnsNumber = rsmd.getColumnCount();
        	while (rs.next()) {
        		for (int i = 1; i <= columnsNumber; i++) {
        			if (i > 1) System.out.print("\t");
        			String columnValue = rs.getString(i);
        			System.out.print(columnValue);
        		}
        		System.out.println();
        	}
        	System.out.println(rs.getString(2));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try { rs.close(); } catch (Exception e){}
            try { pstmt.close(); } catch (Exception e){}
            try { conn.close(); } catch (Exception e){}
        }
    }

	public void delete(String input) {
		String del = "DELETE FROM USERS WHERE NAME = ? OR PHONE = ?";

		Connection conn = null;
    	PreparedStatement del_user =  null;
    	
    	boolean name,number;
    	name = validateName(input);
    	number = validateNumber(input);

    	if(name || number){
    		try{
    			conn = this.connect();
    			del_user = conn.prepareStatement(del);
    			del_user.setString(1, input);
    			del_user.setString(2, input);
    			del_user.executeUpdate();
    		} catch (SQLException e) {
    			System.out.println(e.getMessage());
    		} finally {
    			try { del_user.close(); } catch (Exception e) {}
    			try { conn.close(); } catch (Exception e) {}
    		}
    	}
    	else
    		System.out.println("Error: Invalid entry");
    }

	public void insert(String name, String phone) {
        String insert = "INSERT INTO users(name,phone) VALUES(?,?)";

        Connection conn = null;
    	PreparedStatement pstmt = null;

    	boolean name_in,number;
    	name_in = validateName(name);
    	number = validateNumber(phone);

    	if(name_in && number){        
    		try{
    			conn = this.connect();
    			pstmt = conn.prepareStatement(insert);
    			pstmt.setString(1, name.toUpperCase());
    			pstmt.setString(2, phone);
    			pstmt.executeUpdate();
    		} catch (SQLException e) {
    			System.out.println(e.getMessage());
    		} finally {
    			try { pstmt.close(); } catch (Exception e) {}
    			try { conn.close(); } catch (Exception e) {}
    		}
    	}
    	else
    		System.out.println("Error: Invalid entry");
	}
	
	private Connection connect() {
		// Store necessary values
		String connectionUrl = "jdbc:sqlite:telephone.db";
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
	
	public boolean validateName(String name){		// Function to validate name input
		if(name.split(" ").length > 3)
			return false;

		String rex_name = "^([a-zA-Z])'?[a-zA-Z]*-?[a-zA-Z],? ([a-zA-Z])'?[a-zA-Z]*-?[a-zA-Z ]*$";	// Regular Expression to match full names
		String rex_single = "^([a-zA-Z])'?[a-zA-Z]*-?[a-zA-Z]$";									// Regular Expression to match single names
		
		return name.matches(rex_name) || name.matches(rex_single);
	}
	
	public boolean validateNumber(String number){	// Function to validate phone number input 
		String rex_US = "^(\\+1)?[\\.\\-( ]*([0-9]{3})[\\.\\-) ]*([0-9]{3})[\\.\\- ]*([0-9]{4})$";		// Regular Expression to match American numbers
		String rex_English = "^\\+?\\d{2}( ?\\()-?\\d{2}(\\) ?) ?\\d{3}-?\\d{4}$";						// Regular Expression to match English numbers
		String rex_groups = "^\\d{5}\\.\\d{5}$";														// Regular Expression to match numbers in format #####.#####
		String rex_US_country = "^\\d{3} (1 )?\\d{2,3} \\d{3} \\d{4}$";									// Regular Expression to match numbers in format +## (###) ### #####
		
		return number.matches(rex_US) || number.matches(rex_English) || number.matches(rex_groups) || number.matches(rex_US_country);
	}

}