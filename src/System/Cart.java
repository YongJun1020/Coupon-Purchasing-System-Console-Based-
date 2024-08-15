package System;

public class Cart {
    private String id;
    private String name;
    private double price;
	private int quantity;
	
	public Cart(String id, String name, double price, int quantity) {
		this.id = id;
		this.name = name;
		this.price =  price;
		this.quantity = quantity;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public double getPrice() {
		return price;
	}

	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public double getTotal() {
		return quantity * price;
	}
}
