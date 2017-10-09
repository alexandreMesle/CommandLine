package commandLineMenus.rendering.examples;

import commandLineMenus.ListItemRenderer;

public class ListItemDefaultRenderer<T> implements ListItemRenderer<T>
{
	@Override
	public String title(int index, T item)
	{
		return item.toString();
	}

	@Override
	public String shortcut(int index, T item)
	{
		return "" + (index + 1);
	}

	@Override
	public String empty()
	{
		return "No item to select.\n";
	}

}
