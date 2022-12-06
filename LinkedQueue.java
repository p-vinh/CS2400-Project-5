// 
//  Name:		Pham, Vinh 
//  Project:	5
//  Due:		9 December 2022 
//  Course:		cs-2400-02-f22 
// 
//  Description: 
//      Implementation of queue using java's queue library and queue interface
//
// 

import java.util.Queue;
import java.util.LinkedList;
public class LinkedQueue<T> implements QueueInterface<T> {

    private Queue<T> queue;

    public LinkedQueue() {
        queue = new LinkedList<>();
    }

    public void enqueue(T newEntry) {
        queue.add(newEntry);
    }

    public T dequeue() {
        return queue.remove();
    }

    public T getFront() {
        return queue.peek();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public void clear() {
        queue.clear();
    }
}
