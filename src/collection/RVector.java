package collection;

import java.util.ArrayList;

import basicvar.*;

public class RVector<Var extends CommonVar> extends RCollection<Var> {
	
	public RVector() {
		this.content = new ArrayList<Var>();
		classType = RCollection.NULL;
	}
	
	@Override
	public String toString() {
		String contentStr = "";
		for(Var v : this.content) {
			contentStr += " " + v.toString();
		}	
		return contentStr;
	}
	
	@Override
	public void addElement(Var var) {
		if(this.content.isEmpty()) 
			this.classType = var.getClassType();
		super.addElement(var);
	}
	
	@Override
	public boolean rmElement(Var var) {
		if(super.rmElement(var)) {
			if(this.content.isEmpty())
				this.classType = RCollection.NULL;
			return true;
		}
		return false;
	}
	
	@Override
	public boolean rmElement(int index) {
		if(super.rmElement(index)) {
			if(this.content.isEmpty()) {
				this.classType = RCollection.NULL;
			}
			return true;
		}
		return false;
	}
}
