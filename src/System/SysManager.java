package System;

import java.text.SimpleDateFormat;
import java.util.*;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.format.DateTimeFormatter;


public class SysManager {
	public ArrayList<Cart> cartList = new ArrayList<Cart>();
	public Scanner sc = new Scanner(System.in);
	public Files file =  new Files();
	private SimpleDateFormat dateForm = new SimpleDateFormat("dd/MM/Y");
	
	public String login() {
		boolean exit = false;
		String name = "";
		String password = "";

	    while (!exit) {
	    	while(name.isEmpty() || password.isEmpty()) {
		    	System.out.println("\n\tLogin Page");
		        System.out.print("Name: ");
		        name = sc.nextLine();
		        System.out.print("Password: ");
		        password = sc.nextLine();
		        if(name.isEmpty() || password.isEmpty()) {
			        System.out.println("Name and password cannot be empty. Please try again.");
		    	}
	        }

	        for (User user : file.getUserList()) {
	            if (name.equals(user.getName()) && password.equals(user.getPassword())) {
	                System.out.println("Login Success");
	                return user.getId();
	            }
	        }

	        System.out.println("Wrong account name or password!!! Please try again.\n");
	        exit = true;
	    }
	    return "error";
	}
	
	public void register(boolean isAdmin) {
	    boolean exit = false;

	    while (!exit) {
	        System.out.println("\n\t Register Page");
	        System.out.print("Name         : ");
	        String name = sc.nextLine();
	        System.out.print("Password     : ");
	        String password = sc.nextLine();
	        int phone = 0;
	        boolean validPhone = false;

	        while (!validPhone) {
	            System.out.print("Phone Number : ");
	            try {
	                phone = Integer.parseInt(sc.nextLine());
	                validPhone = true;
	            } catch (NumberFormatException e) {
	                System.out.println("Invalid phone number. Please enter a valid integer.");
	            }
	        }

	        System.out.print("Create Account with above details? (y/n): ");
	        String input = sc.nextLine();

	        if (input.equalsIgnoreCase("y")) {
	            String userId = isAdmin ? new Admin("","","",0).generateUserId(file.getUserList().size()) :
	            						  new Customer("","","",0).generateUserId(file.getUserList().size());
	            User newUser = isAdmin ? new Admin(userId, name, password, phone) :
	                                      new Customer(userId, name, password, phone);
	            file.getUserList().add(newUser);
	            file.saveFile();
	            exit = true;
	        } else if (input.equalsIgnoreCase("n")) {
	            exit = true;
	        } else {
	            System.out.println("Wrong input, please reenter the details");
	            exit = true;
	        }
	    }
	}

	public boolean isAdmin(String userId) {
		if(userId.charAt(0) == 'A') {
			return true;
		}
		return false;
	}
	
	public void addCoupon() {
		boolean exit =false;
		
		while (!exit) {
			boolean validPrice= false, validStock = false;

		    System.out.print("Enter Item Name: ");
		    String name = sc.nextLine();
		    
		    double price = 0;
		    while (!validPrice) {
		    	System.out.print("Enter Item Price: ");
	            try {
	                price = Double.parseDouble(sc.nextLine());
	                validPrice = true;
	            } catch (NumberFormatException e) {
	                System.out.println("Invalid price number. Please enter a valid integer.");
	            }
	        }
		    
		    int stock = 0;
		    while (!validStock) {
		    	System.out.print("Enter Item Stock: ");
	            try {
	            	stock = Integer.parseInt(sc.nextLine());
	            	validStock = true;
	            } catch (NumberFormatException e) {
	                System.out.println("Invalid stock number. Please enter a valid integer.");
	            }
	        }
		    
	        System.out.print("Create Account with above details? (y/n): ");
	        String input = sc.nextLine();

	        if (input.equalsIgnoreCase("y")) {
	        	String couponId = new Coupon("","",0,0).generateCouponId(file.getCouponList().size());
	            Coupon newCoupon = new Coupon(couponId, name, price, stock);
	            file.getCouponList().add(newCoupon);
	            file.saveFile();
	            exit = true;
	        } else if (input.equalsIgnoreCase("n")) {
	            exit = true;
	        } else {
	            System.out.println("Invalid input. Please enter 'y' or 'n'.");
	        }
        }
	}
	
