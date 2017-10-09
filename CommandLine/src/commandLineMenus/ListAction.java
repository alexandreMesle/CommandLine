package commandLineMenus;

/**
 * Binds an action to item from a List. Each time an item 
 * will be selected, the selectedItem() method will be triggered.
 * 
 * @param <T> The type of the items in the List
 */

public interface ListAction<T>
{
	/**
	 * Triggered when the item is selected in a List.
	 * @param index Index of the selected item.
	 * @param item The item selected.
	 */
	
	public void itemSelected(int index, T item);
}
