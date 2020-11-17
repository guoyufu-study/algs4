package edu.princeton.cs.myalgs4.fundamentals.section15;

import edu.princeton.cs.algs4.io.StdOut;

public abstract class UF {

	protected int count;
	protected int[] id;

	public UF(int n) {
		count = n;
		id = new int[n];
		for (int i = 0; i < n; i++) id[i] = i;
	}
	
	public abstract int find(int p);
	
	public abstract void union(int p, int q);
	
	public int count() {
		return count;
	}
	
	public boolean connected(int p, int q) {
		return find(p)==find(q);
	}
	
	/*************************************************/
	// 练习题 1.5.1、1.5.2、1.5.3、
	/*************************************************/
	
	protected static void println(int[] ids) {
		for(int num : ids) // 打印输入每对连接后的结果
			StdOut.printf("%4d", num);
		StdOut.println();
	}
}