	public void editCoupon(String id, int choice) {
		boolean found = false, validPrice = false, validStock = false;
		for (Coupon c : file.getCouponList()) {
			if (id.toLowerCase().equals(c.getId().toLowerCase())) {
				found = true;
				switch(choice) {
				case 1:
					System.out.print("Enter new Name :");
					String name = sc.nextLine();
					c.setName(name);
					file.saveFile();
					break;
				case 2:
					double price = 0;
					while (!validPrice) {
				    	System.out.print("Enter new Price: ");
			            try {
			                price = Double.parseDouble(sc.nextLine());
			                validPrice = true;
			            } catch (NumberFormatException e) {
			                System.out.println("Invalid price number. Please enter a valid integer.");
			            }
			        }
					c.setPrice(price);
					file.saveFile();
					break;
				case 3:
					int stock = 0;
					while (!validStock) {
				    	System.out.print("Enter new Stock: ");
			            try {
			            	stock = Integer.parseInt(sc.nextLine());
			            	validStock = true;
			            } catch (NumberFormatException e) {
			                System.out.println("Invalid stock number. Please enter a valid integer.");
			            }
			        }
					c.setQuantity(stock);
					file.saveFile();
					break;
					default:
						System.out.println("Wrong input. Please try again.");
				}
			}
		}
		if (!found) {
            System.out.println("Item not found.");
        }
	}
	public void removeCoupon() {
		boolean exit =false;
		
		while (!exit) {
			boolean itemRemoved = false;
		    System.out.print("Enter Item Id: ");
		    String id = sc.nextLine();
		    
	        System.out.print("Delete item "+ id + "? (y/n): ");
	        String input = sc.nextLine();
	        
	        if (input.equalsIgnoreCase("y")) {
	        	for(Cart c: cartList) {
		        	if (id.toLowerCase().equals(c.getId().toLowerCase())) {
		        		cartList.remove(c);
		        		file.saveFile();
		        		itemRemoved = true;
		        		System.out.println("Item successfully removed.");
	                    break;
		        	}
		        }
		        if (!itemRemoved) {
	                System.out.println("Item not found in the cart.");
	                break;
	            }
	            exit = true;
	        } else if (input.equalsIgnoreCase("n")) {
	            System.out.println("Item removal cancelled.");
	            exit = true;
	        } else {
	            System.out.println("Invalid input. Please enter 'y' or 'n'.");
	        }
        }
	}
	
	public void printSalesReport() {
	    boolean exit = false;
		while(!exit) {
			boolean found = false;
			System.out.print("Year to check         : 20");
	     	String inputStr = sc.nextLine();
	     	int year;
	     	try {
	     		year = Integer.parseInt(inputStr);
	             if (year < 00 || year > 24) {
	                 System.out.println("Invalid month. Please enter a number between 1 and 12.");
	                 continue;
	                 }
	         } catch (NumberFormatException e) {
	             System.out.println("Invalid input. Please enter a number.");
	             continue;
	     	}
	     	Year selectedYear = Year.of(2000 + year); // Adjust the selected year to be in the range 2000-2024
	     	System.out.print("Month to check (1-12): ");
	     	inputStr = sc.nextLine();
	     	int month;
	     	try {
	     	    month = Integer.parseInt(inputStr);
	     	    if (month < 1 || month > 12) {
	     	        System.out.println("Invalid month. Please enter a number between 1 and 12.");
	     	        continue;
	     	    }
	     	} catch (NumberFormatException e) {
	     	    System.out.println("Invalid input. Please enter a number.");
	     	    continue;
	     	}
	     	Month selectedMonth = Month.of(month);
	     	System.out.println("\n\t\t\t\tSales in " + selectedMonth + " "+ selectedYear +"\n");
		    System.out.printf("%-10s%-20s%-20s%-20s%-20s\n", "Id", "Name", "Price", "Quantity", "Total");
		    double totalSales = 0.0;

		    for (Coupon c : file.getCouponList()) {
		    	int quantity = 0;
		    	for (Order o: file.getOrderList()) {
		    		String orderDateStr = o.getDate();
		    		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		            LocalDate orderDate = LocalDate.parse(orderDateStr, formatter);
		            Month orderMonth = orderDate.getMonth();
		     	    int orderYear = orderDate.getYear();
		    		if(orderMonth.equals(selectedMonth) && orderYear == selectedYear.getValue()) {
		    			found = true;
		    			for (int i = 0; i < o.getCoupons().size(); i ++) {
			    			if (c.getId().equals(o.getCoupons().get(i).getId())) {
			    				quantity+= o.getCoupons().get(i).getQuantity();
			    			}
			    		}
		    		}
		    	}
		    	if (found) {
		    		double totalItemSales = c.getPrice() * quantity;
			    	totalSales += totalItemSales;
			    	if(quantity != 0) {
			    		System.out.printf("%-10s%-20s%-20.2f%-20d%-20.2f\n", 
				    			c.getId(), c.getName(), c.getPrice(), quantity, totalItemSales);
			    	}
		    	}
		    }
		    if(!found) {
		    	System.out.println("No Sales in the month");
		    }
		    
		    System.out.printf("\n%61s%15.2f\n", "Total Sales", totalSales);
			exit = true;
	    }
	}

	
	public void viewCoupon() {
		System.out.printf("\n%-20s%-20s%-20s%-20s\n", "Id", "Item", "Price", "Stock");
        for (Coupon item : file.getCouponList()) {
            System.out.printf("%-20s%-20s%-20.2f%-20d\n", 
            		item.getId(), item.getName(), item.getPrice(), item.getQuantity());
        }
	}
	
