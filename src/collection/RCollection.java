package collection;

import java.util.List;

import basicvar.*;

public abstract class RCollection<Var extends CommonVar> {
	public static final String NULL = "NULL";
	public static final String LIST = "list";
	List<Var> content;
	String classType;
	
	public abstract String toString();
	
	public String getClassType() {
		return this.classType;
	}
	
	public void addElement(Var var) {
		this.content.add(var);
	}
	
	public boolean rmElement(Var var) {
		if(contain(var)) {
			this.content.remove(var);
			return true;
		}		
		return false;
	}
	
	public boolean rmElement(int index) {
		if(index >= 0 && index < this.content.size()) {
			this.content.remove(index);
			return true;
		}
		return false;
	}
	
	public Var getElement(int index) {
		assert(index >= 0 && index < this.content.size());
		return this.content.get(index);
	}
	
	public boolean contain(Var var) {
		return this.content.contains(var);
	}
}
