package commandLineMenus.cycleDetection;

import java.util.LinkedList;

import commandLineMenus.Menu;

public class CycleDetectedException extends RuntimeException
{
	private LinkedList<Menu> cycleDetected;
	
	public CycleDetectedException(LinkedList<Menu> cycleDetected)
	{
		this.cycleDetected = cycleDetected;
	}
	
	@Override
	public String toString()
	{
		return "Un circuit a été détecté dans l'arborescence des menus : " + cycleDetected.toString();
	}
}
