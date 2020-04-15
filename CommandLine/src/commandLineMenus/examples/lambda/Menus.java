package commandLineMenus.examples.lambda;

import commandLineMenus.Menu;
import commandLineMenus.Option;
import commandLineMenus.rendering.examples.util.InOut;

class Menus
{
	public static void main(String[] args)
	{
		Menu mainMenu = new Menu("Mein Menu");
		Option calculator = new Option("Calculator", "c");
		Menu sayHello = new Menu("Say Hello Menu", "Hello", "h");
		mainMenu.add(calculator);
		mainMenu.add(sayHello);
		mainMenu.addQuit("q");
		calculator.setAction(() -> 
		{
			int a = InOut.getInt("Input first operand: "),
				b = InOut.getInt("Saisissez Input second operand: ");
				System.out.println("" + a + " + " + b + " = " + (a+b));
		});
		sayHello.add(new Option("Say Hello", "h", () -> System.out.println("Hello!")));
		sayHello.addBack("r");
		sayHello.setAutoBack(true);
		mainMenu.start();
	}
}
