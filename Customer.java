
public class Customer {

	// attributes of the customer object
	int orderNumber;
	String fullName;
	String contactNum;
	String address;
	String location;
	String email;
	
	// customer constructor
	public Customer(int orderNumber, String fullName, String contactNum, String address,
			String location, String email) {
		
		this.orderNumber = orderNumber;
		this.fullName = fullName;
		this.contactNum = contactNum;
		this.address = address;
		this.location = location;
		this.email = email;
	}
	
	public int getOrderNum() {
		return orderNumber;
	}
	
	public String getName() {
		return fullName;
	}
	
	public String getLocation() {
		return location;
	}
	
	
}

