package commandLineMenus.examples.employees.core;

import java.io.Serializable;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

public class Department implements Serializable, Comparable<Department>
{
	private static final long serialVersionUID = 1L;
	private String name;
	private SortedSet<Employee> employees;
	private Employee administrator;
	
	public Department(String name)
	{
		this.name = name;
		employees = new TreeSet<>();
		administrator = ManageEmployees.getManageEmployees().getRoot();
		ManageEmployees.getManageEmployees().add(this);
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Employee getAdministrator()
	{
		return administrator;
	}
	
	public void setAdministrator(Employee administrator)
	{
		Employee root = ManageEmployees.getManageEmployees().getRoot();
		if (administrator != root && administrator.getDepartment() != this)
			throw new InsufficientRightsException();
		this.administrator = administrator;
	}

	public SortedSet<Employee> getEmployes()
	{
		return Collections.unmodifiableSortedSet(employees);
	}

	public Employee addEmploye(String lastName, String firstName, String mail, String password)
	{
		Employee employee = new Employee(this, lastName, firstName, mail, password);
		employees.add(employee);
		return employee;
	}
	
	void remove(Employee employe)
	{
		employees.remove(employe);
	}
	
	public void remove()
	{
		ManageEmployees.getManageEmployees().remove(this);
	}
	

	@Override
	public int compareTo(Department other)
	{
		return getName().compareTo(other.getName());
	}
	
	@Override
	public String toString()
	{
		return name;
	}
}
