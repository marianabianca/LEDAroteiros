package adt.btree;

import java.util.ArrayList;
import java.util.LinkedList;

public class BTreeImpl<T extends Comparable<T>> implements BTree<T> {

	protected BNode<T> root;
	protected int order;

	public BTreeImpl(int order) {
		this.order = order;
		this.root = new BNode<T>(order);
	}

	@Override
	public BNode<T> getRoot() {
		return this.root;
	}

	@Override
	public boolean isEmpty() {
		return this.root.isEmpty();
	}

	@Override
	public int height() {
		return height(this.root);
	}

	private int height(BNode<T> node) {
		int returnValue = -1;
		
		if (!node.isEmpty()){
			returnValue += 1;
		} 
		
		if (!node.isLeaf()) {
			BNode<T> child = node.getChildren().get(0);
			returnValue += 1 + this.height(child);
		}
		
		return returnValue;
	}

	@Override
	public BNode<T>[] depthLeftOrder() {
		ArrayList<BNode<T>> array = new ArrayList<>();
		
		this.depthLeftOrder(array, this.root);
		
		@SuppressWarnings("unchecked")
		BNode<T>[] returnArray = new BNode[array.size()];
		
		return array.toArray(returnArray);
	}

	private void depthLeftOrder(ArrayList<BNode<T>> array, BNode<T> node) {	
		if (node != null) {
			array.add(node);
			LinkedList<BNode<T>> children = node.getChildren();
			
			for (BNode<T> child : children) {
				this.depthLeftOrder(array, child);
			}
		}
	}

	@Override
	public int size() {
		return this.size(this.root);
	}
	
	private int size(BNode<T> node) {
		int returnValue = 0;
		
		LinkedList<T> elements = node.getElements();
		
		for (int i = 0; i < elements.size(); i++) {
			returnValue++;
		}
		
		LinkedList<BNode<T>> children = node.getChildren();
		
		for (BNode<T> child : children) {
			returnValue += this.size(child);
		}
		
		return returnValue;
	}

	@Override
	public BNodePosition<T> search(T element) {
		return this.search(element, this.root);
	}

	private BNodePosition<T> search(T element, BNode<T> node) {
		BNodePosition<T> returnPair = new BNodePosition<>();
		
		if (node != null) {
			LinkedList<T> elements = node.getElements();
			int i = 0;
			
			while (i < elements.size() && elements.get(i).compareTo(element) < 0) {
				i++;
			}
			
			if (i < elements.size() && elements.get(i).equals(element)) {
				returnPair.node = node;
				returnPair.position = i;
			} else {
				returnPair = this.search(element, node.getChildren().get(i));
			}
		}
		
		return returnPair;
	}

	@Override
	public void insert(T element) {
		if (element != null) {
			this.insert(this.root, element);
		}
	}

	private void insert(BNode<T> node, T element) {
		if (node.isLeaf()) {
			if (node.isFull()) {
				node.addElement(element);
				this.split(node);
			} else {
				node.addElement(element);
			}
		} else {
			LinkedList<T> elements = node.getElements();
			int i = 0;
			
			while (i < elements.size() && elements.get(i).compareTo(element) < 0) {
				i++;
			}
			
			this.insert(node.getChildren().get(i), element);
		}
	}

	private void split(BNode<T> node) {
		T aux = node.getElements().get((this.order/2));
		BNode<T> left = node.getMinors();
		left.setParent(node.getParent());
		BNode<T> right = node.getLargers();
		right.setParent(node.getParent());
		
		node = this.promote(node, aux, left, right);
		
		if (node.getElements().size() >= this.order) {
			this.split(node);
		}
	}

	private BNode<T> promote(BNode<T> node, T aux, BNode<T> left, BNode<T> right) {
		BNode<T> returnNode;
		
		if (node.equals(this.root)) {
			BNode<T> newRoot = new BNode<>(this.order);
			newRoot.addChild(0, left);
			newRoot.addChild(1, right);
			newRoot.addElement(aux);
			
			this.root = newRoot;
			returnNode = newRoot;
		} else {
			node.getParent().addElement(aux);
			BNodePosition<T> position = this.search(aux);
			
			LinkedList<BNode<T>> children = node.getParent().getChildren();
			
			children.set(position.position, left);
			children.add(position.position+1, right);
			
			returnNode = node.getParent();
		}
		
		return returnNode;
	}

	// NAO PRECISA IMPLEMENTAR OS METODOS ABAIXO
	@Override
	public BNode<T> maximum(BNode<T> node) {
		// NAO PRECISA IMPLEMENTAR
		throw new UnsupportedOperationException("Not Implemented yet!");
	}

	@Override
	public BNode<T> minimum(BNode<T> node) {
		// NAO PRECISA IMPLEMENTAR
		throw new UnsupportedOperationException("Not Implemented yet!");
	}

	@Override
	public void remove(T element) {
		// NAO PRECISA IMPLEMENTAR
		throw new UnsupportedOperationException("Not Implemented yet!");
	}

}
