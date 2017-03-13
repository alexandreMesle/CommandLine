package commandLineMenus;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import commandLineMenus.*;

public class DepthFirstSearch
{
	private static void checkEmptyMenu(Menu menu)
	{
		if (!(menu instanceof List<?>) && menu.size() == 0)
			throw menu.new EmptyMenuException();	
	}
	
	private static LinkedList<Menu> checkCycle(Set<Option> enCours, Menu menu)
	{
		if (enCours.contains(menu))
		{
			LinkedList<Menu> cycle = new LinkedList<>();
			cycle.add(menu);
			return cycle;
		}
		return null;
	}
	
	private static void updateCycle(LinkedList<Menu> cycle, Menu menu)
	{
		if (cycle != null 
				&& (cycle.size() == 1 || cycle.getLast() != cycle.getFirst()))
			cycle.addFirst(menu);

	}
	
	private static LinkedList<Menu> depthFirstSearch(Set<Option> enCours, Set<Option> options)
	{
		for(Option option : options)
			if (option instanceof Menu)
			{
				LinkedList<Menu> cycle = depthFirstSearch(enCours, (Menu)option);
				if (cycle != null)
					return cycle;
			}
		return null;
	}
	
	private static LinkedList<Menu> depthFirstSearch(Set<Option> enCours, Menu menu)
	{
		LinkedList<Menu> cycle = checkCycle(enCours, menu);
		if (cycle != null)
			return cycle;
		enCours.add(menu);
		{
			checkEmptyMenu(menu);
			cycle = depthFirstSearch(enCours, menu.getOptions());
			updateCycle(cycle, menu);
		}
		enCours.remove(menu);
		return cycle;
	}
	
	public static void dephtFirstSearch(Menu menu)
	{
		Set<Option> enCours = new HashSet<Option>();
		LinkedList<Menu> cycle = depthFirstSearch(enCours, menu);
		if (cycle != null)
			throw new Menu.CycleDetectedException(cycle);
	}
}
