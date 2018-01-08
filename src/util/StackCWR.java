package util;

import java.util.ArrayList;
import java.util.List;

public class StackCWR {
	List<String> stack = new ArrayList<String>();

	public void push(String a) {
		stack.add(a);
	}
	public String getValue() {
		return stack.get(stack.size()-1);
	}
	public String pop() {
		String value = stack.get(stack.size()-1);
		stack.remove(stack.size()-1);
		return value;
	}
	public boolean isEmpty() {
		return stack.isEmpty();
	}
}
