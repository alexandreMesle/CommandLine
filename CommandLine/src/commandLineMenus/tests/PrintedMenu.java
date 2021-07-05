package commandLineMenus.tests;

import java.util.HashMap;
import java.util.Map;

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
		if (other == null)
		    return false;
		return other.getTitle() == getTitle()
				&& other.getOptions().equals(getOptions());
	}
	
	@Override
	public String toString()
	{
		return  "\n" + title + " " + options.toString();
	}
}
