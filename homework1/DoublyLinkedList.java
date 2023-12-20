import java.util.NoSuchElementException;

/**
 * Your implementation of a non-circular DoublyLinkedList with a tail pointer.
 *
 * @author Nakul Kuttua
 * @version 1.0
 * @userid nkuttua3
 * @GTID 903520821
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class DoublyLinkedList<T> {

    // Do not add new instance variables or modify existing ones.
    private DoublyLinkedListNode<T> head;
    private DoublyLinkedListNode<T> tail;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the element to the specified index. Don't forget to consider whether
     * traversing the list from the head or tail is more efficient!
     *
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index at which to add the new element
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        } else if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index should be btwn 0-size");
        } else if (size == 0) {
            DoublyLinkedListNode node =
                    new DoublyLinkedListNode(data, null, null);
            head = node;
            tail = node;
            size++;
        } else if (index == 0) {
            addToFront(data);
        } else if (index == size) {
            addToBack(data);
        } else if (index < (size / 2)) {
            DoublyLinkedListNode nodeIterate = head;
            DoublyLinkedListNode node =
                    new DoublyLinkedListNode(data, null, null);
            int iterate = 0;
            while (iterate != index) {
                nodeIterate = nodeIterate.getNext();
                iterate++;
            }
            node.setNext(nodeIterate);
            node.setPrevious(nodeIterate.getPrevious());
            nodeIterate.getPrevious().setNext(node);
            nodeIterate.setPrevious(node);
            size++;
        } else {
            DoublyLinkedListNode nodeIterate = tail;
            DoublyLinkedListNode node =
                    new DoublyLinkedListNode(data, null, null);
            int iterate = size - 1;
            while (iterate != index) {
                nodeIterate = nodeIterate.getPrevious();
                iterate--;
            }
            node.setNext(nodeIterate);
            node.setPrevious(nodeIterate.getPrevious());
            nodeIterate.getPrevious().setNext(node);
            nodeIterate.setPrevious(node);
            size++;
        }
    }

    /**
     * Adds the element to the front of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    //Done
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        DoublyLinkedListNode node =
                new DoublyLinkedListNode(data, null, null);
        if (head == null || size == 0) {
            head = node;
            tail = node;
            node.setPrevious(null);
            node.setNext(null);
            size++;
        } else {
            node.setNext(head);
            head.setPrevious(node);
            head = node;
            size++;
        }
    }

    /**
     * Adds the element to the back of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    //Done
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        DoublyLinkedListNode node =
                new DoublyLinkedListNode(data, null, null);
        if (head == null || size == 0) {
            head = node;
            tail = node;
            node.setNext(null);
            node.setPrevious(null);
            size++;
        } else {
            node.setNext(null);
            node.setPrevious(tail);
            tail.setNext(node);
            tail = node;
            size++;
        }
    }

    /**
     * Removes and returns the element at the specified index. Don't forget to
     * consider whether traversing the list from the head or tail is more
     * efficient!
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Incorrect index");
        } else if (index == 0) {
            removeFromFront();
        } else if (index == size - 1) {
            removeFromBack();
        } else if (index < (size / 2)) {
            int temp = 0;
            DoublyLinkedListNode node = head;
            while (temp != index) {
                node = node.getNext();
                temp++;
            }
            node.getPrevious().setNext(node.getNext());
            node.getNext().setPrevious(node.getPrevious());
            size--;
            return (T) node.getData();
        } else {
            int temp = size - 1;
            DoublyLinkedListNode node = head;
            while (temp >= (size / 2)) {
                if (temp == index) {
                    DoublyLinkedListNode temporary2 = node;
                    node.getPrevious().setNext(node.getNext());
                    node.getNext().setPrevious(node.getPrevious());
                    size--;
                    return (T) temporary2;
                }
                temp--;
                node.setPrevious(node.getPrevious());
            }
        }
        return null;
    }

    /**
     * Removes and returns the first element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    //Done
    public T removeFromFront() {
        if (head == null) {
            throw new NoSuchElementException("List cannot be empty");
        }
        DoublyLinkedListNode node = head;
        head = head.getNext();
        head.setPrevious(null);
        size--;
        return (T) node.getData();
    }

    /**
     * Removes and returns the last element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    //Done
    public T removeFromBack() {
        if (head == null) {
            throw new NoSuchElementException("List cannot be empty");
        }
        DoublyLinkedListNode node = tail;
        tail = tail.getPrevious();
        tail.setNext(null);
        size--;
        return (T) node.getData();
    }

    /**
     * Returns the element at the specified index. Don't forget to consider
     * whether traversing the list from the head or tail is more efficient!
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    //Done
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index must be between 0 and size");
        } else if (index == 0) {
            return head.getData();
        } else if (index == size - 1) {
            return tail.getData();
        } else if (index < (size / 2)) {
            int temp = 0;
            DoublyLinkedListNode node = head;
            while (temp != index) {
                node = node.getNext();
                temp++;
            }
            return (T) node.getData();
        } else {
            int temp = size - 1;
            DoublyLinkedListNode node = tail;
            while (temp != index) {
                node = node.getPrevious();
                temp--;
            }
            return (T) node.getData();
        }
    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    //Done
    public boolean isEmpty() {
        return head == null;
    }

    /**
     * Clears the list.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    //Done
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     *
     * Must be O(1) if data is in the tail and O(n) for all other cases.
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        } else if (data == head.getData()) {
            return removeFromFront();
        } else if (data == tail.getData()) {
            DoublyLinkedListNode node = tail;
            tail = null;
            size--;
            return (T) node.getData();
        } else if (data != tail.getData()) {
            DoublyLinkedListNode iterate = tail;
            while (iterate.getData() != data) {
                iterate = iterate.getPrevious();
            }
            iterate.getPrevious().setNext(iterate.getNext());
            iterate.getNext().setPrevious(iterate.getPrevious());
            size--;
            return (T) iterate.getData();
        } else {
            throw new NoSuchElementException("Data cannot be found");
        }
    }

    /**
     * Returns an array representation of the linked list. If the list is
     * size 0, return an empty array.
     *
     * Must be O(n) for all cases.
     *
     * @return an array of length size holding all of the objects in the
     * list in the same order
     */
    //Done
    public Object[] toArray() {
        if (size == 0) {
            T[] arr = (T[]) new Object[size];
            return arr;
        } else {
            DoublyLinkedListNode node = head;
            int temp = 0;
            T[] arr = (T[]) new Object[size];
            for (int i = 0; i < size; i++) {
                arr[i] = (T) node.getData();
                node = node.getNext();
            }
            return arr;
        }
    }

    /**
     * Returns the head node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public DoublyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the tail node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the tail of the list
     */
    public DoublyLinkedListNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
    }

    /**
     * Returns the size of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }
}
