package commandLineMenus.interfaces;

import java.util.List;

/**
 * Refreshs a List<T> before printing it.
 * @param <T> The type of element of the List<T>.
 */

public interface ListData<T>
{
	/**
	 * Returns the elements that will be printed.
	 * @returns The elements that will be printed.
	 */
	
	public List<T> getList();
}
