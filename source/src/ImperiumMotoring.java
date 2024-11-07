public class ImperiumMotoring {
	public static void main(String[] args) {
		String dbURL = "";
		String dbUsername = "";
		String dbPassword = "";
		
		Utils utils = new Utils(dbURL, dbUsername, dbPassword);
		User user = new User();
		/*
		// Registration Test
		UserRegistration userRegistration = new UserRegistration(dbURL, dbUsername, dbPassword);
		userRegistration.registerUser();
		
		*/
			
		// Login Test
		if(Utils.loginUser(user)) {
			System.out.println("You are logged in!");
			if(user.getAdminStatus()) { 
				user = new AdminUser(user);
				System.out.println("Is Admin: " + ((AdminUser)user).getAdminStatus());
			}
			// Delete User Test
			//Utils.deleteUser(user);
		} else {
			System.out.println("Invalid login.");
		}
		
	}
}
