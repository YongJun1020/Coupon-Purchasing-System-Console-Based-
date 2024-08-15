package App;
import java.util.*;

import System.SysManager ;

public class Application {
	private static Scanner sc = new Scanner(System.in);
	public static SysManager sm =  new SysManager();

	private static final int MENU_LOGIN = 1;
	private static final int MENU_REGISTER = 2;
	private static final int MENU_EXIT = 3;

	private static final int ADMIN_MENU_CHECK_COUPON = 1;
	private static final int ADMIN_MENU_SALES_REPORT = 2;
	private static final int ADMIN_MENU_CREATE_ADMIN_ACCOUNT = 3;
	private static final int ADMIN_MENU_VIEW_ADMIN_ACCOUNT = 4;
	private static final int ADMIN_MENU_EXIT = 5;
	
	private static final int ADMIN_COUPON_MENU_ADD_COUPON = 1;
	private static final int ADMIN_COUPON_MENU_EDIT_COUPON = 2;
	private static final int ADMIN_COUPON_MENU_EXIT = 3;
	
	private static final int ADMIN_EDIT_COUPON_MENU_COUPON_NAME = 1;
	private static final int ADMIN_EDIT_COUPON_MENU_COUPON_PRICE= 2;
	private static final int ADMIN_EDIT_COUPON_MENU_COUPON_STOCK = 3;
	private static final int ADMIN_EDIT_COUPON_MENU_EXIT = 4;
	
	private static final int USER_MENU_VIEW_COUPON = 1;
	private static final int USER_MENU_VIEW_ORDER_HISTORY = 2;
	private static final int USER_MENU_VIEW_ACCOUNT = 3;
	private static final int USER_MENU_DELETE_ACCOUNT = 4;
	private static final int USER_MENU_EXIT = 5;
	
	private static final int USER_COUPON_MENU_ADD_COUPON_2_CART = 1;
	private static final int USER_COUPON_MENU_SEARCH_COUPON = 2;
	private static final int USER_COUPON_MENU_VIEW_CART = 3;
	private static final int USER_COUPON_MENU_EXIT = 4;

	private static final int USER_CART_MENU_EDIT_COUPON_QUANTITY = 1;
	private static final int USER_CART_MENU_REMOVE_CART_COUPON = 2;
	private static final int USER_CART_MENU_CHECKOUT = 3;
	private static final int USER_CART_MENU_EXIT = 4;
	
	private static final int USER_EDIT_MENU_USER_NAME = 1;
	private static final int USER_EDIT_MENU_USER_PASSWORD = 2;
	private static final int USER_EDIT_MENU_USER_PHONE = 3;
	private static final int USER_EDIT_MENU_EXIT = 4;
	
	public static void main (String args[]) {
		try {
		    boolean end = false;
		    while (!end) {
			    sm.file.checkFileExist();
			    sm.file.loadFile();
		        printMenu();
		        String inputStr = sc.nextLine();
	            int input;
	            try {
	                input = Integer.parseInt(inputStr); 
	            } catch (NumberFormatException e) {
	                System.out.println("Invalid input. Please enter a number.");
                	clearScreen();
	                continue;
	            }
		        switch (input) {
		            case MENU_LOGIN:
		                String id = sm.login();
		                if (id.equals("error")) {

		                }else if (sm.isAdmin(id)) {
		                	clearScreen();
		                    handleAdminMenu(id);
		                }else {
		                    handleUserMenu(id);
		                }
		                break;
		            case MENU_REGISTER:
		            	sm.register(false);
		                break;
		            case MENU_EXIT:
		                end = true;
		                break;
		            default:
		                System.out.println("Invalid choice. Please try again.");
		        }
		    }
		} catch (Exception e) {
		    e.printStackTrace();
		} finally {
		    sc.close();
		}
	}

	public static void clearScreen() {
		for (int i = 0; i < 100; i++) {
            System.out.println();
        }
	}

	public static void printMenu() {
		System.out.println("<<Welcome to Coupon Purchasing System!>>");
		System.out.println("1.Login");
		System.out.println("2.Register");
		System.out.println("3.Exit");
		System.out.print("Choice: ");
	}
	
