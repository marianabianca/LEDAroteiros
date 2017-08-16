package adt.linkedList;

public class SingleLinkedListImpl<T> implements LinkedList<T> {

	protected SingleLinkedListNode<T> head;

	public SingleLinkedListImpl() {
		this.head = new SingleLinkedListNode<T>();
	}

	@Override
	public boolean isEmpty() {
		return this.head.isNIL();
	}

	@Override
	public int size() {
		int size = 0;
		SingleLinkedListNode<T> aux = this.head;
			
		while (!aux.isNIL()) {
			size++;
			aux = aux.getNext();
		}
		
		return size;		
	}

	@Override
	public T search(T element) {
		SingleLinkedListNode<T> aux = this.head;
		
		while (!(aux.isNIL() || aux.getData().equals(element))) {
			aux = aux.getNext();
		}
		
		return aux.getData();		
	}

	@Override
	public void insert(T element) {
		SingleLinkedListNode<T> newNode = new SingleLinkedListNode<>(element, new SingleLinkedListNode<>());
		
		if (this.isEmpty()) {
			this.head = newNode; 
		} else {
			SingleLinkedListNode<T> aux = this.head;
			
			while (!aux.getNext().isNIL()) {
				aux = aux.getNext();
			}
			
			aux.next = newNode;
		}
	}

	@Override
	public void remove(T element) {
		if (!this.isEmpty()) {
			if (this.head.getData().equals(element)) {
				this.head = this.head.getNext(); 
			} else {
				SingleLinkedListNode<T> aux = head;
				
				while (!aux.getNext().getData().equals(element)) {
					aux = aux.getNext();
				}
				
				aux.next = aux.getNext().getNext();
			}	
		}
	}

	@Override
	public T[] toArray() {
		@SuppressWarnings("unchecked")
		T[] array = (T[]) new Object[this.size()];
		int i = 0;
		
		SingleLinkedListNode<T> aux = this.head;
		
		while (!aux.isNIL()) {
			array[i] = aux.getData();
			i++;
			aux = aux.getNext();
		}
		
		return array;
	}

	public SingleLinkedListNode<T> getHead() {
		return head;
	}

	public void setHead(SingleLinkedListNode<T> head) {
		this.head = head;
	}

}
