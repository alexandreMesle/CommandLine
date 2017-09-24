package commandLineMenus.examples;

import java.util.ArrayList;

import commandLineMenus.*;
import commandLineMenus.interfaces.Action;
import commandLineMenus.interfaces.ListData;
import commandLineMenus.interfaces.ListOption;

public class ListOptions
{
	public static void main(String[] args)
	{
		java.util.List<String> people = new ArrayList<>();
		people.add("Ginette");
		people.add("Marcel");
		people.add("Gisèle");
		List<String> list = getPeopleList(people);
		list.start();
	} 

	// Returns the list to print
	private static List<String> getPeopleList(final java.util.List<String> people)
	{
		List<String> liste = new List<>("Select someone to display his name",
				getListDataPersonnes(people),
				getOptionListePersonnes());
		liste.setAutoBack(false);
		liste.addQuit("q");
		return liste;
	}
	
	private static ListData<String> getListDataPersonnes(final java.util.List<String> people)
	{
		return new ListData<String>()
		{
			@Override
			public java.util.List<String> getList()
			{
				// Returns the people that will be printed.
				return people;
			}
		};
	}
	
	private static ListOption<String> getOptionListePersonnes()
	{
		return new ListOption<String>()
		{
			// Each person will become an option, the following method returns the option associated with each one. 
			public Option getOption(final String someone)
			{
				// Creates an option for someone. The shortcut is useless because it will by default be the index of someone. 
				return new Option("Display " + someone, new Action()
				{
					// Method triggered if someone is selected.
					public void optionSelected()
					{
						System.out.println(someone);
					}
				});
			}
		};
	}
}
