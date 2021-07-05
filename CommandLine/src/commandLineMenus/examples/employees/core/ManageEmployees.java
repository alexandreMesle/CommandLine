package commandLineMenus.examples.employees.core;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

public class ManageEmployees implements Serializable
{
	private static final String FILE_NAME = "GestionPersonnel.srz";
	private static final long serialVersionUID = -105283113987886425L;
	private static ManageEmployees manageEmployees;
	private SortedSet<Department> ligues;
	private Employee root = new Employee(null, "root", "", "", "toor");
	
	public static ManageEmployees getManageEmployees()
	{
		if (manageEmployees == null)
		{
			manageEmployees = readObject();
			if (manageEmployees == null)
				manageEmployees = new ManageEmployees();
		}
		return manageEmployees;
	}
	
	private static ManageEmployees readObject()
	{
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME)))
		{
			return (ManageEmployees) ois.readObject();
		}
		catch (IOException | ClassNotFoundException e)
		{
			return null;
		}
	}
		
	public void sauvegarder() throws ImpossibleToSaveException
	{
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME)))
		{
			oos.writeObject(this);
		}
		catch (IOException e)
		{
			throw new ImpossibleToSaveException();
		}
	}
	
	private ManageEmployees()
	{
		ligues = new TreeSet<>();
	}
	
	public Department getLigue(Employee administrator)
	{
		if (administrator.isAdministrator(administrator.getDepartment()))
			return administrator.getDepartment();
		else
			return null;
	}

	public SortedSet<Department> getDepartments()
	{
		return Collections.unmodifiableSortedSet(ligues);
	}

	void add(Department department)
	{
		ligues.add(department);
	}
	
	void remove(Department department)
	{
		ligues.remove(department);
	}
	
	public Employee getRoot()
	{
		return root;
	}
}
