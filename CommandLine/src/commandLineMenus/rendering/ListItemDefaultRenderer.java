package commandLineMenus.rendering;

public class ListItemDefaultRenderer<T> implements ListItemRenderer<T>
{
	@Override
	public String toString(T item)
	{
		return item.toString();
	}
}
