package commandLineMenus;

import java.nio.file.WatchService;
import java.util.Set;

import commandLineMenus.depthFirstSearch.DepthFirstSearch;
import commandLineMenus.rendering.*;
import commandLineMenus.rendering.examples.ListItemDefaultRenderer;
import commandLineMenus.rendering.examples.MenuDefaultRenderer;

/**
 * Liste de valeurs (de type T) dans laquelle l'utilisateur
 * doit faire une sélection. Les valeurs de trouvant dans le champs
 * {@link liste} sont affichées et l'utilisateur est invité à saisir
 * l'indice de l'élément qu'il souhaite. Par défaut, la méthode toString
 * héritée de Object est utilisée pour afficher les éléments de menu, 
 * mais vous pouvez le modifier en utilisant la méthode 
 * {@link setToString}.
 */

public class List<T> extends Menu
{
	private ListAction<T> action;
	private ListItemRenderer<T> renderer = null;
	private ListModel<T> model = null;
	private Option optionQuit = null, optionBack = null;
	
	/**
	 * Créée une liste.
	 * @param titre intitulé affiché au dessus-de la liste.
	 * @param action l'objet permettant de gérer la liste.
	 */
	
	public List(String titre, ListModel<T> model, ListAction<T> action)
	{
		super(titre);
		this.model = model;
		this.action = action;
		setAutoBack(true);
		setListItemRenderer(new ListItemDefaultRenderer<>());
	}
	
	/**
	 * Créée une liste.
	 * @param titre intitulé affiché au dessus-de la liste.
	 * @param action l'objet permettant de gérer la liste.
	 * @param raccourci raccourci utilisé dans le cas où cette liste est utilisé comme option dans un menu.
	 */
	
	public List(String titre, String raccourci, ListModel<T> model, ListAction<T> action)
	{
		this(titre, model, action);
		this.shortcut = raccourci;
	}
	
	private Action getAction(final int indice, final T element)
	{
		return new Action()
		{
			@Override
			public void optionSelectionnee()
			{
				selectedItem(indice, element);
			}
		};
	}

	/**
	 * Détermine la fonction à appeler quand un élément est sélectionné.
	 * @param action L'objet dont la fonction elementSelectionne() va être appelé.
	 */
	
	public void setAction(ListAction<T> action)
	{
		this.action = action;
	}
	
	public void setModel(ListModel<T> model)
	{
		if (isLocked())
			throw new ConcurrentModificationException("Impossible to change model of list "
					+ getTitle() + " while running.");
		this.model = model;
	}	
	
	
	private void selectedItem(int indice, T element)
	{
		Option option = action.getOption(element);
		if (option != null)
			super.optionSelected();
		else
			action.selectedItem(indice, element);
	}
	
	@Override
	public Set<Option> getOptions()
	{
		actualise();
		return super.getOptions();
	}

	private void add(int index, T element)
	{
		Option option = action.getOption(element);
		if (option == null)
			super.add(new Option(renderer.title(index, element), 
					renderer.shortcut(index, element),
					getAction(index, element))) ;
		else
		{
			option.setShortcut(renderer.shortcut(index, element));
			super.add(option);
		}
	}
	
	/**
	 * Déclenche une erreur, il est interdit de modifier les options d'une Liste.
	 */
	
	@Override
	public void add(Option option)
	{
		throw new ManualOptionAddForbiddenException(this, option);
	}
	
	@Override
	public void addQuit(String raccourci)
	{
		if (isLocked())
			throw new ConcurrentModificationException("Impossible to add \"quit\" option in list \"" 
					+ getTitle() + "\" while running.");
		this.optionQuit = new Option("Exit", raccourci, Action.QUIT);
	}

	@Override
	public void addBack(String raccourci)
	{
		if (isLocked())
			throw new ConcurrentModificationException("Impossible to add backoption in list " 
					+ getTitle() + " while running.");
		optionBack = new Option("Back", raccourci, Action.BACK);
	}
	
	private int actualise()
	{
		java.util.List<T> liste = model.getList();
		if (liste == null)
			throw new NoListModelDefinedException(this);
		clearOptions();
		boolean wasLocked = isLocked();
		if (wasLocked) unlock();
		for (int i = 0 ; i < liste.size() ; i++)
			add(i, liste.get(i));
		if (optionQuit != null)
			super.add(optionQuit);
		if (optionBack!= null)
			super.add(optionBack);
		if (wasLocked) lock();
		return liste.size();
	}
	
	@Override
	protected void run()
	{
		if (action == null)
			throw new NoListActionDefinedException(this);
		int nbOptions = actualise();
		if (nbOptions == 0)
			System.out.println(renderer.empty());
		else
		{
			DepthFirstSearch.findCycle(this);
			unlock();
			setRenderers(new MenuDefaultRenderer());
			lock();
			super.run();
		}
	}
	
	/**
	 * Définit de quelle façon vont s'afficher les éléments de menu.
	 */
	
	public void setListItemRenderer(ListItemRenderer<T> convertisseur)
	{
		if (isLocked())
			throw new ConcurrentModificationException("Impossible to change renderer of list " 
					+ getTitle() + " while running.");
		this.renderer = convertisseur;
	}

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
			return "Il est interdit d'ajouter manuellement une option (" + option.getTitle() +
					") dans une liste (" + list.getTitle() + ").";
		}
	}

	public static class NoListModelDefinedException extends RuntimeException
	{
		private static final long serialVersionUID = 3072039179151217765L;
		private List<?> list;
		
		public NoListModelDefinedException(List<?> list)
		{
			this.list = list;
		}
		
		@Override
		public String toString()
		{
			return "No list model defined for list " + list.getTitle() + ").";
		}
	}

	public static class NoListActionDefinedException extends RuntimeException
	{
		private static final long serialVersionUID = -4035301642069764296L;
		private List<?> list;
		
		public NoListActionDefinedException(List<?> list)
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
	
