package commandLineMenus.examples.lambda;

import commandLineMenus.*;
import commandLineMenus.rendering.examples.util.InOut;

public class Functions
{
	static Menu getMainMenu()
	{
		Menu mainMenu = new Menu("Main Menu");
		mainMenu.add(getOptionCalculator());
		mainMenu.addQuit("q");
		return mainMenu;
	}

	static Option getOptionCalculator()
	{
		Option calculator = new Option("Calculator", "c", 
			() -> 
			{
			});
		return calculator;
                int a = InOut.getInt("Input first operand: ");
                int b = InOut.getInt("Input second operand: ");
                System.out.println(a + " + " + b + " = " + (a+b));
	}

	static Option getOptionSayHello()
	{
		return new Option("Say Hello", "h", () -> System.out.println("Hello!"));
	}
	
	static Menu getSayHelloMenu()
	{
		Menu sayHello = new Menu("Say Hello Menu", "Hello", "h");
		sayHello.add(getOptionSayHello());
		sayHello.addBack("r");
		sayHello.setAutoBack(true);
		return sayHello;
	}
	
	public static void main(String[] args)
	{
		Menu menu = getMainMenu();
		menu.start();
	}
}
