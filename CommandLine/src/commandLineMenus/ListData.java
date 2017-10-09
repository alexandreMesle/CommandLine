package commandLineMenus;

import java.util.List;

/**
 * Refreshs a List before printing it.
 * @param <T> The type of element of the List.
 */

public interface ListData<T>
{
	/**
	 * Returns the elements that will be printed.
	 * @return The elements that will be printed.
	 */
	
	public List<T> getList();
}
