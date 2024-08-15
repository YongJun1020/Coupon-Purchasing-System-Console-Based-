package System;

public class Coupon {
	protected String id;
    private String name;
    private double price;
    private int quantity;

    public Coupon(String id, String name, double price, int quantity) {
    	this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }
    
    public double getPrice() {
        return price;
    }
    
	public void setName(String name) {
		this.name = name;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
	
	public String generateCouponId(int size) {
	    String couponIdPrefix = "C";
	    int couponIdNumber = size + 1;
	    return couponIdPrefix + String.format("%03d", couponIdNumber);
	}
}
