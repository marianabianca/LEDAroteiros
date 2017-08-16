package adt.bst;

import util.Util;

public class BSTImpl<T extends Comparable<T>> implements BST<T> {

	protected BSTNode<T> root;

	public BSTImpl() {
		root = new BSTNode<T>();
	}

	public BSTNode<T> getRoot() {
		return this.root;
	}

	@Override
	public boolean isEmpty() {
		return root.isEmpty();
	}

	@Override
	public int height() {
		return this.height(this.root);
	}

	private int height(BSTNode<T> node) {
		int returnHeight = -1;
		
		if (!node.isEmpty()) {
			returnHeight = 1 + Math.max(this.height((BSTNode<T>) node.getRight()), this.height((BSTNode<T>) node.getLeft()));
		}
		
		return returnHeight;
	}

	@Override
	public BSTNode<T> search(T element) {
		return this.search(this.getRoot(), element);
	}
	
	private BSTNode<T> search(BSTNode<T> node, T element) {
		BSTNode<T> returnElement = new BSTNode<>();
		
		if (!node.isEmpty() && element != null) {
			if (node.getData().equals(element)) {
				returnElement = node;
			} else if (node.getData().compareTo(element) < 0) {
				returnElement = this.search((BSTNode<T>) node.getRight(), element);
			} else if (node.getData().compareTo(element) > 0) {
				returnElement = this.search((BSTNode<T>) node.getLeft(), element);
			}			
		}
		
		return returnElement;
	}

	@Override
	public void insert(T element) {
		if (element != null) {
			this.insert(this.getRoot(), element);
		}
	}

