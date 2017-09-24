package commandLineMenus;

import commandLineMenus.interfaces.Action;
import commandLineMenus.interfaces.MenuRenderer;
import commandLineMenus.rendering.examples.MenuDefaultRenderer;

/**
 * Option figurant dans un menu.
 */

public class Option
{
	private static boolean locked = false;
	protected String shortcut;
	private String title;
	protected Action action;
	protected MenuRenderer menuRenderer = new MenuDefaultRenderer();
	
	public Option(String title, String shortcut, Action action)
	{
		this(title, shortcut);
		this.action = action;
	}
	
	/**
	 * Créée une option.
	 * @param title titre de l'option.
	 * @param shortcut raccourci à saisir pour activer l'option.
	 */
	
	public Option(String title, String shortcut)
	{
		this.title = title;
		this.shortcut = shortcut;
	}
	
	/**
	 * Retourne le raccourci permettant de sélectioner cette option.
	 */
	
	public String getShorcut()
	{
		return shortcut;
	}

	/**
	 * Modifie le raccourci permettant de sélectioner cette option.
	 */
	
	protected void setShortcut(String shortcut)
	{
		this.shortcut = shortcut;
	}

	/**
	 * Retourne le libellé de l'option.
	 */
	
	public String getTitle()
	{
		return title;
	}

	/**
	 * Affecte une action à la sélection de l'option.
	 * @param action l'objet dont la méthode optionSelectionnee() sera 
	 * appelé une fois une option choisie.
	 */
	
	public void setAction(Action action)
	{
		this.action = action;
	}
	
	/**
	 * Retourne l'action associée à la sélection de l'option.
	 * @return l'objet dont la méthode optionSelectionnee() sera 
	 * appelé une fois une option choisie.
	 */
	
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
			action.optionSelectionnee();
		else
			throw this.new NoActionDefinedException();
	}
	
	protected String stringOfOption()
	{
		return menuRenderer.option(getShorcut(), getTitle());
	}

	@Override
	public String toString()
	{
		return getTitle();
	}
	
	public void setRenderer(MenuRenderer menuRenderer)
	{
		if (isLocked())
			throw new ConcurrentModificationException("Impossible to set rendering of "
					+ getTitle() + " while running.");
		this.menuRenderer = menuRenderer;
	}

	protected void setRenderers(MenuRenderer menuRenderer)
	{
		if (this.menuRenderer == null && menuRenderer != null)
			setRenderer(menuRenderer);
	}

	public class ConcurrentModificationException extends RuntimeException
	{
		private static final long serialVersionUID = 6139684008297267150L;

		public ConcurrentModificationException(String message) 
		{
			super(message);
		}
	}

	public class NoActionDefinedException extends RuntimeException
	{
		private static final long serialVersionUID = 6139684008297267150L;

		public NoActionDefinedException() 
		{
			super("Option " + getTitle() + " has no action defined.");
		}
	}

}
