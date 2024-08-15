package System;

public class Customer extends User {

	public Customer(String id, String name, String password, int phone) {
		super(id, name, password, phone, "Customer");
	}
	
	@Override
	public String generateUserId(int size) {
		String customerIdPrefix = "U";
	    int userIdNumber = size + 1;
	    return customerIdPrefix + String.format("%03d", userIdNumber);
	}
}
