package commandLineMenus.examples;

import java.util.ArrayList;

import commandLineMenus.*;

public class Lists
{
	public static void main(String[] args)
	{
		// Creates a list containing "Ginette", "Marcel" et "Gisèle"
		final ArrayList<String> people = new ArrayList<>();
		people.add("Ginette");
		people.add("Marcel");
		people.add("Gisèle");
		
		// Creates a menu with an option for each people in the list
		List<String> menu = new List<String>("People list", 
			new ListData<String>()		
			{
				// Returns the data needed to refresh the list 
				// each time it is displayed. 
				public java.util.List<String> getList()
				{
					return people;
				}	
			},
			new ListAction<String>()
			{				
				// Triggered each time an item is selected
				public void itemSelected(int index, String someone)
				{
					System.out.println("You have selected " + someone 
							+ ", who has the index " + index);
				}
			});
		menu.addQuit("q");
		menu.start();
	}
}