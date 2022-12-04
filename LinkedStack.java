// 
//  Name:		Pham, Vinh 
//  Project:	5
//  Due:		9 December 2022 
//  Course:		cs-2400-02-f22 
// 
//  Description: 
//    Implementation of the Interface Stack using a
//    single linked list.
// 

import java.util.EmptyStackException;

public class LinkedStack<T> implements StackInterface<T> {

	private Node topNode;

	public LinkedStack() {
		topNode = null;
	}

	public void push(T newEntry) {
		topNode = new Node(newEntry, topNode);
	}

	public T pop() {
		if (isEmpty())
			throw new EmptyStackException();

		T top = peek();
		topNode = topNode.next;
		return top;
	}

	public T peek() {
		if (isEmpty())
			throw new EmptyStackException();
		return topNode.data;
	}

	public boolean isEmpty() {
		return topNode == null;
	}

	public void clear() { // change everything to null
		while (!isEmpty()) {
			topNode = null;
			topNode = topNode.next;
		}
	}

	private class Node {
		private T data;
		private Node next;

		private Node(T dataPortion) {
			this(dataPortion, null);
		}

		private Node(T dataPortion, Node nextNode) {
			data = dataPortion;
			next = nextNode;
		}
	}
}
