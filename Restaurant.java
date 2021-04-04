import java.util.Formatter;

public class Restaurant {

	// attributes of the restaurant object
	String name;
	String contactNum;
	String location;
	
	// object constructor 
	public Restaurant(String name, String contactNum, String location) {
	
		this.name = name;
		this.contactNum = contactNum;
		this.location = location;
		
		
	}
	
	// this method writes the invoice to a text file called invoice.txt
	public void printInvoice(String invoice) {
		
		try {
			Formatter newFile = new Formatter("invoice.txt");
			newFile.format(invoice);
			newFile.close();
		}
		catch (Exception e){
			System.out.println("Error cant write receipt");
		}
		
		// tells the user the invoice has been printed
		System.out.println("Invoice Printed!");
	}
	
	
	public String getLocation() {
		return this.location;
	}
}
