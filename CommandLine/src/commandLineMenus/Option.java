package commandLineMenus;

/**
 * Option displayed in a menu. The user will be prompted to type the shortcut to 
 * select it.
 */

public class Option
{
	private static boolean locked = false;
	protected String shortcut;
	private String title;
	protected Action action;
	protected MenuRenderer menuRenderer = null;
	
	/**
	 * Creates an option.
	 * @param title The title of the option.
	 * @param shortcut The shortcut that selects it.
	 */
	
	public Option(String title, String shortcut)
	{
		this.title = title;
		this.shortcut = shortcut;
	}
	
	/**
	 * Creates an option.
	 * @param title The title of the option.
	 * @param shortcut The shortcut that selects it.
	 * @param action The action that will be triggered if the option is selected.
	 */
	
	public Option(String title, String shortcut, Action action)
	{
		this(title, shortcut);
		this.action = action;
	}
	
	/**
	 * Returns the shortcut to select the option.
	 * @return The shortcut to select the option
	 */
	
	public String getShortcut()
	{
		return shortcut;
	}

	protected void setShortcut(String shortcut)
	{
		this.shortcut = shortcut;
	}

	/**
	 * Returns the title of the option.
	 * @return The title of the option
	 */
	
	public String getTitle()
	{
		return title;
	}

	/**
	 * Binds an action to the option. 
	 * @param action The object who possesses the optionSelected() method that will
	 * be triggered if the option is selected.
	 */
	
	public void setAction(Action action)
	{
		this.action = action;
	}
	
	protected Action getAction()
	{
		return action;
	}

	protected boolean lock()
	{
		return setLocked(true);
	}
	
	protected boolean unlock()
	{
		return setLocked(false);
	}

	/*
	 * Return the former value of isLocked
	 */
	
	protected boolean setLocked(boolean isLocked)
	{
		boolean locked = isLocked();
		Option.locked = isLocked;
		return locked;
	}
	
	protected boolean isLocked()
	{
		return locked;
	}
	
	protected void optionSelected()
	{
		if (action != null)
			action.optionSelected();
		else
			throw this.new NoActionDefinedException();
	}
	
	protected String stringOfOption()
	{
		return menuRenderer.option(getShortcut(), getTitle());
	}

	@Override
	public String toString()
	{
		return getTitle();
	}

	/**
	 * Overrides the default renderer with a custom one, the menuRenderer will be applied 
	 * to this and all the sub-menus.
	 * @param menuRenderer The Renderer that will be applied.
	 */
	
	public void setRenderer(MenuRenderer menuRenderer)
	{
		if (isLocked())
			throw new ConcurrentModificationException("Impossible to set rendering of "
					+ getTitle() + " while running.");
		this.menuRenderer = menuRenderer;
	}

	/** 
	 * Returns the renderer applied to this option.
	 * @return The renderer applied to this option.
	 */
	
	public MenuRenderer getRenderer()
	{
		return menuRenderer;
	}
	
	/**
	 * Thrown if you try to modify the menus or options once a menu has been launched.
	 */
	
	public class ConcurrentModificationException extends RuntimeException
	{
		private static final long serialVersionUID = 6139684008297267150L;

		public ConcurrentModificationException(String message) 
		{
			super(message);
		}
	}

	/**
	 * Thrown if no action is defined for the current option.
	 */
	
	public class NoActionDefinedException extends RuntimeException
	{
		private static final long serialVersionUID = 6139684008297267150L;

		public NoActionDefinedException() 
		{
			super("Option " + getTitle() + " has no action defined.");
		}
	}
}
