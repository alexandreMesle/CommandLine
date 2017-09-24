package commandLineMenus.interfaces;

/**
 * Permet d'affecter des actions au choix d'un élément dans un menu.
 */

public interface Action
{
	/**
	 * Action prédéfinie permettant de quitter le programme.
	 */
	
	public static final Action QUIT = new Action()
	{
		@Override 
		public void optionSelected()
		{
			System.exit(0);
		}
	};
	
	/**
	 * Action prédéfinie permettant de revenir au menu précédent.
	 */
	
	public static final Action BACK = new Action()
	{
		@Override 
		public void optionSelected(){}
	};

	/**
	 * Fonction automatiquement exécutée quand une option est sélectionnée.
	 */
	
	public void optionSelected();
}
