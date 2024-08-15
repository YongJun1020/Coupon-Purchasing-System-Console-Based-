package System;
import java.util.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Files {
	private ArrayList<Coupon> couponList = new ArrayList<Coupon>();
	private File couponFile = new File("Coupon.txt");
	private ArrayList<User> userList = new ArrayList<User>();
	private File userFile = new File("User.txt");
	private ArrayList<Order> orderList = new ArrayList<Order>();
	private File orderFile = new File("Order.txt");
	
	
	public ArrayList<Coupon> getCouponList() {
		return couponList;
	}
	
	public void setItemList(ArrayList<Coupon> c) {
		this.couponList = c;
	}
	
	public ArrayList<User> getUserList() {
		return userList;
	}
	
	public void setUserList(ArrayList<User> u) {
		this.userList = u;
	}
	
	public ArrayList<Order> getOrderList() {
		return orderList;
	}
	
	public void setOrderList(ArrayList<Order> o) {
		this.orderList = o;
	}
	
	//TO Check File Exist
	public void checkFileExist() throws Exception 
	{
		if(!couponFile.exists()){
			couponFile.createNewFile();
			}else;
		if(!userFile.exists()){
			userFile.createNewFile();
			}else;
		if(!orderFile.exists()){
			orderFile.createNewFile();
			}else;
	}	
	//To save file
	public void saveFile() {
		try (
			FileWriter couponWriter = new FileWriter(couponFile, false);
			FileWriter userWriter = new FileWriter(userFile, false);
			BufferedWriter orderWriter = new BufferedWriter(new FileWriter(orderFile, false));
		) {
			
			for (Coupon c : couponList) {
				String id = c.getId();
				String cname = c.getName();
				double price = c.getPrice();
				int quantity = c.getQuantity();
				String string = String.format("%s,%s,%,.2f,%d,\n", id, cname, price, quantity);

				couponWriter.write(string);
			}
			for (User u : userList) {
				String id = u.getId();
				String uname = u.getName();
				String password = u.getPassword();
				int phone = u.getPhone();
				String type = u.getType();
				String string = String.format("%s,%s,%s,%d,%s,\n", id, uname, password, phone, type);
				userWriter.write(string);
			}
			for (Order order : orderList) {
				orderWriter.write(order.getId() + "," + order.getCustomerId() + "," + order.getDate( )+ "," + order.getTotalPrice());
		        for (Cart cart : order.getCoupons()) {
		        	orderWriter.write("," + cart.getId() + "," + cart.getName() + "," + cart.getPrice() 
		            + "," + cart.getQuantity());
		        }
		        orderWriter.newLine(); // Move to the next line for the next order
		     }
			/*
			 * 
			 */
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// To load file
	public void loadFile() throws Exception{
		String line;
	    BufferedReader couponReader = new BufferedReader(new FileReader(couponFile));
	    BufferedReader userReader = new BufferedReader(new FileReader(userFile));
	    BufferedReader orderReader = new BufferedReader(new FileReader(orderFile));
	    couponList.clear();
	    userList.clear();
	    orderList.clear();

	    while ((line = couponReader.readLine()) != null) {
	        String[] fields = line.split(",");
	        if (fields.length == 4) { // Ensure that the line has all necessary fields
	            String id = fields[0].trim();
	            String name = fields[1].trim();
	            double price = Double.parseDouble(fields[2].trim());
	            int quantity = Integer.parseInt(fields[3].trim());
	            Coupon c = new Coupon(id, name, price, quantity);
	            couponList.add(c);
	        } else {
	            // Handle invalid lines or missing fields
	            System.out.println("Invalid line: " + line);
	        }
	    }
	    while ((line = userReader.readLine()) != null) {
	        String[] fields = line.split(",");
	        if (fields.length == 5) { // Ensure that the line has all necessary fields
	            String id = fields[0].trim();
	            String name = fields[1].trim();
	            String password = fields[2].trim();
	            int phone = Integer.parseInt(fields[3].trim());
	            String type = fields[4].trim();
	            User u;
	            if (type.equals("Admin")) {
	                u = new Admin(id, name, password, phone);
	            } else if (type.equals("Customer")) {
	                u = new Customer(id, name, password, phone);
	            } else {
	                System.out.println("Unknown user type: " + type);
	                continue;
	            }
	            userList.add(u);
	        } else {
	            // Handle invalid lines or missing fields
	            System.out.println("Invalid line: " + line);
	        }
	    }

	    while ((line = orderReader.readLine()) != null) {
            String[] fields = line.split(",");
            String orderId = fields[0].trim();
            String customerId = fields[1].trim();
            String date = fields[2].trim();
            double totalPrice = Double.parseDouble(fields[3].trim());
            List<Cart> coupons = new ArrayList<>();
            
            // Iterate through the rest of the fields, each representing a coupon ID and quantity pair
            for (int i = 4; i < fields.length; i += 4) {
                String id = fields[i].trim();
                String name = fields[i+1].trim();
                double price = Double.parseDouble(fields[i+2].trim());
                int quantity = Integer.parseInt(fields[i + 3].trim());
                
                
                Cart cart = new Cart(id, name, price, quantity);
                coupons.add(cart);
            }
            
            Order o = new Order(orderId, customerId, coupons, totalPrice, date);
            orderList.add(o);
        }
	    

	    couponReader.close();
	    userReader.close();
	    orderReader.close();
	}

}
