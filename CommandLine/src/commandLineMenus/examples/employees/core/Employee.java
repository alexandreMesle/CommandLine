package commandLineMenus.examples.employees.core;

import java.io.Serializable;

public class Employee implements Serializable, Comparable<Employee>
{
	private static final long serialVersionUID = 4795721718037994734L;
	private String lastName;
	private String firstName;
	private String password;
	private String mail;
	private Department department;
	
	Employee(Department department, String lastName, String firstName, String mail, String password)
	{
		this.lastName = lastName;
		this.firstName = firstName;
		this.password = password;
		this.mail = mail;
		this.department = department;
	}
	
	public boolean isAdministrator(Department department)
	{
		return department.getAdministrator() == this;
	}
	
	public boolean isRoot()
	{
		return ManageEmployees.getManageEmployees().getRoot() == this;
	}
	
	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public String getFirstName()
	{
		return firstName;
	}
	
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getMail()
	{
		return mail;
	}
	
	public void setMail(String mail)
	{
		this.mail = mail;
	}

	public boolean checkPassword(String password)
	{
		return this.password.equals(password);
	}

	public void setPassword(String password)
	{
		this.password= password;
	}

	public Department getDepartment()
	{
		return department;
	}

	public void remove()
	{
		Employee root = ManageEmployees.getManageEmployees().getRoot();
		if (this != root)
		{
			if (isAdministrator(getDepartment()))
				getDepartment().setAdministrator(root);
			department.remove(this);
		}
		else
			throw new CantDeleteRootException();
	}

	@Override
	public int compareTo(Employee other)
	{
		int cmp = getLastName().compareTo(other.getLastName());
		if (cmp != 0)
			return cmp;
		return getFirstName().compareTo(other.getFirstName());
	}
	
	@Override
	public String toString()
	{
		String res = lastName + " " + firstName + " " + mail + " (";
		if (isRoot())
			res += "super-user";
		else
			res += department.toString();
		return res + ")";
	}
}
