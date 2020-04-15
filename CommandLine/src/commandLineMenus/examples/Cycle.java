package commandLineMenus.examples;

import commandLineMenus.Menu;

public class Cycle
{
	public static void main(String[] args)
	{
		Menu root = new Menu("root", "r");
		Menu leaf = new Menu("leaf", "r");
		root.add(leaf);
		leaf.add(root);
		root.start();		
	}
}
