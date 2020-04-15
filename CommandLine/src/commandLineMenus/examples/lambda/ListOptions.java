package commandLineMenus.examples.lambda;

import java.util.ArrayList;

import commandLineMenus.*;

public class ListOptions
{
	private java.util.List<String> people ;
	
	ListOptions(java.util.List<String> people)
	{
		this.people = people;
		List<String> list = getPeopleList();
		list.start();
	}
	
	private List<String> getPeopleList()
	{
		List<String> liste = new List<>("Select someone",
				() -> people,
				(someone) -> getPersonneMenu(someone)
				);
		liste.setAutoBack(false);
		liste.addQuit("q");
		return liste;
	}
	
	private Option getPersonneMenu(final String someone)
	{
		Menu someoneMenu = new Menu("Edit " + someone, someone, null);
		someoneMenu.add(new Option("show", "s", 
				() -> {System.out.println("You must give the man a name : " + someone + ".");})
				);
		someoneMenu.add(new Option("delete", "d", 
					() -> {
					    people.remove(someone); 
					    System.out.println(someone + " has been deleted.");
				    })
				);
		someoneMenu.setAutoBack(true);
		return someoneMenu;
	}
	
	public static void main(String[] args)
	{
		java.util.List<String> people = new ArrayList<>();
		people.add("Ginette");
		people.add("Marcel");
		people.add("Gisèle");
		new ListOptions(people);
	} 
}