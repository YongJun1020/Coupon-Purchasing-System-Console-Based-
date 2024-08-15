package System;

public abstract class User {
	protected String id;
	private String name;
	private String password;
	private int phone;
	private String type;
	
	public User(String id, String name, String password, int phone, String type) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.phone = phone;
		this.type = type;
	}
	
	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public int getPhone() {
		return phone;
	}
	
	public void setPhone(int phone) {
		this.phone = phone;
	}
	
	public String getType() {
		return type;
	}
	
	abstract String generateUserId(int size);
}
