// For Salt Hashing
import java.security.SecureRandom;
import java.util.Base64;
import org.mindrot.jbcrypt.BCrypt;

// For User Login
import java.util.Scanner;
import java.io.Console;

// For MySQL
import java.sql.*;

public class User {
	private int userID;
	private String email;
	private String firstname;
	private String lastname;
	private boolean isAdmin;

	public User(
			int userID, 
			String email, 
			String firstname, 
			String lastname, 
			boolean isAdmin) {
		this.userID = userID;
		this.email = email;
		this.firstname = firstname;
		this.lastname = lastname;
		this.isAdmin = isAdmin;
	}

	public User() {
		this(0, "false", "false", "false", false);
	}

	public void deleteUser(String dbURL, String dbUsername, String dbPassword) {
		try (Connection connection = DriverManager.getConnection(dbURL, dbUsername, dbPassword)) {
			// delete associated sales from user
			//deleteUserData(connection,"sales", userID);
			// delete associated reviews from user
			//deleteUserData(connection, "reviews", userID);
			// delete associated appointments from user
			//deleteUserData(connection, "appointments",  userID);
			// delete associated password from user
			deleteUserData(connection, "saltpw",  userID); // delete associated salts from user
			deleteUserData(connection, "salts",  userID);
			// delete user data from user table
			deleteUserData(connection, "users", userID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Data successfully deleted.");
	}
		
	private void deleteUserData(Connection connection,String table, int userID) {
		//TableNameValidator validator = new TableNameValidator();
		if(!DatabaseTablesValidation.isValidTable(table)) {
			throw new IllegalArgumentException("Invalid table name: " + table);
		}

		String sqlStatement = "DELETE FROM " + table + " WHERE id = ?";
		try (PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
			statement.setInt(1, userID);
			statement.executeUpdate();		
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateUser(String dbURL, String dbUsername, String dbPassword){
		try (Connection connection = DriverManager.getConnection(dbURL, dbUsername, dbPassword)) {
			insertEmail(connection, email, userID);
			insertFirstname(connection, firstname, userID);
			insertLastname(connection, lastname, userID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void insertEmail(Connection connection, String email, int userID) {
		String sqlStatement = "UPDATE users SET email = ? WHERE id = ?";
		try (PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
			statement.setString(1, email);
			statement.setInt(2, userID);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void insertFirstname(Connection connection, String firstname, int userID) {
		String sqlStatement = "UPDATE users SET firstname = ? WHERE id = ?";
		try (PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
			statement.setString(1, firstname);
			statement.setInt(2, userID);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}	

	private void insertLastname(Connection connection, String lastname, int userID) {
		String sqlStatement = "UPDATE users SET lastname = ? WHERE id = ?";
		try (PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
			statement.setString(1, lastname);
			statement.setInt(2, userID);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void setUserID(int updateUserID) {
		this.userID = updateUserID;
	}

	public int getUserID(){
		return this.userID;
	}

	public void setAdminStatus(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public boolean getAdminStatus() {
		return this.isAdmin;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String updateEmail) {
		this.email = updateEmail;
	}

	public String getFirstname() {
		return this.firstname;
	}

	public void setFirstname(String updateFirstname) {
		this.firstname = updateFirstname;
	}

	public String getLastname() {
		return this.lastname;
	}

	public void setLastname(String updateLastname) {
		this.lastname = updateLastname;
	}
}
