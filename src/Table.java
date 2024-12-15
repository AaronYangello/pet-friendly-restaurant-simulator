
public class Table {

	private String name;	 //The name of the table
	private int seats;		 //The number of seats at the table
	private boolean section; //The section the table is in
	
	/**
	 * Create a table object by setting the name, seats, and section of the table to
	 * the given parameters.
	 * @param name
	 * @param seats
	 * @param section
	 * 
	 * <pre>
	 * {@code
	 * //Create a new table with name "Table 1" and 
	 * //5 seats in the pet friendly section
	 * Table table = new Table("Table 1", 5, true);
	 * }
	 * </pre>
	 */
	public Table(String name, int seats, boolean section) {
		this.name = name;
		this.seats = seats;
		this.section = section;
	}

	/**
	 * Set the name of the table to the given name
	 * @param name - The name of the table
	 * 
	 * <pre>
	 * {@code
	 * //Create a new table with name "Table 1" and 
	 * //5 seats in the pet friendly section
	 * Table table = new Table("Table 1", 5, true);
	 * 
	 * //Change the name of the table to "Table 2"
	 * table.setName("Table 2");
	 * }
	 * </pre>
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Set the number of seats the table has to the given number
	 * @param numSeats - The number of seats the table has
	 * 
	 * <pre>
	 * {@code
	 * //Create a new table with name "Table 1" and 
	 * //5 seats in the pet friendly section
	 * Table table = new Table("Table 1", 5, true);
	 * 
	 * //Change the number of seats at the table to 3
	 * table.setNumSeats(3);
	 * }
	 * </pre>
	 */
	public void setNumSeats(int numSeats) {
		this.seats = numSeats;
	}
	
	/**
	 * Set the section the table is in to the given section
	 * <p>Note: true indicates the table is in the pet-friendly section
	 *          false indicates the table is in the non pet friendly section 
	 * </p>
	 * @param section - The section the table is in
	 * 
	 * <pre>
	 * {@code
	 * //Create a new table with name "Table 1" and 
	 * //5 seats in the pet friendly section
	 * Table table = new Table("Table 1", 5, true);
	 * 
	 * //Change the section the table is in to non pet friendly
	 * table.setSection(false);
	 * }
	 * </pre> 
	 */
	public void setSection(boolean section) {
		this.section = section;
	}
	
	/**
	 * The name of the table
	 * @return - The name of the table
	 * 
	 * <pre>
	 * {@code
	 * //Create a new table with name "Table 1" and 
	 * //5 seats in the pet friendly section
	 * Table table = new Table("Table 1", 5, true);
	 * 
	 * //Display the table name
	 * System.out.println(table.getName());
	 * 
	 * //Output: Table 1
	 * }
	 * </pre> 
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Number of seats at the table
	 * @return - The number of seats at the table
	 * 
	 * <pre>
	 * {@code
	 * //Create a new table with name "Table 1" and 
	 * //5 seats in the pet friendly section
	 * Table table = new Table("Table 1", 5, true);
	 * 
	 * //Display the number of seats at the table
	 * System.out.println(table.getNumSeats());
	 * 
	 * //Output: 5
	 * }
	 * </pre> 
	 */
	public int getNumSeats() {
		return seats;
	}
	
	/**
	 * Section the table is in
	 * @return - The section the table is in
	 * 
	 * <pre>
	 * {@code
	 * //Create a new table with name "Table 1" and 
	 * //5 seats in the pet friendly section
	 * Table table = new Table("Table 1", 5, true);
	 * 
	 * //Display the section the table is in
	 * System.out.println(table.getSection());
	 * 
	 * //Output: Pet-Friendly
	 * }
	 * </pre> 
	 */
	public String getSection() {
		return (section ? "Pet-Friendly" : "Non-Pet-Friendly");
	}
		
	/**
	 * Indicates if the table is in the pet section
	 * @return - true if in the pet friendly section, false otherwise
	 */
	public boolean inPetSection() {
		return section;
	}
	
	/**
	 * A string representation of the table, which includes the name
	 * and number of seats.
	 * 
	 * <pre>
	 * {@code
	 * //Create a new table with name "Table 1" and 
	 * //5 seats in the pet friendly section
	 * Table table = new Table("Table 1", 5, true);
	 * 
	 * //Display information about the table
	 * System.out.println(table.toString());
	 * 
	 * //Output: table Table 1 with 5 seats
	 * }
	 * </pre> 
	 */
	public String toString() {
		String str = "";
		
		str = name + " with " + seats + " seats";
		
		return str;
	}
}
