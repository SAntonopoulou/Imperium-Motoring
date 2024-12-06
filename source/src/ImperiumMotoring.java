public class ImperiumMotoring {
	public static void main(String[] args) {
		String dbURL = "jdbc:mysql://impmotordata.santonopoulou.com:3006/impmotor";
		String dbUsername = "impmotorremote";
		String dbPassword = "1lwxDYOVDpuzJnEMpt6P8Lzi33mk5R";
		
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
			System.out.println("Is Admin: " + user.getAdminStatus());
			if(user.getAdminStatus()) { 
				user = new AdminUser(user);
				((AdminUser)user).isAdmin();
			}
			// Delete User Test
			//Utils.deleteUser(user);
		} else {
			System.out.println("Invalid login.");
		}
		
	}
}
