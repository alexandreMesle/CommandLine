package commandLineMenus.cycleDetection;


import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import commandLineMenus.Menu;
import commandLineMenus.Option;

public class CycleDetector
{
	private static LinkedList<Menu> findCycle(Set<Option> enCours, Menu menu)
	{
		LinkedList<Menu> cycle;
		if (enCours.contains(menu))
		{
			cycle = new LinkedList<>();
			cycle.add(menu);
			return cycle;
		}
		enCours.add(menu);
		for(Option option : menu.getOptions())
			if (option instanceof Menu)
			{
				cycle = findCycle(enCours, (Menu)option);
				if (cycle != null)
				{
					if (cycle.getLast() != cycle.getFirst())
						cycle.addFirst(menu);
					return cycle;
				}
			}
		enCours.remove(menu);
		return null;
	}
	
	public static void findCycle(Menu menu)
	{
		Set<Option> enCours = new HashSet<Option>();
		LinkedList<Menu> cycle = findCycle(enCours, menu);
		if (cycle != null)
			throw new CycleDetectedException(cycle);
	}
	

}
