package adt.linkedList;

public class RecursiveDoubleLinkedListImpl<T> extends
		RecursiveSingleLinkedListImpl<T> implements DoubleLinkedList<T> {

	protected RecursiveDoubleLinkedListImpl<T> previous;

	public RecursiveDoubleLinkedListImpl() {

	}

	public RecursiveDoubleLinkedListImpl(T data,
			RecursiveSingleLinkedListImpl<T> next,
			RecursiveDoubleLinkedListImpl<T> previous) {
		super(data, next);
		this.previous = previous;
	}
	
	@Override
	public void insert(T element) {
		if (super.size() == 0) {
			super.setData(element);
			super.setNext(new RecursiveDoubleLinkedListImpl<>());
			this.setPrevious(new RecursiveDoubleLinkedListImpl<>());
		} else if (this.getNext().getData() == null) {
			this.setNext(new RecursiveDoubleLinkedListImpl<>(element, new RecursiveDoubleLinkedListImpl<>(), this));
		} else {
			this.getNext().insert(element);
		}
	}
	
	@Override
	public void remove(T element) {
		if (super.getData().equals(element)) {
			((RecursiveDoubleLinkedListImpl<T>) super.getNext()).setPrevious(this.getPrevious());
			this.getPrevious().setNext(this.getNext());
		} else {
			this.getNext().remove(element);
		}
	}

	@Override
	public void insertFirst(T element) {
		RecursiveDoubleLinkedListImpl<T> aux = new RecursiveDoubleLinkedListImpl<>(super.getData(), super.getNext(), this);
		super.setData(element);
		super.setNext(aux);
	}

	@Override
	public void removeFirst() {
		this.setData(this.getNext().getData());
		this.setNext(this.getNext().getNext());
	}

	@Override
	public void removeLast() {
		if (super.getNext().getNext().getData() == null) {
			super.setNext(new RecursiveDoubleLinkedListImpl<>());
		} else {
			((RecursiveDoubleLinkedListImpl<T>) super.getNext()).removeLast();
		}
	}

	public RecursiveDoubleLinkedListImpl<T> getPrevious() {
		return previous;
	}

	public void setPrevious(RecursiveDoubleLinkedListImpl<T> previous) {
		this.previous = previous;
	}

}
