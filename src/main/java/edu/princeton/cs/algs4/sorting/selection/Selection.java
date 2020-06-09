/******************************************************************************
 *  Compilation:  javac Selection.java
 *  Execution:    java  Selection < input.txt
 *  Dependencies: StdOut.java StdIn.java
 *  Data files:   https://algs4.cs.princeton.edu/21elementary/tiny.txt
 *                https://algs4.cs.princeton.edu/21elementary/words3.txt
 *   
 *  Sorts a sequence of strings from standard input using selection sort.
 *   
 *  % more tiny.txt
 *  S O R T E X A M P L E
 *
 *  % java Selection < tiny.txt
 *  A E E L M O P R S T X                 [ one string per line ]
 *    
 *  % more words3.txt
 *  bed bug dad yes zoo ... all bad yet
 *  
 *  % java Selection < words3.txt
 *  all bad bed bug dad ... yes yet zoo    [ one string per line ]
 *
 ******************************************************************************/

package edu.princeton.cs.algs4.sorting.selection;

import java.util.Comparator;

import edu.princeton.cs.algs4.io.StdIn;
import edu.princeton.cs.algs4.io.StdOut;

/**
 * {@code Selection} 类提供了使用 <em>选择排序</em> 对数组进行排序的静态方法。
 * 
 * <p>
 * 此实现使 ~ 1/2 <em>n</em><sup>2</sup> 次比较，可对长度为 <em>n</em> 的数组进行排序。因此不适合对大型数组进行排序。
 * 它恰好执行n次交换。
 * 
 * <p>
 * 此排序算法不稳定。 它使用 &Theta;(1) 额外的内存（不包括输入数组）。
 * 
 * <p>
 * 有关其他文档，请参见Robert Sedgewick和Kevin Wayne撰写的
 * <i>第4版算法</i> <a href="https://algs4.cs.princeton.edu/21elementary">2.1节</a>。
 * 
 * <p>
 *  The {@code Selection} class provides static methods for sorting an
 *  array using <em>selection sort</em>.
 *  This implementation makes ~ &frac12; <em>n</em><sup>2</sup> compares to sort
 *  any array of length <em>n</em>, so it is not suitable for sorting large arrays.
 *  It performs exactly <em>n</em> exchanges.
 *  <p>
 *  This sorting algorithm is not stable. It uses &Theta;(1) extra memory
 *  (not including the input array).
 *  <p>
 *  For additional documentation, see
 *  <a href="https://algs4.cs.princeton.edu/21elementary">Section 2.1</a>
 *  of <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public class Selection {

    // 该类不应该被实例化
    private Selection() { }

    /**
     * 使用自然顺序以升序重新排列数组。
     * <p>
     * Rearranges the array in ascending order, using the natural order.
     * @param a the array to be sorted
     */
    public static void sort(Comparable[] a) {
        int n = a.length;
        for (int i = 0; i < n; i++) {
            int min = i;
            for (int j = i+1; j < n; j++) {
                if (less(a[j], a[min])) min = j;
            }
            exch(a, i, min);
            assert isSorted(a, 0, i);
        }
        assert isSorted(a);
    }

    /**
     * 使用比较器按升序重新排列数组。
     * <p>
     * Rearranges the array in ascending order, using a comparator.
     * @param a the array
     * @param comparator the comparator specifying the order
     */
    public static void sort(Object[] a, Comparator comparator) {
        int n = a.length;
        for (int i = 0; i < n; i++) {
            int min = i;
            for (int j = i+1; j < n; j++) {
                if (less(comparator, a[j], a[min])) min = j;
            }
            exch(a, i, min);
            assert isSorted(a, comparator, 0, i);
        }
        assert isSorted(a, comparator);
    }


   /***************************************************************************
    *  排序辅助函数.
    ***************************************************************************/
    
    // is v < w ?
    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    // is v < w ?
    private static boolean less(Comparator comparator, Object v, Object w) {
        return comparator.compare(v, w) < 0;
    }
        
        
    // exchange a[i] and a[j]
    private static void exch(Object[] a, int i, int j) {
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }


   /***************************************************************************
    *  检查数组是否有序。用于调试。
    ***************************************************************************/

    // is the array a[] sorted?
    private static boolean isSorted(Comparable[] a) {
        return isSorted(a, 0, a.length - 1);
    }
        
    // is the array sorted from a[lo] to a[hi]
    private static boolean isSorted(Comparable[] a, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++)
            if (less(a[i], a[i-1])) return false;
        return true;
    }

    // is the array a[] sorted?
    private static boolean isSorted(Object[] a, Comparator comparator) {
        return isSorted(a, comparator, 0, a.length - 1);
    }

    // is the array sorted from a[lo] to a[hi]
    private static boolean isSorted(Object[] a, Comparator comparator, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++)
            if (less(comparator, a[i], a[i-1])) return false;
        return true;
    }



    // print array to standard output
    private static void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            StdOut.println(a[i]);
        }
    }

    /**
     * 从标准输入中读取一系列字符串； 选择将它们排序；然后将它们按升序打印到标准输出。
     * 
     * <p>
     * Reads in a sequence of strings from standard input; selection sorts them; 
     * and prints them to standard output in ascending order. 
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        String[] a = StdIn.readAllStrings();
        Selection.sort(a);
        show(a);
    }
}

/******************************************************************************
 *  Copyright 2002-2020, Robert Sedgewick and Kevin Wayne.
 *
 *  This file is part of algs4.jar, which accompanies the textbook
 *
 *      Algorithms, 4th edition by Robert Sedgewick and Kevin Wayne,
 *      Addison-Wesley Professional, 2011, ISBN 0-321-57351-X.
 *      http://algs4.cs.princeton.edu
 *
 *
 *  algs4.jar is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  algs4.jar is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with algs4.jar.  If not, see http://www.gnu.org/licenses.
 ******************************************************************************/
