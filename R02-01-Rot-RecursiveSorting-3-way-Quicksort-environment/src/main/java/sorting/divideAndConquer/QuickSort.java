package sorting.divideAndConquer;

import sorting.AbstractSorting;
import util.Util;

/**
 * Quicksort is based on the divide-and-conquer paradigm. The algorithm chooses
 * a pivot element and rearranges the elements of the interval in such a way
 * that all elements lesser than the pivot go to the left part of the array and
 * all elements greater than the pivot, go to the right part of the array. Then
 * it recursively sorts the left and the right parts. Notice that if the list
 * has length == 1, it is already sorted.
 */
public class QuickSort<T extends Comparable<T>> extends AbstractSorting<T> {

	@Override
	public void sort(T[] array, int leftIndex, int rightIndex) {
		if (leftIndex < rightIndex && !(array.length == 0 || leftIndex < 0 || rightIndex >= array.length || array == null)) {
			int pivot = this.particiona(array, leftIndex, rightIndex);
 			sort(array, leftIndex, pivot-1);
			sort(array, pivot+1, rightIndex);
		}
	}
	
	private int particiona(T[] array, int leftIndex, int rightIndex) {
		int pivot = leftIndex;
		int divisao = leftIndex + 1;
		
		for (int i = leftIndex + 1; i <= rightIndex; i++) {
			if (array[i].compareTo(array[pivot]) <= 0) {
				Util.swap(array, divisao, i);
				divisao++;
			}
		}
		
		Util.swap(array, divisao-1, pivot);
		return divisao - 1;
	}
}

