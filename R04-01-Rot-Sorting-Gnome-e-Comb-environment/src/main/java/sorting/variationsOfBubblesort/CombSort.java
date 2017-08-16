package sorting.variationsOfBubblesort;

import sorting.AbstractSorting;
import util.Util;

/**
 * The combsort algoritm.
 */
public class CombSort<T extends Comparable<T>> extends AbstractSorting<T> {
	@Override
	public void sort(T[] array, int leftIndex, int rightIndex) {
		if (!(array.length == 0 || leftIndex < 0 || rightIndex >= array.length || array == null || leftIndex > rightIndex)) {
			int size = rightIndex - leftIndex + 1;
			double factor = 1.25;
			int gap = (int) (size / factor);
			int i = 0;
			
			while ((gap / factor) > 0) {
				if ((i + gap) > rightIndex) {
					gap = (int) (gap / factor);
					i = 0;
				} else if (array[i].compareTo(array[i+gap]) > 0) {
					Util.swap(array, i, i+gap);
					i++;
				} else {
					i++;
				}
			}			
		}
		
	}
}
