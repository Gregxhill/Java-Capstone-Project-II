import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		
		// create a scanner object
		Scanner input = new Scanner(System.in);
		
		// get user input for customer object
		System.out.println("Enter order number");
		int orderNum = input.nextInt();
		
		System.out.println("Enter customers full name");
		String fullName = input.next();
		fullName += input.nextLine();
		
		System.out.println("Enter customers contact number");
		String custContactNum = input.nextLine();
		
		System.out.println("Enter the customers address");
		String custAddress = input.nextLine();
		
		System.out.println("Enter the customers city");
		String custLocation = input.nextLine();
		
		System.out.println("Enter customers email address");
		String custEmail = input.nextLine();
		
		// create a customer object
		Customer customer1 = new Customer(orderNum, fullName, custContactNum, custAddress, custLocation, custEmail);
		
		
		
		// get user input for restaurant object
		System.out.println(" Enter the restaurants name");
		String restName = input.nextLine();
		
		System.out.println("Enter the restaurants contact number");
		String restContactNum = input.nextLine();
		
		System.out.println("Enter the restaurants location(city)");
		String restLocation = input.nextLine();
		
		//create a restaurant object
		Restaurant restaurant1 = new Restaurant(restName, restContactNum, restLocation);
		
		
		
		// get delivery driver and pass the restaurants location to the method
		String driver = driver(restaurant1.getLocation());
		
		// initialize variables for later
		String foodOrder = "";
		double total = 0;
		String specialPrepInstructions = "";
		
		// if there is a driver available continue with the order else end the program
		if(driver.isEmpty())
		{
			System.exit(0);
		}
		else 
		{
			// create array lists to store the food order
			ArrayList<String> foodItems = new ArrayList<>();
			ArrayList<Double> foodItemsQuantity = new ArrayList<>();
			ArrayList<Double> foodItemsPrices = new ArrayList<>();
			
			//initialize a boolean variable and an index variable
			Boolean submit = false;
			int index = 0;
			
			// this while loop continuously prompts the user to enter their order info until they enter "0"
			while(!submit) {
				
				System.out.println("What would they like to order? Enter 0 to submit/cancel ");
				String inputValue = input.next();
				inputValue += input.nextLine();
				
				// if the user enter "0" then the order is submitted and the loop ends
				if(inputValue.equals("0"))
				{
					submit = true;
				}
				else
				{
					foodItems.add(index, inputValue);
					
					System.out.println("For what price?");
					foodItemsPrices.add(index, input.nextDouble());
					
					System.out.println("How many would they like to purchase?");
					foodItemsQuantity.add(index, input.nextDouble());
					
					
				}
				
			}
			
			
			// the user is prompted to enter special preparation instructions
			System.out.println("Are there any special prep instructions? If not enter 'N' ");
			specialPrepInstructions = input.nextLine();
			if(specialPrepInstructions.equals("N") || specialPrepInstructions.equals("n"))
			{
				specialPrepInstructions = "";
			}
				
			// loops through the foodItems array, calculates the total and adds the orders to the foodOrder string
			for(int i=0; i<foodItems.size(); i++) {
				total += (foodItemsPrices.get(i) * foodItemsQuantity.get(i));
				foodOrder += String.valueOf(foodItemsQuantity.get(i)) + " x " + foodItems.get(i) + 
						" (R" + String.valueOf(foodItemsPrices.get(i)) + ")\n";
			}
				
			
			
			
		}
		
		
		
		
		// adds everything to a string to pass to the restaurant objects printInvoice method
		String receipt = "Order Number: " + String.valueOf(customer1.orderNumber) + "\n" 
		+ "Customer: " + customer1.fullName + "\n" 
			+ "Email: " + customer1.email + "\n"
			+ "Phone Number: " + customer1.contactNum + "\n"
			+ "Location: " + customer1.location + "\n\n"
			+ "You have ordered the following from " + restaurant1.name + " in " + restaurant1.location + ":" + "\n\n"
			+ foodOrder +"\n\n"
			+ "Special Instructions: " + specialPrepInstructions + "\n\n"
			+ "Total: R" + String.valueOf(total) + "\n\n"
			+ driver + " is nearest to the restaurant and so he will be delivering your order to you at:\n\n"
			+ customer1.address + "\n\n"
			+ "If you need to contact the restaurant, their number is " + restaurant1.contactNum;
		
		
		// the restaurant object's printInvoice method is called and is passed the concatenated string
		restaurant1.printInvoice(receipt);
		
		// calls customerOrders function to print out a text file with all the customers
		customerOrders(customer1.getOrderNum(), customer1.getName());
		
		// calls a function to print out the customer names and locations:
		customerLocation(customer1.getName(), customer1.getLocation());
		
		

	}
