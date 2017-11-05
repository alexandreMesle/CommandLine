package commandLineMenus.rendering.examples;

import commandLineMenus.MenuRenderer;
import commandLineMenus.rendering.examples.util.InOut;

public class MenuDefaultRenderer implements MenuRenderer
{

	@Override
	public String header(String title)
	{
		return title + "\n";
	}

	@Override
	public String option(String shortcut, String label)
	{
		return shortcut + " : " + label;
	}
	
	@Override
	public String optionsSeparator()
	{
		return "\n";
	}

	@Override
	public String footer()
	{
		return "\n";
	}

	@Override
	public String menusSeparator()
	{
		return "\n---------------------------\n";
	}
	
	@Override
	public String prompt()
	{
		return "\nSelect an option : ";
	}

	@Override
	public String invalidInput(String input)
	{
		return "The shortcut \"" + input + "\" is not available.\n";
	}

	@Override
	public String inputString()
	{
		while(true)
		{
			try
			{
				return InOut.getString();
			}
			catch (Exception e) 
			{
				System.out.println(invalidInput(""));
			}
		}		
	}

	@Override
	public void outputString(String string)
	{
		System.out.print(string);
	}
	
	@Override
	public String toString()
	{
		return "Menu default renderer";
	}
}
