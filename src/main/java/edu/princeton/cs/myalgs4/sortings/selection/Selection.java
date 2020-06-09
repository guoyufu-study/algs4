package edu.princeton.cs.myalgs4.sortings.selection;

import java.util.Collections;
import java.util.Comparator;

import edu.princeton.cs.algs4.io.StdIn;
import edu.princeton.cs.algs4.io.StdOut;

/**
 * 选择排序
 */
class Selection {

	private Selection() {
	};
	
	/**
	 * 使用自然顺序以升序重新排列数组。
	 */
	public static <Key extends Comparable<Key>> void sort(Key[] a) {
		int n = a.length;
		for (int i = 0; i < n; i++) {
			int min = i;
			for (int j = i+1; j < n; j++) {
				if(less(a[j], a[min]))
					min = j;
			}
			exch(a, i, min);
		}
		
		assert isSorted(a);
	}
	
	/**
	 * 使用比较器按升序重新排列数组。
	 */
	public static <Key> void sort(Key[] a, Comparator<Key> comparator) {
		int n = a.length;
		
		for (int i = 0; i < n; i++) {
			// 找到最小值
			int min = i;
			for (int j = i+1; j < n; j++) {
				if(less(comparator, a[j], a[min])) 
					min = j;
			}
			// 交换
			exch(a, i, min);
		}
		
		assert isSorted(a, comparator);
	}

	/***************************************************************************
	 * 排序辅助函数.
	 ***************************************************************************/

	// is v < w ?
	private static <Key extends Comparable<Key>> boolean less(Key v, Key w) {
		return v.compareTo(w) < 0;
	}

	// is v < w ?
	private static <Key> boolean less(Comparator<Key> comparator, Key v, Key w) {
		return comparator.compare(v, w) < 0;
	}

	// exchange a[i] and a[j]
	private static void exch(Object[] a, int i, int j) {
		Object swap = a[i];
		a[i] = a[j];
		a[j] = swap;
	}

	/***************************************************************************
	 * 检查数组是否有序。用于调试。
	 ***************************************************************************/

	// is the array a[] sorted?
	private static <Key extends Comparable<Key>> boolean isSorted(Key[] a) {
		return isSorted(a, 0, a.length - 1);
	}

	// is the array sorted from a[lo] to a[hi]
	private static <Key extends Comparable<Key>> boolean isSorted(Key[] a, int lo, int hi) {
		for (int i = lo + 1; i <= hi; i++)
			if (less(a[i], a[i - 1]))
				return false;
		return true;
	}

	// is the array a[] sorted?
	private static <Key> boolean isSorted(Key[] a, Comparator<Key> comparator) {
		return isSorted(a, comparator, 0, a.length - 1);
	}

	// is the array sorted from a[lo] to a[hi]
	private static <Key> boolean isSorted(Key[] a, Comparator<Key> comparator, int lo, int hi) {
		for (int i = lo + 1; i <= hi; i++)
			if (less(comparator, a[i], a[i - 1]))
				return false;
		return true;
	}

	// 将数组打印到标准输出
	private static void show(Object[] a) {
		for (int i = 0; i < a.length; i++) {
			StdOut.printf("%s, ",a[i]);
		}
		StdOut.println();
	}

	/**
	 * 从标准输入中读取一系列字符串； 选择将它们排序；然后将它们按升序打印到标准输出。
	 */
	public static void main(String[] args) {
		// d a c b q p m n g h f
		String[] a = StdIn.readAllStrings();
		
		// 自然顺序排序
//		sort(a);
//		show(a);
//		assert isSorted(a);
		
		// 比较器顺序排序 
		sort(a, Collections.reverseOrder());
		show(a);
		assert isSorted(a, Collections.reverseOrder());
		
	}
}