// this method returns the best driver: based on location and load size
	public static String driver( String restLocation) {
		
		// initializes variables and arraylists for later
		int counter = 0;
		ArrayList<String> relevantDrivers = new ArrayList<>();
		ArrayList<Integer> driverLoads = new ArrayList<>();
		String chosenDriver = "";
		
		
		// reads driver.txt
		try {
			File driversText = new File("drivers", "drivers.txt");
			Scanner textScanner = new Scanner(driversText);
			
			while(textScanner.hasNext()) {
				String driverDetails = textScanner.nextLine();					// saves a line in the text file as a string
				String driverDetailLowercase = driverDetails.toLowerCase();		// converts the string to lowercase
				
				// if the line in the text file contains the restaurants location, it is added to the relevantDrivers arraylist
				if (driverDetailLowercase.contains(restLocation.toLowerCase()))
				{
					relevantDrivers.add(counter, driverDetails);
					counter++;
					
				}
			}
			
			textScanner.close();
		}
		catch (Exception e){
			System.out.println("Cant read drivers.txt");
		}
		
		
		// if no suitable driver is found the user is notified, else the drivers loads are compared
		if(relevantDrivers.isEmpty())
		{
			System.out.println("Sorry cant deliver to your area");
		}
		else
		{
			
		
			// save the driver loads to an integer array list
			for(int i=0; i<relevantDrivers.size(); i++) {
				String driver = relevantDrivers.get(i);
				String load = driver.replaceAll("[^0-9]", "");		// regex expression removes all other characters besides the numeric ones
				driverLoads.add(i, Integer.parseInt(load));			// the loads are added to the driverLoads array as integers
			
			}
		

		
			// saves the value at index 0 and compares the other values to it.
			// the value at the lowest corresponding index (driverLoads[i] == relevantDriver[i]) in relevantDrivers is added to chosenDriver
			int lowestLoad = driverLoads.get(0);
			chosenDriver = relevantDrivers.get(0);
			for(int i =1; i<driverLoads.size(); i++) {
				
				if(driverLoads.get(i) < lowestLoad)
				{
					lowestLoad = driverLoads.get(i);
					chosenDriver = relevantDrivers.get(i);
				}
					
			}
			
			
		
		}
		// calls the updateLoad function and passes the chosen driver
		updateLoad(chosenDriver);
		
		// the string from the text file is split and the drivers name is returned	
		String[] chosenDriverArray = chosenDriver.split(",");
		return chosenDriverArray[0];
		
	}



// This method prints out a text file with the customers names and order numbers in alphabetical order
	public static void customerOrders(int orderNum, String fullName) {
		
		
		ArrayList<String> customerArrayList = new ArrayList<>();
		
		// uses a try catch statement to read and write to the CustomersOrders text file
		try {
			File customers = new File("CustomerOrders.txt");
			Scanner scanFile = new Scanner(customers);
			
			while(scanFile.hasNext()) {
				customerArrayList.add(scanFile.nextLine());
			}
			
			if (customerArrayList.isEmpty()) {									// writes to the file if its empty
				FileWriter fw = new FileWriter(customers);
				PrintWriter pw = new PrintWriter(fw);
				pw.println(fullName.toLowerCase() + orderNum);
				pw.close();
			}
			else {
				FileWriter fw = new FileWriter(customers);
				PrintWriter pw = new PrintWriter(fw);								
				String customerFull = fullName.toLowerCase() + orderNum; 
				customerArrayList.add(customerFull);
				Collections.sort(customerArrayList);								// sorts the array in alphabetical order
				
				for(int i=0; i<customerArrayList.size(); i++) {						// adds customer to the array and writes the array to the file
					pw.println(customerArrayList.get(i));
				}
				
				pw.close();
			}
			
			
			scanFile.close();
		}
		catch (Exception e) {
			System.out.println("Couldnt read CustomerOrders.txt");
		}
		
		
	}
	
