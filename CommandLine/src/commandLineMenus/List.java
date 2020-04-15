package commandLineMenus;

import commandLineMenus.rendering.examples.ListItemDefaultRenderer;

/**
 * Menu dynamically populated with a list of items. The type T is not 
 * the type displayed but the type kept in memory for further treatment.
 * The data is read through a ListData.
 * The selection of an item triggers either a ListAction, either a ListOption.
 */

public class List<T> extends Menu
{
	private ListAction<T> listAction = null;
	private ListOption<T> listOption = null;
	private ListData<T> model = null;
	private Option optionQuit = null;
	private Option optionBack = null;
	private ListItemRenderer<T> itemRenderer;
	
	private List(String title, ListData<T> data)
	{
		super(title);
		this.model = data;
		setAutoBack(true);
		setListItemRenderer(new ListItemDefaultRenderer<>());
	}
	
	/**
	 * Creates a List.
	 * @param title The title of the list.
	 * @param data The implementation of ListData that refreshes the list
	 * @param action The implementation of ListAction that will be triggered if 
	 * an item is selected.
	 */
	
	public List(String title, ListData<T> data, ListAction<T> action)
	{
		this(title, data);
		this.listAction = action;
	}
	
	/**
	 * Creates a List.
	 * @param title The title of the list.
	 * @param data The implementation of ListData that refreshes the list
	 * @param option The sub-menu that will be opened if 
	 * an item is selected.
	 */
	
	public List(String title, ListData<T> data, ListOption<T> option)
	{
		this(title, data);
		this.listOption = option;
	}
	
	/**
	 * Creates a List.
	 * @param title The title of the list.
	 * @param shortcut The shortcut that opens the list if this is a sub-menu.
	 * @param data The implementation of ListData that refreshes the list
	 * @param action The implementation of ListAction that will be triggered if 
	 * an item is selected.
	 */
	
	public List(String title, String shortcut, ListData<T> data, ListAction<T> action)
	{
		this(title, data, action);
		this.shortcut = shortcut;
	}
	
	/**
	 * Creates a List.
	 * @param title The title of the list.
	 * @param shortcut The shortcut that opens the list if this is a sub-menu.
	 * @param data The implementation of ListData that refreshes the list
	 * @param option The sub-menu that will be opened if 
	 * an item is selected.
	 */
	
	public List(String title, String shortcut, ListData<T> data, ListOption<T> option)
	{
		this(title, data, option);
		this.shortcut = shortcut;
	}
	
	private Action getAction(final int indice, final T element)
	{
		return new Action()
		{
			@Override
			public void optionSelected()
			{
				selectedItem(indice, element);
			}
		};
	}

	private void selectedItem(int indice, T element)
	{
		if (listOption != null)
			super.optionSelected();
		if (listAction != null)
			listAction.itemSelected(indice, element);
	}
	
	private void add(int index, T element)
	{
		if (listAction != null)
			super.add(new Option(itemRenderer.title(index, element), 
					itemRenderer.shortcut(index, element),
					getAction(index, element))) ;
		if (listOption != null)
		{
			Option option = listOption.getOption(element);
			option.setShortcut(itemRenderer.shortcut(index, element));
			super.add(option);
		}
	}
	
	/**
	 * Do never call add() if this is a list, it is forbidden to manually add an option in a List.
	 */
	
	@Override
	public void add(Option option)
	{
		throw new ManualOptionAddForbiddenException(this, option);
	}
	
	@Override
	public void addQuit(String shortcut)
	{
		addQuit("Exit", shortcut);
	}

	@Override
	public void addQuit(String title, String shortcut)
	{
		if (isLocked())
			throw new ConcurrentModificationException("Impossible to add \"quit\" option in list \"" 
					+ getTitle() + "\" while running.");
		this.optionQuit = new Option(title, shortcut, Action.QUIT);
	}

	/**
	 * Adds an option to go back to the parent menu, if the list is empty, 
	 * a go back option is automatically added with the 'q' shortcut.
	 * @param shortcut The shortcut that will appear in the menu.
	 */
	
	
	@Override
	public void addBack(String shortcut)
	{
		addBack("Back", shortcut);
	}
	
	@Override
	public void addBack(String title, String shortcut)
	{
		if (isLocked())
			throw new ConcurrentModificationException("Impossible to add backoption in list " 
					+ getTitle() + " while running.");
		optionBack = new Option(title, shortcut, Action.BACK);
	}

	ListAction<T> getListAction()
	{
		return listAction;
	}
	
	ListOption<T> getListOption()
	{
		return listOption;
	}
	
	@Override
	protected int actualize()
	{
		java.util.List<T> liste = model.getList();
		if (liste == null)
			throw new NoListDataDefinedException(this);
		clearOptions();
		boolean wasLocked = unlock();
		for (int i = 0 ; i < liste.size() ; i++)
			add(i, liste.get(i));
		if (optionQuit != null)
			super.add(optionQuit);
		if (optionBack!= null)
			super.add(optionBack);
		if (liste.size() == 0 && optionBack == null)
			super.addBack("q");
		setLocked(wasLocked);
		return liste.size();
	}
	
	@Override
	protected Option runOnce()
	{
		int nbOptions = actualize();
		if (nbOptions == 0)
			menuRenderer.outputString(itemRenderer.empty());
		unlock();
		new DepthFirstSearch(this);
		lock();
		Option option = null;
		printBetweenMenus();
		String get = inputOption();
		option = getOption(get);
		if (option != null)
			option.optionSelected();
		else
			menuRenderer.outputString(menuRenderer.invalidInput(get));
		return option;
	}	
	
	/**
	 * Overrides the default display of an item with a custom one. listItemRenderer only applies to 
	 * the current list.
	 * @param listItemRenderer The implementation of ListItemRenderer that customizes the display.
	 */
	
	public void setListItemRenderer(ListItemRenderer<T> listItemRenderer)
	{
		if (isLocked())
			throw new ConcurrentModificationException("Impossible to change renderer of list " 
					+ getTitle() + " while running.");
		this.itemRenderer = listItemRenderer;
	}

	/**
	 * Thrown if one tries to manually edit the options displayed.
	 */
	
	public static class ManualOptionAddForbiddenException extends RuntimeException
	{
		private static final long serialVersionUID = -5126287607702961669L;
		private Option option;
		private List<?> list;

		ManualOptionAddForbiddenException(List<?> list, Option option)
		{
			this.list = list;
			this.option = option;
		}
		
		@Override
		public String toString()
		{
			return "It is forbidden to manually add an option (ie. : " + option.getTitle() +
					") in a list (ie. : " + list.getTitle() + ").";
		}
	}

	/**
	 * Thrown if the current list has no ListData.
	 */
	
	public static class NoListDataDefinedException extends RuntimeException
	{
		private static final long serialVersionUID = 3072039179151217765L;
		private List<?> list;
		
		public NoListDataDefinedException(List<?> list)
		{
			this.list = list;
		}
		
		@Override
		public String toString()
		{
			return "No list model defined for list " + list.getTitle() + ").";
		}
	}

	/**
	 * Thrown if there is a problem with the listAction or the listOption.
	 * Only one of the two must be defined. The exception is raised is none is defined, 
	 * and if both are defined.
	 */
	
	public static class ListActionOrOptionException extends RuntimeException
	{
		private static final long serialVersionUID = -4035301642069764296L;
		private List<?> list;
		
		public ListActionOrOptionException(List<?> list)
		{
			this.list = list;
		}
		
		@Override
		public String toString()
		{
			return "No list action defined for list " + list.getTitle() + ").";
		}
	}
}
