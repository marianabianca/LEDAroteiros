package adt.queue;

public class CircularQueue<T> implements Queue<T> {

	private T[] array;
	private int tail;
	private int head;
	private int elements;

	@SuppressWarnings("unchecked")
	public CircularQueue(int size) {
		array = (T[]) new Object[size];
		head = -1;
		tail = -1;
		elements = 0;
	}

	@Override
	public void enqueue(T element) throws QueueOverflowException {
		if (this.isFull()){
			throw new QueueOverflowException();
		} else if (this.isEmpty()) {
			head++;
			tail++;
		} else {
			tail = (head + elements) % array.length;
		}
		
		array[tail] = element;
		elements++;
	}

	@Override
	public T dequeue() throws QueueUnderflowException {
		if (this.isEmpty()) {
			throw new QueueUnderflowException();
		}
		
		T element = array[head];
		
		if (head == tail) {
			tail = -1;
			head = -1;
		} else {
			head = (head + 1) % array.length;
		}
		
		elements--;
		return element;
	}

	@Override
	public T head() {
		T returnObj;
		
		if (this.isEmpty()) {
			returnObj = null;
		} else {
			returnObj = array[head];
		}
		
		return returnObj;
	}

	@Override
	public boolean isEmpty() {
		return head == -1;
	}

	@Override
	public boolean isFull() {
		return array.length == elements;
	}

}
