package adt.heap;

import java.util.Arrays;
import java.util.Comparator;

import util.Util;

/**
 * O comportamento de qualquer heap é definido pelo heapify. Neste caso o
 * heapify dessa heap deve comparar os elementos e colocar o maior sempre no
 * topo. Ou seja, admitindo um comparador normal (responde corretamente 3 > 2),
 * essa heap deixa os elementos maiores no topo. Essa comparação não é feita 
 * diretamente com os elementos armazenados, mas sim usando um comparator. 
 * Dessa forma, dependendo do comparator, a heap pode funcionar como uma max-heap 
 * ou min-heap.
 */
public class HeapImpl<T extends Comparable<T>> implements Heap<T> {

	protected T[] heap;
	protected int index = -1;
	/**
	 * O comparador é utilizado para fazer as comparações da heap. O ideal é
	 * mudar apenas o comparator e mandar reordenar a heap usando esse
	 * comparator. Assim os metodos da heap não precisam saber se vai funcionar
	 * como max-heap ou min-heap.
	 */
	protected Comparator<T> comparator;

	private static final int INITIAL_SIZE = 20;
	private static final int INCREASING_FACTOR = 10;

	/**
	 * Construtor da classe. Note que de inicio a heap funciona como uma
	 * min-heap.
	 */
	@SuppressWarnings("unchecked")
	public HeapImpl(Comparator<T> comparator) {
		this.heap = (T[]) (new Comparable[INITIAL_SIZE]);
		this.comparator = comparator;
	}

	// /////////////////// METODOS IMPLEMENTADOS
	private int parent(int i) {
		return (i - 1) / 2;
	}

	/**
	 * Deve retornar o indice que representa o filho a esquerda do elemento
	 * indexado pela posicao i no vetor
	 */
	private int left(int i) {
		return (i * 2 + 1);
	}

	/**
	 * Deve retornar o indice que representa o filho a direita do elemento
	 * indexado pela posicao i no vetor
	 */
	private int right(int i) {
		return (i * 2 + 1) + 1;
	}

	@Override
	public boolean isEmpty() {
		return this.index == -1;
	}

	@Override
	public T[] toArray() {
		@SuppressWarnings("unchecked")
		T[] resp = Util.makeArrayOfComparable(index + 1);
		for (int i = 0; i <= index; i++) {
			resp[i] = this.heap[i];
		}
		return resp;
	}

	// ///////////// METODOS A IMPLEMENTAR
	/**
	 * Valida o invariante de uma heap a partir de determinada posicao, que pode
	 * ser a raiz da heap ou de uma sub-heap. O heapify deve colocar os maiores
	 * (comparados usando o comparator) elementos na parte de cima da heap.
	 */
	private void heapify(int position) {
		T parent = this.heap[position];
		int indexLeft = this.left(position);
		int indexRight = this.right(position);
		
		if (indexLeft < this.size() && indexRight < this.size()) {
			this.heapifyThreeElements(position, parent, indexLeft, indexRight);
		} else if (indexLeft < this.size()) {
			this.heapifyTwoElements(position, parent, indexLeft);
		}
	}

	private void heapifyTwoElements(int position, T parent, int indexLeft) {
		T left = this.heap[indexLeft];
		
		if (this.comparator.compare(left, parent) > 0) {
			Util.swap(heap, indexLeft, position);
			this.heapify(indexLeft);
		}
	}

	private void heapifyThreeElements(int position, T parent, int indexLeft, int indexRight) {
		T left = this.heap[indexLeft];
		T right = this.heap[indexRight];
		
		if (this.comparator.compare(left, parent) > 0 && this.comparator.compare(left, right) >= 0) {
			Util.swap(heap, indexLeft, position);
			this.heapify(indexLeft);
		} else if (this.comparator.compare(right, parent) > 0 && this.comparator.compare(right, left) >= 0) {
			Util.swap(heap, indexRight, position);
			this.heapify(indexRight);
		}
	}

	@Override
	public void insert(T element) {
		// ESSE CODIGO E PARA A HEAP CRESCER SE FOR PRECISO. NAO MODIFIQUE
		if (index == heap.length - 1) {
			heap = Arrays.copyOf(heap, heap.length + INCREASING_FACTOR);
		}
		
		if (element != null){
			this.index++;
			this.heap[this.index] = element;
			
			for (int i = this.index; i >= 0; i--) {
				this.heapify(i);
			}
		}
	}

	@Override
	public void buildHeap(T[] array) {
		this.heap = array;
		this.index = array.length - 1;
		
		for (int i = array.length - 1; i >= 0; i--) {
			this.heapify(i);
		}
	}

	@Override
	public T extractRootElement() {
		T element = null;
				
		if (this.index >= 0) {
			element = this.heap[0];
			
			Util.swap(this.heap, 0, this.index);
			
			this.heap[this.index] = null;
			this.index--;
			
			this.heapify(0);
		}
		
		return element;
	}

	@Override
	public T rootElement() {
		T element = null;
		
		if (this.index >= 0) {
			element = this.heap[0];
		}
		
		return element;
	}

	@Override
	public T[] heapsort(T[] array) {
		Comparator<T> actualComparator = this.changeComparator();
		
		this.buildHeap(array);
		
		T[] returnArray = Util.makeArrayOfComparable(array.length);
		toArraySort(array, returnArray);
		
		this.setComparator(actualComparator);
		
		return returnArray;
	}

	private void toArraySort(T[] array, T[] returnArray) {
		for (int i = 0; i < array.length; i++) {
			T element = this.extractRootElement();
			returnArray[i] = element;
		}
	}

	private Comparator<T> changeComparator() {
		Comparator<T> actualComparator = this.getComparator();
		Comparator<T> minComparator = (o1, o2) -> o2.compareTo(o1);
		this.comparator = minComparator;
		
		return actualComparator;
	}

	@Override
	public int size() {
		return this.index + 1;
	}

	public Comparator<T> getComparator() {
		return comparator;
	}

	public void setComparator(Comparator<T> comparator) {
		this.comparator = comparator;
	}

	public T[] getHeap() {
		return heap;
	}

}
