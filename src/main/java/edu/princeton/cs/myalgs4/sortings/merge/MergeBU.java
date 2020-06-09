package edu.princeton.cs.myalgs4.sortings.merge;

import edu.princeton.cs.algs4.io.StdIn;
import edu.princeton.cs.algs4.io.StdOut;

/**
 * 自底向上的归并排序
 *
 */
class MergeBU {

	private MergeBU() {
	}

	/**
	 * 使用自然顺序以升序重新排列数组
	 */
	public static <Key extends Comparable<Key>> void sort(Key[] a) {
		int n = a.length;
		@SuppressWarnings("unchecked")
		Key[] aux = (Key[]) new Comparable[n];
		
		for (int len = 1; len < n; len *= 2) {
			for (int lo = 0; lo < n-len; lo+=len+len) {
				int mid = lo+len-1;
				int hi = Math.min(lo+len+len-1, n-1);
				merge(a, aux, lo, mid, hi);
			}
		}
		
		assert isSorted(a);
	}
	
	private static <Key extends Comparable<Key>> void merge(Key[] a, Key[] aux, int lo, int mid, int hi) {
		assert isSorted(a, lo, mid);
		assert isSorted(a, mid+1, hi);
		
		// 复制到 aux[]
		for (int k = lo; k <= hi; k++) {
			aux[k] = a[k];
		}
		
		// 合并到 a[]
		int i = lo, j = mid+1;
		for (int k = lo; k <= hi; k++) {
			if		(i>mid)					a[k] = aux[j++];
			else if	(j>hi)					a[k] = aux[i++];
			else if	(less(aux[j], aux[i]))	a[k] = aux[j++];
			else 							a[k] = aux[i++];
		}
		
	}

	/***********************************************************************
	 * 排序辅助函数
	 ***************************************************************************/

	// is v < w ?
	private static <Key extends Comparable<Key>> boolean less(Key v, Key w) {
		return v.compareTo(w) < 0;
	}

	/***************************************************************************
	 * 检查数组是否有序。用于调试
	 ***************************************************************************/
	private static <Key extends Comparable<Key>> boolean isSorted(Key[] a) {
		return isSorted(a, 0, a.length-1);
	}
	
	private static <Key extends Comparable<Key>> boolean isSorted(Key[] a, int lo, int hi) {
		for (int i = lo+1; i <= hi; i++)
			if (less(a[i], a[i-1]))
				return false;
		return true;
	}

	// 将数组打印到标准输出
	private static void show(Object[] a) {
		for (int i = 0; i < a.length; i++) {
			 StdOut.printf("%s, ", a[i]);
        }
        StdOut.println();
	}

	/**
	 * 从标准输入中读取一系列字符串；
	 * 将它们自下而上的归并排序； 
	 * 并将它们以升序打印到标准输出。
	 */
	public static void main(String[] args) {
		String[] a = StdIn.readAllStrings();
		sort(a);
		show(a);
		assert isSorted(a);
	}

}
