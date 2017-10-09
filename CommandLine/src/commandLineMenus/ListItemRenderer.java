package commandLineMenus;

/**
 * Overrides the display of an item in a list. By default, 
 * the toString() function is used, but you can customize it.
 * 
 * @see commandLineMenus.rendering.examples.ListItemDefaultRenderer
 */

public interface ListItemRenderer<T>
{
	/**
	 * Returns the shortcut that will be displayed, 
	 * please make sure there will be no collision.
	 * 
	 * @param index The index of the item. Be careful, 
	 * the indexes begin from 1.
	 * @param item The item binded with this shortcut.
	 * @return The shortcut that will be displayed.
	 */
	
	public String shortcut(int index, T item);
	
	/**
	 * Returns the title that will be displayed.
	 * 
	 * @param index The index of the item. Be careful, 
	 * the indexes begin from 1.
	 * @param item The item binded with this shortcut.
	 * @return The title that will be displayed.
	 */
	public String title(int index, T item);
	
	/**
	 * What will be printed if the list is empty.
	 * @return What will be printed if the list is empty.
	 */
	
	public String empty();
}
