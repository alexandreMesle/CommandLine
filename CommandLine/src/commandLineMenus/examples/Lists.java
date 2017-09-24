package commandLineMenus.examples;

import java.util.ArrayList;

import commandLineMenus.*;
import commandLineMenus.interfaces.ListAction;
import commandLineMenus.interfaces.ListData;

public class Lists
{
	public static void main(String[] args)
	{
		// Création d'une liste contenant les trois chaînes "Ginette", "Marcel" et "Gisèle"
		final ArrayList<String> people = new ArrayList<>();
		people.add("Ginette");
		people.add("Marcel");
		people.add("Gisèle");
		// Création d'un menu proposant une option par personne
		List<String> menu = new List<String>("People list", 
			new ListData<String>()
			{
				// Retourne la liste des personnes formant le menu
				public java.util.List<String> getList()
				{
					return people;
				}	
			},
			new ListAction<String>()
			{
				// Exécutée automatiquement lorsqu'un élément de liste est sélectionné
				public void itemSelected(int index, String someone)
				{
					System.out.println("You have selected " + someone + ", who has the index " + index);
				}
			});
		// Ajoute une option quitter à la fin de la liste
		menu.addQuit("q");
		// Lancement du menu
		menu.start();
	}
}