package commandLineMenus.examples.lambda;

import java.util.ArrayList;

import commandLineMenus.*;

public class ListOptionRenderer
{
	private static ListItemRenderer<String> getListItemRenderer()
	{
		return new ListItemRenderer<String>()
		{
			@Override
			public String title(int index, String item){return item;}
			@Override
			public String shortcut(int index, String item){return "" + item.charAt(0) + item.charAt(2);}
			@Override
			public String empty(){return null;}
		};
	}

	private static Option getOptionAfficher(String element)
	{
		return new Option("Afficher", "a", () -> System.out.println(element));
	}
	
	private static Menu getSomeoneMenu(String element)
	{
		Menu menu = new Menu(element);
		menu.add(getOptionAfficher(element));
		menu.addBack("q");
		return menu;
	}
	
	private static List<String> getPeopleMenu()
	{
		final ArrayList<String> people = new ArrayList<>();
		people.add("Ginette");
		people.add("Marcel");
		people.add("Gisèle");
		List<String> menu = new List<>(
				"People list",
				"p",
				() -> people,
				(String element) -> getSomeoneMenu(element)
				);
		menu.setListItemRenderer(getListItemRenderer());
		menu.addQuit("q");
		return menu;
	}
	
	public static void main(String[] args)
	{
		Menu menu = new Menu("Root");
		menu.add(getPeopleMenu());
		menu.start();
	}
}