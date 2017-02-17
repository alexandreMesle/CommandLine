package commandLineMenus.examples;

import commandLineMenus.Menu;

public class TestCycle
{
	public static void main(String[] args)
	{
		Menu racine = new Menu("racine", "r"),
				feuille = new Menu("feuille", "r");
		racine.ajoute(feuille);
		feuille.ajoute(racine);
		racine.start();		
	}
}
