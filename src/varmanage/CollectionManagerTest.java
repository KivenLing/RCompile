package varmanage;

/**
 * @author 周文杰
 * 单元测试-collectionmanager
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
import collection.RVector;

public class CollectionManagerTest {

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
		CollectionManager collectionManager = CollectionManager.getInstance();
		RVector<CommonVar> rVector = new RVector<>();
		rVector.addElement(new RInteger(1));
		collectionManager.addElement("RV1", rVector);
		assertEquals(true, collectionManager.isContains("RV1"));
	}

	@Ignore
	public void testAddElement() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetElement() {
		CollectionManager collectionManager = CollectionManager.getInstance();
		RVector<RInteger> rVector = new RVector<RInteger>();
		rVector.addElement(new RInteger(1));
		collectionManager.addElement("RV1", rVector);
		assertEquals(rVector, collectionManager.getElement("RV1"));
	}

	@Test
	public void testRmElement() {
		CollectionManager collectionManager = CollectionManager.getInstance();
		RVector<CommonVar> rVector = new RVector<>();
		rVector.addElement(new RInteger(1));
		collectionManager.addElement("RV1", rVector);
		collectionManager.rmElement("RV1");
		assertEquals(false, collectionManager.isContains("RV1"));
	}

	@Test
	public void testPrintElement() {
		CollectionManager collectionManager = CollectionManager.getInstance();
		RVector<CommonVar> rVector = new RVector<>();
		rVector.addElement(new RInteger(1));
		collectionManager.addElement("RV1", rVector);
		assertEquals(" 1", collectionManager.printElement());
	}

}
