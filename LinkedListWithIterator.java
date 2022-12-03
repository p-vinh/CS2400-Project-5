import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedListWithIterator<T> implements ListWithIteratorInterface<T> {

    private Node firstNode;
    private Node lastNode;
    private int numberOfEntries;

    public LinkedListWithIterator() {
        firstNode = null;
        numberOfEntries = 0;
    }

    public void add(int newPosition, T newEntry) {
        if ((newPosition >= 1) && (newPosition <= numberOfEntries + 1)) {
            Node newNode = new Node(newEntry);
            if (isEmpty()) {
                firstNode = newNode;
                lastNode = newNode;
            } else if (newPosition == 1) {
                newNode.next = firstNode;
                firstNode = newNode;
            } else if (newPosition == numberOfEntries + 1) {
                lastNode.next = newNode;
                lastNode = newNode;
            } else {
                Node nodeBefore = getNodeAt(newPosition - 1);
                Node nodeAfter = nodeBefore.next;
                newNode.next = nodeAfter;
                nodeBefore.next = newNode;
            } // end if
            numberOfEntries++;
        } else
            throw new IndexOutOfBoundsException("Illegal position given to add operation.");
    }

    public void add(T newEntry) {
        Node newNode = new Node(newEntry);

        if (isEmpty())
            firstNode = newNode;
        else
            lastNode.next = newNode;

        lastNode = newNode;
        numberOfEntries++;
    }

    public T remove(int givenPosition) {
        T result = null; // Return value
        if ((givenPosition >= 1) && (givenPosition <= numberOfEntries)) {
            // Assertion: !isEmpty()
            if (givenPosition == 1) // Case 1: Remove first entry
            {
                result = firstNode.data; // Save entry to be removed
                firstNode = firstNode.next;
                if (numberOfEntries == 1)
                    lastNode = null; // Solitary entry was removed
            } else // Case 2: Not first entry
            {
                Node nodeBefore = getNodeAt(givenPosition - 1);
                Node nodeToRemove = nodeBefore.next;
                Node nodeAfter = nodeToRemove.next;
                nodeBefore.next = nodeAfter;
                result = nodeToRemove.data;
                if (givenPosition == numberOfEntries)
                    lastNode = nodeBefore; // Last node was removed
            } // end if
            numberOfEntries--;
        } else
            throw new IndexOutOfBoundsException("Illegal position given to remove operation.");
        return result; // Return removed entry
    }

    public void clear() {
        firstNode = null;
        numberOfEntries = 0;
    }

    public T replace(int givenPosition, T newEntry) {
        if ((givenPosition >= 1) && (givenPosition <= numberOfEntries)) {
            // Assertion: !isEmpty()
            Node desiredNode = getNodeAt(givenPosition);
            T originalEntry = desiredNode.data;
            desiredNode.data = newEntry;
            return originalEntry;
        } else
            throw new IndexOutOfBoundsException("Illegal position given to replace operation.");
    }

    public T getEntry(int givenPosition) {
        if ((givenPosition >= 1) && (givenPosition <= numberOfEntries)) {
            // Assertion: !isEmpty()
            return getNodeAt(givenPosition).data;
        } else
            throw new IndexOutOfBoundsException("Illegal position given to getEntry operation.");
    }

    public T[] toArray() {
        @SuppressWarnings("unchecked")
        T[] result = (T[]) new Object[numberOfEntries];
        int index = 0;
        Node currentNode = firstNode;
        while ((index < numberOfEntries) && (currentNode != null)) {
            result[index] = currentNode.data;
            currentNode = currentNode.next;
            index++;
        }
        return result;
    }

    public boolean contains(T anEntry) {
        boolean found = false;
        Node currentNode = firstNode;
        while (!found && (currentNode != null)) {
            if (anEntry.equals(currentNode.data))
                found = true;
            else
                currentNode = currentNode.next;
        } // end while
        return found;
    }

    public int getLength() {
        return numberOfEntries;
    }

    public boolean isEmpty() {
        return numberOfEntries == 0;
    }

    public Iterator<T> iterator() {
        return new IteratorForLinkedList();
    }

    public Iterator<T> getIterator() {
        return iterator();
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

    private class IteratorForLinkedList implements Iterator<T> {

        private Node nextNode;

        private IteratorForLinkedList() {
            nextNode = firstNode;
        }

        public boolean hasNext() {
            return nextNode != null;
        }

        public T next() {
            T result;
            if (hasNext()) {
                result = nextNode.data;
                nextNode = nextNode.next; // Advance iterator
            } else
                throw new NoSuchElementException("Illegal call to next(); " +
                        "iterator is after end of list.");
            return result;
        }

        public void remove() {
            throw new UnsupportedOperationException("remove() is not supported by this iterator");
        }

    }

    // Returns a reference to the node at a given position.
    // Precondition: The chain is not empty;
    // 1 <= givenPosition <= numberOfEntries.
    private Node getNodeAt(int givenPosition) {
        // Assertion: (firstNode != null) &&
        // (1 <= givenPosition) && (givenPosition <= numberOfEntries)
        Node currentNode = firstNode;
        // Traverse the chain to locate the desired node
        // (skipped if givenPosition is 1)
        for (int counter = 1; counter < givenPosition; counter++)
            currentNode = currentNode.next;
        // Assertion: currentNode != null
        return currentNode;
    } // end getNodeAt

    private Node getReferenceTo(T anEntry) {
        boolean found = false;
        Node currentNode = firstNode;

        while (!found && (currentNode != null)) {
            if (anEntry.equals(currentNode.data))
                found = true;
            else
                currentNode = currentNode.next;
        }

        return currentNode;
    }

}
