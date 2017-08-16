package sorting.divideAndConquer.threeWayQuicksort;

import sorting.AbstractSorting;
import util.Util;

public class ThreeWayQuickSort<T extends Comparable<T>> extends
		AbstractSorting<T> {

	/**
	 * No algoritmo de quicksort, selecionamos um elemento como pivot,
	 * particionamos o array colocando os menores a esquerda do pivot 
	 * e os maiores a direita do pivot, e depois aplicamos a mesma estrategia 
	 * recursivamente na particao a esquerda do pivot e na particao a direita do pivot. 
	 * 
	 * Considerando um array com muitoe elementos repetidos, a estrategia do quicksort 
	 * pode ser otimizada para lidar de forma mais eficiente com isso. Essa melhoria 
	 * eh conhecida como quicksort tree way e consiste da seguinte ideia:
	 * - selecione o pivot e particione o array de forma que
	 *   * arr[l..i] contem elementos menores que o pivot
	 *   * arr[i+1..j-1] contem elementos iguais ao pivot.
	 *   * arr[j..r] contem elementos maiores do que o pivot. 
	 *   
	 * Obviamente, ao final do particionamento, existe necessidade apenas de ordenar
	 * as particoes contendo elementos menores e maiores do que o pivot. Isso eh feito
	 * recursivamente. 
	 **/
	@Override
	public void sort(T[] array, int leftIndex, int rightIndex) {
		if (leftIndex < rightIndex && !(array.length == 0 || leftIndex < 0 || rightIndex >= array.length || array == null)) {
			int[] posicoes = this.particiona(array, leftIndex, rightIndex);
 			sort(array, leftIndex, posicoes[0]-1);
			sort(array, posicoes[1], rightIndex);
		}
	}
	
	private int[] particiona(T[] array, int leftIndex, int rightIndex){
		int pivot = leftIndex;
		int divisao1 = leftIndex + 1;
		int divisao2 = leftIndex + 1;
		
		for (int j = leftIndex+1; j <= rightIndex; j++) {
			if (array[j].compareTo(array[pivot]) < 0) {
				Util.swap(array, j, divisao1);
				if (divisao2 > divisao1) {
					Util.swap(array, j, divisao2);
				}
				divisao1++;
				divisao2++;
			} else if (array[j].compareTo(array[pivot]) == 0) {
				Util.swap(array, j, divisao2);
				divisao2++;
			}
		}
		
		Util.swap(array, divisao1-1, pivot);
		
		int[] paredes = {divisao1, divisao2};
		return paredes;
	}

}
