package edu.princeton.cs.myalgs4.fundamentals.section15.exercise;

import java.util.Arrays;

import edu.princeton.cs.algs4.io.StdIn;
import edu.princeton.cs.algs4.io.StdOut;
import edu.princeton.cs.myalgs4.fundamentals.section15.UF;

/**
 * 带路径压缩的高度加权快并 - 并查集
 * @author 123
 *
 */
public class BestUF2 extends UF {
	
	private int[] hs; // 高度加权

	public BestUF2(int n) {
		super(n);
		hs = new int[n];
		Arrays.fill(hs, 1);
	}

	@Override
	public int find(int p) {
		while(p!=id[p]) {
			id[p] = id[id[p]]; // 带路径压缩
			p = id[p];
		}
		return p;
	}

	@Override
	public void union(int p, int q) {
		int pID = find(p);
		int qID = find(q);
		
		if(pID==qID) return ;
		
		if(hs[pID]<hs[qID]) {
			id[pID] = qID;
			hs[qID] = Math.max(hs[pID]+1, hs[qID]);
		} else {
			id[qID] = pID;
			hs[pID] = Math.max(hs[qID]+1, hs[pID]);
		}
		
		count--;
	}
	
	public static void main(String[] args) {
		int n = StdIn.readInt();
		UF uf = new BestUF2(n);
		while(!StdIn.isEmpty()) {
			int p = StdIn.readInt();
			int q = StdIn.readInt();
			
			if(uf.connected(p, q)) 
				continue;
			
			uf.union(p, q);
			StdOut.printf("%d %d%n", p, q);
		}
		StdOut.printf("%d components.%n", uf.count());
		
	}

}
