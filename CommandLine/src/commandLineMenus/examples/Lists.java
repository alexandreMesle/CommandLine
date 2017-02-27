package commandLineMenus.examples;

import java.util.ArrayList;

import commandLineMenus.*;

public class Lists
{
	public static void main(String[] args)
	{
		// Création d'une liste contenant les trois chaînes "Ginette", "Marcel" et "Gisèle"
		final ArrayList<String> personnes = new ArrayList<>();
		personnes.add("Ginette");
		personnes.add("Marcel");
		personnes.add("Gisèle");
		// Création d'un menu proposant une option par personne
		List<String> menu = new List<String>("Liste des Personnes", 
			new ListModel<String>()
			{
				// Retourne la liste des personnes formant le menu
				public java.util.List<String> getList()
				{
					return personnes;
				}
	
			},
			new ListAction<String>()
			{
				// Exécutée automatiquement lorsqu'un élément de liste est sélectionné
				public void selectedItem(int indice, String element)
				{
					System.out.println("Vous avez sélectionné "+ element+ ", qui a l'indice " + indice);
				}
	
				// Retourne l'option que l'on souhaite créer, null si l'on préfère que l'option soit 
				// crée automatiquement
				public Menu getOption(final String element)
				{
					return null;
				}
			});
		// Ajoute une option quitter à la fin de la liste
		menu.addQuit("q");
		// Lancement du menu
		menu.start();
	}
}
