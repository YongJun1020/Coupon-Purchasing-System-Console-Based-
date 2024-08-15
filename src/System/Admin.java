package System;

public class Admin extends User{

	public Admin(String id, String name, String password, int phone) {
		super(id, name, password, phone, "Admin");
		// TODO Auto-generated constructor stub
	}
	@Override
	public String generateUserId(int size) {
		String customerIdPrefix = "A";
	    int userIdNumber = size + 1;
	    return customerIdPrefix + String.format("%03d", userIdNumber);
	}
}
