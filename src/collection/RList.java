package collection;

import java.util.ArrayList;

import basicvar.*;
/**
 * @author 凌凯、周文杰
 * @since 2018.01.06
 * 对R的list封装
 */
public class RList extends RCollection<CommonVar> {

	public RList() {
		this.content = new ArrayList<CommonVar>();
		this.classType = RCollection.LIST;
		
	}
	@Override
	public String toString() {
		String contentStr = "";
		for(CommonVar v : this.content) {
			contentStr += " " + v.toString();
		}	
		return contentStr;
	}

	public static void main(String[] args) {
		RInteger ri = new RInteger(5);
		RChar rc = new RChar("5");
		RComplex rrc = new RComplex(1, 2);
		RList rl = new RList();
		rl.addElement(ri);
		rl.addElement(rc);
		rl.addElement(rrc);
		System.out.println(rl.toString());
	}
	
}
