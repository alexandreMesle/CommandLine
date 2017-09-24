# CommandLine

Framework for building command-line user interfaces. Through menus and options, it allows developers to quickly set up a back-end user interface.

# How to use it

The framework organizes the user dialog between menus and options. It allows the user to navigate through menus. 
In each menu, the user chooses an option, the selection of an option triggers an action.

## Menus and options

In the following example, we show how to create a menu, and how to include an option in this menu.
 
```
// Creates a menu with the title Hello
Menu helloMenu = new Menu("Hello Menu");

// Creates an option with the title "Say Hello", the shorcut to select it is "h"
Option sayHelloOption = new Option("Say Hello", "h");

// Adds the option sayHello to the menu.
helloMenu.add(sayHelloOption);

// Adds the option allowing to close the menu.
helloMenu.addQuit("q");

// Creates an action that will be binded to the sayHello option. 
Action sayHelloAction = new Action()
{
	
	// optionSelected() is triggered each time the "sayHello" option is selected.
	@Override
	public void optionSelected()
	{
		System.out.println("Hello!");
	}
};

// Binds sayHelloAction to the option sayHelloOption 
sayHelloOption.setAction(sayHelloAction);

// Launches the menu
helloMenu.start();

// Must be printed once the menu has been left.
System.out.println("Good bye!");
```
  
The previous code displays the following menu.
  
```
Hello Menu

h : Say Hello
q : Exit

Select an option : 
```

Inputing the shortcut "h" selects the option "Say Hello", and then inputing "q" closes the application.
You can notice that selecting the option "Say Hello" automatically triggers the method `optionSelected()`. 

## Nesting menus

Since `Menu` inherits `Option`, it is possible to nest a menu A inside a menu B. Then A will appear as a sub-menu of B.
The user will have the possibility to select A as if it was an option of the menu B, and the action associated with 
this selection will automatically be the opening of A.   

```
// Creates the root menu of the application
Menu rootMenu = new Menu("Root Menu");

// Creates two options
Option calculatorOption = new Option("Calculator", "c");
Menu sayHelloMenu = new Menu("Say Hello Sub-Menu", "Hello", "h");

// Adds an option to the rootMenu 
rootMenu.add(calculatorOption);

// Adds the sub-menu sayHelloMenu to the rootMenu
// Please notice that since Menu extends Option, polymorphism allows us to pass the Menu sayHelloMenu where an Option was expected.
rootMenu.add(sayHelloMenu);

// Adds the quit option
rootMenu.addQuit("q");

// Defines the action that will be triggered if the calculator is selected.
calculatorOption.setAction(new Action()
{
	// Method triggered if the calculatorOption is selected 
	public void optionSelected()
	{
		int a = InOut.getInt("Input the first operand : "),
				b = InOut.getInt("Input the second operand : ");
		System.out.println("" + a + " + " + b + " = " + (a+b));
	}
});

// Please notice that the action can be passed to the constructor of Option 
sayHelloMenu.add(				
		new Option("Say Hello", "h", new Action()
		{
			public void optionSelected()
			{
				System.out.println("Hello!");
			}
		}));

// Adds an option to go back to the rootMenu
sayHelloMenu.addBack("r");

// Once an option has been selected in sayHelloMenu, and the associated action is done, we will automatically go back to the rootMenu. 
sayHelloMenu.setAutoBack(true);

rootMenu.start();
```

Here is an example of how the program behaves : 

```
Root Menu

c : Calculator
h : Hello
q : Exit

Select an option : 
c
Input the first operand : 2
Input the second operand : 3
2 + 3 = 5

---------------------------

Root Menu

c : Calculator
h : Hello
q : Exit

Select an option : 
h

---------------------------

Say Hello Sub-Menu

h : Say Hello
r : Back

Select an option : 
h
Hello!

---------------------------

Root Menu

c : Calculator
h : Hello
q : Exit

Select an option : 
q
```

## Lists

La librarie permet aussi de créer automatiquement un menu en utilisant une 
liste :

