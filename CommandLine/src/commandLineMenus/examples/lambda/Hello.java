package commandLineMenus.examples.lambda;

import commandLineMenus.Menu;
import commandLineMenus.Option;

class Hello
{
	public static void main(String[] args)
	{
		Menu menu = new Menu("Menu bonjour");
		Option direBonjour = new Option("Dire Bonjour", "b");
		menu.add(direBonjour);
		menu.addQuit("q");
		direBonjour.setAction( () -> System.out.println("Bonjour !") );
		menu.start();
	}
}
