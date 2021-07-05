package commandLineMenus;

import java.util.HashSet;


import java.util.Set;
import java.util.Collection;
import java.util.Stack;

import commandLineMenus.List.ListActionOrOptionException;
import commandLineMenus.rendering.examples.MenuDefaultRenderer;

class DepthFirstSearch
{
	private static interface Visitor
	{
		public void visit(Option node);
	}
	
	private	Stack<Menu> stack = new Stack<>();
	private	Set<Menu> stackSet = new HashSet<>();
	private Set<Visitor> visitors = new HashSet<>();
	
	public DepthFirstSearch(Menu root)
	{
		initVisitors();
		depthFirstSearch(root);
	}	

	private void depthFirstSearch(Collection<Option> options)
	{
		for(Option option : options)
			depthFirstSearch(option);
	}
	
	private void depthFirstSearch(Option option)
	{
		visit(option);
		if (option instanceof Menu)
		{
			Menu menu = (Menu)option;
			push(menu);
			depthFirstSearch(menu.getOptions());
			pop();
		}
	}	

	private void initVisitors()
	{
		visitors.add(checkEmptyMenu());
		visitors.add(setRenderers());
		visitors.add(checkCycle());
		visitors.add(checkActionDefined());
	}

	private void visit(Option node)
	{
		for (Visitor visitor : visitors)
			visitor.visit(node);		
	}
	
	private Visitor checkEmptyMenu()
	{
		return (node) -> 
		{
			if (node instanceof Menu && !(node instanceof List<?>))
			{
				Menu menu = (Menu) node;
				if (menu.size() == 0)
				    throw menu.new EmptyMenuException();	
			}
		};
	}
	
	private Visitor checkCycle()
	{
		return (node) ->
		{
			if ((node instanceof Menu) && isInStack(node))
				throw new Menu.CycleDetectedException(subList((Menu)node));
		};
	}

	private Visitor setRenderers()
	{
		return (node) ->
		{
			if (node.getRenderer() == null)
			{
				if (isEmpty())
					node.setRenderer(new MenuDefaultRenderer());
				else
					node.setRenderer(top().getRenderer());
			}
		};
	}

	private Visitor checkActionDefined()
	{
		return (node) -> 
		{
			if (node instanceof List<?>)
			{
				List<?> list = (List<?>)node;
				if (!xor(list.getListAction() == null, list.getListOption() == null))
					throw new ListActionOrOptionException(list);
			}
		};
	}
	
	private boolean xor(boolean a, boolean b)
	{
		return (a && !b) || (!a && b);
	}

	private boolean isInStack(Option node)
	{
		return stackSet.contains(node);
	}
	
	private java.util.List<Menu> subList(Menu node)
	{
		stack.push(node);		
		return stack.subList(stack.indexOf(node), stack.size());
	}

	private Option top()
	{
		return stack.peek();
	}
	
	private void push(Menu node)
	{
		stack.push(node);
		stackSet.add(node);
	}
	
	private void pop()
	{
		Option node = top();
		stack.pop();
		stackSet.remove(node);
	}

	private boolean isEmpty()
	{
		return stackSet.isEmpty();
	}
}
