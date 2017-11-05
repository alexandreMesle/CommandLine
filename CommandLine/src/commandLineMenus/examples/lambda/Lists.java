package commandLineMenus.examples.lambda;

import java.util.ArrayList;

import commandLineMenus.*;

public class Lists
{
	public static void main(String[] args)
	{
		final ArrayList<String> people = new ArrayList<>();
		people.add("Ginette");
		people.add("Marcel");
		people.add("Gisèle");
		List<String> menu = new List<String>(
				"People list", 
				() -> people,
				(int index, String element) -> System.out.println("You have selected "+ element+ ", who has the index " + index)
				);
		menu.addQuit("q");
		menu.start();
	}
}
