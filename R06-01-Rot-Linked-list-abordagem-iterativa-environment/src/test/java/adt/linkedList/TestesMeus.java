package adt.linkedList;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TestesMeus {
	
	DoubleLinkedListImpl<Integer> oi;
	
	@Before
	public void setUp() {
		oi = new DoubleLinkedListImpl<>();
	}

	@Test
	public void test() {
		oi.insert(36);
		oi.insert(40);
		oi.insert(50);
		assertEquals(3, oi.size());
		oi.remove(50);
		assertEquals(2, oi.size());
	}

}
