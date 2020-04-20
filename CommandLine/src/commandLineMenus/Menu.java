package commandLineMenus;


import java.util.ArrayList;

import java.util.Collections;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/** Menu printed on the terminal (if you don't override the layout)
 * The {@link title} is displayed on the top of the menu, followed by 
 * an options list.
 * 
 * The user can select one option that is automatically triggered, or add 
 * a sub-menu as an option.
 */


public class Menu extends Option
{
	private Map<String, Option> optionsMap = new TreeMap<>();
	private List<Option> optionsList = new ArrayList<>();
	private boolean autoBack = false;
	private String shortTitle;
	private static boolean betweenMenus;
	private static boolean exit;
	
	/**
	 * Creates a menu.
	 * @param title The title displayed on the top of the menu.
	 */
	
	public Menu(String title)
	{
		this(title, null);
		shortTitle = title;
	}
	
	/**
	 * Creates a menu.
	 * @param title The title displayed on the top of the menu.
	 * @param shortcut If this is a sub-menu, the shortcut that will appear in the parent menu.
	 */
	
	public Menu(String title, String shortcut)
	{
		super(title, shortcut);
	}

	/**
	 * Creates a menu.
	 * @param longTitle The title displayed on the top of the menu.
	 * @param shortcut If this is a sub-menu, the shortcut that will appear in the parent menu.
	 * @param shortTitle If this is a sub-menu, the title that will appear in the parent menu.
	 */
	
	public Menu(String longTitle, String shortTitle, String shortcut)
	{
		super(longTitle, shortcut);
		this.shortTitle = shortTitle; 
	}

	private void checkConcurrentModification(String message)
	{
		if (isLocked())
			throw new ConcurrentModificationException(message);		
	}
	
	private void privateAdd(Option option)
	{
		checkConcurrentModification("Impossible to add option \""
					+ option.getTitle() + "\" while running.");
		String raccourci = option.getShortcut();
		if (raccourci == null)
			throw new ShortcutMissingException(option);
		Option autre = optionsMap.get(raccourci);
		if (autre != null)
			throw new CollisionException(autre, option);
		optionsMap.put(option.getShortcut(), option);
		optionsList.add(option);
	}
	
	/**
	 * Adds an option in the current menu.
	 * @param option The option to add.
	 */
	
	public void add(Option option)
	{
		privateAdd(option);
	}

	/**
	 * Returns the options of this menu.
	 * @return The options of this menu.
	 */

	public Collection<Option> getOptions()
	{
		return Collections.unmodifiableCollection(optionsMap.values());
	}
	
	/**
	 * The number of options in the current menu.
	 * @return The number of options.
	 */

	public int size()
	{
		return optionsList.size();
	}
	
	protected void clearOptions()
	{
		optionsList.clear();
		optionsMap.clear();
	}

	/**
	 * Adds an option to close all menus.
	 * @param shortcut The shortcut that will appear in the menu.
	 */
	
	public void addQuit(String shortcut)
	{
		add(new Option("Exit", shortcut, Action.QUIT));
	}
	
	/**
	 * Adds an option to close all menus.
	 * @param title The title that will appear in the menu.
	 * @param shortcut The shortcut that will appear in the menu.
	 */
	
	public void addQuit(String title, String shortcut)
	{
		add(new Option(title, shortcut, Action.QUIT));
	}
	
	/**
	 * Adds an option to go back to the parent menu.
	 * @param shortcut The shortcut that will appear in the menu.
	 */
	
	public void addBack(String shortcut)
	{
		addBack("Back", shortcut);
	}
	
	/**
	 * Adds an option to go back to the parent menu.
	 * @param title The title that will appear in the menu.
	 * @param shortcut The shortcut that will appear in the menu.
	 */
	
	public void addBack(String title, String shortcut)
	{
		privateAdd(new Option(title, shortcut, Action.BACK));
	}
	
	/**
	 * Setter for autoBack, that is true iff we go back to the parent menu once the action 
	 * is complete.
	 * @param autoBack iff go back to the parent menu once the action is complete.
	 */
	
	public void setAutoBack(boolean autoBack)
	{
		checkConcurrentModification("Impossible to change autoBack while running.");
		this.autoBack = autoBack;
	}
	
	protected String inputOption()
	{
		menuRenderer.outputString(this.toString());
		return menuRenderer.inputString();
	}
	
	private static void reset()
	{
		betweenMenus = false;
		exit = false;
	}

	/**
	 * Launches the menu. Be careful, the menu is run in he main thread and it is not possible
	 * to start a menu while an other is running.
	 */
	
	public void start()
	{
		new DepthFirstSearch(this);
		if (isLocked())
			throw new ConcurrentExecutionException();
		try
		{
			lock();
			reset();
			run();
		}
		finally
		{
			unlock();			
		}
	}

	protected int actualize()
	{
		return getOptions().size();
	}
	
	protected void printBetweenMenus()
	{
		if (betweenMenus)
			menuRenderer.outputString((menuRenderer.menusSeparator()));
		else
			betweenMenus = true;
		
	}
	
