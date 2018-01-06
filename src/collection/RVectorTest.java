package collection;

/**
 * @author 周文杰
 * 单元测试-Rvector
 * data:2018.01.06
 */
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import basicvar.CommonVar;
import basicvar.RInteger;

public class RVectorTest {

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
	public void testToString() {
		RVector<RInteger> rv = new RVector<RInteger>();
		RInteger integer = new RInteger(1);
		rv.addElement(integer);
		assertEquals(" 1", rv.toString());
	}

	@Ignore
	public void testAddElement() {
		fail("Not yet implemented");
	}


	@Test
	public void testRmElementInt() {
		RVector<CommonVar> rv = new RVector<CommonVar>();
		RInteger i = new RInteger(1);
		rv.addElement(i);
		rv.addElement(new RInteger(2));
		rv.rmElement(0);
		assertEquals(" 2", rv.toString());
	}


	@Test
	public void testGetElement() {
		RVector<CommonVar> rv = new RVector<CommonVar>();
		RInteger i = new RInteger(1);
		rv.addElement(i);
		assertEquals(i, rv.getElement(0));
	}



}
