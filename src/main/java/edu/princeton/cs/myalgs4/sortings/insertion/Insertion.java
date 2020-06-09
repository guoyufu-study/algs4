package edu.princeton.cs.myalgs4.sortings.insertion;

import java.util.Comparator;
import java.util.stream.IntStream;

import edu.princeton.cs.algs4.io.StdIn;
import edu.princeton.cs.algs4.io.StdOut;

class Insertion {

	private Insertion() {
	}

	/**
	 * 使用自然顺序以升序重新排列数组。
	 */
	static <Key extends Comparable<Key>> void sort(Key[] a) {
		int n = a.length;
		for (int i = 1; i < n; i++) {
			for (int j = i; j > 0 && less(a[j], a[j - 1]); j--)
				exchange(a, j, j - 1);
		}
	}

	/**
	 * 使用比较器按升序重新排列数组。
	 */
	static <Key> void sort(Key[] a, Comparator<Key> comparator) {
		int n = a.length;
		for (int i = 1; i < n; i++)
			for (int j = i; j > 0 && less(comparator, a[j], a[j - 1]); j--)
				exchange(a, j, j - 1);
	}

	/**
	 * 返回一个排列，该排列以升序给出数组中的元素。
	 */
	static <Key extends Comparable<Key>> int[] indexSort(Key[] a) {

		int n = a.length;
		int[] index = IntStream.range(0, n).toArray();
		for (int i = 1; i < n; i++) {
			for (int j = i; j > 0 && less(a[index[j]], a[index[j - 1]]); j--) {
				exchange(index, j, j - 1);
			}
		}

		return index;
	}

	/***************************************************************************
	 * 排序辅助函数
	 ***************************************************************************/
	// 比较
	private static <Key> boolean less(Comparator<Key> comparator, Key v, Key w) {
		return comparator.compare(v, w) < 0;
	}

	private static <Key extends Comparable<Key>> boolean less(Key v, Key w) {
		return v.compareTo(w) < 0;
	}

	// 交换
	private static void exchange(Object[] a, int i, int j) {
		Object swap = a[i];
		a[i] = a[j];
		a[j] = swap;
	}

	private static void exchange(int[] a, int i, int j) {
		int swap = a[i];
		a[i] = a[j];
		a[j] = swap;
	}

	/***************************************************************************
	 * 检查数组是否有序。用于调试。
	 ***************************************************************************/
	private static <Key extends Comparable<Key>> boolean isSorted(Key[] a) {
		for (int i = 1; i < a.length; i++)
			if (less(a[i], a[i - 1]))
				return false;
		return true;
	}

	// 将数组打印到标准输出
	private static <Key extends Comparable<Key>> void show(Key[] a) {
		for (int i = 0; i < a.length; i++) {
			StdOut.printf("%s, ", a[i]);
		}
		StdOut.println();
	}

	public static void main(String[] args) {
		// d a c b w h g p i k e f 
		String[] a = StdIn.readAllStrings();
		
//		sort(a);
//		sort(a, Collections.reverseOrder());
		show(a);
		
		assert isSorted(a);
		
		for (int index : indexSort(a)) {
			StdOut.printf("%s, ", a[index]);
		}
		
		
	}
}
