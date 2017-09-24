package commandLineMenus.examples.lambda;

import commandLineMenus.Menu;
import commandLineMenus.Option;
import commandLineMenus.rendering.examples.util.InOut;

class Menus
{
	public static void main(String[] args)
	{
		Menu menuPrincipal = new Menu("Menu Principal");
		Option calculatrice = new Option("Calculatrice", "c");
		Menu direBonjour = new Menu("Menu bonjour", "Bonjour", "b");
		menuPrincipal.add(calculatrice);
		menuPrincipal.add(direBonjour);
		menuPrincipal.addQuit("q");
		calculatrice.setAction( () -> 
		{
			int a = InOut.getInt("Saisissez la première opérande : "),
				b = InOut.getInt("Saisissez la deuxième opérande : ");
				System.out.println("" + a + " + " + b + " = " + (a+b));
		});
		direBonjour.add(new Option("Dire bonjour", "b", 
				() -> System.out.println("Bonjour !")
				));
		direBonjour.addBack("r");;
		direBonjour.setAutoBack(true);
		menuPrincipal.start();
	}
}
