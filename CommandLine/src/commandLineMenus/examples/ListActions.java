package commandLineMenus.examples;

import java.util.ArrayList;

import commandLineMenus.*;
import commandLineMenus.interfaces.ListAction;
import commandLineMenus.interfaces.ListData;

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

	// Returns the list to print
	private static List<String> getPeopleList(final java.util.List<String> people)
	{
		List<String> liste = new List<>("Select someone to display his name",
				getListModelPersonnes(people),
				getActionListePersonnes());
		liste.setAutoBack(false);
		liste.addQuit("q");
		return liste;
	}
	
	private static ListData<String> getListModelPersonnes(final java.util.List<String> people)
	{
		return new ListData<String>()
		{
			@Override
			public java.util.List<String> getList()
			{
				// Retourne les éléments affichés dans le menu.
				return people;
			}
		};
	}
	
	private static ListAction<String> getActionListePersonnes()
	{
		return new ListAction<String>()
		{
			// Vide, car on souhaite créer manuellement chaque option.
			public void itemSelected(int indice, String personne)
			{
				System.out.println(personne);
			}
		};
	}
}