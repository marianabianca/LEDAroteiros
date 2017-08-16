package adt.linkedList;

public class DoubleLinkedListImpl<T> extends SingleLinkedListImpl<T> implements
		DoubleLinkedList<T> {

	protected DoubleLinkedListNode<T> last;
	
	public DoubleLinkedListImpl() {
		super.head = new DoubleLinkedListNode<>();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void remove(T element) {
		if (!this.isEmpty()) {
			if (this.head.getData().equals(element)) {
				if (this.head.equals(this.last)) {
					this.head = new DoubleLinkedListNode();
					this.last = (DoubleLinkedListNode<T>) this.head;
				} else {
					((DoubleLinkedListNode<T>) this.head.getNext()).setPrevious(((DoubleLinkedListNode<T>) this.head).getPrevious());
					this.head = ((DoubleLinkedListNode<T>) this.head.getNext());
				}
			} else {
				DoubleLinkedListNode<T> aux = (DoubleLinkedListNode<T>) this.head;
				
				while (!aux.isNIL() && !aux.getData().equals(element)) {
					aux = (DoubleLinkedListNode<T>) aux.getNext();
				}
				
				if (aux.equals(this.last)) {
					this.last.getPrevious().setNext(new DoubleLinkedListNode<>());
					this.last = this.last.getPrevious();
				} else {
					((DoubleLinkedListNode<T>) aux.getNext()).setPrevious(aux.getPrevious());
					aux.getPrevious().setNext(aux.getNext());
				}
			}
		}
	}

	@Override
	public void insertFirst(T element) {
		SingleLinkedListNode<T> aux = new DoubleLinkedListNode<>(element, (DoubleLinkedListNode<T>) super.head, new DoubleLinkedListNode<>());
		super.head = aux;
	}
	
	@Override
	public void insert(T element) {
		if (super.isEmpty()) {
			super.head = new DoubleLinkedListNode<>(element, new DoubleLinkedListNode<>(), new DoubleLinkedListNode<>());
			this.last = (DoubleLinkedListNode<T>) super.head;
		} else if (super.size() == 1) {
			this.last = new DoubleLinkedListNode<>(element, new DoubleLinkedListNode<>(), (DoubleLinkedListNode<T>) super.head);
			super.head.setNext(this.last);
		} else {
			SingleLinkedListNode<T> aux = new DoubleLinkedListNode<>(element, new DoubleLinkedListNode<>(), this.last);
			this.last.setNext(aux);
			this.setLast((DoubleLinkedListNode<T>) this.last.getNext());
		}
	}

	@Override
	public void removeFirst() {
		super.head = super.head.getNext();
	}

	@Override
	public void removeLast() {
		if (super.size() == 1) {
			super.setHead(new DoubleLinkedListNode<>());
			this.setLast(new DoubleLinkedListNode<>());
		} else if (super.size() == 2) {
			super.head.setNext(new DoubleLinkedListNode<>());
			this.setLast((DoubleLinkedListNode<T>) super.head);
		} else {
			this.setLast(this.last.getPrevious());
			this.last.setNext(new DoubleLinkedListNode<>());
		}
	}

	public DoubleLinkedListNode<T> getLast() {
		return last;
	}

	public void setLast(DoubleLinkedListNode<T> last) {
		this.last = last;
	}

}
