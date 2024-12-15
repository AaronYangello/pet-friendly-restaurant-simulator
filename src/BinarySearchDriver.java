import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BinarySearchDriver {
	static BufferedReader in = new BufferedReader (new InputStreamReader(System.in));

    public static void main(String args[]) throws IOException {
         //Create a list of tables
         ListSLS<Table> tables = new ListSLS<Table>();
         
         //Create 3 tables with different numbers of seats
         tables.add(0, new Table("Table 1", 3, true));
         tables.add(1, new Table("Table 2", 5, true));
         tables.add(2, new Table("Table 3", 2, true));
         
         //Sort tables by number of seats
         sortTables(tables);
         
         //Number of tables in the list
         int size = tables.size();
         
         //Loop over list
         for(int i = 0; i < size; i++) {
          //Display table name and number of seats
          System.out.println(tables.get(i).getName() + "(" + tables.get(i).getNumSeats() + " seats)");
         }
         
         //Output:
         //Table 3 (2 seats)
         //Table 1 (3 seats)
         //Table 2 (5 seats)
    }
    
    private static int searchParties(ListSLS<Party> list, String partyName) {
    	if(list.isEmpty()) {
    		return -1;
    	}
    	
    	int numItems = list.size();
    	
		int mid = 0, low = 0, high = numItems - 1;
		
		String midKey = "";
		
		if(numItems > 0 && partyName.compareTo(list.get(numItems - 1).getName()) >= 0) {
			return (-1 - numItems);
		}
		
		while(low < high) {
			mid = (low + high) / 2;
			midKey = list.get(mid).getName();
			
			if(partyName.compareTo(midKey) <= 0) {
				high = mid;
			}
			else { 
				low = mid + 1;
			}
		}
		
		mid = (low + high) / 2;
		
		midKey = list.get(mid).getName();
		
		if(partyName.equals(midKey)) {
			return mid;
		}
		else {
			return (-1 - mid);
		}
	}

    private static int searchTables(ListSLS<Table> list, String tableName) {
    	int numItems = list.size();
    	
		int mid = 0, low = 0, high = numItems - 1;
		
		String midKey = "";
		
		if(numItems > 0 && tableName.compareTo(list.get(numItems - 1).getName()) >= 0) {
			return (-1 - numItems);
		}
		
		while(low < high) {
			mid = (low + high) / 2;
			midKey = list.get(mid).getName();
			
			if(tableName.compareTo(midKey) <= 0) {
				high = mid;
			}
			else { 
				low = mid + 1;
			}
		}
		
		mid = (low + high) / 2;
		
		midKey = list.get(mid).getName();
		
		if(tableName.equals(midKey)) {
			return mid;
		}
		else {
			return (-1 - mid);
		}
	}
    
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
    	while(!isSorted) {
    		//Assume the list is sorted until there is a swap
    		isSorted = true;
    		
    		//Iterate over the unsorted part
        	for(int i = 0; i < size-i; i++) {
        		//Temporary tables used to compare against each other
        		Table table1 = tables.get(i), table2 = tables.get(i+1);
        		
        		//The first table has less seats than the second table
        		if(table1.getNumSeats() > table2.getNumSeats()) {
        			//Swap tables
        			//Insert the second table in the first table's spot
        			tables.add(i, table2);
        			
        			//Remove the second table
        			tables.remove(i+2);
        			
        			//There were changes, so it is not sorted
        			isSorted = false;
        		}
        	}
    	}    	
    }
}
