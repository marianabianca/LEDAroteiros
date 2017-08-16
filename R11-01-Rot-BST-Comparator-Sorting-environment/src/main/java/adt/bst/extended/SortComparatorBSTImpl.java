package adt.bst.extended;

import java.util.Comparator;

import adt.bst.BSTImpl;
import adt.bst.BSTNode;
import util.Util;

/**
 * Implementacao de SortComparatorBST, uma BST que usa um comparator interno em suas funcionalidades
 * e possui um metodo de ordenar um array dado como parametro, retornando o resultado do percurso
 * desejado que produz o array ordenado. 
 * 
 * @author Adalberto
 *
 * @param <T>
 */
public class SortComparatorBSTImpl<T extends Comparable<T>> extends BSTImpl<T> implements SortComparatorBST<T> {

	private Comparator<T> comparator;
	
	public SortComparatorBSTImpl(Comparator<T> comparator) {
		super();
		this.comparator = comparator;
	}

	@Override
	public T[] sort(T[] array) {
		super.root = new BSTNode<>();
		
		for (T t : array) {
			super.insert(t);
		}
		
		return super.order();
	}

	@Override
	public T[] reverseOrder() {
		T[] returnArray = Util.makeArrayOfComparable(this.size());
 		this.reverseOrder(super.getRoot(), returnArray);
 		
 		return returnArray;
	}
 
 	private void reverseOrder(BSTNode<T> node, T[] returnArray) {
 		if (!node.isEmpty()) {
 			this.reverseOrder((BSTNode<T>) node.getRight(), returnArray);
 			super.addElementToArray(returnArray, node.getData());
 			this.reverseOrder((BSTNode<T>) node.getLeft(), returnArray);
 		}
 	}

	public Comparator<T> getComparator() {
		return comparator;
	}

	public void setComparator(Comparator<T> comparator) {
		this.comparator = comparator;
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
			} else if (this.comparator.compare(node.getData(), element) < 0) {
				returnElement = this.search((BSTNode<T>) node.getRight(), element);
			} else if (this.comparator.compare(node.getData(), element) > 0) {
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
			this.addNewNode(node, element);
		} else if (this.comparator.compare(node.getData(), element) > 0) {
			this.insert((BSTNode<T>) node.getLeft(), element);
		} else if (this.comparator.compare(node.getData(), element) < 0) {
			this.insert((BSTNode<T>) node.getRight(), element);
		}
	}
	
	private void addNewNode(BSTNode<T> node, T element) {
		@SuppressWarnings({ "unchecked", "rawtypes" })
		BSTNode<T> newNode = new BSTNode.Builder()
				.data(element)
				.parent(node.getParent())
				.left(new BSTNode<>())
				.right(new BSTNode<>())
				.build();
		newNode.getLeft().setParent(newNode);
		newNode.getRight().setParent(newNode);
		if (node.getParent() == null) {
			this.root = newNode;
		} else if (this.comparator.compare(node.getParent().getData(), element) > 0) {
			node.getParent().setLeft(newNode);
		} else if (this.comparator.compare(node.getParent().getData(), element) < 0) {
			node.getParent().setRight(newNode);
		}
	}
	
	@Override
	public BSTNode<T> sucessor(T element) {
		BSTNode<T> node = this.search(element);
		BSTNode<T> returnNode = null;

		if (!node.isEmpty()) {
			if (!node.getRight().isEmpty()) {
				returnNode = super.minimum((BSTNode<T>) node.getRight());
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
		} else if (this.comparator.compare(node.getParent().getData(), value) > 0) {
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
 				returnNode = super.maximum((BSTNode<T>) node.getLeft());
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
 		} else if (this.comparator.compare(node.getParent().getData(), value) < 0) {
 			returnNode = (BSTNode<T>) node.getParent();
 		} else {
 			returnNode = (BSTNode<T>) this.predecessorUp((BSTNode<T>) node.getParent(), value);
 		}
 		
 		return returnNode;
 	}
	
}
