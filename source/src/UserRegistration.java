// For Salt Hashing
import java.security.SecureRandom;
import java.util.Base64;
import org.mindrot.jbcrypt.BCrypt;

// For User Creation
import java.util.Scanner;

// For MySQL
import java.sql.*;

public class UserRegistration {
	private final String dbURL;
	private final String dbUsername;
	private final String dbPassword;

	public UserRegistration(String dbURL, String dbUsername, String dbPassword) {
		this.dbURL = dbURL;
		this.dbUsername = dbUsername;
		this.dbPassword = dbPassword;
	}

	public void registerUser() {
		try (Connection connection = DriverManager.getConnection(dbURL, dbUsername, dbPassword)) {
			Scanner scanner = new Scanner(System.in);
			String email, password, firstname, lastname;
			int userID;
			boolean isUnique = false;

			while (!isUnique) {
				System.out.print("Enter your email: ");
				email = scanner.nextLine();
				System.out.print("Enter a password: ");
				password = scanner.nextLine();
				System.out.print("Enter your first name: ");
				firstname = scanner.nextLine();
				System.out.print("Enter you last name: ");
				lastname = scanner.nextLine();

				userID = insertUser(connection, email, firstname, lastname);
				if (userID != -1) {
					System.out.println("User created successfully with ID: " + userID);
					isUnique = true;
					String salt = generateBcryptSalt();
					String saltPassword = generateSaltPassword(password, salt);
					insertSaltRecord(connection, userID, salt);
					insertSaltPasswordRecord(connection, userID, saltPassword);
				} else {
					System.out.println("Error: Email is already in use. Try a different email.");
				}
			}
			scanner.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private int insertUser(Connection connection, String email, String firstname, String lastname) {
		String sqlStatement = "INSERT INTO users (email, firstname, lastname) VALUES (?, ?, ?)";
		try( PreparedStatement statement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS)) {
			statement.setString(1, email);
			statement.setString(2, firstname);
			statement.setString(3, lastname);
			statement.executeUpdate();

			try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					return generatedKeys.getInt(1);
				}
			}
		} catch (SQLException e) {
			if (e.getSQLState().equals("23000")) {
				// 23000 indicates a violation of the unique constraint
				// so we can tell that the email is already in use
				return -1;
			} else {
				e.printStackTrace();
			}
		}
		return -1;
	}

	private void insertSaltPasswordRecord(Connection connection, int id, String saltedPassword) {
		String sqlStatement = "INSERT INTO saltpw (id, saltpw) VALUES (?, ?)";
		try (PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
			statement.setInt(1, id);
			statement.setString(2, saltedPassword);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void insertSaltRecord(Connection connection, int id, String salt) {
		String sqlStatement = "INSERT INTO salts (id, salt) VALUES (?, ?)";
		try (PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
			statement.setInt(1, id);
			statement.setString(2, salt);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private String generateSaltPassword(String password, String salt) {
		return BCrypt.hashpw(password, salt);
	}

	private String generateSaltString() {
		SecureRandom random = new SecureRandom();
		byte[] saltBytes = new byte[16];
		random.nextBytes(saltBytes);
		return Base64.getEncoder().encodeToString(saltBytes);
	}

	private String generateBcryptSalt() {
		return BCrypt.hashpw(generateSaltString(), BCrypt.gensalt());
	}
}
