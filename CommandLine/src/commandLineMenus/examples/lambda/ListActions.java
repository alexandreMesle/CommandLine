package commandLineMenus.examples.lambda;

import java.util.ArrayList;

import commandLineMenus.List;

public class ListActions
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

	private static List<String> getPeopleList(final java.util.List<String> people)
	{
		List<String> liste = new List<>("Select someone to display his name",
				() -> people, // The list to print
				(int index, String someone) -> System.out.println(someone) // what happens when a item is selected
				);
		liste.setAutoBack(false);
		liste.addQuit("q");
		return liste;
	}
}