	public void addCoupon2Cart() {
	    System.out.print("Enter Item Id: ");
	    String itemId = sc.nextLine();
	    
	    System.out.print("Enter Quantity to Add: ");
	    int quantityToAdd;
	    try {
	        quantityToAdd = Integer.parseInt(sc.nextLine());
	    } catch (NumberFormatException e) {
	        System.out.println("Invalid quantity. Please enter a valid integer.");
	        return;
	    }

	    if (cartList.isEmpty() || !checkCart(itemId)) {
	        boolean itemFound = false;
	        for (Coupon coupon : file.getCouponList()) {
	            if (itemId.toLowerCase().equals(coupon.getId().toLowerCase())) {
	                itemFound = true;
	                if (quantityToAdd <= coupon.getQuantity()) {
	                    cartList.add(new Cart(itemId.toUpperCase(), coupon.getName(), coupon.getPrice(), quantityToAdd));
	                    System.out.println("Item successfully added to the cart.");
	                } else {
	                    System.out.println("Not enough quantity available.");
	                }
	                break;
	            }
	        }
	        if (!itemFound) {
	            System.out.println("Item not found in the inventory.");
	        }
	    } else {
	        System.out.println("Item is already in the cart.");
	    }
	}

	private boolean checkCart(String itemId) {
	    for (Cart cartItem : cartList) {
	        if (itemId.toLowerCase().equals(cartItem.getId().toLowerCase())) {
	            return true;
	        }
	    }
	    return false;
	}
	
	public void searchItem() {
		System.out.print("Item Name      : ");
		String name = sc.nextLine().trim();
		int found = 0;
		for (Coupon coupon : file.getCouponList()) {
	        if (coupon.getName().toLowerCase().contains(name.toLowerCase())) {
	            System.out.printf("%-20s%-20s%-20.2f%-20d\n", 
	                    coupon.getId(), coupon.getName(), coupon.getPrice(), coupon.getQuantity());
	            found++;
	        }
	    }
		if (found == 0 ) {
			System.out.println("Item not found.");
		}
		System.out.println("Press enter to continue");
		sc.nextLine();
		System.out.println();
	}
	public double viewCart() {
	    double total = 0;

	    if (cartList.isEmpty()) {
	        System.out.println("Your cart is empty.");
	    } else {
	        System.out.printf("%-5s%-20s%-20s%-20s%-20s\n", "Id", "Item", "Price", "Quantity", "Total");
	        for (Cart c : cartList) {
	            total += c.getTotal();
	            System.out.printf("%-5s%-20s%-20.2f%-20d%-20.2f\n", c.getId(), c.getName(), c.getPrice(),
	                    c.getQuantity(), c.getTotal());
	        }
	        System.out.printf("%65s%-20.2f\n", "Total Price:", total);
	    }
	    return total;
	}
	
