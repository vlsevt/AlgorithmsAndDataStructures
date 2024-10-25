package hwa2.src;

import java.util.*;

/**
 * Comparison of sorting methods. The same array of double values is
 * used for all methods.
 *
 * @author Jaanus
 * @version 1.1
 * @since 1.8
 */

//https://www.geeksforgeeks.org/binary-insertion-sort/

public class DoubleSorting {

   /** maximal array length */
   static final int MAX_SIZE = 512000;

   /** number of competition rounds */
   static final int NUMBER_OF_ROUNDS = 4;

   /**
    * Main method.
    *
    * @param args
    *           command line parameters
    */
   public static void main(String[] args) {
      final double[] origArray = new double[MAX_SIZE];
      Random generator = new Random();
      for (int i = 0; i < MAX_SIZE; i++) {
         origArray[i] = generator.nextDouble()*1000.;
      }
      int rightLimit = MAX_SIZE / (int) Math.pow(2., NUMBER_OF_ROUNDS);

      // Start a competition
      for (int round = 0; round < NUMBER_OF_ROUNDS; round++) {
         double[] acopy;
         long stime, ftime, diff;
         rightLimit = 2 * rightLimit;
         System.out.println();
         System.out.println("Length: " + rightLimit);

         acopy = Arrays.copyOf(origArray, rightLimit);
         stime = System.nanoTime();
         insertionSort(acopy);
         ftime = System.nanoTime();
         diff = ftime - stime;
         System.out.printf("%34s%11d%n", "Insertion sort: time (ms): ", diff / 1000000);
         checkOrder(acopy);

         acopy = Arrays.copyOf(origArray, rightLimit);
         stime = System.nanoTime();
         binaryInsertionSortVlad(acopy);
         ftime = System.nanoTime();
         diff = ftime - stime;
         System.out.printf("%34s%11d%n", "VLAD Binary insertion sort: time (ms): ", diff / 1000000);
         checkOrder(acopy);

         acopy = Arrays.copyOf(origArray, rightLimit);
         stime = System.nanoTime();
         binaryInsertionSortJegor(acopy);
         ftime = System.nanoTime();
         diff = ftime - stime;
         System.out.printf("%34s%11d%n", "JEGOR Binary insertion sort: time (ms): ", diff / 1000000);
         checkOrder(acopy);

         acopy = Arrays.copyOf(origArray, rightLimit);
         stime = System.nanoTime();
         binaryInsertionSortNew(acopy);
         ftime = System.nanoTime();
         diff = ftime - stime;
         System.out.printf("%34s%11d%n", "New Binary insertion sort: time (ms): ", diff / 1000000);
         checkOrder(acopy);

         acopy = Arrays.copyOf(origArray, rightLimit);
         stime = System.nanoTime();
         mergeSort(acopy, 0, acopy.length);
         ftime = System.nanoTime();
         diff = ftime - stime;
         System.out.printf("%34s%11d%n", "Merge sort: time (ms): ", diff / 1000000);
         checkOrder(acopy);

         acopy = Arrays.copyOf(origArray, rightLimit);
         stime = System.nanoTime();
         quickSort(acopy, 0, acopy.length);
         ftime = System.nanoTime();
         diff = ftime - stime;
         System.out.printf("%34s%11d%n", "Quicksort: time (ms): ", diff / 1000000);
         checkOrder(acopy);

         acopy = Arrays.copyOf(origArray, rightLimit);
         stime = System.nanoTime();
         Arrays.sort(acopy);
         ftime = System.nanoTime();
         diff = ftime - stime;
         System.out.printf("%34s%11d%n", "Java API  Arrays.sort: time (ms): ", diff / 1000000);
         checkOrder(acopy);
      }
   }

   /**
    * Insertion sort.
    *
    * @param a
    *           array to be sorted
    */
   public static void insertionSort(double[] a) {
      if (a.length < 2)
         return;
      for (int i = 1; i < a.length; i++) {
         double b = a[i];
         int j;
         //[2, 5, 3, 9, 6]
         for (j = i - 1; j >= 0; j--) {
            if (a[j] <= b)
               break;
            a[j + 1] = a[j];
         }
         a[j + 1] = b;
      }
   }


