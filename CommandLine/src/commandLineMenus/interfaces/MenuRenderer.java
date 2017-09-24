package commandLineMenus.interfaces;

/**
 * Override this class if you want to customize the display of the user dialog. A default implementation is provided with the package. 
 * @author Alexandre.
 * @see commandLineMenus.rendering.examples.MenuDefaultRenderer
 */

public interface MenuRenderer
{
	/**
	 * Outputs message to the user.
	 * @param message the message to print.
	 */
	public void outputString(String message);

	/**
	 * Inputs a string from the user.
	 * @return the string input from the user.
	 */
	public String inputString();

	/**
	 * Returns the header of the menu.
	 * @param title The title that should appear in the header. 
	 * @return the string to print
	 */
	public String header(String title);
	
	
	/**
	 * Prints an option
	 * @param shortcut the shorcut to select this option.
	 * @param label the label of the option
	 * @return the string to print
	 */
	
	public String option(String shortcut, String label);

	/**
	 * The string to print between two options.
	 * @return the string to print
	 */

	public String optionsSeparator();
	
	/**
	 * The string to print at the end of each menu.
	 * @return the string to print
	 */

	
	public String footer();

	/**
	 * The string to print between two menus.  	
	 * @return the string to print
	 */

	public String menusSeparator();

	/**
	 * The string to print to prompt the user for input.  	
	 * @return the string to print
	 */

	public String prompt();
	
	/**
	 * The string to print if the user made a wrong input.  	
	 * @return the string to print
	 */

	public String invalidInput(String input);
}
