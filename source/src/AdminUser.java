class AdminUser extends User {

    public AdminUser(User user) {
        super(user.getUserID(), user.getEmail(), user.getFirstname(), user.getLastname(), user.getAdminStatus());
    }
}
