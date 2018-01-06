package varmanage;
/**
 * @author 周文杰
 * 单元测试-commonvarmanager
 * data:2018.01.06
 */
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import basicvar.RInteger;

public class CommonVarManagerTest {

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

	@Ignore
	public void testGetInstance() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsContains() {
		CommonVarManager comvarman = CommonVarManager.getInstance();
		comvarman.addElement("int1", new RInteger(1));
		assertEquals(true, comvarman.isContains("int1"));
	}

	@Ignore
	public void testAddElement() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetElement() {
		CommonVarManager comvarman = CommonVarManager.getInstance();
		RInteger integer = new RInteger(1);
		comvarman.addElement("int1", integer);
		assertEquals(integer, comvarman.getElement("int1"));
	}

	@Test
	public void testRmElement() {
		CommonVarManager comvarman = CommonVarManager.getInstance();
		comvarman.addElement("int1", new RInteger(1));
		comvarman.rmElement("int1");
		assertEquals(false, comvarman.isContains("int1"));
		
	}

	@Ignore
	public void testPrintElement() {
		fail("Not yet implemented");
	}

}