	public static void printMainMenu() 
	{
		System.out.println("\n<<Menu>>");
		System.out.println("1.View Coupon");
		System.out.println("2.Order History");
		System.out.println("3.View Account Details");
		System.out.println("4.Delete Account");
		System.out.println("5.Exit");
		System.out.print("Choice: ");
	}
	
	public static void printCouponMenu() 
	{
		System.out.println("\n<<Menu>>");
		System.out.println("1.Add Coupon to Cart");
		System.out.println("2.Search Coupon");
		System.out.println("3.View Cart");
		System.out.println("4.Back");
		System.out.print("Choice: ");
	}
	
	public static void printCartMenu() 
	{
		System.out.println("\n<<Menu>>");
		System.out.println("1.Edit Coupon");
		System.out.println("2.Remove Coupon");
		System.out.println("3.Checkout");
		System.out.println("4.Back");
		System.out.print("Choice: ");
	}
	
	public static void printUserEditMenu() {
		System.out.println("\n<<Menu>>");
		System.out.println("1.Edit Name");
		System.out.println("2.Edit Password");
		System.out.println("3.Edit Phone");
		System.out.println("4.Back");
		System.out.print("Choice: ");
	}
	
	public static void printAdminMenu() {
		System.out.println("\n<<Menu>>");
		System.out.println("1.View Item");
		System.out.println("2.Print Sales");
		System.out.println("3.Create Admin Account");
		System.out.println("4.View Admin Account");
		System.out.println("5.Back");
		System.out.print("Choice: ");
	}
	
	public static void printAdminCouponMenu() {
		System.out.println("\n<<Menu>>");
		System.out.println("1.Add Item");
		System.out.println("2.Edit Item");
		System.out.println("3.Back");
		System.out.print("Choice: ");
	}
	
	public static void printAdminCouponEditMenu() {
		System.out.println("\n<<Menu>>");
		System.out.println("1.Edit Name");
		System.out.println("2.Edit Price");
		System.out.println("3.Edit Stock");
		System.out.println("4.Back");
		System.out.print("Choice: ");
	}

	private static void handleAdminMenu(String userId) {
	    boolean exit = false;
	    while (!exit) {
	        printAdminMenu();
	        String inputStr = sc.nextLine();
		    int choice;
            try {
            	choice = Integer.parseInt(inputStr);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }
	        switch (choice) {
	            case ADMIN_MENU_CHECK_COUPON:
	            	handleAdminCouponMenu();
	                break;
	            case ADMIN_MENU_SALES_REPORT:
	            	sm.printSalesReport();
	                break;
	            case ADMIN_MENU_CREATE_ADMIN_ACCOUNT:
	            	sm.register(true);
	            	break;
	            case ADMIN_MENU_VIEW_ADMIN_ACCOUNT:	
	            	handleUserAccountMenu(userId);
	            	break;
	            case ADMIN_MENU_EXIT:
	                exit = true;
	                break;
	            default:
	                System.out.println("Invalid choice. Please try again.");
	        }
	    }
	}
	
	private static void handleAdminCouponMenu() {
	    boolean exit = false;
	    while (!exit) {
	    	sm.viewCoupon();
	        printAdminCouponMenu();
	        String inputStr = sc.nextLine();
		    int choice;
            try {
            	choice = Integer.parseInt(inputStr);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }
	        switch (choice) {
	            case ADMIN_COUPON_MENU_ADD_COUPON:
	            	sm.addCoupon();
	                break;
	            case ADMIN_COUPON_MENU_EDIT_COUPON:
	            	handleAdminCouponEditMenu();
	                break;
	            case ADMIN_COUPON_MENU_EXIT:
	                exit = true;
	                break;
	            default:
	                System.out.println("Invalid choice. Please try again.");
	        }
	    }
	}
	
