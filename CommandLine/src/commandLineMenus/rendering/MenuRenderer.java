package commandLineMenus.rendering;

public interface MenuRenderer
{
	public String header(String title);
	
	public String option(String shortcut, String label);

	public String betweenOptions();
	
	public String footer();

	public String betweenMenus();

	public String prompt();

	public String invalidInput(String input);
}
