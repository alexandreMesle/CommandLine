package commandLineMenus.cycleDetection;

import java.util.LinkedList;
import java.util.List;

import commandLineMenus.Menu;

public class CycleDetectedException extends RuntimeException
{
	private static final long serialVersionUID = -2884917321791851520L;
	private LinkedList<Menu> cycleDetected;
	
	public CycleDetectedException(LinkedList<Menu> cycleDetected)
	{
		this.cycleDetected = cycleDetected;
	}
	
	private String stringOfCycle(List<Menu> list)
	{
		String res = "";
		for (Menu menu : list)
			res += menu.getTitle() + " -> ";
		return res;
	}
	
	@Override
	public String toString()
	{
		return "A directed cycle has been detected in the menu tree : " + stringOfCycle(cycleDetected);
	}
}
