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

	public boolean loginUser() {
		boolean isLogged = false;
		try (Connection connection = DriverManager.getConnection(dbURL, dbUsername, dbPassword)) {
			Scanner scanner = new Scanner(System.in);
			Console console = System.console();

			if (console == null) {
				System.out.println("No console available!");
				return false;
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
					isLogged = true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isLogged;
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