	private void insert(BSTNode<T> node, T element) {
		if (node.isEmpty()) {
			this.setNewNode(node, element);
		} else if (node.getData().compareTo(element) < 0) {
			this.insert((BSTNode<T>) node.getRight(), element);
		} else if (node.getData().compareTo(element) > 0) {
			this.insert((BSTNode<T>) node.getLeft(), element);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void setNewNode(BSTNode<T> node, T element) {
		node.setParent(node.getParent());
		node.setData(element);
		node.setLeft(new BSTNode.Builder()
				.parent(node)
				.build());
		node.setRight(new BSTNode.Builder()
				.parent(node)
				.build());
	}

	@Override
	public BSTNode<T> maximum() {
		return this.maximum(this.getRoot());
	}

	private BSTNode<T> maximum(BSTNode<T> node) {
		BSTNode<T> returnElement = null;

		if (!node.isEmpty()) {
			if (node.getRight().isEmpty()) {
				returnElement = (BSTNode<T>) node;
			} else {
				returnElement = this.maximum((BSTNode<T>) node.getRight());
			}
		}
		
		return returnElement;
	}

	@Override
	public BSTNode<T> minimum() {
		return this.minimum(this.getRoot());
	}

	private BSTNode<T> minimum(BSTNode<T> node) {
		BSTNode<T> returnElement = null;

		if (!node.isEmpty()){
			if (node.getLeft().isEmpty()) {
				returnElement = (BSTNode<T>) node;
			} else {
				returnElement = this.minimum((BSTNode<T>) node.getLeft());
			}
		}
		
		return returnElement;
	}

	@Override
	public BSTNode<T> sucessor(T element) {
		BSTNode<T> node = this.search(element);
		BSTNode<T> returnNode = null;
		
		if (!node.isEmpty()) {
			if (!node.getRight().isEmpty()) {
				returnNode = this.minimum((BSTNode<T>) node.getRight());
			} else {
				returnNode = this.sucessorUp(node, node.getData());
			}
		}
		
		return returnNode;
	}
	
	private BSTNode<T> sucessorUp(BSTNode<T> node, T value) {
		BSTNode<T> returnNode = null;
		
		if (node.getParent() == null) {
			returnNode = null;
		} else if (node.getParent().getData().compareTo(value) > 0) {
			returnNode = (BSTNode<T>) node.getParent();
		} else {
			returnNode = (BSTNode<T>) this.sucessorUp((BSTNode<T>) node.getParent(), value);
		}
		
		return returnNode;
	}

	@Override
	public BSTNode<T> predecessor(T element) {
		BSTNode<T> node = this.search(element);
		BSTNode<T> returnNode = null;
		
		if (!node.isEmpty()) {
			if (!node.getLeft().isEmpty()) {
				returnNode = this.maximum((BSTNode<T>) node.getLeft());
			} else {
				returnNode = this.predecessorUp(node, node.getData());
			}
		}
		
		return returnNode;
	}

	private BSTNode<T> predecessorUp(BSTNode<T> node, T value) {
		BSTNode<T> returnNode;
		
		if (node.getParent() == null) {
			returnNode = null;
		} else if (node.getParent().getData().compareTo(value) < 0) {
			returnNode = (BSTNode<T>) node.getParent();
		} else {
			returnNode = (BSTNode<T>) this.predecessorUp((BSTNode<T>) node.getParent(), value);
		}
		
		return returnNode;
	}

	@Override
	public void remove(T element) {
		if (element != null) {
			BSTNode<T> node = this.search(element);
			
			if (node.isLeaf()) {
				node.setData(null);
			} else if (!node.getLeft().isEmpty() && !node.getRight().isEmpty()) {
				T predNode = this.predecessor(node.getData()).getData();
				this.remove(predNode);
				node.setData(predNode);
			} else if (!node.getRight().isEmpty()) {
				this.removeOneChild(node, (BSTNode<T>) node.getRight());
			} else {
				this.removeOneChild(node, (BSTNode<T>) node.getLeft());
			}
		}
	}

	private void removeOneChild(BSTNode<T> node, BSTNode<T> childNode) {
		if (node.getParent() == null) {
			node.setData(childNode.getData());
			node.setLeft(childNode.getLeft());
			node.setRight(childNode.getRight());
		} else if (node.getParent().getRight().equals(node)) {
			node.getParent().setRight(childNode);
		} else if (node.getParent().getLeft().equals(node)) {
			node.getParent().setLeft(childNode);
		}
		
		childNode.setParent(node.getParent());
	}

	@Override
	public T[] preOrder() {
		T[] returnArray = Util.makeArrayOfComparable(this.size());
		this.preOrder(this.getRoot(), returnArray);
		
		return returnArray;
	}

	private void preOrder(BSTNode<T> node, T[] returnArray) {
		if (!node.isEmpty()) {
			this.addElementToArray(returnArray, node.getData());
			this.preOrder((BSTNode<T>) node.getLeft(), returnArray);
			this.preOrder((BSTNode<T>) node.getRight(), returnArray);
		}
	}

	private void addElementToArray(T[] returnArray, T value) {
		int i = 0;
		while (returnArray[i] != null) {
			i++;
		}
		
		returnArray[i] = value;
	}

	@Override
	public T[] order() {
		T[] returnArray = Util.makeArrayOfComparable(this.size());
		this.order(this.getRoot(), returnArray);
		
		return returnArray;
	}

	private void order(BSTNode<T> node, T[] returnArray) {
		if (!node.isEmpty()) {
			this.order((BSTNode<T>) node.getLeft(), returnArray);
			this.addElementToArray(returnArray, node.getData());
			this.order((BSTNode<T>) node.getRight(), returnArray);
		}
	}

	@Override
	public T[] postOrder() {
		T[] returnArray = Util.makeArrayOfComparable(this.size());
		this.postOrder(this.getRoot(), returnArray);
		
		return returnArray;
	}
	
	private void postOrder(BSTNode<T> node, T[] returnArray) {
		if (!node.isEmpty()) {
			this.postOrder((BSTNode<T>) node.getLeft(), returnArray);
			this.addElementToArray(returnArray, node.getData());
			this.postOrder((BSTNode<T>) node.getRight(), returnArray);
		}
	}

	/**
	 * This method is already implemented using recursion. You must understand
	 * how it work and use similar idea with the other methods.
	 */
	@Override
	public int size() {
		return size(root);
	}

	private int size(BSTNode<T> node) {
		int result = 0;
		// base case means doing nothing (return 0)
		if (!node.isEmpty()) { // indusctive case
			result = 1 + size((BSTNode<T>) node.getLeft())
					+ size((BSTNode<T>) node.getRight());
		}
		return result;
	}

}
