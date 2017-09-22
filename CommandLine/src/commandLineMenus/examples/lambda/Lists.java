package commandLineMenus.examples.lambda;

import java.util.ArrayList;

import commandLineMenus.*;

public class Lists
{
	public static void main(String[] args)
	{
		final ArrayList<String> personnes = new ArrayList<>();
		personnes.add("Ginette");
		personnes.add("Marcel");
		personnes.add("Gisèle");
		List<String> menu = new List<String>(
				"Liste des Personnes", 
				() -> personnes,
				(int indice, String element) -> System.out.println("Vous avez sélectionné "+ element+ ", qui a l'indice " + indice)
				);
		menu.addQuit("q");
		menu.start();
	}
}
