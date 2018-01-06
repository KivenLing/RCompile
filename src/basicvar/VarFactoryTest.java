package basicvar;

/**
 * @author 周文杰
 * 单元测试-VarFactory
 * data:2018.01.06
 */
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class VarFactoryTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testCreateVar() {
		VarFactory vf = new VarFactory();
		CommonVar var = vf.createVar("name", "integer", "1L");
		assertEquals("integer", var.getClassType());
	}

}
