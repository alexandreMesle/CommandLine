package commandLineMenus.tests;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

import commandLineMenus.MenuRenderer;

class PrintedMenu
{
	private String title;
	private Map<String, String> options = new HashMap<>();
	
	public void setTitle(String title)
	{
		this.title = title;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public String option(String shortcut, String label)
	{
		options.put(shortcut, label);
		return null;
	}

	public Map<String, String> getOptions()
	{
		return options;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		PrintedMenu other = (PrintedMenu) obj;
		return other.getTitle() == getTitle()
				&& other.getOptions().equals(getOptions());
	}
	
	@Override
	public String toString()
	{
		return  "\n" +title + " " + options.toString();
	}
}

class MenuMockRenderer implements MenuRenderer, Iterable<PrintedMenu>
{
	private Stack<PrintedMenu> printedMenus = new Stack<>();
	private Queue<String> inputs = new LinkedList<String>();
	public static final boolean DEBUG = false;

	public void debug(String message)
	{
		if (DEBUG)
			System.out.println(message);
	}
	
	private PrintedMenu top()
	{
		return printedMenus.peek();
	}
	
	private void push()
	{
		printedMenus.push(new PrintedMenu());
	}
	
	public MenuMockRenderer()
	{
		printedMenus.push(new PrintedMenu());
	}
	
	public void input(List<String> inputs)
	{
		this.inputs.addAll(inputs);
	}

	public void input(String input)
	{
		this.inputs.add(input);
	}
	
	@Override
	public void outputString(String message)
	{
	}

	@Override
	public String inputString()
	{
		debug("User has entered " + inputs.peek());
		if (inputs.isEmpty())
			throw new RuntimeException("Nothing to input !!!");
		return inputs.poll();
	}

	@Override
	public String header(String title)
	{
		debug("Title is " + title);
		top().setTitle(title);
		return title;
	}

	@Override
	public String option(String shortcut, String label)
	{
		debug("Adds shortcut " + shortcut + " for option " + label);
		printedMenus.peek().option(shortcut, label);
		return null;
	}

	@Override
	public String optionsSeparator()
	{
		return null;
	}

	@Override
	public String footer()
	{
		return null;
	}

	@Override
	public String menusSeparator()
	{
		debug("End of menu");
		debug("===========");
		push();
		return null;
	}

	@Override
	public String prompt()
	{
		return null;
	}

	@Override
	public String invalidInput(String input)
	{
		return null;
	}

	@Override
	public Iterator<PrintedMenu> iterator()
	{
		return printedMenus.iterator();
	}
	
	@Override
	public String toString()
	{
		return printedMenus.toString();
	}
	
	public Stack<PrintedMenu> getPrintedMenus()
	{
		return printedMenus;
	}
}
