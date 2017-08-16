package sorting.divideAndConquer;

import java.util.Arrays;

import sorting.AbstractSorting;

/**
 * Merge sort is based on the divide-and-conquer paradigm. The algorithm
 * consists of recursively dividing the unsorted list in the middle, sorting
 * each sublist, and then merging them into one single sorted list. Notice that
 * if the list has length == 1, it is already sorted.
 */
public class MergeSort<T extends Comparable<T>> extends AbstractSorting<T> {

	@Override
	public void sort(T[] array, int leftIndex, int rightIndex) {
		if (rightIndex > leftIndex && !(array.length == 0 || leftIndex < 0 || rightIndex >= array.length || array == null)) {
			int meio = (leftIndex + rightIndex) / 2;
			sort(array, leftIndex, meio);
			sort(array, meio+1, rightIndex);
			merge(array, leftIndex, meio, meio+1, rightIndex);
		}
	}
	
	private void merge(T[] array, int inicio1, int final1, int inicio2, int final2) {
		T[] copia = Arrays.copyOf(array, array.length);
		int indice = inicio1;
		
		while (inicio1 <= final1 && inicio2 <= final2) {
			if (copia[inicio1].compareTo(copia[inicio2]) < 0) {
				array[indice] = copia[inicio1];
				inicio1++;
			} else {
				array[indice] = copia[inicio2];
				inicio2++;
			}
			indice++;
		}
		
		if (final1 >= inicio1) {
			for (int i = inicio1; i <= final1; i++) {
				array[indice] = copia[i];
				indice++;
			}
		}
		
		if (final2 >= inicio2) {
			for (int i = inicio2; i <= final2; i++) {
				array[indice] = copia[i];
				indice++;
			}
		}
	}
}


