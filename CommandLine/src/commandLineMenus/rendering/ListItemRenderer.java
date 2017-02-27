package commandLineMenus.rendering;

public interface ListItemRenderer<T>
{
	public String shortcut(int index, T item);
	
	public String title(int index, T item);
	
	public String empty();
}
