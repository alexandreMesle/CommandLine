package commandLineMenus.examples;

import java.util.ArrayList;

import commandLineMenus.*;
import commandLineMenus.interfaces.Action;
import commandLineMenus.interfaces.ListModel;
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
				getListModelPersonnes(people),
				getOptionListePersonnes());
		liste.setAutoBack(false);
		liste.addQuit("q");
		return liste;
	}
	
	private static ListModel<String> getListModelPersonnes(final java.util.List<String> people)
	{
		return new ListModel<String>()
		{
			@Override
			public java.util.List<String> getList()
			{
				// Retourne les éléments affichés dans le menu.
				return people;
			}
		};
	}
	
	private static ListOption<String> getOptionListePersonnes()
	{
		return new ListOption<String>()
		{
			// Retourne l'option associée à element.
			public Option getOption(final String personne)
			{
				// Crée une option, le raccourci est laissé null car il sera écrasé par l'indice
				return new Option("Display " + personne, null, new Action()
				{
					// Action exécutée si l'option est sélectionnée.
					public void optionSelectionnee()
					{
						System.out.println(personne);
					}
				});
			}
		};
	}
}
