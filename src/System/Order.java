package System;
import java.util.*;

public class Order {
	protected String id;
	private String customerId;
	private List<Cart> coupons;
	private double totalPrice;
	private String date;
	
	
	public Order(String id, String customerId, List<Cart> coupons, double totalPrice, String date) {
		super();
		this.id = id;
		this.customerId = customerId;
		this.coupons = coupons;
		this.totalPrice = totalPrice;
		this.date = date;
	}
	public String getId() {
		return id;
	}
	
	public String getCustomerId() {
		return customerId;
	}
	
	public List<Cart> getCoupons() {
		return coupons;
	}
	
	public double getTotalPrice() {
		return totalPrice;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setCoupons(List<Cart> coupons) {
		this.coupons = coupons;
	}
	
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
}