   public static void binaryInsertionSortVlad(double[] a) {
      for (int i = 1; i < a.length; i++) {
         double x = a[i];
         int j = Math.abs(Arrays.binarySearch(a, 0, i, x) + 1);
         System.arraycopy(a, j, a, j + 1, i - j);
         a[j] = x;
      }
   }


   /**
    * Binary insertion sort.
    *
    * @param a
    *           array to be sorted
    */
   public static void binaryInsertionSortJegor(double[] a) {
      if (a.length < 2) {
         return;
      }
      for (int i = 1; i < a.length; i++)
      {
         double b = a[i];
         int index = Math.abs(Arrays.binarySearch(a, 0, i, b) + 1);
         System.arraycopy(a, index,
                 a, index + 1, i - index);

         a[index] = b;
      }
   }

   static int binarySearch(double[] arr, int N, double key) {
      int L = 0;
      int R = N;
      while (L < R) {
         int mid = (L + R) / 2;
         if (arr[mid] <= key) {
            L = mid + 1;
         } else {
            R = mid;
         }
      }
      return L;
   }

   public static void binaryInsertionSortNew(double[] a) {
      for (int i = 1; i < a.length; i++) {
         double key = a[i];
         int pos = binarySearch(a, i, key);
         int j = i;
         while (j > pos) {
            a[j] = a[j - 1];
            j--;
         }
         a[pos] = key;
      }
   }



   /**
    * Merge sort.
    *
    * @param array
    *           array to be sorted
    * @param left
    *           begin of an interval (included)
    * @param right
    *           end of an interval (excluded)
    */
   public static void mergeSort(double[] array, int left, int right) {
      if (array.length < 2)
         return;
      if ((right - left) < 2)
         return;
      int k = (left + right) / 2;
      mergeSort(array, left, k);
      mergeSort(array, k, right);
      merge(array, left, k, right);
   }

   /**
    * Merge two intervals.
    *
    * @param array
    *           original
    * @param left
    *           start1
    * @param k
    *           start2 = end1
    * @param right
    *           end2
    */
   static public void merge(double[] array, int left, int k, int right) {
      if (array.length < 2 || (right - left) < 2 || k <= left || k >= right)
         return;
      double[] tmp = new double[right - left];
      int n1 = left;
      int n2 = k;
      int m = 0;
      while (true) {
         if ((n1 < k) && (n2 < right)) {
            if (array[n1] > array[n2]) {
               tmp[m++] = array[n2++];
            } else {
               tmp[m++] = array[n1++];
            }
         } else {
            if (n1 >= k) {
               for (int i = n2; i < right; i++) {
                  tmp[m++] = array[i];
               }
               break;
            } else {
               for (int i = n1; i < k; i++) {
                  tmp[m++] = array[i];
               }
               break;
            }
         }
      }
      System.arraycopy(tmp, 0, array, left, right - left);
   }

   /**
    * Sort a part of the array using quicksort method.
    *
    * @param array
    *           array to be changed
    * @param l
    *           starting index (included)
    * @param r
    *           ending index (excluded)
    */
   public static void quickSort(double[] array, int l, int r) {
      if (array == null || array.length < 1 || l < 0 || r <= l)
         throw new IllegalArgumentException("quickSort: wrong parameters");
      if ((r - l) < 2)
         return;
      int i = l;
      int j = r - 1;
      double x = array[(i + j) / 2];
      do {
         while (array[i] < x)
            i++;
         while (x < array[j])
            j--;
         if (i <= j) {
            double tmp = array[i];
            array[i] = array[j];
            array[j] = tmp;
            i++;
            j--;
         }
      } while (i < j);
      if (l < j)
         quickSort(array, l, j + 1); // recursion for left part
      if (i < r - 1)
         quickSort(array, i, r); // recursion for right part
   }

   /**
    * Check whether an array is ordered.
    *
    * @param a
    *           sorted (?) array
    * @throws IllegalArgumentException
    *            if an array is not ordered
    */
   static void checkOrder(double[] a) {
      if (a.length < 2)
         return;
      for (int i = 0; i < a.length - 1; i++) {
         if (a[i] > a[i + 1])
            throw new IllegalArgumentException(
                    "array not ordered: " + "a[" + i + "]=" + a[i] + " a[" + (i + 1) + "]=" + a[i + 1]);
      }
   }

}
