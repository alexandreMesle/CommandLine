package commandLineMenus.examples.lambda;

import java.util.ArrayList;

import commandLineMenus.*;
import commandLineMenus.interfaces.ListOption;

public class ListOptions
{
	java.util.List<String> people;
	
	public ListOptions(java.util.List<String> people)
	{
		this.people = people;
		List<String> list = getPeopleList(people);
		list.start();
	}
	
	private List<String> getPeopleList(final java.util.List<String> people)
	{
		List<String> liste = new List<>("Select someone to display his name",
				() -> people, 
				getListOptionPersonne()
				);
		liste.setAutoBack(false);
		liste.addQuit("q");
		return liste;
	}
	
	private ListOption<String> getListOptionPersonne()
	{
		return (String personne) -> new Option("Display " + personne, null, () -> System.out.println(personne));
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