// 
//  Name:		Pham, Vinh 
//  Project:	5
//  Due:		9 December 2022 
//  Course:		cs-2400-02-f22 
// 
//  Description: 
//      Implementation of a linked list iwth an iterator.
//      Uses java's iterator and linked list libraries.
//
// 

import java.util.Iterator;
import java.util.LinkedList;
public class LinkedListWithIterator<T> implements ListWithIteratorInterface<T> {

    private LinkedList<T> list;

    public LinkedListWithIterator() {
        list = new LinkedList<>();
    }

    public void add(int newPosition, T newEntry) {
        list.add(newPosition, newEntry);
    }

    public void add(T newEntry) {
        list.add(newEntry);
    }

    public T remove(int givenPosition) {
        return list.remove(givenPosition); // Return removed entry
    }

    public void clear() {
        list.clear();
    }

    public T replace(int givenPosition, T newEntry) {
        return list.set(givenPosition, newEntry);
    }

    public T getEntry(int givenPosition) {
        return list.get(givenPosition);
    }

    public T[] toArray() {
        @SuppressWarnings("unchecked")
        T[] temp = (T[])list.toArray();
        return temp;
    }

    public boolean contains(T anEntry) {
        return list.contains(anEntry);
    }

    public int getLength() {
        return list.size();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public Iterator<T> iterator() {
        return list.iterator();
    }

    public Iterator<T> getIterator() {
        return iterator();
    }
}
