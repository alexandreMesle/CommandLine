package commandLineMenus.examples;

import commandLineMenus.Action;
import commandLineMenus.Menu;
import commandLineMenus.Option;
import commandLineMenus.rendering.examples.util.InOut;

class Menus
{
	public static void main(String[] args)
	{
		// Creates the root menu of the application
		Menu rootMenu = new Menu("Root Menu");
		
		// Creates two options
		Option calculatorOption = new Option("Calculator", "c");
		Menu sayHelloMenu = new Menu("Say Hello Sub-Menu", "Hello", "h");
		
		// Adds an option to the rootMenu 
		rootMenu.add(calculatorOption);
		
		// Adds the sub-menu sayHelloMenu to the rootMenu
		// Please notice that since Menu extends Option, polymorphism allows us to pass the Menu sayHelloMenu where an Option was expected.
		rootMenu.add(sayHelloMenu);
		
		// Adds the quit option
		rootMenu.addQuit("q");
		
		// Defines the action that will be triggered if the calculator is selected.
		calculatorOption.setAction(new Action()
		{
			// Method triggered if the calculatorOption is selected 
			public void optionSelected()
			{
				int a = InOut.getInt("Input the first operand : ");
				int b = InOut.getInt("Input the second operand : ");
				System.out.println("" + a + " + " + b + " = " + (a+b));
			}
		});
		
		// Please notice that the action can be passed to the constructor of Option 
		sayHelloMenu.add(				
				new Option("Say Hello", "h", new Action()
				{
					public void optionSelected()
					{
						System.out.println("Hello!");
					}
				}));
		
		// Adds an option to go back to the rootMenu
		sayHelloMenu.addBack("r");
		
		// Once an option has been selected in sayHelloMenu, and the associated action is done, we will automatically go back to the rootMenu. 
		sayHelloMenu.setAutoBack(true);
		
		rootMenu.start();
	}
}