package function;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import basicvar.RChar;
/**
 * 
 * @author 周文杰
 * @since 2018.01.07
 * 测试varuplow
 */
public class VarUpLowTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testRtoupper() {
		VarUpLow uL = new VarUpLow();
		String temp1 = uL.Rtoupper("aaaAAA");
		assertEquals("AAAAAA", temp1);
	}

	@Test
	public void testRtolower() {
		VarUpLow uL = new VarUpLow();
		String temp1 = uL.Rtolower("aaaAAA");
		assertEquals("aaaaaa", temp1);
	}

}
