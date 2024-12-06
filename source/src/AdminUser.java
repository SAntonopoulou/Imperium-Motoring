class AdminUser extends User {
	public AdminUser(User user) {
		super(
				user.getUserID(), 
				user.getEmail(), 
				user.getFirstname(), 
				user.getLastname(),
				user.getAdminStatus());
	}

	public void isAdmin() {
		System.out.println("Yes, I am admin!");
	}
}