	public void editCartCoupon() {
	    if (cartList.isEmpty()) {
	        System.out.println("Your cart is empty, no item to change.");
	        return;
	    }

	    System.out.print("Item Id        : ");
	    String itemId = sc.nextLine();
	    System.out.print("Quantity Change to: ");
	    String inputStr = sc.nextLine();
	    int newQuantity = 0;
	    try {
	        newQuantity = Integer.parseInt(inputStr);
	    } catch (NumberFormatException e) {
	        System.out.println("Invalid input. Please enter a valid number.");
	        return; 
	    }

	    boolean itemFound = false;

	    for (Coupon coupon : file.getCouponList()) {
	        if (itemId.toLowerCase().equals(coupon.getId().toLowerCase())) {
	            itemFound = true;
	            if (newQuantity <= coupon.getQuantity()) {
	                for (Cart cartItem : cartList) {
	                    if (itemId.toLowerCase().equals(cartItem.getId().toLowerCase())) {
	                        cartItem.setQuantity(newQuantity);
	                        break;
	                    }
	                }
	                System.out.println("Successfully Changed");
	            } else {
	                System.out.println("Not Enough Quantity");
	            }
	            break;
	        }
	    }

	    if (!itemFound) {
	        System.out.println("Item not found");
	    }
	}
	
	public boolean checkOut(String id) {
	    boolean exit = false;
	    while (!exit) {
	        double total = viewCart();
	        System.out.println("\nDo you want to checkout? (y/n)");
	        String input = sc.nextLine();

	        if (input.length() > 0) {
	            char choice = input.charAt(0);
	            switch (choice) {
	                case 'y':
	                case 'Y':
	                    makePayment();
	                    createOrder(id, total);
	                    exit = true;
	                    return true;
	                case 'n':
	                case 'N':
	                    exit = true;
	                    System.out.println("Exiting...");
	                    break;
	                default:
	                    System.out.println("Wrong input. Please try again.");
	            }
	        }
	    }
	    return false;
	}
	
	public void makePayment() {
	    String cardNum;
	    String cvv;
	    boolean validInput;
	    do {
	        System.out.print("Enter Card Number (15 digits): ");
	        cardNum = sc.nextLine();
	        System.out.print("Enter CVV (3 digits): ");
	        cvv = sc.nextLine();

	        // Validate card number and CVV lengths
	        if (cardNum.length() != 15 || cvv.length() != 3) {
	            System.out.println("Invalid input. Card Number must be 15 digits and CVV must be 3 digits.");
	            validInput = false;
	        } else {
	            validInput = true;
	        }
	    } while (!validInput);
	}

	private void createOrder(String id, double total) {
	    String date = dateForm.format(new Date());
	    for (int i = 0; i < file.getUserList().size(); i++) {
	        if (id.equals(file.getUserList().get(i).getId())) {
	            Order order = new Order("O" + (file.getOrderList().size() + 1), file.getUserList().get(i).getId(), 
	                                     new ArrayList<>(cartList), total, date);
	            file.getOrderList().add(order);

	            updateCouponQuantities();
	            file.saveFile();
	            cartList.clear(); // Clear cartList after successful checkout
	            System.out.println("Thank you for purchasing the coupons");
	        }
	    }
	}

	private void updateCouponQuantities() {
	    for (int j = 0; j < file.getCouponList().size(); j++) {
	        for (Cart c : cartList) {
	            if (c.getId().equals(file.getCouponList().get(j).getId())) {
	                int newQuantity = file.getCouponList().get(j).getQuantity() - c.getQuantity();
	                file.getCouponList().get(j).setQuantity(newQuantity);
	            }
	        }
	    }
	}
	
