package commandLineMenus.examples.lambda;

import java.util.ArrayList;

import commandLineMenus.*;

public class ListRenderer
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
				(int index, String element) -> System.out.println("You have selected " + element + ", who has the index " + index)
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