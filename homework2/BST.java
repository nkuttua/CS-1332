import java.lang.reflect.Array;
import java.util.*;

/**
 * Your implementation of a BST.
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
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        //Exception checker to see if data is null
        if (data == null) {
            throw new IllegalArgumentException("The data is null");
        }
        for (T iterate : data) {
            //Exception checker to see if data is null
            if (iterate == null) {
                throw new IllegalArgumentException("The data is null");
            }
            add(iterate);
        }
    }

    /**
     * Adds the data to the tree.
     *
     * This must be done recursively.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        //Exception checker to see if data is null
        if (data == null) {
            throw new IllegalArgumentException("The data is null");
        }
        root = addHelperMethod(root, data);
    }

    /**
     * Custom Helper Method to add a node
     * @param node the node to add
     * @param data the data of the node to add
     * @return the node to be added
     */
    private BSTNode<T> addHelperMethod(BSTNode<T> node, T data) {
        if (node == null) {
            size++;
            return new BSTNode<>(data);
        } else if (data.compareTo(node.getData()) > 0) {
            node.setRight(addHelperMethod(node.getRight(), data));
        } else if (data.compareTo(node.getData()) < 0) {
            node.setLeft(addHelperMethod(node.getLeft(), data));
        }
        return node;
    }

    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the predecessor to
     * replace the data. You MUST use recursion to find and remove the
     * predecessor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        //Exception checker to see if data is null
        if (data == null) {
            throw new IllegalArgumentException("The data is null");
        }
        BSTNode<T> temp = new BSTNode<>(null);
        root = remove(root, data, temp);
        //Exception checker to see if node exists
        if (temp.getData() == null) {
            throw new NoSuchElementException("The data does not exist");
        }
        return temp.getData();
    }

    /**
     * Private helper method to remove a node
     * @param node the node to remove
     * @param data the data of the node to remove also
     * @param temp a temp node for recursive purposes
     * @return the node that gets removed
     */
    private BSTNode<T> remove(BSTNode<T> node, T data, BSTNode<T> temp) {
        if (node == null) {
            return null;
        } else if (data.compareTo(node.getData()) > 0) {
            node.setRight(remove(node.getRight(), data, temp));
        } else if (data.compareTo(node.getData()) < 0) {
            node.setLeft(remove(node.getLeft(), data, temp));
        } else {
            temp.setData(node.getData());
            size--;
            if (node.getLeft() == null && node.getRight() == null) {
                return null;
            } else if (node.getLeft() != null && node.getRight() != null) {
                BSTNode<T> temp2 = new BSTNode<>(null);
                node.setRight(removeSuccessor(node.getRight(), temp2));
                node.setData(temp2.getData());
            } else if (node.getLeft() != null) {
                return node.getLeft();
            } else if (node.getRight() != null) {
                return node.getRight();
            }
        }
        return node;
    }

    /**
     * Helper method for recursively removing a successor node.
     *
     * @param node a node to remove (the successor in this case)
     * @param temp a temp node for recursive purposes
     * @return the successor node
     */
    private BSTNode<T> removeSuccessor(BSTNode<T> node, BSTNode<T> temp) {
        if (node.getLeft() == null) {
            temp.setData(node.getData());
            return node.getRight();
        }
        node.setLeft(removeSuccessor(node.getLeft(), temp));
        return removeSuccessor(node.getLeft(), temp);
    }

    /**
     * Returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        //Exception checker to see if data is null
        if (data == null) {
            throw new IllegalArgumentException("The data is null");
        }
        //Exception checker to see if node exists
        if (getHelperMethod(root, data) == null) {
            throw new NoSuchElementException("The data could not be found");
        }
        return getHelperMethod(root, data);
    }

    /**
     * Private helper method for the get method
     * @param node node to get
     * @param data data of the node to get
     */
    private T getHelperMethod(BSTNode<T> node, T data) {
        if (node == null) {
            return null;
        } else if (data.compareTo(node.getData()) == 0) {
            return node.getData();
        } else if (data.compareTo(node.getData()) > 0) {
            return getHelperMethod(node.getRight(), data);
        } else if (data.compareTo(node.getData()) < 0) {
            return getHelperMethod(node.getLeft(), data);
        }
        return null;
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * This must be done recursively.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        //Exception checker to see if data is null
        if (data == null) {
            throw new IllegalArgumentException("The data is null");
        }
        return getHelperMethod(root, data) != null;
    }

    /**
     * Generate a pre-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        List<T> list = new ArrayList<>();
        preOrderHelperMethod(root, list);
        return list;
    }

    /**
     * Private helper Method for the preorder method
     * @param node node to add in preorder fashion
     * @param list The List (from the BST) to add the nodes into
     */
    private void preOrderHelperMethod(BSTNode<T> node, List<T> list) {
        if (node != null) {
            list.add(node.getData());
            preOrderHelperMethod(node.getLeft(), list);
            preOrderHelperMethod(node.getRight(), list);
        }
    }

    /**
     * Generate an in-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {
        List<T> list = new ArrayList<>();
        inOrderHelperMethod(root, list);
        return list;
    }

    /**
     * Private helper Method for the inorder method
     * @param node node to add in inOrder fashion
     * @param list list (from the BST) to put all the nodes into
     */
    private void inOrderHelperMethod(BSTNode<T> node, List<T> list) {
        if (node != null) {
            inOrderHelperMethod(node.getLeft(), list);
            list.add(node.getData());
            inOrderHelperMethod(node.getRight(), list);
        }
    }

    /**
     * Generate a post-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {
         List<T> list = new ArrayList<>();
         postOrderHelperMethod(getRoot(), list);
         return list;
    }

    /**
     * Private helper Method for the postorder method
     * @param node node to add in postOrder fashion
     * @param list list (from the BST) to put all the nodes into
     */
    private void postOrderHelperMethod(BSTNode<T> node, List<T> list) {
        if (node != null) {
            postOrderHelperMethod(node.getLeft(), list);
            postOrderHelperMethod(node.getRight(), list);
            list.add(node.getData());
        }
    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     *
     * Must be O(n).
     *
     * @return the level order traversal of the tree
     */
    public List<T> levelorder() {
        Queue<BSTNode<T>> queue = new LinkedList<>();
        List<T> list = new ArrayList<>();
        queue.add(root);
        while (queue.isEmpty() == false) {
            BSTNode<T> node = queue.poll();
            list.add(node.getData());
            if (node.getLeft() != null) {
                queue.add(node.getLeft());
            }
            if (node.getRight() != null) {
                queue.add(node.getRight());
            }
        }
        return list;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * This must be done recursively.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (root == null) {
            return -1;
        } else {
            return heightHelperMethod(root) - 1;
        }
    }

    /**
     * Private helper method for the height method
     * @param node to get height of
     * @return the int value of the node's height
     */
    private int heightHelperMethod(BSTNode<T> node) {
        if (node == null) {
            return 0;
        } else if (heightHelperMethod(node.getLeft()) < heightHelperMethod(node.getRight())) {
            return heightHelperMethod(node.getRight()) + 1;
        } else {
            return heightHelperMethod(node.getLeft()) + 1;
        }
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
