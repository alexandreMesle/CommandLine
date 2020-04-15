package commandLineMenus.examples.lambda;

import commandLineMenus.Menu;
import commandLineMenus.Option;

class Hello
{
	public static void main(String[] args)
	{
		Menu menu = new Menu("Hello Menu");
		Option sayHelloOption = new Option("Say Hello", "h");
		menu.add(sayHelloOption);
		menu.addQuit("q");
		sayHelloOption.setAction(() -> System.out.println("Hello!"));
		menu.start();
	}
}
