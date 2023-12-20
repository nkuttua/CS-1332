import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * Your implementation of an AVL Tree.
 *
 * @author Nakul Kuttua
 * @userid nkuttua3
 * @GTID 903520821
 * @version 1.0
 */
public class AVL<T extends Comparable<? super T>> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private AVLNode<T> root;
    private int size;

    /**
     * A no-argument constructor that should initialize an empty AVL.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Initializes the AVL tree with the data in the Collection. The data
     * should be added in the same order it appears in the Collection.
     *
     * @throws IllegalArgumentException if data or any element in data is null
     * @param data the data to add to the tree
     */
    public AVL(Collection<T> data) {
        //Exception checker to see if data is null
        if (data == null) {
            throw new IllegalArgumentException("The data is null");
        }
        for (T iterate : data) {
            //Exception checker to see if data is null
            if (data == null) {
                throw new IllegalArgumentException("The data is null");
            }
            add(iterate);
        }
    }

    /**
     * Adds the data to the AVL. Start by adding it as a leaf like in a regular
     * BST and then rotate the tree as needed.
     *
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors going up the tree,
     * rebalancing if necessary.
     *
     * @throws java.lang.IllegalArgumentException if the data is null
     * @param data the data to be added
     */
    public void add(T data) {
        //Exception checker to see if data is null
        if (data == null) {
            throw new IllegalArgumentException("The data is null");
        } else {
            root = addHelperMethod(root, data);
        }
    }


    /**
     * Private Helper Method for the add Method. Is Recursive
     * @param node the current node to add
     * @param data the data in the node
     * @return a node
     */
    private AVLNode<T> addHelperMethod(AVLNode<T> node, T data) {
        if (node == null) {
            AVLNode<T> temp = new AVLNode<T>(data);
            size++;
            return temp;
        } else {
            if (data.compareTo(node.getData()) < 0) {
                node.setLeft(addHelperMethod(node.getLeft(), data));
            } else if (data.compareTo(node.getData()) > 0) {
                node.setRight(addHelperMethod(node.getRight(), data));
            }
        }
        updateHelperMethod(node);
        return balanceHelperMethod(node);
    }

    /**
     * Private Helper Method for the update method. Is recursive
     * @param node node to update
     */
    private void updateHelperMethod(AVLNode<T> node) {
        int lh, rh = 0;
        if (node.getLeft() == null) {
            lh = -1;
        } else {
            lh = node.getLeft().getHeight();
        }
        if (node.getRight() == null) {
            rh = -1;
        } else {
            rh = node.getRight().getHeight();
        }
        if (lh > rh) {
            node.setHeight(lh + 1);
        } else {
            node.setHeight(rh + 1);
        }
        node.setBalanceFactor(lh - rh);
    }

    /**
     * Private Helper Method for the balance method. Calls other private methods
     * @param node to balance
     * @return the balanced node
     */
    private AVLNode<T> balanceHelperMethod(AVLNode<T> node) {
        if (node.getBalanceFactor() <= -2) {
            if (node.getRight().getBalanceFactor() >= 1) {
                node.setRight(rrHelperMethod(node.getRight()));
            }
            node = rlHelperMethod(node);
        } else if (node.getBalanceFactor() >= 2) {
            if (node.getLeft().getBalanceFactor() <= -1) {
                node.setLeft(rlHelperMethod(node.getLeft()));
            }
            node = rrHelperMethod(node);
        }
        return node;
    }

    /**
     * Private helper method for rotating right
     * @param node node to rotate right
     * @return the right rotated node
     */
    private AVLNode<T> rlHelperMethod(AVLNode<T> node) {
        AVLNode<T> r = node.getRight();
        node.setRight(r.getLeft());
        r.setLeft(node);
        updateHelperMethod(node);
        updateHelperMethod(r);
        return r;
    }

    /**
     * private helper method for rotating left node
     * @param node the node to rotate left
     * @return the left rotated node
     */
    private AVLNode<T> rrHelperMethod(AVLNode<T> node) {
        AVLNode<T> l = node.getLeft();
        node.setLeft(l.getRight());
        l.setRight(node);
        updateHelperMethod(node);
        updateHelperMethod(l);
        return l;
    }

    /**
     * Removes the data from the tree. There are 3 cases to consider:
     *
     * 1: the data is a leaf. In this case, simply remove it.
     * 2: the data has one child. In this case, simply replace it with its
     * child.
     * 3: the data has 2 children. Use the successor to replace the data,
     * not the predecessor. As a reminder, rotations can occur after removing
     * the successor node.
     *
     * Remember to recalculate heights going up the tree, rebalancing if
     * necessary.
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to remove from the tree.
     * @return the data removed from the tree. Do not return the same data
     * that was passed in.  Return the data that was stored in the tree.
     */
    public T remove(T data) {
        AVLNode<T> node = new AVLNode<>(null);
        //Exception checker to see if data is null
        if (data == null) {
            throw new IllegalArgumentException("The data is null");
        } else {
            root = removeHelperMethod(root, node, data);
            size--;
            return node.getData();
        }
    }

    /**
     * private helper method to remove a node, uses a temp node for recursion
     * purposes.
     * @param node node to remove
     * @param temp temp node for recursion purposes
     * @param data data in the node to remove
     * @return the removed node
     */
    private AVLNode<T> removeHelperMethod(AVLNode<T> node, AVLNode<T> temp, T data) {
        //Exception checker to see if node exists
        if (node == null) {
            throw new NoSuchElementException("The data could not be found");
        } else if (data.compareTo(node.getData()) < 0) {
            node.setLeft(removeHelperMethod(node.getLeft(), temp, data));
        } else if (data.compareTo(node.getData()) > 0) {
            node.setRight(removeHelperMethod(node.getRight(), temp, data));
        } else {
            temp.setData(node.getData());
            if (node.getLeft() != null && node.getRight() != null) {
                AVLNode<T> temp2 = new AVLNode<>(null);
                node.setRight(successor(node.getRight(), temp2));
                node.setData(temp2.getData());
            } else {
                if (node.getLeft() == null && node.getRight() != null) {
                    return node.getRight();
                } else if (node.getRight() == null) {
                    return node.getLeft();
                }
            }
        }
        updateHelperMethod(node);
        return balanceHelperMethod(node);
    }

    /**
     * private helper method for the successor node
     * @param node the successor node
     * @param temp the temp node
     * @return the balanced node
     */
    private AVLNode<T> successor(AVLNode<T> node, AVLNode<T> temp) {
        if (node.getLeft() == null) {
            temp.setData(node.getData());
            node.setLeft(null);
            return node.getRight();
        } else {
            node.setLeft(successor(node.getLeft(), temp));
            updateHelperMethod(node);
            return balanceHelperMethod(node);
        }
    }

    /**
     * Returns the data in the tree matching the parameter passed in (think
     * carefully: should you use value equality or reference equality?).
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to search for in the tree.
     * @return the data in the tree equal to the parameter. Do not return the
     * same data that was passed in.  Return the data that was stored in the
     * tree.
     */
    public T get(T data) {
        //Exception checker to see if data is null
        if (data == null) {
            throw new IllegalArgumentException("The data is null");
        }
        //Exception checker to see if node exists
        if (!contains(data)) {
            throw new NoSuchElementException("The data could not be found");
        } else {
            AVLNode<T> node = getHelperMethod(root, data);
            return node.getData();
        }
    }

    /**
     * private helper method for the get method. uses recursion
     * @param node the node to get
     * @param data the data in the node to get
     * @return the node to get
     */
    private AVLNode<T> getHelperMethod(AVLNode<T> node, T data) {
        if (data.compareTo(node.getData()) < 0) {
            return getHelperMethod(node.getLeft(), data);
        } else if (data.compareTo(node.getData()) > 0) {
            return getHelperMethod(node.getRight(), data);
        } else {
            return node;
        }
    }

    /**
     * Returns whether or not data equivalent to the given parameter is
     * contained within the tree. The same type of equality should be used as
     * in the get method.
     *
     * @throws IllegalArgumentException if the data is null
     * @param data the data to search for in the tree.
     * @return whether or not the parameter is contained within the tree.
     */
    public boolean contains(T data) {
        //Exception checker to see if data is null
        if (data == null) {
            throw new IllegalArgumentException("The data is null");
        } else {
            return containsHelperMethod(root, data);
        }
    }

    /**
     * private helper method to see if AVL contains a node
     * @param node the node to look for
     * @param data the data in the node to look for
     * @return a boolean if the node is contained in the AVL
     */
    private boolean containsHelperMethod(AVLNode<T> node, T data) {
        if (node == null) {
            return false;
        }
        if (data == node.getData()) {
            return true;
        } else if (data.compareTo(node.getData()) < 0) {
            return containsHelperMethod(node.getLeft(), data);
        } else if (data.compareTo(node.getData()) > 0) {
            return containsHelperMethod(node.getRight(), data);
        }
        return true;
    }

    /**
     * Clears the tree.
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * Since this is an AVL, this method does not need to traverse the tree
     * and should be O(1)
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (root == null) {
            return -1;
        } else {
            return root.getHeight();
        }
    }

    /**
     * Returns the size of the AVL tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return number of items in the AVL tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD
        return size;
    }

    /**
     * Returns the root of the AVL tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the AVL tree
     */
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }
}