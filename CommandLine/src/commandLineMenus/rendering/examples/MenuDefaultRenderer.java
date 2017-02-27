package commandLineMenus.rendering.examples;

import commandLineMenus.rendering.MenuRenderer;

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
	public String betweenOptions()
	{
		return "\n";
	}

	@Override
	public String footer()
	{
		return "\n";
	}

	@Override
	public String betweenMenus()
	{
		return "---------------------------";
	}

	@Override
	public String prompt()
	{
		return "\nSelect an option : ";
	}

	@Override
	public String invalidInput(String input)
	{
		return "The option " + input + " is not available.\n";
	}

}
