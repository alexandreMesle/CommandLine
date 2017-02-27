package commandLineMenus.examples;

import commandLineMenus.Menu;

public class Cycle
{
	public static void main(String[] args)
	{
		Menu racine = new Menu("racine", "r"),
				feuille = new Menu("feuille", "r");
		racine.add(feuille);
		feuille.add(racine);
		racine.start();		
	}
}
