package commandLineMenus.interfaces;

public interface MenuRenderer
{
	public void outputString(String string);

	public String inputString();

	public String header(String title);
	
	public String option(String shortcut, String label);

	public String betweenOptions();
	
	public String footer();

	public String betweenMenus();

	public String prompt();
	
	public String invalidInput(String input);
}
