package commandLineMenus;

/**
 * Binds an action to an option. 
 */

public interface Action
{
	/**
	 * Predefined action that leaves all menus.
	 */
	
	public static final Action QUIT = Menu::quit;
			
	/**
	 * Predefined action that goes back to the parent menu.
	 */
	
	// TODO add a real goback
	public static final Action BACK = () -> {};
	
	/**
	 * Automatically triggered when an option is selected.
	 */
	
	public void optionSelected();
}
