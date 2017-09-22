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
				() -> people, // la liste à agfficher
				(int indice, String personne) -> System.out.println(personne) // ce qui se produit quand un élément est sélectionné
				);
		liste.setAutoBack(false);
		liste.addQuit("q");
		return liste;
	}
}