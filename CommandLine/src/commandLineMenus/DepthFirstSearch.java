package commandLineMenus;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

class DepthFirstSearch
{
	private static interface Visitor
	{
		public void visit(Menu node);
	}
	
	private	Stack<Option> stack = new Stack<Option>();
	private	Set<Option> hashStack = new HashSet<Option>();
	private Set<Visitor> visitors = new HashSet<>();
	
	public DepthFirstSearch(Menu root)
	{
		initVisitors();
		depthFirstSearch(root);
	}	

	private void depthFirstSearch(Set<Option> options)
	{
		for(Option option : options)
			if (option instanceof Menu)
				depthFirstSearch((Menu)option);
	}
	
	private void depthFirstSearch(Menu menu)
	{
		visit(menu);
		push(menu);
		depthFirstSearch(menu.getOptions());
		pop(menu);
	}	

	private void initVisitors()
	{
		visitors.add(checkEmptyMenu());
		visitors.add(checkCycle());
	}

	private void visit(Menu node)
	{
		for (Visitor visitor : visitors)
			visitor.visit(node);		
	}
	
	private Visitor checkEmptyMenu()
	{
		return (node) -> 
		{
			if (!(node instanceof List<?>) && node.size() == 0)
				throw node.new EmptyMenuException();	
		};
	}
	
	private Visitor checkCycle()
	{
		return (node) ->
		{
			if (isInStack(node))
				throw new Menu.CycleDetectedException(subList(node));
		};
	}
	
	
	private boolean isInStack(Menu node)
	{
		return hashStack.contains(node);
	}
	
	private java.util.List<Option> subList(Menu node)
	{
		stack.push(node);		
		return stack.subList(stack.indexOf(node), stack.size());
	}

	private void push(Menu node)
	{
		stack.push(node);
		hashStack.add(node);
	}
	
	private void pop(Menu node)
	{
		stack.pop();
		hashStack.remove(node);
	}
}
