package edu.princeton.cs.myalgs4.sortings.insertion;

import edu.princeton.cs.algs4.io.StdIn;
import edu.princeton.cs.algs4.io.StdOut;

/**
 * 排入排序优化版
 */
class InsertionX {

	private InsertionX() {
	}

	/**
	 * 使用自然顺序以升序重新排列数组。
	 */
	static <Key extends Comparable<Key>> void sort(Key[] a) {
		int n = a.length;

		// 放置最小的元素作为哨兵
		int exchanges = 0;
		for (int i = n - 1; i > 0; i--) {
			if (less(a[i], a[i - 1])) {
				exch(a, i, i - 1);
				exchanges++;
			}
		}

		if (exchanges == 0)
			return;

		// 半交换插入排序
		for (int i = 2; i < n; i++) {
			Key v = a[i];
			int j = i;
			while (less(v, a[j - 1])) {
				a[j] = a[j - 1];
				j--;
			}
			a[j] = v;
		}

		assert isSorted(a);
	}

	/***************************************************************************
	 * 排序辅助函数
	 ***************************************************************************/

	// is v < w ?
	private static <Key extends Comparable<Key>> boolean less(Key v, Key w) {
		return v.compareTo(w) < 0;
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
	private static <Key extends Comparable<Key>> boolean isSorted(Key[] a) {
		for (int i = 1; i < a.length; i++)
			if (less(a[i], a[i - 1]))
				return false;
		return true;
	}

	// print array to standard output
	private static <Key extends Comparable<Key>> void show(Key[] a) {
		for (int i = 0; i < a.length; i++) {
			StdOut.printf("%s, ",a[i]);
		}
		StdOut.println();
	}
	
	public static void main(String[] args) {
		// d a c b q p m n g h f
		String[] a = StdIn.readAllStrings();
		
		sort(a);
		show(a);
		
		assert isSorted(a);
	}
}
