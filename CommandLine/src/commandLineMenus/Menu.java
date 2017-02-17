package commandLineMenus;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import commandLineMenus.cycleDetection.CycleDetector;
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
	private boolean retourAuto = false;
	private String titreCourt;
	private static boolean running = false;
	
	/**
	 * Créée un menu.
	 * @param titre titre affiché au dessus du menu.
	 */
	
	public Menu(String titre)
	{
		super(titre, null);
		titreCourt = titre;
	}
	
	/**
	 * Créée un menu.
	 * @param titre titre affiché au dessus du menu.
	 * @param raccourci Si ce menu est aussi une option, 
	 * raccourci permettant de l'activer.
	 */
	
	public Menu(String titre, String raccourci)
	{
		super(titre, raccourci);
	}

	/**
	 * Créée un menu.
	 * @param titreLong titre affiché au dessus du menu.
	 * @param titreCourt titre affiché en tant qu'élément de menu (ou en tant qu'option).
	 * @param raccourci Si ce menu est aussi une option, 
	 * raccourci permettant de l'activer.
	 */
	
	public Menu(String titreLong, String titreCourt, String raccourci)
	{
		super(titreLong, raccourci);
		this.titreCourt = titreCourt; 
	}

	/**
	 * Ajoute une option dans le menu.
	 * @param option option à ajouter.
	 */
	
	public void ajoute(Option option)
	{
		String raccourci = option.getRaccourci();
		if (raccourci == null)
			throw new ShortcutMissingException(option);
		Option autre = optionsMap.get(raccourci);
		if (autre != null)
			throw new CollisionException(autre, option);
		optionsMap.put(option.getRaccourci(), option);
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
	
	/**
	 * Ajoute une option permettant de quitter le programme.
	 * @param raccourci le raccourci permettant de quitter le programme.
	 */
	
	public void ajouteQuitter(String raccourci)
	{
		ajoute(new Option("Quitter", raccourci, Action.QUITTER));
	}
	
	/**
	 * Ajoute une option permettant de revenir au menu précédent.
	 * @param raccourci le raccourci permettant de revenir au menu précédent.
	 */
	
	public void ajouteRevenir(String raccourci)
	{
		ajoute(new Option("Revenir", raccourci, Action.REVENIR));
	}
	
	/**
	 * Détermine si le choix d'une option entraîne automatiquement le retour au menu précédent.
	 * Faux par défaut.
	 * @param retourAuto vrai ssi si le choix d'une option entraîne le retour au 
	 * menu précédent.
	 */
	
	public void setRetourAuto(boolean retourAuto)
	{
		this.retourAuto = retourAuto;
	}
	
	protected String saisitOption()
	{
		return InOut.getString(this.toString());
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
		running = true;
		Option option = null;
		do
		{
			String saisie = saisitOption();
			option = optionsMap.get(saisie);
			if (option != null)
				option.optionSelectionnee();
			else
				System.out.println("Cette option n'est pas disponible.");
		}
		while(option == null || !retourAuto && option.getAction() != Action.REVENIR);
	}

	@Override
	void optionSelectionnee()
	{
		running = false;
		this.start();
		running = true;
	}
	
	@Override
	public String stringOfOption()
	{
		if (titreCourt != null)
			return raccourci + " : " + titreCourt;
		else
			return super.stringOfOption();
	}
	
	@Override
	public String toString()
	{
		String res = getTitre() + '\n';
		for (Option option : optionsList)
			res += option.stringOfOption() + "\n";
		return res;
	}

	public class CollisionException extends RuntimeException
	{
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
			return "Collision entre " + oldOption.getTitre()
			+ " et " + newOption.getTitre() + " pour le raccourci" +
			newOption.getRaccourci() + " dans le menu " + 
			getTitre() + ".";
		}
	}

	public class ShortcutMissingException extends RuntimeException
	{
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
			return "Impossible d'ajouter l'option " + option.getTitre() + 
				" dans le menu " + getTitre() + " si le raccourci n'a pas été spécifié.";
		}
	}
	
	public class ConcurrentMenusException extends RuntimeException
	{
		@Override
		public String toString()
		{
			return "Impossible de lancer " + getTitre() + ", menu.start() ne peut être lancé que depuis le menu racine.";
		}
	}
	
	public class OptionNonAvailableException extends Exception
	{
		private String shortcut;
		
		public String getShortcut()
		{
			return shortcut;
		}
		
		OptionNonAvailableException(String shortcut)
		{
			this.shortcut = shortcut;
		}
		
		@Override
		public String toString()
		{
			return "Impossible de lancer " + getTitre() + ", menu.start() ne peut être lancé que depuis le menu racine.";
		}
	}
}