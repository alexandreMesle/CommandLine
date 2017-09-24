package commandLineMenus.tests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import commandLineMenus.Menu;
import commandLineMenus.Menu.CycleDetectedException;
import commandLineMenus.Menu.EmptyMenuException;
import commandLineMenus.Option;

public class TestMenus
{

	@Test
	public void checkTitle()
	{
		Menu menu = new Menu("Bonjour");
		assertEquals(menu.getTitle(), "Bonjour");
	}
	
	@Test
	public void checkShorcut()
	{
		Option option = new Option("Coucou", "sh");
		assertEquals(option.getTitle(), "Coucou");
		assertEquals(option.getShorcut(), "sh");
	}
	
	@Test
	public void addOption()
	{
		Menu menu = new Menu("Coucou");
		Option option = new Option("Option", "shorcut");
		menu.add(option);
		assertTrue(menu.getOptions().contains(option));
		assertEquals(menu.getOptions().size(), 1);
	}
	
	@Test
	public void checkCycle()
	{
		Menu racine = new Menu("racine", "r"),
			feuille = new Menu("feuille", "r");
		racine.add(feuille);
		feuille.add(racine);
		try
		{
			racine.start();
			assertTrue("An exception should have occurred !", false);
		}
		catch(CycleDetectedException c)		
		{
			List<Menu> cycle = c.getCycleDetected();
			assertTrue(cycle.get(0) == racine);
			assertTrue(cycle.get(1) == feuille);
			assertTrue(cycle.get(2) == racine);
		}
	}

	@Test
	public void checkEmptyMnu()
	{
		Menu racine = new Menu("racine", "r");
		try
		{
			racine.start();
			assertTrue("An exception should have occurred !", false);
		}
		catch(EmptyMenuException e)		
		{
			assertEquals(e.getMenu(), racine);
		}
	}
	
}
