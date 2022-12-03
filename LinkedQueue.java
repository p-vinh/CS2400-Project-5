import java.util.NoSuchElementException;

public class LinkedQueue<T> implements QueueInterface<T> {

    private Node firstNode;
    private Node lastNode;

    public LinkedQueue() {
        firstNode = null;
        lastNode = null;
    }

    public void enqueue(T newEntry) {
        Node newNode = new Node(newEntry);

        if (isEmpty())
            firstNode = newNode;
        else
            lastNode.next = newNode;
    }

    public T dequeue() {
        T front = getFront();
        firstNode.data = null;
        firstNode = firstNode.next;

        if (firstNode == null)
            lastNode = null;
        return front;

    }

    public T getFront() {
        if (isEmpty())
            throw new NoSuchElementException();
        else
            return firstNode.data;
    }

    public boolean isEmpty() {
        return (firstNode == null) && (lastNode == null);
    }

    public void clear() { // Will run out of memory. Not cleared properly
        firstNode = null;
        lastNode = null;
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
