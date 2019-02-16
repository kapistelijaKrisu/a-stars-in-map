package model.structure;

/**
 * First-in-forst-out queue
 * Classis queue implementation. Only head and tail is known and nodes are connected by reference.
 * @param <T> Type of object to contain
 */
public class FIFOQueue<T> {

    private static class Node<T> {
        private final T value;
        private Node<T> before;
        private Node<T> after;


        private Node(T value) {
            this.value = value;
            this.before = null;
            this.after = null;

        }

        private T getValue() {
            return value;
        }

        private void setAfter(Node<T> next) {
            this.before = next;
        }

        private Node<T> getAfter() {
            return before;
        }
    }

    private Node<T> first;
    private Node<T> last;

    /**
     * Creates an empty queue where head and tail are null.
     */
    public FIFOQueue() {
        first = null;
        last = null;
    }

    /**
     *
     * @param value object to enqueue to the tail of the queue
     */
    public void enqueue(T value) {
        Node<T> oldlast = last;
        last = new Node<>(value);
        if (isEmpty()) first = last;
        else oldlast.setAfter(last);
    }

    /**
     * Deletes head (oldest) object
     * @return deleted head (oldest object in list)
     */
    public T dequeue() {
        if (isEmpty()) return null;
        T next = first.getValue();

        first = first.getAfter();

        if (isEmpty()) last = null;
        return next;
    }

    public boolean isEmpty() {
        return first == null;
    }
}
