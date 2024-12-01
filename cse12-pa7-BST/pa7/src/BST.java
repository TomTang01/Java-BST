import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @param <K> The type of the keys of this BST. They need to be comparable by nature of the BST
 * "K extends Comparable" means that BST will only compile with classes that implement Comparable
 * interface. This is because our BST sorts entries by key. Therefore keys must be comparable.
 * @param <V> The type of the values of this BST. 
 */
public class BST<K extends Comparable<? super K>, V> implements DefaultMap<K, V> {
	/* 
	 * 
	 * You may add any instance variables you need, but 
	 * you may NOT use any class that implements java.util.SortedMap
	 * or any other implementation of a binary search tree
	 */
	ArrayList<K> keyList;
	Node<K, V> root;
	int size = 0;

	private Node<K,V> put(Node<K,V> e, K key, V value) {
		if(e == null) {
			e = new Node<K, V>(key, value, null, null);
			this.size += 1;
			return e;
		}
		else if(key.compareTo(e.getKey()) > 0) {
			e.right = put(e.right, key, value);
			return e;
		}
		else if(key.compareTo(e.getKey()) < 0) {
			e.left = put(e.left, key, value);
			return e;
		}
		return null;
	}

	@Override
	public boolean put(K key, V value) throws IllegalArgumentException {
		if(key == null) {throw new IllegalArgumentException(ILLEGAL_ARG_NULL_KEY);}
		Node<K, V> treeRoot = put(root,key,value);
		if(treeRoot == null) {
			return false;
		}
		this.root = treeRoot;
		return true;
	}

	private boolean replace(Node<K, V> e, K key, V newValue) {
		if(e == null) {
			return false;
		}
		else if(key.compareTo(e.getKey()) > 0) {
			return replace(e.right, key, newValue);
		}
		else if(key.compareTo(e.getKey()) < 0) {
			return replace(e.left, key, newValue);
		}
		e.setValue(newValue);
		return true;
	}

	@Override
	public boolean replace(K key, V newValue) throws IllegalArgumentException {
		if(key == null) {throw new IllegalArgumentException(ILLEGAL_ARG_NULL_KEY);}
		return replace(root, key, newValue);
	}

	private boolean remove(Node<K, V> e, K key) {
		if(e == null) {
			return false;
		}
		else if(key.compareTo(e.getKey()) > 0) {
			return remove(e.right, key);
		}
		else if(key.compareTo(e.getKey()) < 0) {
			return remove(e.left, key);
		}
		e.setValue(null);
		size -= 1;
		return true;
	}

	@Override
	public boolean remove(K key) throws IllegalArgumentException {
		if(key == null) {throw new IllegalArgumentException(ILLEGAL_ARG_NULL_KEY);}
		return remove(root, key);
	}

	@Override
	public void set(K key, V value) throws IllegalArgumentException {
		if(key == null) {throw new IllegalArgumentException(ILLEGAL_ARG_NULL_KEY);}
		if(!containsKey(key)) {put(key, value);}
		else {replace(key, value);}
	}

	private V get(Node<K, V> e, K key) {
		if(e == null) {
			return null;
		}
		else if(key.compareTo(e.getKey()) > 0) {
			return get(e.right, key);
		}
		else if(key.compareTo(e.getKey()) < 0) {
			return get(e.left, key);
		}
		return e.getValue();
	}

	@Override
	public V get(K key) throws IllegalArgumentException {
		if(key == null) {throw new IllegalArgumentException(ILLEGAL_ARG_NULL_KEY);}
		return get(root, key);
	}

	@Override
	public int size() {
		return this.size;
	}

	@Override
	public boolean isEmpty() {
		return this.size == 0;
	}

	private boolean containsKey(Node<K, V> e, K key) {
		if(e == null) {
			return false;
		}
		else if(key.compareTo(e.getKey()) > 0) {
			return containsKey(e.right, key);
		}
		else if(key.compareTo(e.getKey()) < 0) {
			return containsKey(e.left, key);
		}
		if(e.getValue() == null) {return false;}
		return true;
	}

	@Override
	public boolean containsKey(K key) throws IllegalArgumentException {
		if(key == null) {throw new IllegalArgumentException(ILLEGAL_ARG_NULL_KEY);}
		return containsKey(root, key);
	}

	public void keys(Node<K, V> e) {
		if(e == null) {return;}
		keys(e.left);
		if(!(e.getValue() == null)) {
			this.keyList.add(e.getKey());
		}
		keys(e.right);
	}

	// Keys must be in ascending sorted order
	// You CANNOT use Collections.sort() or any other sorting implementations
	// You must do inorder traversal of the tree
	@Override
	public List<K> keys() {
		keyList = new ArrayList<>();
		keys(root);
		return keyList;
	}

	private static class Node<K extends Comparable<? super K>, V> 
								implements DefaultMap.Entry<K, V> {
		/* 
		 * Instance variables
		 */
		K key;
		V value;
		Node<K, V> left;
		Node<K, V> right;

		public Node(K key, V value, Node<K,V> left, Node<K,V> right) {
			this.key = key;
			this.value = value;
			this.left = left;
			this.right = right;
		}

		@Override
		public K getKey() {
			return this.key;
		}

		@Override
		public V getValue() {
			return this.value;
		}
		
		@Override
		public void setValue(V value) {
			this.value = value;
		}
	}
}