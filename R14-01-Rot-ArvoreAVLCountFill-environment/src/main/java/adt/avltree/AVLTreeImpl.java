package adt.avltree;

import adt.bst.BSTImpl;
import adt.bst.BSTNode;
import adt.bt.Util;

/**
 * 
 * Performs consistency validations within a AVL Tree instance
 * 
 * @author Claudio Campelo
 *
 * @param <T>
 */
public class AVLTreeImpl<T extends Comparable<T>> extends BSTImpl<T> implements
		AVLTree<T> {

	// TODO Do not forget: you must override the methods insert and remove
	// conveniently.

	// AUXILIARY
	protected int calculateBalance(BSTNode<T> node) {
		int leftHeight = super.height((BSTNode<T>) node.getLeft());
		int rightHeight = super.height((BSTNode<T>) node.getRight());
		
		int balance = leftHeight - rightHeight;
		
		return balance;
	}

	// AUXILIARY
	protected void rebalance(BSTNode<T> node) {
		int balance = this.calculateBalance(node);
		
		if (balance > 1) {
			this.rebalanceLeft(node);
		} else if (balance < -1) {
			this.rebalanceRight(node);
		}
	}

	private void rebalanceLeft(BSTNode<T> node) {
		int balanceLeft = this.calculateBalance((BSTNode<T>) node.getLeft());
		
		if (balanceLeft > 0) {
			Util.rightRotation(node);
		} else if (balanceLeft < 0) {
			Util.leftRotation((BSTNode<T>) node.getLeft());
			Util.rightRotation(node);
		}
		
		if (this.getRoot().equals(node)) {
			this.root = (BSTNode<T>) node.getParent();
		}
	}
	
	private void rebalanceRight(BSTNode<T> node) {
		int balanceRight = this.calculateBalance((BSTNode<T>) node.getRight());
		
		if (balanceRight < 0) {
			Util.leftRotation(node);
		} else if (balanceRight > 0) {
			Util.rightRotation((BSTNode<T>) node.getRight());
			Util.leftRotation(node);
		}
		
		if (this.getRoot().equals(node)) {
			this.root = (BSTNode<T>) node.getParent();
		}
	}

	// AUXILIARY
	protected void rebalanceUp(BSTNode<T> node) {
		if (node != null) {
			this.rebalance(node);
			this.rebalanceUp((BSTNode<T>) node.getParent());
		}
	}
	
	@Override
	public void insert(T element) {
		if (element != null) {
			this.insert(this.getRoot(), element);
		} 
	}

	private void insert(BSTNode<T> node, T element) {
		if (node.isEmpty()) {
			super.addNewNode(node, element);
		} else if (node.getData().compareTo(element) > 0) {
			this.insert((BSTNode<T>) node.getLeft(), element);
			this.rebalance(node);
		} else if (node.getData().compareTo(element) < 0) {
			this.insert((BSTNode<T>) node.getRight(), element);
			this.rebalance(node);
		}
	}
	
	@Override
	public void remove(T element) {
 		BSTNode<T> node = this.search(element);
 			
 		if (!node.isEmpty()) {
 			if (node.isLeaf()) {
 				node.setData(null);
 				this.rebalanceUp(node);
 			} else if (!node.getLeft().isEmpty() && !node.getRight().isEmpty()) {
 				T predNode = this.sucessor(node.getData()).getData();
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
 		this.rebalanceUp(node);
 	}
}
