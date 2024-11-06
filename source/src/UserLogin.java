// For Salt Hashing
import java.security.SecureRandom;
import java.util.Base64;
import org.mindrot.jbcrypt.BCrypt;

// For User Login
import java.util.Scanner;
import java.io.Console;

// For MySQL
import java.sql.*;

public class UserLogin {
	private final String dbURL;
	private final String dbUsername;
	private final String dbPassword;

	public UserLogin(String dbURL, String dbUsername, String dbPassword) {
		this.dbURL = dbURL;
		this.dbUsername = dbUsername;
		this.dbPassword = dbPassword;
	}

	/* NOTE!!!!!!!!!
	 * NEED TO ADD: admin status check
	 * NEED TO ADD: admin field (false by default)
	 */

	public Object[] loginUser() {
		Object[] userData = new Object[6];
		for (int i = 0; i < userData.length; i++) {
			if(i == 5) {
				userData[i] = false;
			} else {
				userData[i] = 0;
			}
		}
		userData[0] = false;
		
		try (Connection connection = DriverManager.getConnection(dbURL, dbUsername, dbPassword)) {
			Scanner scanner = new Scanner(System.in);
			Console console = System.console();

			if (console == null) {
				System.out.println("No console available!");
				return userData;
			}

			String email, password, db_salt, db_saltPassword, saltPassword;
			int userID = -1;

			System.out.print("Email: ");
			email = scanner.nextLine();

			char[] passwordArray = console.readPassword("Password: ");
			password = new String(passwordArray);
			
			userID = getUserIDFromDB(connection, email);
			if (userID != -1) {
				db_salt = getUserSaltFromDB(connection, userID);
				db_saltPassword = getUserSaltedPasswordFromDB(connection, userID);
				saltPassword = generateSaltPassword(password, db_salt);
				if (saltPassword.equals(db_saltPassword)) {
					userData[0] = true;
				}
				// fill user object with data
				getUserData(connection, userID, userData);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userData;
	}
	
	private void getUserData(Connection connection, int userID, Object[] userObj) {
		userObj[1] = userID;
		String query = "SELECT * FROM users WHERE id = ?";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, userID);
			ResultSet results = statement.executeQuery();
			int data_position = 2;
			if (results.next()) {
				// set object details here
				userObj[data_position] = results.getString("email");
				userObj[data_position+1] = results.getString("firstname");
				userObj[data_position+2] = results.getString("lastname");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		query = "SELECT * FROM admin WHERE id = ?";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, userID);
			ResultSet results = statement.executeQuery();
			int data_position = 5;
			if (results.next()) {
				if (results.getInt("id") == userID) {
					userObj[data_position] = true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private int getUserIDFromDB(Connection connection, String email) {
		String query = "SELECT id FROM users WHERE email = ?";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, email);
			ResultSet results = statement.executeQuery();
			if (results.next()) {
				return results.getInt("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	private String getUserSaltedPasswordFromDB(Connection connection, int userID) {
		String query = "SELECT saltpw FROM saltpw WHERE id = ?";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, userID);
			ResultSet results = statement.executeQuery();
			if (results.next()) {
				return results.getString("saltpw");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "Invalid";
	}

	private String getUserSaltFromDB(Connection connection, int userID) {
		String query = "SELECT salt FROM salts WHERE id = ?";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, userID);
			ResultSet results = statement.executeQuery();
			if (results.next()) {
				return results.getString("salt");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "Invalid";
	}

	private String generateSaltPassword(String password, String salt) {
		return BCrypt.hashpw(password, salt);
	}
}
