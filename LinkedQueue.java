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
        return queue.poll();
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
