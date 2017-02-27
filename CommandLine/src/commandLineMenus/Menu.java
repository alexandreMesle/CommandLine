package commandLineMenus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.Renderer;

import commandLineMenus.cycleDetection.CycleDetector;
import commandLineMenus.rendering.MenuRenderer;
import commandLineMenus.rendering.examples.MenuDefaultRenderer;
import commandLineMenus.util.InOut;

/**
 * Menu affiché en ligne de commande. En haut du menu est affiché le {@link titre}, 
 * suivi par une liste d'options. L'utilisateur est invité à choisir une option
 * qui est ensuite exécutée. Il est possible de placer un sous-menu en option, 
 * ou il est possible d'utiliser la classe Option pour affecter une action à la sélection 
 * d'une action.
 */

public class Menu extends Option
{
	private Map<String, Option> optionsMap = new TreeMap<>();
	private List<Option> optionsList = new ArrayList<>();
	private boolean autoBack = false;
	private String shortTitle;
	private static boolean running = false;
	
	/**
	 * Créée un menu.
	 * @param title titre affiché au dessus du menu.
	 */
	
	public Menu(String title)
	{
		this(title, null);
		shortTitle = title;
	}
	
	/**
	 * Créée un menu.
	 * @param title titre affiché au dessus du menu.
	 * @param shorcut Si ce menu est aussi une option, 
	 * raccourci permettant de l'activer.
	 */
	
	public Menu(String title, String shorcut)
	{
		super(title, shorcut);
	}

	/**
	 * Créée un menu.
	 * @param longTitle titre affiché au dessus du menu.
	 * @param shortTitle titre affiché en tant qu'élément de menu (ou en tant qu'option).
	 * @param shortcut Si ce menu est aussi une option, 
	 * raccourci permettant de l'activer.
	 */
	
	public Menu(String longTitle, String shortTitle, String shortcut)
	{
		super(longTitle, shortcut);
		this.shortTitle = shortTitle; 
	}

	/**
	 * Ajoute une option dans le menu.
	 * @param option option à ajouter.
	 */
	
	public void add(Option option)
	{
		//TODO verrouiller l'ajout une fois l'appli lancée
		String raccourci = option.getShorcut();
		if (raccourci == null)
			throw new ShortcutMissingException(option);
		Option autre = optionsMap.get(raccourci);
		if (autre != null)
			throw new CollisionException(autre, option);
		optionsMap.put(option.getShorcut(), option);
		optionsList.add(option);
	}
	
	public Set<Option> getOptions()
	{
		return new HashSet<>(optionsMap.values());
	}
	
	protected void clearOptions()
	{
		optionsList.clear();
		optionsMap.clear();
	}
	
	protected void setRenderers(MenuRenderer menuRenderer)
	{
		super.setRenderers(menuRenderer);
		for (Option option : getOptions())
			option.setRenderers(this.menuRenderer);
	}
	/**
	 * Ajoute une option permettant de quitter le programme.
	 * @param shorcut le raccourci permettant de quitter le programme.
	 */
	
	public void addQuit(String shorcut)
	{
		add(new Option("Exit", shorcut, Action.QUIT));
	}
	
	/**
	 * Ajoute une option permettant de revenir au menu précédent.
	 * @param shorcut le raccourci permettant de revenir au menu précédent.
	 */
	
	public void addBack(String shorcut)
	{
		add(new Option("Back", shorcut, Action.BACK));
	}
	
	/**
	 * Détermine si le choix d'une option entraîne automatiquement le retour au menu précédent.
	 * Faux par défaut.
	 * @param autoBack vrai ssi si le choix d'une option entraîne le retour au 
	 * menu précédent.
	 */
	
	public void setAutoBack(boolean autoBack)
	{
		this.autoBack = autoBack;
	}
	
	protected String getOption()
	{
		System.out.print(this.toString());
		while(true)
		{
			try
			{
				return InOut.getString();				
			}
			catch (IOException e) 
			{
				System.out.println(menuRenderer.invalidInput(""));
			}
		}
	}
	
	/**
	 * Exécute le menu.	Attention, il n'est autorisé de lancer qu'un menu à la fois. Si vous avez besoin de lancer
	 * un menu dans un sous-menu, lisez la documentation du package pour voir comment procéder. 
	 */
	
	public void start()
	{
		CycleDetector.findCycle(this);
		if (running)
			throw new ConcurrentMenusException();
		// TODO Vérifier qu'aucun menu n'est vide
		setRenderers(new MenuDefaultRenderer());
		running = true;
		run();
		running = false;
	}

	protected void run()
	{
		if (getOptions().size() == 0)
			throw new EmptyMenuException();
		Option option = null;
		boolean between = false;
		do
		{
			if (between)
				System.out.println(menuRenderer.betweenMenus());
			else
				between = true;
			String get = getOption();
			option = optionsMap.get(get);
			if (option != null)
				option.optionSelected();
			else
				System.out.println(menuRenderer.invalidInput(get));
		}
		while(option == null || !autoBack && option.getAction() != Action.BACK);
	}

	@Override
	void optionSelected()
	{
		this.run();
	}
	
	@Override
	public String stringOfOption()
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
		String res = menuRenderer.header(getTitle());
		boolean between = false;
		for (Option option : optionsList)
		{
			if (!between) 
				res += menuRenderer.betweenOptions();
			else
				between = true;
			res += option.stringOfOption();
		}
		res += emptyIfNull(menuRenderer.footer());
		res += emptyIfNull(menuRenderer.prompt());
		return res;
	}

	public class CollisionException extends RuntimeException
	{
		private static final long serialVersionUID = 1142845287292812411L;
		private Option oldOption, newOption;
		
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
			this.oldOption = oldOption;
			this.newOption = newOption;
		}
		
		@Override
		public String toString()
		{
			return "Collision between " + oldOption.getTitle()
			+ " and " + newOption.getTitle() + " for the shorcut " +
			newOption.getShorcut() + " in the menu " + 
			getTitle() + ".";
		}
	}

	public class ShortcutMissingException extends RuntimeException
	{
		private static final long serialVersionUID = -194430644006701341L;
		private Option option;
		
		public Option getOption()
		{
			return option;
		}
		
		ShortcutMissingException(Option option)
		{
			this.option = option;
		}
		
		@Override
		public String toString()
		{
			return "Impossible to add option " + option.getTitle() + 
				" in the menu " + getTitle() + " if the shorcut hasn't been specified.";
		}
	}
	
	public class EmptyMenuException extends RuntimeException
	{
		private static final long serialVersionUID = 3617589300605854823L;

		@Override
		public String toString()
		{
			return "Impossible to launch an empty menu " + getTitle() + ".";
		}
	}

	public class ConcurrentMenusException extends RuntimeException
	{
		private static final long serialVersionUID = 770804726891062420L;

		@Override
		public String toString()
		{
			return "Impossible to launch " + getTitle() + ", menu.start() can only be ran from the root menu.";
		}
	}
	
}