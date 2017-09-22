package commandLineMenus.examples;

import commandLineMenus.Menu;
import commandLineMenus.Option;
import commandLineMenus.interfaces.Action;

class Menus
{
	public static void main(String[] args)
	{
		// Création du menu racine de l'application.
		Menu menuPrincipal = new Menu("Menu Principal");
		// Création de deux options
		Option calculatrice = new Option("Calculatrice", "c");
		Menu direBonjour = new Menu("Menu bonjour", "Bonjour", "b");
		// Imbrication des deux options dans le menu
		menuPrincipal.add(calculatrice);
		// Vous remarquez que comme Menu hérite de Option, on peut mettre un menu dans un menu
		menuPrincipal.add(direBonjour);
		menuPrincipal.addQuit("q");
		// Définition de l'action pour la calculatrice
		calculatrice.setAction(new Action()
		{
			// Méthode exécutée lorsque l'option calculatrice est sélectionnée.
			public void optionSelectionnee()
			{
				int a = commandLineMenus.util.InOut.getInt("Saisissez la première opérande : "),
						b = commandLineMenus.util.InOut.getInt("Saisissez la deuxième opérande : ");
				System.out.println("" + a + " + " + b + " = " + (a+b));
			}
		});
		// Il est possible de passer l'action en paramètre directement dans le constructeur.
		direBonjour.add(new Option("Dire bonjour", "b", new Action()
		{
			public void optionSelectionnee()
			{
				System.out.println("Bonjour !");
			}
		}));
		// Ajout d'une option permettant de revenir au menu parent
		direBonjour.addBack("r");;
		// Retour automatique au menu parent si une option est exécutée.
		direBonjour.setAutoBack(true);
		// Lancement du menu
		menuPrincipal.start();
	}
}