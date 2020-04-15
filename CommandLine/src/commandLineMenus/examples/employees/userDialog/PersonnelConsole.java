package commandLineMenus.examples.employees.userDialog;

import java.util.ArrayList;

import commandLineMenus.*;
import commandLineMenus.examples.employees.core.*;

import static commandLineMenus.rendering.examples.util.InOut.*;

public class PersonnelConsole
{
	private ManageEmployees gestionPersonnel;
	
	public PersonnelConsole(ManageEmployees gestionPersonnel)
	{
		this.gestionPersonnel = gestionPersonnel;
	}
	
	public void start()
	{
		menuPrincipal().start();
	}
	
	private Menu menuPrincipal()
	{
		Menu menu = new Menu("Gestion du personnel des ligues");
		menu.add(editerEmploye(gestionPersonnel.getRoot()));
		menu.add(menuLigues());
		menu.add(menuQuitter());
		return menu;
	}

	private Menu menuQuitter()
	{
		Menu menu = new Menu("Quitter", "q");
		menu.add(quitterEtEnregistrer());
		menu.add(quitterSansEnregistrer());
		menu.addBack("r");
		return menu;
	}
	
	private Menu menuLigues()
	{
		Menu menu = new Menu("Gérer les ligues", "l");
		menu.add(afficherLigues());
		menu.add(ajouterLigue());
		menu.add(selectionnerLigue());
		menu.addBack("q");
		return menu;
	}

	private Option afficherLigues()
	{
		return new Option("Afficher les ligues", "l", () -> System.out.println(gestionPersonnel.getDepartments()));
	}
	
	private Option afficherEmployes(final Department ligue)
	{
		return new Option("Afficher les employes", "l", () -> System.out.println(ligue.getEmployes()));
	}
	
	private Option afficher(final Department ligue)
	{
		return new Option("Afficher la ligue", "l", 
				() -> 
				{
					System.out.println(ligue);
					System.out.println("administrée par " + ligue.getAdministrator());
				}
		);
	}

	private Option afficher(final Employee employe)
	{
		return new Option("Afficher l'employé", "l", () -> System.out.println(employe));
	}

	private Option ajouterLigue()
	{
		return new Option("Ajouter une ligue", "a", () -> new Department (getString("nom : ")));
	}
	
	private Option ajouterEmploye(final Department ligue)
	{
		return new Option("ajouter un employé", "a",
				() -> ligue.addEmploye(getString("nom : "), 
			            getString("prenom : "), 
			            getString("mail : "), 
			            getString("password : "))
		);
	}
	
	private Menu editerLigue(Department ligue)
	{
		Menu menu = new Menu("Editer " + ligue.getName());
		menu.add(afficher(ligue));
		menu.add(gererEmployes(ligue));
		menu.add(changerAdministrateur(ligue));
		menu.add(changerNom(ligue));
		menu.add(supprimer(ligue));
		menu.addBack("q");
		return menu;
	}

	private Menu editerEmploye(Employee employe)
	{
		Menu menu = new Menu("Gérer le compte " + employe.getLastName(), "c");
		menu.add(afficher(employe));
		menu.add(changerNom(employe));
		menu.add(changerPrenom(employe));
		menu.add(changerMail(employe));
		menu.add(changerPassword(employe));
		menu.addBack("q");
		return menu;
	}

	private Menu gererEmployes(Department ligue)
	{
		Menu menu = new Menu("Gérer les employés de " + ligue.getName(), "e");
		menu.add(afficherEmployes(ligue));
		menu.add(ajouterEmploye(ligue));
		menu.add(modifierEmploye(ligue));
		menu.add(supprimerEmploye(ligue));
		menu.addBack("q");
		return menu;
	}

	private List<Employee> modifierEmploye(final Department ligue)
	{
		return new List<>("Modifier un employé", "e", 
				() -> new ArrayList<>(ligue.getEmployes()),
				(index, element) -> editerEmploye(element)
				);
	}
	
	private List<Employee> supprimerEmploye(final Department ligue)
	{
		return new List<>("Supprimer un employé", "s", 
				() -> new ArrayList<>(ligue.getEmployes()),
				(index, element) -> element.remove()
				);
	}
	
	private List<Employee> changerAdministrateur(final Department ligue)
	{
		return new List<>("Changer d'administrateur", "c", 
				() -> new ArrayList<>(ligue.getEmployes()), 
				(index, element) -> ligue.setAdministrator(element)
				);
	}		
	
	private Option changerNom(final Department ligue)
	{
		return new Option("Renommer", "r", () -> ligue.setName(getString("Nouveau nom : ")));
	}

	private List<Department> selectionnerLigue()
	{
		return new List<>("Sélectionner une ligue", "e", 
				() -> new ArrayList<>(gestionPersonnel.getDepartments()),
				(element) -> editerLigue(element)
				);
	}
	
	private Option supprimer(Department ligue)
	{
		return new Option("Supprimer", "d", ligue::remove);
	}
	
	private Option changerNom(final Employee employe)
	{
		return new Option("Changer le nom", "n", () -> employe.setLastName(getString("Nouveau nom : ")));
	}
	
	private Option changerPrenom(final Employee employe)
	{
		return new Option("Changer le prénom", "p", () -> employe.setFirstName(getString("Nouveau prénom : ")));
	}
	
	private Option changerMail(final Employee employe)
	{
		return new Option("Changer le mail", "e", () -> employe.setMail(getString("Nouveau mail : ")));
	}
	
	private Option changerPassword(final Employee employe)
	{
		return new Option("Changer le password", "x", () -> employe.setPassword(getString("Nouveau password : ")));
	}
	
	private Option quitterEtEnregistrer()
	{
		return new Option("Quitter et enregistrer", "q", 
				() -> 
				{
					try
					{
						gestionPersonnel.sauvegarder();
						Action.QUIT.optionSelected();
					} 
					catch (ImpossibleToSaveException e)
					{
						System.out.println("Impossible d'effectuer la sauvegarde");
					}
				}
			);
	}
	
	private Option quitterSansEnregistrer()
	{
		return new Option("Quitter sans enregistrer", "a", Action.QUIT);
	}
	
	private boolean verifiePassword()
	{
		boolean ok = gestionPersonnel.getRoot().checkPassword(getString("password : "));
		if (!ok)
			System.out.println("Password incorrect.");
		return ok;
	}
	
	public static void main(String[] args)
	{
		PersonnelConsole personnelConsole = new PersonnelConsole(ManageEmployees.getManageEmployees());
		if (personnelConsole.verifiePassword())
			personnelConsole.start();		
	}
}