	private static void handleAdminCouponEditMenu() {
	    boolean exit = false;
	    while (!exit) {
	    	sm.viewCoupon();
	    	System.out.print("Enter Item Id to Edit: ");
		    String id = sc.nextLine();
	        printAdminCouponEditMenu();
	        String inputStr = sc.nextLine();
		    int choice;
            try {
            	choice = Integer.parseInt(inputStr);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }
	        switch (choice) {
	            case ADMIN_EDIT_COUPON_MENU_COUPON_NAME:
	            case ADMIN_EDIT_COUPON_MENU_COUPON_PRICE:
	            case ADMIN_EDIT_COUPON_MENU_COUPON_STOCK:
	            	sm.editCoupon(id, choice);
	            	exit = true;
	            	break;
	            case ADMIN_EDIT_COUPON_MENU_EXIT:
	                exit = true;
	                break;
	            default:
	                System.out.println("Invalid choice. Please try again.");
	        }
	    }
	}

	private static void handleUserMenu(String userId) {
	    boolean exit = false;
	    while (!exit) {
	        printMainMenu();
	        String inputStr = sc.nextLine();
		    int choice;
            try {
            	choice = Integer.parseInt(inputStr);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }
	        switch (choice) {
	            case USER_MENU_VIEW_COUPON:
	            	handleCouponMenu(userId);
	                break;
	            case USER_MENU_VIEW_ORDER_HISTORY:
	            	sm.viewOrderHistory(userId);
	                break;
	            case USER_MENU_VIEW_ACCOUNT:
	            	handleUserAccountMenu(userId);
	            	break;
	            case USER_MENU_DELETE_ACCOUNT:
	                exit = sm.deleteAccount(userId);
	                break;
	            case USER_MENU_EXIT:
	                exit = true;
	                break;
	            default:
	                System.out.println("Invalid choice. Please try again.");
	        }
	    }
	}

	private static void handleCouponMenu(String userId) {
	    boolean exit = false;
	    while (!exit) {
	    	sm.viewCoupon();
	        printCouponMenu();
	        String inputStr = sc.nextLine(); 
		    int choice;
            try {
            	choice = Integer.parseInt(inputStr);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue; 
            }
	        switch (choice) {
	            case USER_COUPON_MENU_ADD_COUPON_2_CART:
	            	sm.addCoupon2Cart();
	                break;
	            case USER_COUPON_MENU_SEARCH_COUPON:
	            	sm.searchItem();
	                break;
	            case USER_COUPON_MENU_VIEW_CART:
	            	handleCartMenu(userId);
	                break;
	            case USER_COUPON_MENU_EXIT:
	                exit = true;
	                break;
	            default:
	                System.out.println("Invalid choice. Please try again.");
	        }
	    }
	}
	
	private static void handleCartMenu(String userId) {
	    boolean exit = false;
	    while (!exit) {
	        sm.viewCart();
	        printCartMenu();
	        String inputStr = sc.nextLine();
		    int choice;
            try {
            	choice = Integer.parseInt(inputStr);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }
	        switch (choice) {
	            case USER_CART_MENU_EDIT_COUPON_QUANTITY:
	            	sm.editCartCoupon();
	                break;
	            case USER_CART_MENU_REMOVE_CART_COUPON:
	            	sm.removeCoupon();
	                break;
	            case USER_CART_MENU_CHECKOUT:
	            	sm.checkOut(userId);
	                break;
	            case USER_CART_MENU_EXIT:
	                exit = true;
	                break;
	            default:
	                System.out.println("Invalid choice. Please try again.");
	        }
	    }
	}
	
	private static void handleUserAccountMenu(String userId){
		boolean exit = false;
	    while (!exit) {
	    	sm.viewUserDetails(userId);
	        printUserEditMenu();
	        String inputStr = sc.nextLine();
		    int choice;
            try {
            	choice = Integer.parseInt(inputStr);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }
	        switch (choice) {
	            case USER_EDIT_MENU_USER_NAME:
	            case USER_EDIT_MENU_USER_PASSWORD:
	            case USER_EDIT_MENU_USER_PHONE:
	            	sm.editUser(userId, choice);
	                break;
	            case USER_EDIT_MENU_EXIT:
	                exit = true;
	                break;
	            default:
	                System.out.println("Invalid choice. Please try again.");
	        }
	    }
	}
}
