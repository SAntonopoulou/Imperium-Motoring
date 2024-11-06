// For Salt Hashing
import java.security.SecureRandom;
import java.util.Base64;
import org.mindrot.jbcrypt.BCrypt;

// For User Login
import java.util.Scanner;
import java.io.Console;

// For MySQL
import java.sql.*;

public class Utils {
	private static String dbURL;
	private static String dbUsername;
	private static String dbPassword;
	
	public Utils(String dbURL, String dbUsername, String dbPassword) {
		this.dbURL = dbURL;
		this.dbUsername = dbUsername;
		this.dbPassword = dbPassword;
	}
	
	public static boolean loginUser(User user) {
		boolean logged = false;
		UserLogin userLogin = new UserLogin(dbURL, dbUsername, dbPassword);
		Object[] userData = userLogin.loginUser();
		if (userData[0] instanceof Boolean && ((Boolean) userData[0])) {
			user.setUserID((Integer) userData[1]);
			user.setEmail((String) userData[2]);
			user.setFirstname((String) userData[3]);
			user.setLastname((String) userData[4]);
			user.setAdminStatus((Boolean) userData[5]);
			logged = true;
		}
		return logged;
	}

	public static void deleteUser(User user) {
		user.deleteUser(dbURL, dbUsername, dbPassword);
	}

	private static User createUser(Object[] userData) {
		return new User(
				(Integer) userData[1], 
				(String) userData[2], 
				(String) userData[3], 
				(String) userData[4],
				(Boolean) userData[5]);
	}	
}
