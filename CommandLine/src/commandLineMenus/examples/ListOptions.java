package commandLineMenus.examples;

import java.util.ArrayList;

import commandLineMenus.*;

public class ListOptions
{
	// If you have to manipulate data, use a private non static field.
	private java.util.List<String> people ;
	
	ListOptions(java.util.List<String> people)
	{
		this.people = people;
		List<String> list = getPeopleList();
		list.start();
	}
	
    // Returns the list to print
	private List<String> getPeopleList()
	{
		List<String> liste = new List<>("Select someone",
				getListDataPeople(),
				getOptionListPeople());
		liste.setAutoBack(false);
		liste.addQuit("q");
		return liste;
	}
	
	private ListData<String> getListDataPeople()
	{
		return new ListData<String>()
		{
			@Override
			public java.util.List<String> getList()
			{
				return people;
			}
		};
	}
	
	private ListOption<String> getOptionListPeople()
	{
		return new ListOption<String>()
		{
			// Each person will become an option
			// The following method returns the option 
			// associated with each one. 
			public Option getOption(String someone)
			{
				return getPersonneMenu(someone);
			}
		};
	}
	
	// Creates an sub-menu for someone. 
	private Option getPersonneMenu(final String someone)
	{
		Menu someoneMenu = new Menu("Edit " + someone, someone, null);
		someoneMenu.add(getDisplaySomeoneOption(someone));
		someoneMenu.add(getDeleteSomeoneOption(someone));
		someoneMenu.setAutoBack(true);
		return someoneMenu;
	}
	
	// Returns the option to display someone
	private Option getDisplaySomeoneOption(String someone)
	{
		return new Option("show", "s", new Action()
		{
			@Override
			public void optionSelected()
			{
				System.out.println("You must give the man a name : " + someone + ".");
			}
		});
	}
	
	// Returns the option to delete someone
	private Option getDeleteSomeoneOption(String someone)
	{
		return new Option("delete", "d", new Action()
		{
			@Override
			public void optionSelected()
			{
				people.remove(someone);
				System.out.println(someone + " has been deleted.");
			}
		});
	}

	// Do not use the main to create the menus, always create
	// into functions.
	public static void main(String[] args)
	{
		java.util.List<String> people = new ArrayList<>();
		people.add("Ginette");
		people.add("Marcel");
		people.add("Gisèle");
		new ListOptions(people);
	} 
}
