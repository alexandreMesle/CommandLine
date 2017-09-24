package commandLineMenus.examples;

import commandLineMenus.*;
import commandLineMenus.interfaces.Action;
import commandLineMenus.rendering.examples.util.InOut;

public class Functions
{
	static Menu getMainMenu()
	{
		Menu mainMenu = new Menu("Main Menu");
		mainMenu.add(getCalculatorOption());
		mainMenu.add(getSayHelloMenu());
		mainMenu.addQuit("q");
		return mainMenu;
	}

	static Option getCalculatorOption()
	{
		Option calculator = new Option("Calculator", "c", getCalculatorAction());
		return calculator;
	}

	static Action getCalculatorAction()
	{
		return new Action()
		{
			public void optionSelected()
			{
				int a = InOut.getInt("Input the first operand : "),
						b = InOut.getInt("Input the second operand : ");
				System.out.println("" + a + " + " + b + " = " + (a+b));
			}
		};
	}
	
	static Action getSayHelloAction()
	{
		return new Action()
		{
			public void optionSelected()
			{
				System.out.println("Hello!");
			}
		};
	}
	
	static Option getSayHelloOption()
	{
		return new Option("Say Hello", "h", getSayHelloAction());
	}
	
	static Menu getSayHelloMenu()
	{
		Menu sayHelloMenu = new Menu("Say Hello Menu", "Hello", "h");
		sayHelloMenu.add(getSayHelloOption());
		sayHelloMenu.addBack("r");;
		sayHelloMenu.setAutoBack(true);
		return sayHelloMenu;
	}
	
	public static void main(String[] args)
	{
		Menu menu = getMainMenu();
		menu.start();
	}
}