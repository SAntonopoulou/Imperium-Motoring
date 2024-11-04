public class ImperiumMotoring {
	public static void main(String[] args) {
		// storing these here is a temporary solution; we will create a configuration file later
		// the the owners of the shop would then put in their database credentials
		String dbURL = "[ put database link here ]";
		String dbUsername = "[ put database username here ]";
		String dbPassword = "[ put database password here ]";

		UserRegistration userRegistration = new UserRegistration(dbURL, dbUsername, dbPassword);

		userRegistration.registerUser();
	}
}
