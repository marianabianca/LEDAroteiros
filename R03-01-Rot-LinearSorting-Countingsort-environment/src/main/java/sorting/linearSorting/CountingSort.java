package sorting.linearSorting;

import java.util.Arrays;

import sorting.AbstractSorting;

/**
 * Classe que implementa a estratégia de Counting Sort vista em sala. Procure
 * evitar desperdicio de memoria alocando o array de contadores com o tamanho
 * sendo o máximo inteiro presente no array a ser ordenado.
 * 
 */
public class CountingSort extends AbstractSorting<Integer> {

	@Override
	public void sort(Integer[] array, int leftIndex, int rightIndex) {
		if (!(array == null || leftIndex > rightIndex || leftIndex < 0 || rightIndex > array.length)) {
			int maior = maiorDoArray(array, leftIndex, rightIndex);
			
			Integer[] contador = this.conta(array, maior, leftIndex, rightIndex);
			Integer[] acumulador = this.acumula(contador);
			
			this.ordena(array, leftIndex, rightIndex, acumulador);
		}
	}
	
	/**
	 * Ordenador do array
	 * 
	 * @param array array a ser ordenado
	 * @param leftIndex o indice do inicio da secção do array a ser ordenado
	 * @param rightIndex o indice do termino da secção do array a ser ordenado
	 * @param acumulador array acumulador
	 */
	private void ordena(Integer[] array, int leftIndex, int rightIndex, Integer[] acumulador) {
		Integer[] copiaArray = Arrays.copyOf(array, array.length);
		
		for (int i = leftIndex; i <= rightIndex; i++) {
			int pos = acumulador[copiaArray[i]] - 1;
			array[pos] = copiaArray[i];
			acumulador[copiaArray[i]]--;
		}
	}
	
	/**
	 * Calcula qual o menor numero do array
	 * 
	 * @param array array a ser utilizado
	 * @param leftIndex indice de inicio da analise do array
	 * @param rightIndex indice de termino da analise do array
	 * @return o menor numero do array (inteiro)
	 */
	private int maiorDoArray(Integer[] array, int leftIndex, int rightIndex) {
		int maior = array[leftIndex];
		
		for (int i = leftIndex; i <= rightIndex; i++) {
			if (array[i] > maior) {
				maior = array[i];
			}
		}
		
		return maior;
	}
	
	/**
	 * Contador de quantos numeros de cada ha no array
	 * 
	 * @param array array a ser utilizado na contagem
	 * @param menor o menor numero do array a ser utilizado entre o intervalo do leftIndex (inclusive)
	 * e do rightIndex (inclusive)
	 * @param maior o maior numero do array a ser utilizado entre o intervalo do leftIndex (inclusive)
	 * e do rightIndex (inclusive)
	 * @param leftIndex indice de inicio da analise do array
	 * @param rightIndex indice de termino da analise do array
	 * @return o array contador
	 */
	private Integer[] conta(Integer[] array, int maior, int leftIndex, int rightIndex) {
		int tamanhoContador = maior + 1;
		Integer[] contador = new Integer[tamanhoContador];
		
		for (int i = 0; i < contador.length; i++) {
			contador[i] = 0;
		}
		
		for (int i = leftIndex; i <= rightIndex; i++) {
			contador[array[i]] += 1;
		}
		
		return contador;
	}

	/**
	 * Acumulador que utiliza o array contador como base
	 * 
	 * @param array array contador a ser utilizado como base
	 * @return o array acumulador
	 */
	private Integer[] acumula(Integer[] array) {
		Integer[] acumulador = Arrays.copyOf(array, array.length);
		
		for (int i = 1; i < acumulador.length; i++) {
			acumulador[i] += acumulador[i-1];
		}
		
		return acumulador;
	}
	
}
