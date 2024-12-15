
public class Party {

	private String name;		//The name and unique identifier of the party
	private int size;			//The size of the party, number of seats required to seat the party
	private Table table;		//The table at which the party is seated
	private boolean sectionPreference;	//The section in which the part prefers to sit
								//True = pet friendly, False = No pets allowed
	
	/**
	 * Create a party object by setting the name, size, and section preference of the party.
	 * @param name - The name of the party
	 * @param size - The size of the party
	 * @param sectionPreference - The section preference of the party
	 * 
	 * <pre>
	 * {@code
	 * //Create a new party of 5 people with name Robert who prefer to sit in the pet 
	 * //friendly section
	 * Party party = new Party("Robert", 5, true);
	 * }
	 * </pre>
	 */
	public Party(String name, int size, boolean sectionPreference) {
		this.name = name;
		this.size = size;
		this.table = null;
		this.sectionPreference = sectionPreference;
	}
	
	/**
	 * Set the name of the party to the given name
	 * @param name - The intended name for the party
	 * 
	 * <pre>
	 * {@code
	 * //Create a new party with name Robert with size 5 in the 
	 * //pet friendly section
	 * Party party = new party ("Robert", 5, true);
	 *  
	 * //Change the name to Jess
	 * party.setName("Jess");
	 * }
	 * </pre>
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Set the size of the party to the given size
	 * @param size - The intended party size
	 * 
	 * <pre>
	 * {@code
	 * //Create a new party with name Robert with size 5 in the 
	 * //pet friendly section
	 * Party party = new party ("Robert", 5, true);
	 * 
	 * //Change the party size to 3
	 * party.setSize(3);
	 * }
	 * </pre>
	 */
	public void setSize(int size) {
		this.size = size;
	}
	
	/**
	 * Set the table at which the party is seated
	 * @param table - The table at which the customer is seated
	 * 
	 * <pre>
	 * {@code
	 * //Create a new party with name Robert with size 5 in the 
	 * //pet friendly section
	 * Party party = new party ("Robert", 5, true);
	 * 
	 * //Create new table with name Table 1 and 5 seats in the
	 * //pet friendly section
	 * Table table = new Table("Table 1", 5, true);
	 * 
	 * //Seat the Robert party at Table 1
	 * party.setTable(table);
	 * }
	 * </pre>
	 */
	public void setTable(Table table) {
		this.table = table;
	}
	
	/**
	 * Set the section preference of the party to the given preference
	 * @param sectionPreference - The intended section preference of the party
	 * 
	 * <pre>
	 * {@code
	 * //Create a new party with name Robert with size 5 in the 
	 * //pet friendly section
	 * Party party = new party ("Robert", 5, true);
	 * 
	 * //Change the section preference of the party
	 * party.setSectionPreference(true);
	 * }
	 * </pre>
	 */
	public void setSectionPreference(boolean sectionPreference) {
		this.sectionPreference = sectionPreference;
	}
	
	/**
	 * The name of the party
	 * @return - The name of the party
	 * 
	 * <pre>
	 * {@code
	 * //Create a new party with name Robert with size 5 in the 
	 * //pet friendly section
	 * Party party = new party ("Robert", 5, true);
	 * 
	 * //Display the name of the party, which is Robert
	 * System.out.println(party.getName());
	 * }
	 * </pre>
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * The size of the party
	 * @return - The size of the party
	 * 
	 * <pre>
	 * {@code
	 * //Create a new party with name Robert with size 5 in the 
	 * //pet friendly section
	 * Party party = new party ("Robert", 5, true);
	 * 
	 * //Display the size of the party, which is 5
	 * System.out.println(party.getSize());
	 * }
	 * </pre>
	 */
	public int getSize() {
		return size;
	}
	
	/**
	 * The table at which the party is seated
	 * <p>Note: A null return value indicates the party is not seated</p>
	 * @return - The table at which the party is seated
	 * 
	 * <pre>
	 * {@code
	 * //Create a new party with name Robert with size 5 in the 
	 * //pet friendly section
	 * Party party = new party ("Robert", 5, true);
	 * 
	 * //Create new table with name Table 1 and 5 seats in the
	 * //pet friendly section
	 * Table table = new Table("Table 1", 5, true);
	 * 
	 * //Seat the Robert party at Table 1
	 * party.setTable(table);
	 * 
	 * //Display the size of the party, which is Table 1
	 * System.out.println(party.getTable());
	 * }
	 * </pre>
	 */
	public Table getTable() {
		return table;
	}
	
	/**
	 * The section preference of the party
	 * @return - True if the party's section preference is the pet-friendly section
	 * 
	 * <pre>
	 * {@code
	 * //Create a new party with name Robert with size 5 in the 
	 * //pet friendly section
	 * Party party = new party ("Robert", 5, true);
	 * 
	 * //Party prefers pet friendly section
	 * if(party.perfersPetSection) {
	 *  //Display the party preference of the party, which is Pet Friendly
	 *  System.out.println("Party prefers pet-friendly section");
	 *  }
	 * }
	 * </pre>
	 */
	public boolean prefersPetSection() {
		return sectionPreference;
	}
	
	/**
	 * A string representation of the party, including name, size, and section preference;
	 * 
	 * <pre>
	 * {@code
	 * //Create a new party with name Robert with size 5 in the 
	 * //pet friendly section
	 * Party party = new party ("Robert", 5, true);
	 * 
	 * //Display the party information
	 * System.out.println(party.toString());
	 * 
	 * //Output: Customer Robert party of 5(Pet)
	 * }
	 * </pre>
	 */
	public String toString() {
		//The return string
		String str = "";
		
		//Decode the section to display a string representing the section preference
		String section = (sectionPreference) ? "Pet" : "No Pet";
		
		//Create the string with the party information
		str = "Customer " + name + " party of " + size + "(" + section + ")";
		
		//Return the string
		return str;
	}
}