	public void viewOrderHistory(String id) {
		boolean exit = false;
	    while(!exit) {
		    double total = 0;
		    boolean foundOrder = false;
	     	System.out.print("Year to check         : 20");
	     	String inputStr = sc.nextLine();
	     	int year;
	     	try {
	     		year = Integer.parseInt(inputStr);
	             if (year < 00 || year > 24) {
	                 System.out.println("Invalid month. Please enter a number between 1 and 12.");
	                 continue;
	                 }
	         } catch (NumberFormatException e) {
	             System.out.println("Invalid input. Please enter a number.");
	             continue;
	     	}
	     	Year selectedYear = Year.of(2000 + year); // Adjust the selected year to be in the range 2000-2024
	     	System.out.print("Month to check (1-12): ");
	     	inputStr = sc.nextLine();
	     	int month;
	     	try {
	     	    month = Integer.parseInt(inputStr);
	     	    if (month < 1 || month > 12) {
	     	        System.out.println("Invalid month. Please enter a number between 1 and 12.");
	     	        continue;
	     	    }
	     	} catch (NumberFormatException e) {
	     	    System.out.println("Invalid input. Please enter a number.");
	     	    continue;
	     	}
	     	Month selectedMonth = Month.of(month);
	     	System.out.println("\n\t\t\t\tOrder made in " + selectedMonth + " "+ selectedYear +"\n");
	     	for (Order order : file.getOrderList()) {
	     	    String orderDateStr = order.getDate();
	     	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	     	    LocalDate orderDate = LocalDate.parse(orderDateStr, formatter);
	     	    Month orderMonth = orderDate.getMonth();
	     	    int orderYear = orderDate.getYear(); // Retrieve the year as an integer
	     	    if (id.equals(order.getCustomerId()) && orderMonth.equals(selectedMonth) && orderYear == selectedYear.getValue()) {
	     	        foundOrder = true;
		            System.out.println("\nOrder id: " + order.getId());
		            System.out.println("Date: " + order.getDate());
		            System.out.println("Item List: ");
		            System.out.printf("%-20s%-20s%-20s%-20s%-20s\n", "No", "Item", "Price", "Quantity", "Total");
		            
		            total = 0;

		            List<Cart> coupons = order.getCoupons();
		            for (int i = 0; i < coupons.size(); i++) {
		                Cart c = coupons.get(i);
		                System.out.printf("%-20d%-20s%-20.2f%-20d%-20.2f\n", i+1, c.getName(), c.getPrice(),
		                        c.getQuantity(), c.getPrice() * c.getQuantity());
		                total += c.getPrice() * c.getQuantity();
		            }
		            System.out.printf("%80s%-20.2f\n", "Total Price:", total);
		        }
		    }

		    if (!foundOrder) {
		        System.out.println("No orders made in this period.");
		    }
		    exit = true;
	    }
	}
	
	public void viewUserDetails(String id) {
		for (User u : file.getUserList()) {
			if (id.equals(u.getId())) {
				System.out.println("\nName :" + u.getName());
				System.out.println("Phone :" + u.getPhone());
			}
		}
	}
	
	public void editUser(String id, int choice) {
		boolean validPhone = false;
		for (User u : file.getUserList()) {
			if (id.equals(u.getId())) {
				switch(choice) {
				case 1:
					System.out.print("Enter new Name :");
					String name = sc.nextLine();
					u.setName(name);
					file.saveFile();
					break;
				case 2:
					System.out.print("Enter new Password :");
					String password = sc.nextLine();
					u.setPassword(password);
					file.saveFile();
					break;
				case 3:
					int phone = 0;
					while (!validPhone) {
				    	System.out.print("Enter new Phone: ");
			            try {
			                phone = Integer.parseInt(sc.nextLine());
			                validPhone = true;
			            } catch (NumberFormatException e) {
			                System.out.println("Invalid phone number. Please enter a valid integer.");
			            }
			        }
					u.setPhone(phone);
					file.saveFile();
					break;
				default:
					System.out.println("Wrong input. Please try again.");
				}
			}
		}
	}
	
	public boolean deleteAccount(String id) {
	    System.out.println("Delete this account? (y/n)");
	    String input = sc.nextLine().trim();

	    if (input.equalsIgnoreCase("y")) {
	        Iterator<User> iterator = file.getUserList().iterator();
	        while (iterator.hasNext()) {
	            User user = iterator.next();
	            if (id.equals(user.getId())) {
	                iterator.remove();
	                file.saveFile();
	                System.out.println("Account deleted successfully.");
	                return true;
	            }
	        }
	        System.out.println("Account with ID " + id + " not found.");
	    } else {
	        System.out.println("Account deletion cancelled.");
	    }
	    return false;
	}
}
