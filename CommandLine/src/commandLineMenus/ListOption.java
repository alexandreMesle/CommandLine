package commandLineMenus;

/**
 * Nest options into a List. For each item, 
 * getOption(T item) will return he option associated
 * with the item.
 * @param <T> the type of each item. Be careful, 
 * the printed type is always string, by default it is 
 * displayed with the toString() function.
 * 
 */

public interface ListOption<T>
{
	/**
	 * Returns the option binded to an item of the list.
	 * @param item the element that will be shown in the list.
	 * @return The sub-menu that will be opened if item is selected.
	 */
	
	public Option getOption(T item);
}
