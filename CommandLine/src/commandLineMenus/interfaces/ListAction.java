package commandLineMenus.interfaces;

/**
 * Permet de gérer les listes. C'est-à-dire : affecter une action au choix d'un élément 
 * dans une liste, et actualiser les éléments de la liste devant être affichée.
 */

public interface ListAction<T>
{
	/**
	 * Fonction exécutée lorsqu'un élément est choisi dans une liste.
	 * @param index indice de l'élément sélectionné.
	 * @param item élément sélectionné.
	 */
	
	public void itemSelected(int index, T item);
}
