import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Random;

/**
 * Your implementation of various sorting algorithms.
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

public class Sorting {

    /**
     * Implement insertion sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Array or comparator is null");
        }
        for (int i = 1; i < arr.length; i++) {
            int j = i - 1;
            while (j >= 0 && comparator.compare(arr[j + 1], arr[j]) < 0) {
                T temp = arr[j];
                arr[j] = arr[j + 1];
                arr[j + 1] = temp;
                j--;
            }
        }
    }

    /**
     * Implement cocktail sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * NOTE: See pdf for last swapped optimization for cocktail sort. You
     * MUST implement cocktail sort with this optimization
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void cocktailSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Array or comparator is null");
        }
        boolean swap = true;
        int start = 0;
        int start2 = start;
        int end = arr.length - 1;
        int end2 = end;
        while (swap) {
            swap = false;
            for (int i = start; i < end; i++) {
                if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                    T temp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = temp;
                    swap = true;
                    end2 = i;
                }
            }
            end = end2;
            if (swap) {
                swap = false;
                for (int i = end; i > start; i--) {
                    if (comparator.compare(arr[i - 1], arr[i]) > 0) {
                        T temp = arr[i - 1];
                        arr[i - 1] = arr[i];
                        arr[i] = temp;
                        swap = true;
                        start2 = i;
                    }
                }
                start = start2;
            }
        }
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Array or comparator is null");
        }
        if (arr.length < 2) {
            return;
        }
        int length = arr.length;
        int middle = length / 2;
        T[] l = (T[]) new Object[middle];
        T[] r = (T[]) new Object[length - middle];
        for (int i = 0; i < middle; i++) {
            l[i] = arr[i];
        }
        for (int i = middle; i < length; i++) {
            r[i - middle] = arr[i];
        }
        mergeSort(l, comparator);
        mergeSort(r, comparator);
        int i = 0;
        int j = 0;
        int k = 0;
        while (i < l.length && j < r.length) {
            if (comparator.compare(l[i], r[j]) <= 0) {
                arr[k++] = l[i++];
            } else {
                arr[k++] = r[j++];
            }
        }
        while (i < l.length) {
            arr[k++] = l[i++];
        }
        while (j < r.length) {
            arr[k++] = r[j++];
        }
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(kn)
     *
     * And a best case running time of:
     * O(kn)
     *
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need.
     *
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Array or comparator is null");
        }
        LinkedList<Integer>[] hold = (LinkedList<Integer>[]) new LinkedList[19];
        int modNum = 10;
        int divNum = 1;
        boolean bool = true;
        while (bool) {
            bool = false;
            for (int num : arr) {
                int b = num / divNum;
                if (b / 10 != 0) {
                    bool = true;
                }
                if (hold[b % modNum + 9] == null) {
                    hold[b % modNum + 9] = new LinkedList<>();
                }
                hold[b % modNum + 9].add(num);
            }
            int arrIndex = 0;
            for (int i = 0; i < hold.length; i++) {
                if (hold[i] != null) {
                    for (int num : hold[i]) {
                        arr[arrIndex++] = num;
                    }
                    hold[i].clear();
                }
            }
            divNum = divNum * 10;
        }
    }

    /**
     * Implement quick sort.
     *
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     *
     * int pivotIndex = rand.nextInt(b - a) + a;
     *
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     *
     * int pivotIndex = rand.nextInt(b - a + 1) + a;
     *
     * It should be:
     * in-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not receive
     * credit if you do not use the one we have taught you!
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @throws java.lang.IllegalArgumentException if the array or comparator or
     *                                            rand is null
     */
    public static <T> void quickSort(T[] arr, Comparator<T> comparator,
                                     Random rand) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Array or comparator is null");
        }
        quickSortRec(arr, 0, arr.length - 1, comparator, rand);
    }

    /**
     * Private Recursive method for quicksort
     *
     * @param <T>        data type
     * @param arr        array passed in
     * @param start      start index
     * @param end        end index
     * @param comparator the Comparator used
     * @param rand       Random object for pivots
     */
    private static <T> void quickSortRec(T[] arr, int start, int end, Comparator<T> comparator, Random rand) {
        if ((end - start) < 1) {
            return;
        }
        int pivIdx = rand.nextInt((end - start) + 1) + start;
        T pivVal = arr[pivIdx];
        T temp = arr[start];
        arr[start] = arr[pivIdx];
        arr[pivIdx] = temp;

        int i = start + 1;
        int j = end;
        while (i <= j) {
            while (i <= j && comparator.compare(arr[i], pivVal) <= 0) {
                i++;
            }
            while (i <= j && comparator.compare(arr[j], pivVal) >= 0) {
                j--;
            }
            if (i <= j) {
                temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                i++;
                j--;
            }
        }
        temp = arr[start];
        arr[start] = arr[j];
        arr[j] = temp;

        quickSortRec(arr, start, j - 1, comparator, rand);
        quickSortRec(arr, j + 1, end, comparator, rand);
    }
}
