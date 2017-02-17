package commandLineMenus;

import java.util.List;

import commandLineMenus.rendering.*;

/**
 * Liste de valeurs (de type T) dans laquelle l'utilisateur
 * doit faire une sélection. Les valeurs de trouvant dans le champs
 * {@link liste} sont affichées et l'utilisateur est invité à saisir
 * l'indice de l'élément qu'il souhaite. Par défaut, la méthode toString
 * héritée de Object est utilisée pour afficher les éléments de menu, 
 * mais vous pouvez le modifier en utilisant la méthode 
 * {@link setToString}.
 */

public class Liste<T> extends Menu
{
	private ListAction<T> action;
	private ListItemRenderer<T> listItemRenderer = null;
	private ListModel<T> listModel = null;
	private Option optionQuitter = null, optionRevenir = null;
	
	/**
	 * Créée une liste.
	 * @param titre intitulé affiché au dessus-de la liste.
	 * @param action l'objet permettant de gérer la liste.
	 */
	
	public Liste(String titre, ListModel<T> listModel, ListAction<T> action)
	{
		super(titre);
		this.listModel = listModel;
		this.action = action;
		setRetourAuto(true);
		setListItemRenderer(new ListItemDefaultRenderer<>());
	}
	
	/**
	 * Créée une liste.
	 * @param titre intitulé affiché au dessus-de la liste.
	 * @param action l'objet permettant de gérer la liste.
	 * @param raccourci raccourci utilisé dans le cas où cette liste est utilisé comme option dans un menu.
	 */
	
	public Liste(String titre, String raccourci, ListModel<T> listModel, ListAction<T> action)
	{
		this(titre, listModel, action);
		this.raccourci = raccourci;
	}
	
	private Action getAction(final int indice, final T element)
	{
		return new Action()
		{
			@Override
			public void optionSelectionnee()
			{
				elementSelectionne(indice, element);
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
	
	private void elementSelectionne(int indice, T element)
	{
		if (action != null)
			action.elementSelectionne(indice, element);
	}
	
	private void actualise()
	{
		List<T> liste = listModel.getList();
		clearOptions();
		for (int i = 0 ; i < liste.size() ; i++)
		{
			T element = liste.get(i);
			Option option = action.getOption(element);
			if (option == null)
			{
				String string = listItemRenderer.toString(element);
				super.ajoute(new Option(string, "" + (i + 1), getAction(i, element))) ;
			}
			else
			{
				option.setRaccourci("" + (i + 1));
				super.ajoute(option);				
			}
		}
		if (optionQuitter != null)
			super.ajoute(optionQuitter);
		if (optionRevenir!= null)
			super.ajoute(optionRevenir);
	}
	
	/**
	 * Lance l'exécution de la liste.
	 */
	
	@Override
	public void start()
	{
		actualise();
		super.start();
	}
	
	/**
	 * Définit de quelle façon vont s'afficher les éléments de menu.
	 */
	
	public void setListItemRenderer(ListItemRenderer<T> convertisseur)
	{
		this.listItemRenderer = convertisseur;
	}
		
	/**
	 * Déclenche une erreur, il est interdit de modifier les options d'une Liste.
	 */
	
	@Override
	public void ajoute(Option option)
	{
		throw new ManualOptionAddForbiddenException(this, option);
	}
	
	@Override
	public void ajouteQuitter(String raccourci)
	{
		optionQuitter = new Option("Quitter", raccourci, Action.QUITTER);
	}

	@Override
	public void ajouteRevenir(String raccourci)
	{
		optionRevenir = new Option("Revenir", raccourci, Action.REVENIR);
	}
	
	public static class ManualOptionAddForbiddenException extends RuntimeException
	{
		private Option option;
		private Liste<?> liste;

		ManualOptionAddForbiddenException(Liste liste, Option option)
		{
			this.liste = liste;
			this.option = option;
		}
		
		@Override
		public String toString()
		{
			return "Il est interdit d'ajouter manuellement une option (" + option.getTitre() +
					") dans une liste (" + liste.getTitre() + ").";
		}
	}
}
	
