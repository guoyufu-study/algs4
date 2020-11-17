package edu.princeton.cs.myalgs4.fundamentals.section15;

import java.util.Arrays;

import edu.princeton.cs.algs4.io.StdIn;
import edu.princeton.cs.algs4.io.StdOut;

public class WeightedQuickUnionUF extends UF {
	
	private static int visits;// 1.5.3 访问数组的次数
	
	int[] sz;

	public WeightedQuickUnionUF(int n) {
		super(n);
		sz = new int[n];
		Arrays.fill(sz, 1);
	}

	@Override
	public int find(int p) {
		visits++;
		while(p!=id[p]) {
			visits+=2;
			p = id[p];
		}
		return p;
	}

	@Override
	public void union(int p, int q) {
		int pID = find(p);
		int qID = find(q);
		
		if(pID==qID) return ;
		
		visits+=8;
		if(sz[pID]<sz[qID]) {
			id[pID] = qID;
			sz[qID] += sz[pID];
		} else {
			id[qID] = pID;
			sz[pID] += sz[qID];
		}
		
		count--;
	}
	
	public static void main(String[] args) {
		int n = StdIn.readInt();
		UF uf = new WeightedQuickUnionUF(n);
		while(!StdIn.isEmpty()) {
			int p = StdIn.readInt();
			int q = StdIn.readInt();
			
			StdOut.printf("%d %d%n", p, q);
			if(uf.connected(p, q)) {
				println(uf.id);// 1.5.3 打印输入每对连接后的结果
				continue;
			}
			
			uf.union(p, q);
			println(uf.id);// 1.5.3 打印输入每对连接后的结果
		}
		StdOut.printf("%d components.%n", uf.count());
		
		StdOut.printf("1.5.3 答案：访问数组的次数是 %d", visits);//1.5.3 访问数组的次数
	}


}