```
// Creates a list containing "Ginette", "Marcel" et "Gisèle"
final ArrayList<String> people = new ArrayList<>();
people.add("Ginette");
people.add("Marcel");
people.add("Gisèle");

// Creates a menu with an option for each people in the list
List<String> menu = new List<String>("People list", 
	new ListData<String>()		
{
		// Returns the data needed to refresh the list each time it is displayed. 
		public java.util.List<String> getList()
		{
			return people;
		}	
	},
	new ListAction<String>()
	{				
		// Triggered each time an item is selected
		public void itemSelected(int index, String someone)
		{
			System.out.println("You have selected " + someone + ", who has the index " + index);
		}
	});
menu.addQuit("q");
menu.start();
```

Voici un exemple d'exécution : 

```
1 : Ginette
2 : Marcel
3 : Gisèle
q : Quitter
2
Vous avez sélectionné Marcel, qui a l'indice 1
```

(Attention, dans java.util.List les indices commencent à 0. Mais ils sont 
affichés en partant de l'indice 1.

## Code organisation

Il est conseillé, pour clarifier le code, de répartir les 
instructions dans des fonctions de la façon suivante :

```
static Menu getMenuPrincipal()
{
	Menu menuPrincipal = new Menu("Menu Principal");
	menuPrincipal.ajoute(getOptionCalculatrice());
	menuPrincipal.ajouteQuitter("q");
	return menuPrincipal;
}

static Option getOptionCalculatrice()
{
	Option calculatrice = new Option("Calculatrice", "c", getActionCalculatrice());
	return calculatrice;
}

static Action getActionCalculatrice()
{
	return new Action()
	{
		public void optionSelectionnee()
		{
			int a = utilitaires.EntreesSorties.getInt("Saisissez la première opérande : "),
				b = utilitaires.EntreesSorties.getInt("Saisissez la deuxième opérande : ");
			System.out.println("" + a + " + " + b + " = " + (a+b));
		}
	};
}
	
static Action getActionDireBonjour()
{
	return new Action()
	{
		public void optionSelectionnee()
		{
			System.out.println("Bonjour !");
		}
	};
}

static Option getOptionDireBonjour()
{
	return new Option("Dire bonjour", "b", getActionDireBonjour());
}
	
static Menu getMenuDireBonjour()
{
	Menu direBonjour = new Menu("Menu bonjour", "bonjour", "b");
	direBonjour.ajoute(getOptionDireBonjour());
	direBonjour.ajouteRevenir("r");;
	direBonjour.setRetourAuto(true);
	return direBonjour;
}
	
public static void main(String[] args)
{
	Menu menu = getMenuPrincipal();
	menu.start();
}
```

## Nesting options in lists

Vous avez aussi la possibilité d'inclure des options dans des listes.

```
public static void main(String[] args)
{
	List<String> personnes = new ArrayList<>();
	personnes.add("Ginette");
	personnes.add("Marcel");
	personnes.add("Gisèle");
	Liste<String> liste = getListePersonne(personnes);
	liste.start();
} 
// Retourne La liste à afficher
private static Liste<String> getListePersonne(final List<String> personnes)
{
	Liste<String> liste = new Liste<>("Choisissez une personne pour l'afficher", 
		getActionListePersonnes(personnes));
	return liste;
}

private static ActionListe<String> getActionListePersonnes(final List<String> personnes)
{
	return new ActionListe<String>()
	{
		// Retourne les éléments affichés dans le menu.
		public List<String> getListe()
		{
			return personnes;
		}

		// Vide, car on souhaite créer manuellement chaque option.
		public void elementSelectionne(int indice, String element){}

		// Retourne l'option associée à element.
		public Option getOption(final String personne)
		{
			// Crée une option, le raccourci est laissé null car il sera écrasé par l'indice
			return new Option("Afficher " + personne, null, new Action()
			{
				// Action exécutée si l'option est sélectionnée.
				public void optionSelectionnee()
				{
					System.out.println("Affichage de " + personne);
				}
			});
		}
	};
}
```

## Lambda expressions

It is strongly advised to use lambda expressions. 
