package function;
/**
 * @author 周文杰
 * @since 2018.01.07
 * varpaste单元测试
 */
import basicvar.CommonVar;
import basicvar.VarFactory;

public class VarPasteTest {
	public static void main(String [] args) {
		VarFactory vFactory = new VarFactory();
		vFactory.createVar("r1", CommonVar.CHARACTER, "this");
		vFactory.createVar("r2", CommonVar.CHARACTER, "is");
		vFactory.createVar("r3", CommonVar.CHARACTER, "this");
		vFactory.createVar("r4", CommonVar.CHARACTER, "test");
		String string1  = "r1,r2,r3,r4";
		String string2  = "r1,r2,r3,r4,sep=asd";
		String string3  = "r1,r2,r3,r4,sep=sa,collapse=";
		VarPaste vPaste= new VarPaste();
		
		System.out.println(vPaste.RPaste(string1));
		System.out.println(vPaste.RPaste(string2));
		System.out.println(vPaste.RPaste(string3));
	}
}