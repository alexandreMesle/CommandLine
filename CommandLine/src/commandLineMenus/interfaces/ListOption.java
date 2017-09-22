package commandLineMenus.interfaces;

import commandLineMenus.Option;

/**
 * Permet de gérer les listes. C'est-à-dire : affecter une action au choix d'un élément 
 * dans une liste, et actualiser les éléments de la liste devant être affichée.
 */

public interface ListOption<T>
{
	/**
	 * Fonction permettant d'ajouter un menu dans la liste. Le raccourci sera écrasé par l'indice
	 * de l'élément dans la liste affichée.
	 * @param item élément de la liste affiché dans ce menu.
	 * @returns Le sous-menu qui sera affichée dans la liste.
	 */
	
	public Option getOption(T item);
}
