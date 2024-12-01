import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.junit.*;

public class BSTTest {
	BST<String, Integer> tree;
	
	void setUp(){
		tree = new BST<String, Integer>();
	}

	@Test
	public void putNullTest() {
		setUp();
		boolean hasError = false;
		try{
			tree.put(null, 80);
		} catch (IllegalArgumentException ex) {
			hasError = true;
		}
		assertEquals(hasError, true);
	}

	@Test
	public void putTest() {
		setUp();
		assertEquals(true, tree.put("J", 11));
		assertEquals(1, tree.size());
		assertEquals(new Integer(11), tree.get("J"));
		assertEquals(false, tree.put("J", 11));
		assertEquals(true, tree.put("S", 12));
		assertEquals(true, tree.put("V", 13));
		assertEquals(true, tree.put("A", 14));
		assertEquals(new Integer(12), tree.get("S"));
		assertEquals(new Integer(14), tree.get("A"));
	}

	@Test
	public void replaceTest() {
		setUp();
		tree.put("John", 11);
		tree.put("Smith", 11);
		assertEquals(true, tree.replace("John", 12));
		assertEquals(new Integer(12), tree.get("John"));
		assertEquals(true, tree.replace("Smith", 12));
		assertEquals(false, tree.replace("Johnny", 12));
	}

	@Test
	public void replaceNullTest() {
		setUp();
		boolean hasError = false;
		try{
			tree.put("John", 11);
			tree.put("Smith", 11);
			tree.replace(null, 80);
		} catch (IllegalArgumentException ex) {
			hasError = true;
		}
		assertEquals(hasError, true);
	}

	@Test
	public void removeNullTest() {
		setUp();
		boolean hasError = false;
		try{
			tree.put("John", 11);
			tree.put("Smith", 11);
			tree.remove(null);
		} catch (IllegalArgumentException ex) {
			hasError = true;
		}
		assertEquals(hasError, true);
	}

	@Test
	public void removeTest() {
		setUp();
		tree.put("John", 11);
		tree.put("Smith", 11);
		tree.replace("John", 12);
		assertEquals(2, tree.size());
		assertEquals(true, tree.remove("John"));
		assertEquals(1, tree.size());
		assertEquals(false, tree.remove("Johnny"));
		assertEquals(1, tree.size());
	}

	@Test
	public void isEmptyTest() {
		setUp();
		assertEquals(true, tree.isEmpty());
	}

	@Test
	public void isEmptyTest2() {
		setUp();
		tree.put("John", 11);
		assertEquals(false, tree.isEmpty());
		tree.replace("John", 12);
		assertEquals(false, tree.isEmpty());
		tree.remove("John");
		assertEquals(true, tree.isEmpty());
	}

	@Test
	public void containsKeyTest() {
		setUp();
		assertEquals(false, tree.containsKey("John"));
		tree.put("John", 11);
		assertEquals(true, tree.containsKey("John"));
		tree.replace("John", 12);
		assertEquals(true, tree.containsKey("John"));
	}

	@Test
	public void containsKeyNullTest() {
		setUp();
		boolean hasError = false;
		try{
			tree.put("John", 11);
			tree.put("Smith", 11);
			tree.containsKey(null);
		} catch (IllegalArgumentException ex) {
			hasError = true;
		}
		assertEquals(hasError, true);
	}

	@Test
	public void setTest() {
		setUp();
		tree.set("John", 11);
		assertEquals(new Integer(11), tree.get("John"));
		tree.set("Smith", 11);
		assertEquals(new Integer(11), tree.get("Smith"));
	}

	@Test
	public void setTest2() {
		setUp();
		tree.set("John", 11);
		tree.set("Smith", 11);
		tree.set("John", 12);
		assertEquals(new Integer(12), tree.get("John"));
		assertEquals(new Integer(11), tree.get("Smith"));
	}

	@Test
	public void getTest() {
		setUp();
		tree.set("John", 11);
		assertEquals(new Integer(11), tree.get("John"));
		tree.set("John", 12);
		assertEquals(new Integer(12), tree.get("John"));
		assertEquals(null, tree.get("Johnny"));
	}

	@Test
	public void gwtNullTest() {
		setUp();
		boolean hasError = false;
		try{
			tree.put("John", 11);
			tree.put("Smith", 11);
			tree.get(null);
		} catch (IllegalArgumentException ex) {
			hasError = true;
		}
		assertEquals(hasError, true);
	}

	@Test
	public void sizeTest() {
		setUp();
		assertEquals(0, tree.size());
	}

	@Test
	public void sizeTest2() {
		setUp();
		tree.set("John", 11);
		tree.set("John", 12);
		tree.set("Smith", 11);
		tree.set("Kate", 11);
		tree.remove("Kate");
		tree.remove("Johnny");
		assertEquals(2, tree.size());
	}

	@Test
	public void keysTest() {
		setUp();
		tree.set("J", 11);
		tree.set("K", 12);
		tree.set("S", 11);
		tree.set("A", 11);
		List<String> list = new ArrayList<>();
		list.add("A");
		list.add("J");
		list.add("K");
		list.add("S");
		assertEquals(list, tree.keys());
	}

	@Test
	public void keysTest2() {
		setUp();
		tree.set("J", 11);
		tree.set("K", 12);
		tree.set("S", 11);
		tree.set("A", 11);
		tree.set("A", 13);
		tree.remove("K");
		List<String> list = new ArrayList<>();
		list.add("A");
		list.add("J");
		list.add("S");
		assertEquals(list, tree.keys());
	}
}
