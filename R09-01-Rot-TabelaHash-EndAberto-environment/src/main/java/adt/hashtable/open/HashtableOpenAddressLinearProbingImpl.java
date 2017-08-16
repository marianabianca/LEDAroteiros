package adt.hashtable.open;

import adt.hashtable.hashfunction.HashFunction;
import adt.hashtable.hashfunction.HashFunctionClosedAddressMethod;
import adt.hashtable.hashfunction.HashFunctionLinearProbing;

public class HashtableOpenAddressLinearProbingImpl<T extends Storable> extends
		AbstractHashtableOpenAddress<T> {

	public HashtableOpenAddressLinearProbingImpl(int size,
			HashFunctionClosedAddressMethod method) {
		super(size);
		hashFunction = new HashFunctionLinearProbing<T>(size, method);
		this.initiateInternalTable(size);
	}

	@Override
	public void insert(T element) {
		if (super.isFull()) {
			throw new HashtableOverflowException();
		}
		
		if (element != null && this.search(element) == null) {
			int probe = 0;			
			int hashCode = this.getHash(element, probe);
			
			while (this.getElement(hashCode) != null && !this.getElement(hashCode).equals(deletedElement) && probe != super.table.length) {
				super.COLLISIONS++;
				probe++;
				hashCode = this.getHash(element, probe);
			}
			
			super.table[hashCode] = element;
			super.elements++;
		}
		
	}

	private int getHash(T element, int probe) {
		HashFunction<T> hashFuncion = super.getHashFunction();
		int hashCode = ((HashFunctionLinearProbing<T>) hashFuncion).hash(element, probe);
		return hashCode;		
	}

	@Override
	public void remove(T element) {
		if (element != null && this.search(element) != null) {
			int index = this.indexOf(element);
			this.takeCollisions(element);
			super.table[index] = new DELETED();
			super.elements--;
		}
	}

	private void takeCollisions(T element) {
		int probe = 0;			
		int hashCode = this.getHash(element, probe);
		
		while (this.getElement(hashCode) != null && !this.getElement(hashCode).equals(deletedElement) && probe != super.table.length) {
			super.COLLISIONS--;
			probe++;
			hashCode = this.getHash(element, probe);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public T search(T element) {
		T returnElement = null;
		int index = this.indexOf(element);
		
		if (element != null && index != -1) {
			returnElement = (T) this.getElement(index);
		}
		
		return returnElement;
	}

	@Override
	public int indexOf(T element) {
		int returnIndex = -1;
		
		if (element != null) {
			int probe = 0;			
			returnIndex = this.getHash(element, probe);
			
			while (this.getElement(returnIndex) != null && !this.getElement(returnIndex).equals(element) && probe != super.table.length) {
				probe++;
				returnIndex = this.getHash(element, probe);
			}
			
			if ((this.getElement(returnIndex) == null) || (this.getElement(returnIndex) != null && !this.getElement(returnIndex).equals(element))) {
				returnIndex = -1;
			}			
		} 
		
		return returnIndex;
	}
	
	@SuppressWarnings("unchecked")
	private T getElement(int index) {
		return (T) super.table[index];
	}

}