// this method updates the drivers.txt file by incrementing the chosen drivers load
	public static void updateLoad(String driver) {
		
		
		String driverName = "";
		String driverLocation = "";
		String driverLoad = "";
		
		String[] driverArray = driver.split(",");									// the string passed to this function is in this format: name, city, load. It is split and processed
		
		
		driverName = driverArray[0];
		driverLocation = driverArray[1];
		driverLoad = driverArray[2].replaceAll("[^0-9]", "");						// extracts the load as an integer from the string
		int driverLoadInt = Integer.valueOf(driverLoad);
		driverLoadInt ++;
		
		// the new updated string is created.
		String updatedLoad = driverName + "," + driverLocation + ", " + driverLoadInt;
		
		
		// update drivers.txt file:
		ArrayList<String> driversFileArray = new ArrayList<>();
		
		// reads the drivers file and saves each line to an array
		try {
			File driversTextFile = new File("drivers", "drivers.txt");
			Scanner sc = new Scanner(driversTextFile);
			
			while(sc.hasNext()){
				driversFileArray.add(sc.nextLine());
			}
			sc.close();
		}
		catch (Exception e) {
			System.out.println("Cant read drivers.txt");
		}
		
		// saves the index of the element in the array that matches the driver info passed into this function
		int index = 0;
		for(int i=0; i<driversFileArray.size(); i++) {
			if(driversFileArray.get(i).equals(driver)) {
				index = i;
			}
		}
		
		// updates the array by replacing the element with the newly created string
		driversFileArray.set(index, updatedLoad);
		
		// rewrites the drivers file with the updated info
		try {
			File driversTextFile = new File("drivers", "drivers.txt");
			FileWriter fw = new FileWriter(driversTextFile);
			PrintWriter pw = new PrintWriter(fw);
			
			for(int i=0; i<driversFileArray.size(); i++) {
				pw.println(driversFileArray.get(i));
			}
			
			pw.close();
		}
		catch (Exception e) {
			System.out.println("cannot read/write to drivers.txt");
		}
		
		
		
	}

// this method prints out a text file with all the served customers, grouped by location (city)
	public static void customerLocation(String fullName, String location) {
		
		// the CustomersLocations file is read and each line stored in the ArrayList
		ArrayList<String> towns = new ArrayList<>();
		try {
			File locationsFile = new File ("CustomersLocations.txt");
			Scanner sc = new Scanner(locationsFile);
			
			while(sc.hasNext()) {
				towns.add(sc.nextLine());
			}
			
			sc.close();
		}
		catch (Exception e) {
			System.out.println("Cant read CustomersLocations");
		}
		
		// iterates through the arraylist and gets the index of the city that matches the customers location(city)
		int index = 0;
		String uppercaseLocation = location.toUpperCase();
		for(int i=0; i<towns.size(); i++) {
			if(towns.get(i).contains(uppercaseLocation)) {
				index = i;
				
			}
			
		}
		
		// adds the customer to the array, under the appropriate city heading (one index after the name of the appropriate city)
		towns.add(index +1, fullName);
		
		
		// writes the new array to the file
		try {
			File locationsFile = new File ("CustomersLocations.txt");
			FileWriter fw = new FileWriter(locationsFile);
			PrintWriter pw = new PrintWriter(fw);
			
			for(int i=0; i<towns.size(); i++){
				pw.println(towns.get(i));
			}
			
			pw.close();
		}
		catch (Exception e) {
			System.out.println("Cant write to CustomersLocations");
		}
		
	}


}



