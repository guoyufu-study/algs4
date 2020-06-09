package edu.princeton.cs.myalgs4.sortings.merge;

import java.util.stream.IntStream;

import edu.princeton.cs.algs4.io.StdIn;
import edu.princeton.cs.algs4.io.StdOut;

/**
 * 归并排序
 */
class Merge {

	// 这个类不应该被实例化
	private Merge() {
	}
	
	/**
	 * 使用自然顺序以升序重新排列数组
	 */
	public static <Key extends Comparable<Key>> void sort(Key[] a) {
		@SuppressWarnings("unchecked")
		Key[] aux = (Key[]) new Comparable[a.length];
		
		sort(a, aux, 0, a.length-1);
		
		assert isSorted(a);
	}

	/**
	 * 使用自然顺序以升序重新排列子数组 [lo, hi]
	 */
	private static <Key extends Comparable<Key>> void sort(Key[] a, Key[] aux, int lo, int hi) {
		if(lo >= hi) return ;
		// 二分，并排序子数组
		int mid = lo + (hi-lo)/2;
		sort(a, aux, lo, mid);
		sort(a, aux, mid+1, hi);
		// 归并
		merge(a, aux, lo, mid, hi);
	}

	 // 使用 aux[lo .. hi] 稳定地合并 [lo .. mid] 与 [mid+1 ..hi] 
	private static <Key extends Comparable<Key>> void merge(Key[] a, Key[] aux, int lo, int mid, int hi) {
		// 前提条件： a[lo .. mid] 和 a[mid+1 .. hi] 是有序子数组
		assert isSorted(a, lo, mid);
		assert isSorted(a, mid+1, hi);
		
		// 复制到 aux[]
		for (int k = lo; k <= hi; k++) {
			aux[k] = a[k];
		}
		
		// 归并回 a[]
		int i = lo, j = mid+1;
		for (int k = lo; k <= hi; k++) {
			if(i>mid) a[k] = aux[j++];
			else if(j>hi) a[k] = aux[i++];
			else if(less(aux[j], aux[i])) a[k] = aux[j++];
			else a[k] = aux[i++];
		}
	}

	
	public static <Key extends Comparable<Key>> int[] indexSort(Key[] a) {
		int n = a.length;
		int[] index = IntStream.range(0, n).toArray();
		
		int[] aux = new int[n];
		sort(a, index, aux, 0, n-1);
		
		assert isSorted(a, index);
		return index;
		
	}
	
	private static <Key extends Comparable<Key>> void sort(Key[] a, int[] index, int[] aux, int lo, int hi) {
		// 终止
		if(lo>=hi) return ;
		
		int mid = lo + (hi-lo)/2;
		sort(a, index, aux, lo, mid);
		sort(a, index, aux, mid+1, hi);
		merge(a, index, aux, lo, mid, hi);
	}

	private static <Key extends Comparable<Key>> void merge(Key[] a, int[] index, int[] aux, int lo, int mid, int hi) {
		
		assert isSorted(a, index, lo, mid);
		assert isSorted(a, index, mid+1, hi);
		
		// 复制到 aux[]
		for (int k = lo; k <= hi; k++) {
			aux[k] = index[k];
		}
		
		// 归并到 a[]
		int i = lo, j = mid+1;
		for (int k = lo; k <= hi; k++) {
			if(i>mid)							index[k] = aux[j++];
			else if(j>hi)						index[k] = aux[i++];
			else if(less(a[aux[j]], a[aux[i]]))	index[k] = aux[j++];
			else 								index[k] = aux[i++];
		}
	}

	/***************************************************************************
	 * 排序辅助函数
	 ***************************************************************************/

	// is v < w ?
	private static <Key extends Comparable<Key>> boolean less(Key v, Key w) {
		return v.compareTo(w) < 0;
	}

	/***************************************************************************
	 * 检查数组是否有序。用于调试
	 ***************************************************************************/
	
	/**
	 * 检查数组 a 是否有序
	 */
	private static <Key extends Comparable<Key>> boolean isSorted(Key[] a) {
		return isSorted(a, 0, a.length - 1);
	}

	/**
	 * 检查子数组 a[lo, hi] 是否有序
	 */
	private static <Key extends Comparable<Key>> boolean isSorted(Key[] a, int lo, int hi) {
		for (int i = lo + 1; i <= hi; i++)
			if (less(a[i], a[i - 1]))
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
	
	private static <Key extends Comparable<Key>> boolean isSorted(Key[] a, int[] index) {
		return isSorted(a, index, 0, a.length-1);
	}
	
	private static <Key extends Comparable<Key>> boolean isSorted(Key[] a, int[] index, int lo, int hi) {
		for (int i = lo+1; i <= hi; i++) 
			if(less(a[index[i]], a[index[i-1]]))
				return false;
		return true;
	}

	private static void show(Object[] a, int[] index) {
        for (int i = 0; i < a.length; i++) {
            StdOut.printf("%s, ", a[index[i]]);
        }
        StdOut.println();
    }
	
	public static void main(String[] args) {
		// common -> inputfile : ${project_loc}\src\main\resources\22mergesort\words3.txt
		String[] a = StdIn.readAllStrings();
		
		// 排序
//		sort(a);
		show(a);
//		assert isSorted(a);
		
		// 索引排序
		int[] index = indexSort(a);
		show(a, index);
		assert isSorted(a, index);
	}
}
