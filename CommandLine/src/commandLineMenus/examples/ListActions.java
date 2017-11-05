package commandLineMenus.examples;

import java.util.ArrayList;

import commandLineMenus.*;

public class ListActions
{
	public static void main(String[] args)
	{
		// Since List extends Menu, it is possible to assign a List<String> to a Menu.
		Menu peopleMenu = getPeopleList();
		
		// Starts the menu
		peopleMenu.start();
	} 

	// Returns the menu to print, please note that the return type is List
	private static List<String> getPeopleList()
	{
		java.util.List<String> people = new ArrayList<>();
		people.add("Ginette");
		people.add("Marcel");
		people.add("Gisèle");
		
		// The List<> constructor takes as arguments the title, the ListData, and the ListAction.
		List<String> liste = new List<>("Select someone to display his name",
				getListDataPeople(people),
				getActionListPeople());
		liste.setAutoBack(false);
		liste.addQuit("q");
		return liste;
	}
	
	// The listData<> contains the function that refreshes the List.
	private static ListData<String> getListDataPeople(final java.util.List<String> people)
	{
		return new ListData<String>()
		{
			@Override
			public java.util.List<String> getList()
			{
				// Returns the list printed in the menu
				return people;
			}
		};
	}
	
	// The list action contains the methods that are triggered if an item is selected.
	private static ListAction<String> getActionListPeople()
	{
		return new ListAction<String>()
		{
			// Triggered if a item is selected, the index and item are passed as arguments.
			public void itemSelected(int index, String someone)
			{
				System.out.println(someone);
			}
		};
	}
}