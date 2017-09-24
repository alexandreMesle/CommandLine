package commandLineMenus.examples;

import commandLineMenus.Menu;
import commandLineMenus.Option;
import commandLineMenus.interfaces.Action;

class Hello
{
	public static void main(String[] args)
	{
		// Creates a menu with the title Hello
		Menu menu = new Menu("Hello Menu");
		// Creates an option with the title "Say Hello", the shorcut to select it is "h"
		Option sayHelloOption = new Option("Say Hello", "h");
		// Adds the option sayHello to the menu.
		menu.add(sayHelloOption);
		// Adds the option allowing to close the menu.
		menu.addQuit("q");
		// Creates an action that will be binded to the sayHello option. 
		Action sayHelloAction = new Action()
		{
			// optionSelected() is triggered each time the "sayHello" option is selected.
			@Override
			public void optionSelected()
			{
				System.out.println("Hello !");
			}
		};
		// Binds sayHelloAction to the option sayHelloOption 
		sayHelloOption.setAction(sayHelloAction);
		// Launches the menu
		menu.start();
	}
}
