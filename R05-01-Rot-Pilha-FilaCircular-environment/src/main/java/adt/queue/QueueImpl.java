package adt.queue;

public class QueueImpl<T> implements Queue<T> {

	private T[] array;
	private int tail;

	@SuppressWarnings("unchecked")
	public QueueImpl(int size) {
		array = (T[]) new Object[size];
		tail = -1;
	}

	@Override
	public T head() {
		T returnObj;
		if (this.isEmpty()) {
			returnObj = null;
		} else {
			returnObj =  array[0];
		}
		
		return returnObj;
	}

	@Override
	public boolean isEmpty() {
		return tail == -1;
	}

	@Override
	public boolean isFull() {
		return tail == array.length-1;
	}

	private void shiftLeft() {
		for (int i = 0; i < tail; i++) {
			T aux = array[i];
			array[i] = array[i+1];
			array[i+1] = aux;
		}
	}

	@Override
	public void enqueue(T element) throws QueueOverflowException {
		if (this.isFull()) {
			throw new QueueOverflowException();
		}
		
		tail++;
		array[tail] = element;
	}

	@Override
	public T dequeue() throws QueueUnderflowException {
		if (this.isEmpty()) {
			throw new QueueUnderflowException();
		}
		
		T element = array[0];
		this.shiftLeft();
		tail--;
		
		return element;
	}

}



