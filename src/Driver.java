/* 
 * Driver.java
 * Purpose: Data Structure and Algorithms Lab Project
 * Status: Complete and thoroughly tested
 * Last update: 04/12/18
 * Submitted:  04/19/18
 * Comment: test suite and sample run attached
 * @author: Aaron Yangello
 * @version: 2018.01.11
 */

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Driver {

    static BufferedReader in = new BufferedReader (new InputStreamReader(System.in));

    public static void main(String args[]) throws IOException
    {
    	//Lists of available and unavailable tables in each section
    	ListSLS<Table> petSectionAvailable = new ListSLS<Table>(),
    				   noPetSectionAvailable = new ListSLS<Table>();
    	//List of parties waiting to be seated and parties being served
    	ListSLS<Party> 	partiesWaiting = new ListSLS<Party>(), 
    					partiesSeated = new ListSLS<Party>();
    	
    	//The input string
    	String line;
    	
    	//Prompt the user to enter the table configuration
    	System.out.println("Enter your restaurant configuration:");
    	
    	//Configure tables in the pet-friendly section
    	configureTables("pet-friendly", petSectionAvailable);
    	
    	//Configure tables in the non-pet-friendly section
    	configureTables("non-pet-friendly", noPetSectionAvailable);
    	
    	//Display the menu options for the user
    	System.out.println(	"\r\n" +
    						"Select from the following menu:\r\n" +
    						"\t0.\tClose the restaurant.\r\n" + 
			    			"\t1.\tCustomer party enters the restaurant.\r\n" + 
			    			"\t2.\tCustomer party is seated and served.\r\n" + 
			    			"\t3.\tCustomer party leaves the restaurant.\r\n" + 
			    			"\t4.\tAdd a table.\r\n" + 
			    			"\t5.\tRemove a table.\r\n" + 
			    			"\t6.\tDisplay available tables.\r\n" + 
			    			"\t7.\tDisplay info about waiting customer parties.\r\n" + 
			    			"\t8.\tDisplay info about customer parties being served.\r\n");
    	
    	//Prompt User for menu selection
		System.out.print("Make your menu selection now: ");
    	
    	//Reading each line of input file
		while((line = in.readLine().trim()) != null) {
			//Echo input
			System.out.println(line);
			
			//Decide what to do based on the menu option entered
			switch(line) {
				//Close the restaurant 
				case "0" : close(); 					break;  
				
				//A party of customers enter
				case "1" : partyEnters( partiesWaiting,
										partiesSeated);
														break;
				
				//A party of customers is seated
				case "2" : partySeated(	petSectionAvailable, 
										noPetSectionAvailable, 
										partiesWaiting,
										partiesSeated);
						   								break;
						   								
				//A party of customers leaves the restaurant
				case "3" : partyLeaves(	petSectionAvailable, 
										noPetSectionAvailable, 
										partiesWaiting,
										partiesSeated);
														break;
				
				//A table is added to the section
				case "4" : addTable(petSectionAvailable, 
									noPetSectionAvailable,
									partiesSeated);
														break; 
				
				//A table is removed from the section
				case "5" : removeTable(	petSectionAvailable, 
										noPetSectionAvailable, 
										partiesSeated);
														break;
				
				//Display information about the available tables
				case "6" : displayAvailableTables(	petSectionAvailable,
													noPetSectionAvailable); 	
														break;
				
				//Display information about the waiting parties
				case "7" : displayWaitingParties(partiesWaiting);
														break;
				
				//Display information about the seated parties
				case "8" : displayServedParties(partiesSeated);
														break;
			}
						
			//Debug
			//System.out.println("Test Point: " + tp++);
			//Prompt User for menu selection
			System.out.print("\r\nYou know the options.Make your menu selection now: ");
		}
    }
    
    /**
     * Table Configuration
     * @throws IOException 
     * @throws NumberFormatException 
     */
    private static void configureTables(String section, ListSLS<Table> available) throws NumberFormatException, IOException {
    	//Ask how many tables are in the pet friendly section
    	System.out.print(">>How many tables does your " + section + " section have? ");
    	
    	//Store off number of tables to echo in output file
    	int numTables = Integer.parseInt(in.readLine().trim());
    	
    	//Echo input
    	System.out.println(numTables);
    	
    	//Section to which the table will be added
		boolean preference = (section == "pet-friendly") ? true : false;
    	
    	//Ask for information about each table
    	for(int i = 0; i < numTables; i++) {
    		//Flag indicating that the table exists already
    		boolean exists = true;
    		
    		//Desired name of table
    		String name = "";
    		
    		//Keep asking for a different name while a table with the given name exists
    		while(exists) {
    			//Ask for the table name
        		System.out.print(">>Enter table name:");
    			
	    		//Save off table name to echo in output file
	    		name = in.readLine().trim();
	    		
	    		//Echo input
	    		System.out.println(name);
	    		
	    		//Search for the name to ensure a table by that name does not already exist
	    		exists = searchTables(available, name) >= 0;
	    		
	    		//A table with the given name exists
	    		if(exists) {
	    			//Prompt for the user to enter another name for the table
	    			System.out.println(" This table already exists! Please enter another table name.");
	    		}
    		}
    		
    		//Prompt user to enter the number of seats at the table
    		System.out.print(">>Enter number of seats:");
    		
    		//Save off number of seats to be echoed in the output file
    		int numSeats = Integer.parseInt(in.readLine().trim());
    		
    		//Echo input
    		System.out.println(numSeats);
    		
    		//Add the table to the front of the list
    		available.add(0, new Table(name, numSeats, preference));
    	}
    	
    	//Sort tables by number of seats
    	sortTables(available);
    }
    
    /**
     * Close Restaurant
     */
    private static void close() {
    	System.out.println("We are closing the restaurant...Good Bye!");
    	System.exit(1);
    }
    
    /**
     * Party enters the restaurant
     * @throws IOException 
     */
    private static void partyEnters(ListSLS<Party> partiesWaiting, ListSLS<Party> partiesSeated) throws IOException {
    	//To store the index at which the party will be added.
    	int idx = 0;
    	
    	//To store the desired name of the party
    	String name = "";
    	
    	//Keep asking for a customer name as long as a party by the given name exist
    	while(idx >= 0) {
    	
	    	//Prompt user for party name
	    	System.out.print(">>Enter customer name : ");
	    	
	    	//Save off user input to echo in output file
	    	name = in.readLine().trim();
	    	
	    	//Echo input
	    	System.out.println(name);
	    	
	    	//Search first the list of parties seated. If no party by that name is found
	    	//among the seated parties, search for the name in the list of waiting parties
	    	int idxSeated = searchParties(partiesSeated, name);
	    	
	    	//There was no party found by the given name in the list of seated parties
	    	if(idxSeated < 0) {
	    		//Search the list of parties waiting to be seated for the given name
	    		idx = searchParties(partiesWaiting, name);
	    	}
    	
	    	if(idxSeated >= 0 || idx >= 0) {
	    		//Display there is a party by that name that exists
	    		System.out.println("There already exists a customer with this name in the restaurant.\r\n" + 
	    						   "\tPlease select another name.");
	    	}
    	}
    	
    	//If this is the first customer, add first in the list, otherwise, add them to the end
    	idx = (partiesWaiting.isEmpty()) ? 0 : partiesWaiting.size();
    	
    	//Prompt user for number of seats
    	System.out.print(">>Enter number of seats for customer " + name + ": ");
    	
    	//Save off user input to echo in output file
    	int seats = Integer.parseInt(in.readLine().trim());
    	
    	//Echo input
    	System.out.println(seats);
    	
    	//Prompt user for section preference
    	System.out.print(">>Does your part have pets (Y/N)?");
    	
    	//Save off user input to echo in output file
    	String pets = in.readLine().trim();
    	
    	//Echo input
    	System.out.println(pets);
    	
    	//Add party to the list of waiting parties
    	partiesWaiting.add(idx, new Party(name, seats, (pets.equals("Y") || pets.equals("y") ) ) );    	
    }
    
    /**
     * Party is seated
     */
    private static void partySeated(ListSLS<Table> petSection, ListSLS<Table> noPetSection, 
    								ListSLS<Party> partiesWaiting, ListSLS<Party> partiesSeated) {
    	//There are no parties waiting to be seated 
    	if(partiesWaiting.isEmpty()) {
    		//Display there are no parties waiting
    		System.out.println("No customers to serve!");
    		return;
    	}
    	
    	//There are parties waiting so attempt to seat them
    	//Number of parties waiting
    	int size = partiesWaiting.size();
    	
    	//Flag to indicate that a customer was seated
    	boolean partySeated = false;
    	
    	//Party attempting to seat
		Party party;
    	
    	//Keep attempting to seat customers until one is seated
    	//Or there are no more customers to seat
    	for(int i = 0; !partySeated && i < size; i++) {
    		//Check the next party in line
    		party = partiesWaiting.get(i);
    		
    		//Number of seats required to seat party
    		int seats = party.getSize();
    		
    		//Use the appropriate list of tables
    		ListSLS<Table> tables = party.prefersPetSection() ? petSection : noPetSection;
    		
    		//Number of tables in section
    		int numTables = tables.size();

    		//Look for a table big enough to fit the party until there 
    		//are no more parties to attempt to seat or a table is found
    		for(int j = 0; j < numTables && !partySeated; j++) {
    			//The table at which the party will be seated
        		//Start with the first in the list
        		Table table = tables.get(j);
    			    			
    			//The table is big enough to seat the party
    			if(table.getNumSeats() >= seats) {
    				//Assign the table to the party
    				party.setTable(table);
    				
    				//Remove table from list of available tables in that section
    				tables.remove(j);
    				
    				//Found a table, so seat party at that table
    				//Add party to the end of the list of seated parties
    				partiesSeated.add(partiesSeated.size(), party);
    				
    				//Remove party from waiting list
    				partiesWaiting.remove(i);
    				
    				//Set flag to indicate party is seated
    				partySeated = true;
    				
    				//Display party was seated
    				System.out.println("Serving " + party.toString() + " at table " + table.toString() + ".");
    			}
    		}
    		//No table was found for this party
    		if(!partySeated) {
    			//Display this party will not be seated
    			System.out.println("Could not find a table with " + seats + " seats for customer " + party.getName() + "!");
    		}
    		
    	}
    	
    	//No party was seated
    	if(!partySeated) {
    		//Display no parties were seated
    		System.out.println("No party can be served!");
    	}
    }
    
    /**
     * Party leaves the restaurant
     * @throws IOException 
     */
    private static void partyLeaves(ListSLS<Table> petSection, ListSLS<Table> noPetSection,
    								ListSLS<Party> partiesWaiting, ListSLS<Party> partiesSeated) throws IOException {
    	//No one is being served, so no one leaves
    	if(partiesSeated.isEmpty()) {
    		//Display no one is being served
    		System.out.println("No customer is being served!");
    		return;
    	}
    	
    	//Prompt user for name of party that is leaving
    	System.out.print(">>Enter the name of the customer that wants to leave:");
    	
    	//Save off input to echo in output file
    	String name = in.readLine().trim();
    	
    	//Echo input
    	System.out.println(name);
    	
    	//Search for party among seated customers
    	int idx = searchParties(partiesSeated, name);
    	
    	//Party not found in the list of seated parties
    	if(idx < 0) {
    		//Search for party among waiting customers
    		idx = searchParties(partiesWaiting, name);
    		
    		//Party is not found among those waiting
    		if(idx < 0) {
    			System.out.println("Party " + name + " not found.");
    		}
    		//Party found among those waiting
    		else {
    			//Display they are not allowed to leave
    			System.out.println(" Customer " + name + " is not being served but waiting to be seated.");
    		}
    	}
    	//Party found among those being served
    	else {
    		//Get the party info
    		Party party = partiesSeated.get(idx);
    		
    		//The table the party will free up
    		Table table = party.getTable();
    		
    		//Add table back to list of available tables in the specified section
    		//Table is in pet friendly section
    		if(table.inPetSection()) {    			
    			//Add table to list of available tables in the pet friendly section
    			petSection.add(petSection.size(), table);
    			
    			//Sort the the list of available tables in the pet section
    			sortTables(petSection);
    		}
    		//Table is in non pet friendly section
    		else {
    			//Add table to list of available tables in the pet friendly section
    			noPetSection.add(noPetSection.size(), table);
    			
    			//Sort the the list of available tables in the pet section
    			sortTables(noPetSection);
    		}
    		
    		//Display that the table was freed
			System.out.println("Table " + table.toString() + " has been freed.");
			
			//Remove customer from list of seated parties
			partiesSeated.remove(idx);
			
			//Display the party left
			System.out.println("Customer " + party.toString() + " is leaving the restaurant.");
    	}
    }

    /**
     * A table is added to a section
     * @throws IOException 
     */
    private static void addTable(ListSLS<Table> petSection, ListSLS<Table> noPetSection, ListSLS<Party> partiesSeated) throws IOException {
    	//Display that a table is being added
    	System.out.print(">>You are now adding a table.\r\n" + 
    	//Prompt user for the section to which the table will be added
    					 " To which section would you like to add this table?(P/N):");
    	
    	//Save off input to echo in output file
    	String section = in.readLine().trim();
    	
    	//Echo input
    	System.out.println(section);
    	
    	//Pick the section list to search for
    	ListSLS<Table> tables = (section.equals("P") ? petSection : noPetSection);
    	    	
    	//Flag indicated if the table exists
    	boolean exists = true;
    	
    	//The desired name of the table
    	String name = "";
    	
    	//Keep asking for the table name while a table by that name exists
    	while(exists) {
    		//Prompt user for to enter the table name
    		System.out.print(">>Enter table name:");
    		
    		//Save off input to echo in output file
    		name = in.readLine().trim();
    		
    		//Echo input
    		System.out.println(name);
    		
    		//Search for table by that given name in the specified section
    		exists = searchTables(tables, name) >= 0 || searchPartiesForTable(partiesSeated, name) >= 0;
    		
    		//The table already exists
    		if(exists) {
    			//Convert section input to string representation 
    			String desiredSection = (section.equals("P")) ? "pet-friendly" : "non-pet-friendly";
    			
    			//Display table exists in the desired section
    			System.out.println("This table already exists in the " + desiredSection + " section! Please enter another table name");
    		}
    	}
    	    	
    	//Prompt user for number of seats at the table
    	System.out.print(">>Enter number of seats:");
    	
    	//Save off input to echo in output file
    	int seats = Integer.parseInt(in.readLine().trim());
    	
    	//Echo input
    	System.out.println(seats);
    	
    	//Add table to end of list
    	tables.add(tables.size(), new Table(name, seats, (section.equals("P"))));
    	
    	//Sort the tables
    	sortTables(tables);
    }
   
    /**
     * A table is removed from the section
     * @throws IOException 
     */
    private static void removeTable(ListSLS<Table> petSection, ListSLS<Table> noPetSection, 
    								ListSLS<Party> partiesSeated) throws IOException {
    	//Display that a table is being removed
    	System.out.print(">>You are now removing a table.\r\n" + 
    	//Prompt user for the section to which the table will be removed
    					 " From which section would you like to remove this table?(P/N):");
    	
    	//Save off input to echo in output file
    	String section = in.readLine().trim();
    	
    	//Echo input
    	System.out.println(section);
    	
    	//Pick the section list to search in
    	ListSLS<Table> tables = (section.equals("P")) ? petSection : noPetSection;
    	
    	//Prompt user for name of table to remove
    	System.out.print(">>Enter table name:");
    	
    	//Save off input to echo in output file
    	String name = in.readLine().trim();
    	
    	//Echo input
    	System.out.println(name);
    	
    	//The index of the table to remove
    	//Search list of seated parties to see if table is occupied
    	int idx = searchPartiesForTable(partiesSeated, name);
    	
    	//Table being used
    	if(idx >= 0) {
    		//Display that the table is in use
    		System.out.println("Can't remove a table that is currently in use!");
    	}
    	//The table is not being used
    	else {
    		//Search for table in specified section to ensure it exists
    		idx = searchTables(tables, name);
    		
    		//The table doesn't exist
    		if(idx < 0) {
    			//Convert section input into string representation of the section
    			String sectionName = (section.equals("P") ? "pet-friendly" : "non pet-friendly");
    			
    			//Display the table doesn't exist
    			System.out.println("\r\nThis table doesn't exists in the " + sectionName + " section! Please enter another table name.");
    		}
    		//The table does exist and is unused
    		else {
    			//Remove the table
    			tables.remove(idx);
    			
    			//Display the table was removed
    			System.out.println("Table " + name + " has been removed.");
    		}
    	}
    	
    }

    /**
     * Display information about available tables
     */
    private static void displayAvailableTables( ListSLS<Table> petSectionAvailable, 
    											ListSLS<Table> noPetSectionAvailable) {
    	//Display pet friendly section first    	
    	//Number of tables available in the pet friendly section
    	int size = petSectionAvailable.size();
    	
    	//Display that this info is about tables in the pet friendly section
    	System.out.println("The following " + size + " tables are available in the pet-friendly section:");
    	
    	//Iterate through list and display information about each table
    	for(int i = 0; i < size; i++) {
    		//Display information about the table
    		System.out.println("table " + petSectionAvailable.get(i).toString());
    	}
    	
    	//Display non pet friendly section first    	
    	//Number of tables available in the non pet friendly section
    	size = noPetSectionAvailable.size();
    	
    	//Display that this info is about tables in the non pet friendly section
    	System.out.println("The following " + size + " tables are available in the non-pet-friendly section:");
    	
    	//Iterate through list and display information about each table
    	for(int i = 0; i < size; i++) {
    		//Display information about the table
    		System.out.println("table " + noPetSectionAvailable.get(i).toString());
    	}
    }

    /**
     * Display information about parties waiting to be seated
     */
    private static void displayWaitingParties(ListSLS<Party> partiesWaiting) {
    	//There are no parties waiting to be seated
    	if(partiesWaiting.isEmpty()) {
    		//Display that there are no parties waiting
    		System.out.println("\tNo customers are waiting for tables!");
    		return;
    	}
    	
    	//There are parties waiting
    	//The size of the list of parties waiting
    	int size = partiesWaiting.size();
    	
    	//Display that party information will be display
    	System.out.println("The following customer parties are waiting for tables:");
    	
    	//Loop through the waiting parties
    	for(int i = 0; i < size; i++) {
    		//Display party information
    		System.out.println(partiesWaiting.get(i).toString());
    	}
    	
    }

    /**
     * Display information about parties being served
     */
    private static void displayServedParties(ListSLS<Party> partiesSeated) {
    	//No one is being served
    	if(partiesSeated.isEmpty()) {
    		//Display no one is being served
    		System.out.println("\tNo customers are being served!");
    		return;
    	}
    	
    	//Number of parties being served
    	int size = partiesSeated.size();
    	
    	//Number of parties being served in either section
    	int petSize = 0, noPetSize = 0;
    	
    	//Display strings for those being served 
    	String petInfo = "", noPetInfo = "";
    	
    	//Loop over list of parties being served
    	for(int i = 0; i < size; i++) {
    		//The party for which info is being collected
    		Party party = partiesSeated.get(i);
    		
    		//The table at which the party is seated
    		Table table = party.getTable();
    		
    		//The party is in the pets friendly section
    		if(table.inPetSection()) {
    			//Increment number of parties in pet friendly section
    			petSize++;
    			
    			//Collect info about party and table
    			petInfo += party.toString() + " at table " + table.toString() + "\r\n";
    		}
    		//The party is in the non pet friendly section
    		else {
    			//Increment number of parties in the non pet friendly section
    			noPetSize++;
    			
    			//Collect info about party and table
    			noPetInfo += party.toString() + " at table " + table.toString() + "\r\n";
    		}
    	}
    	
    	//Display to user that info will be displayed about the parties being served
		//in the pet friendly section.
    	//There is at least one party being served
    	if(petSize > 0) {
			//There is only one customer in the pet friendly section
			if(petSize == 1) {
				//Use "customer is" for correct grammar
				System.out.println("The following customer is being served in the pet-friendly section:");
			}
			//There is more than one customer in the pet friendly section
			else {
				System.out.println("The following customers are being served in the pet-friendly section:");
			}
			
			//Display the info about the parties being served in the pet friendly section
			System.out.print(petInfo);
    	}
		
		//Display to user that info will be displayed about the parties being served
		//in the non pet friendly section.
    	//There is at least one party being served
    	if(noPetSize > 0) {
			//There is only one customer in the non pet friendly section
			if(noPetSize == 1) {
				//Use "customer is" for correct grammar
				System.out.println("The following customer is being served in the non pet-friendly section:");
			}
			//There is more than one customer in the non pet friendly section
			else {
				System.out.println("The following customers are being served in the non pet-friendly section:");
			}
			
			//Display the info about the parties being served in the pet friendly section
			System.out.println(noPetInfo);
    	}
    }
    
    /**
     * Search for particular table in the list of tables using sequential search.
     * @param list - The list to be searched
     * @param tableName - The name of the table for which the search is performed
     * @return - The positive index at which the party exists in the list.
     *  		 A negative 1 (-1) indicates that the party does not exist in the list
     *  
     *  <pre>
     * {@code
     * //Create a list of table
     * ListSLS<Table> tables = new ListSLS<Table>();
     *  
     * //The desired name for a new table
     * int desiredName = "Table 1";
     *  
     * //Search to see if a table by this name already exists.
     * int index = search(tables, desiredName);
     *  
     * //The result is negative, which indicates it does not exist in the list
     * if(index < 0) {
     * 			
     *  //Add the table to the list
     *  //This table's name is "Table 1", has 5 seats, and is in the 
     *  //pet friendly section
     *  tables.add(tables.size(), new Table(desiredName, 5, true);
     *  	
     *  //Display that that the table was added
     *  System.out.print("Table " + desiredName + " was added.");
     * } 
     * 
     * //The result is positive or zero, which means it exists in the list already
     * else {
     * 	//Display that the table exists
     *  System.out.println("Table " + desiredName + " already exists. Please select another name.");
     * }
     * }
     *  </pre>
     */
    private static int searchTables(ListSLS<Table> list, String tableName) {
    	//List is empty
    	if(list.isEmpty()) {
    		//The item is not here and should be inserted at index 0
    		return -1;
    	}
    	
    	//The index at which the found party exists
    	int idx = -1;
    	
    	//Number of items in list
    	int numItems = list.size();
    	
    	//Flag indicating the party was found
    	boolean found = false;
    	
    	//Iterate over list until party is found or the end is reached
		for(int i = 0; !found && i < numItems; i++) {
			//The party names match
			if(tableName.equals(list.get(i).getName())) {
				//Save the index at which the party was found
				idx = i;
				//Set the flag that indicates the party was found
				found = true;
			}
		}
		
		//Return the index at which the party exists in the list
		return idx;
	}
    
    /**
     * Search for particular party in list using sequential search.
     * @param list - The list to be searched
     * @param tableName - The name of the table for which the search is performed
     * @return - The positive index at which the party exists in the list.
     *  		 A negative 1 (-1) indicates that the party does not exist in the list
     *  
     *  <pre>
     * {@code
     * //Create a list of table
     * ListSLS<Party> parties = new ListSLS<Party>();
     *  
     * //The desired name for a new party
     * int desiredName = "Jones";
     *  
     * //The index at which the party should be inserted.
     * //Search to see if a party by this name already exists.
     * int index = search(parties, desiredName);
     *  
     * //The result is negative, which indicates it does not exist in the list
     * if(index < 0) {  	
     *  //Display that that the party does not exist
     *  System.out.print("Party " + desiredName + " does not exist.");
     * } 
     * 
     * //The result is positive or zero, which means it exists in the list already
     * else {
     * 	//Display that the party exists
     *  System.out.println("Party " + desiredName + " already exists. Please select another name.");
     * }
     * }
     *  </pre>
     */
    private static int searchParties(ListSLS<Party> list, String partyName) {
    	//List is empty
    	if(list.isEmpty()) {
    		//The item is not here and should be inserted at index 0
    		return -1;
    	}
    	
    	//The index at which the found party exists
    	int idx = -1;
    	
    	//Number of items in list
    	int numItems = list.size();
    	
    	//Flag indicating the party was found
    	boolean found = false;
    	
    	//Iterate over list until party is found or the end is reached
		for(int i = 0; !found && i < numItems; i++) {
			//The party names match
			if(partyName.equals(list.get(i).getName())) {
				//Save the index at which the party was found
				idx = i;
				//Set the flag that indicates the party was found
				found = true;
			}
		}
		
		//Return the index at which the party exists in the list
		return idx;
	}
    
    /**
     * Search for particular table being used by a particular party using sequential search.
     * @param list - The list of parties to be searched
     * @param tableName - The name of the table for which the search is performed
     * @return - The positive index at which the table is occupied by a party in the list.
     *  		 A negative 1 (-1) indicates that the table is not being occupied
     *  
     *  <pre>
     * {@code
     * //The following code provides an example of using the 
     * //searchPartiesForTable method to see if a table is being used
     * 
     * //Create a list of parties
     * ListSLS<Party> parties = new ListSLS<Party>();
     *  
     * //The table for which the list will be searched
     * int tableName = "Table 1";
     *  
     * //The index at which the party using the table exists
     * int index = searchPartiesForTable(parties, tableName);
     *  
     * //The result is negative, which indicates it does not exist in the list
     * if(index < 0) {
     * 	//Display the table is not being used
     *  System.out.println("Table was not found in the list of occupied tables"); 	
     * } 
     * 
     * //The result is positive or zero, which means it is being used
     * else {
     * 	//Display that a party is at that table
     *  System.out.println("There is a party using that table");
     * }
     * }
     *  </pre>
     */
    private static int searchPartiesForTable(ListSLS<Party> list, String tableName) {
    	//List is empty
    	if(list.isEmpty()) {
    		//The item is not here and should be inserted at index 0
    		return -1;
    	}
    	
    	//The index at which the found party exists
    	int idx = -1;
    	
    	//Number of items in list
    	int numItems = list.size();
    	
    	//Flag indicating the party was found
    	boolean found = false;
    	
    	//Iterate over list until party is found or the end is reached
		for(int i = 0; !found && i < numItems; i++) {
			//The party names match
			if(tableName.equals(list.get(i).getTable().getName())) {
				//Save the index at which the party was found
				idx = i;
				//Set the flag that indicates the party was found
				found = true;
			}
		}
		
		//Return the index at which the party exists in the list
		return idx;
    }


    /**
     * Sorts the tables in ascending order by number of seats using an improved bubble sort algorithm
     * @param tables - The list of tables to be sorted
     * 
     * <pre>
     * {@code
     * //Create a list of tables
     * ListSLS<Table> tables = new ListSLS<Table>();
     * 
     * //Create 3 tables with different numbers of seats
     * tables.add("Table 1", 3, true);
     * tables.add("Table 2", 5, true);
     * tables.add("Table 3", 2, true);
     * 
     * //Sort tables by number of seats
     * sortTables(tables);
     * 
     * //Number of tables in the list
     * int size = tables.size();
     * 
     * //Loop over list
     * for(int i = 0; i < size; i++) {
     *  //Display table name and number of seats
     *  System.out.println(tables.get(i).getName() + "(" + tables.get(i).getNumSeats() + " seats)");
     * }
     * 
     * //Output:
     * //Table 3 (2 seats)
     * //Table 1 (3 seats)
     * //Table 2 (5 seats)
     * }</pre>
     */
    private static void sortTables(ListSLS<Table> tables) {
    	//The number of tables in the list
    	int size = tables.size();
    	
    	//There is 1 or 0 items in the list
    	if(size < 2) {
    		//There is nothing to sort
    		return;
    	}
    	
    	//There is more than 1 item to sort
    	//The flag indicating there is no more sorting to do
    	boolean isSorted = false;
    	
    	//Keep iterating until the list is sorted
    	for(int i = 0; !isSorted && i < size; i++) {
    		//Assume the list is sorted until there is a swap
    		isSorted = true;
    		
    		//Iterate over the unsorted part
        	for(int j = 0; j < size-i-1; j++) {
        		//Temporary tables used to compare against each other
        		Table table1 = tables.get(j), table2 = tables.get(j+1);
        		
        		//The first table has less seats than the second table or
        		//The number of seats are equal and table 1's name is lexicographically
        		//greater than table 2's name
        		if(table1.getNumSeats() > table2.getNumSeats() ) {
        			//||
				  //(table1.getNumSeats() == table2.getNumSeats() &&
        		   //table1.getName().compareTo(table2.getName()) > 0) ) {
        			//Swap tables
        			//Insert the second table in the first table's spot
        			tables.add(j, table2);
        			
        			//Remove the second table
        			tables.remove(j+2);
        			
        			//There were changes, so it is not sorted
        			isSorted = false;
        		}
        	}
    	}    	
    }
}











