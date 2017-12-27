package basicvar;

public class Main {

	public static void main(String[] args) {
//		CommonVar var1 = new RComplex(-3,4);
//		System.out.println(var1.getClassType());
//		CommonVar var2 = new RChar("www.pohb.cn");
//		System.out.println(var2.getClassType());
//		System.out.println(((RComplex) var1).toString());
//		System.out.println(((RChar) var2).toString());
//		CommonVar var3 = new RBoolean(true);
//		System.out.println(((RBoolean) var3).toString());
//		CommonVar var4 = new RNumeric(4.0);
//		System.out.println(var4.toString());
		
		VarFactory vf = new VarFactory();
		RChar rc = (RChar) vf.createVar("1", CommonVar.CHARACTER, "www");
		System.out.println(rc.toString());
		RInteger vi = (RInteger) vf.createVar("2", CommonVar.INTEGER, "55L");
		System.out.println(vi.toString());
		RComplex rcm = (RComplex) vf.createVar("3", CommonVar.COMPLEX, "5.2+3.3i");
		System.out.println(rcm.toString());
	}

}