	protected Option getOption(String shortcut)
	{
		return optionsMap.get(shortcut);
	}
	
	protected Option runOnce()
	{
		Option option = null;
		if (actualize() == 0)
			throw new EmptyMenuException();
		printBetweenMenus();
		String get = inputOption();
		option = getOption(get);
		if (option != null)
			option.optionSelected();
		else
			menuRenderer.outputString(menuRenderer.invalidInput(get));
		return option;
	}
	
	protected void run()
	{
		Option option = null;
		do
		{			
			option = runOnce();
		}
		while(keepOnRunning(option));
	}

	private boolean keepOnRunning(Option option)
	{
		return !exit && (option == null 
				|| (!autoBack && option.getAction() != Action.BACK));
	}
	
	@Override
	protected void optionSelected()
	{
		this.run();
	}
	
	@Override
	protected String stringOfOption()
	{
		if (shortTitle != null)
			return menuRenderer.option(shortcut, shortTitle);
		else
			return super.stringOfOption();
	}
	
	private String emptyIfNull(String s)
	{
		if (s != null)
			return s;
		return "";
	}
	
	@Override
	public String toString()
	{
		StringBuilder res = new StringBuilder(menuRenderer.header(getTitle()));
		boolean between = false;
		for (Option option : optionsList)
		{
			if (between)
				res.append(menuRenderer.optionsSeparator());
			else
				between = true;
			res.append(option.stringOfOption());
		}
		res.append(emptyIfNull(menuRenderer.footer()))
		    .append(emptyIfNull(menuRenderer.prompt()));
		return res.toString();
	}

	/**
	 * Thrown if two options have the same shortcut.
	 */
	
	public class CollisionException extends RuntimeException
	{
		private static final long serialVersionUID = 1142845287292812411L;
		private final transient Option oldOption;
		private final transient Option newOption;
		
		public Option getOldOption()
		{
			return oldOption;
		}
		
		public Option getNewOption()
		{
			return newOption;
		}
		
		CollisionException(Option oldOption, Option newOption)
		{
			super("Collision between " + oldOption.getTitle()
				+ " and " + newOption.getTitle() + " for the shorcut "
				+ newOption.getShortcut() + " in the menu " + 
			getTitle() + ".");
			this.oldOption = oldOption;
			this.newOption = newOption;
		}
	}

	/**
	 * Thrown if an option has no shortcut.
	 */
	
	public class ShortcutMissingException extends RuntimeException
	{
		private static final long serialVersionUID = -194430644006701341L;
		private final transient Option option;
		
		public Option getOption()
		{
			return option;
		}
		
		ShortcutMissingException(Option option)
		{
			super("Impossible to add option \"" + option.getTitle() 
				+ "\" in the menu \"" + getTitle() + "\" if the shorcut hasn't been specified.");
			this.option = option;
		}
	}
	
	/**
	 * Thrown if a menu has no option.
	 */
	
	public class EmptyMenuException extends RuntimeException
	{
		private static final long serialVersionUID = 3617589300605854823L;

		public Menu getMenu()
		{
			return Menu.this;
		}
		
		public EmptyMenuException() 
		{
			super("Impossible to launch the empty menu \"" + getTitle() + "\".");
		}
	}

	/**
	 * Thrown if a menu is launched while an other is running.
	 */

	public class ConcurrentExecutionException extends RuntimeException
	{
		private static final long serialVersionUID = 770804726891062420L;

		ConcurrentExecutionException() 
		{
			super("Impossible to launch \"" + getTitle() 
				+ "\", a menu application is already running.");
		}
	}
	
	/**
	 * Thrown if a menu is an ancestor if himself.
	 */
	
	public static class CycleDetectedException extends RuntimeException
	{
		private static final long serialVersionUID = -2884917321791851520L;

		private final transient List<Menu> cycleDetected;
		
		public CycleDetectedException(List<Menu> cycleDetected)
		{
			super("A directed cycle has been detected in the menu tree :\n" 
					+ stringOfCycle(cycleDetected));
			this.cycleDetected = cycleDetected;
		}
		
		/**
		 * Returns the Menus of the cycle. 
		 * @return
		 */
		
		public List<Menu> getCycleDetected()
		{
			return Collections.unmodifiableList(cycleDetected);
		}

		private static String stringOfCycle(List<Menu> list)
		{
			StringBuilder res = new StringBuilder("[");
			boolean first = true;
			for (Option menu : list)
			{
				if (!first)
					res.append(" -> ");
				else
					first = false;
				res.append(menu.getTitle());
			}
			return res.append("]").toString();
		}


		@Override
		public String toString()
		{
			return stringOfCycle(cycleDetected);
		}
	}
	
	/**
	 * Once quit() is called, all the menus will close as soon as possible. 
	 */
	
	public static void quit()
	{
		exit = true; 
	}

	/**
	 * Once soBack() is called, the current menu will close as soon as possible.
	 */
	
	public static void goBack()
	{
		// TODO goBack method
	}
}