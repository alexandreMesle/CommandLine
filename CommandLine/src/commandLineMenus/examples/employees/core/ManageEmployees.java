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
		ObjectInputStream ois = null;
		try
		{
			FileInputStream fis = new FileInputStream(FILE_NAME);
			ois = new ObjectInputStream(fis);
			return (ManageEmployees)(ois.readObject());
		}
		catch (IOException | ClassNotFoundException e)
		{
			return null;
		}
		finally
		{
				try
				{
					if (ois != null)
						ois.close();
				} 
				catch (IOException e){}
		}	
	}
		
	public void sauvegarder() throws ImpossibleToSaveException
	{
		ObjectOutputStream oos = null;
		try
		{
			FileOutputStream fis = new FileOutputStream(FILE_NAME);
			oos = new ObjectOutputStream(fis);
			oos.writeObject(this);
		}
		catch (IOException e)
		{
			throw new ImpossibleToSaveException();
		}
		finally
		{
			try
			{
				if (oos != null)
					oos.close();
			} 
			catch (IOException e){}
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
