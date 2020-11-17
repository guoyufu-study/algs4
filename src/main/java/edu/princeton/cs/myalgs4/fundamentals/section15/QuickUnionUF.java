package edu.princeton.cs.myalgs4.fundamentals.section15;

import edu.princeton.cs.algs4.io.StdIn;
import edu.princeton.cs.algs4.io.StdOut;

/**
 * 快并-并查集
 *
 */
public class QuickUnionUF extends UF {
	
	private static int visits;// 1.5.2 访问数组的次数
	
	public QuickUnionUF(int n) {
		super(n);
	}

	@Override
	public int find(int p) {
		visits++;
		while(p!=id[p]) {
			visits+=2;
			p = id[p]; // 找根
		}
		return p;
	}

	@Override
	public void union(int p, int q) {
		int pID = find(p);
		int qID = find(q);
		
		if(pID==qID) return ;
		
		visits++;
		id[pID] = qID;
		
		count--;
	}
	
	public static void main(String[] args) {
		int n = StdIn.readInt();
		UF uf = new QuickUnionUF(n);
		while(!StdIn.isEmpty()) {
			int p = StdIn.readInt();
			int q = StdIn.readInt();
			
			StdOut.printf("%d %d%n", p, q);
			if(uf.connected(p, q)) {
				println(uf.id);// 1.5.2 打印输入每对连接后的结果
				continue;
			}
			
			uf.union(p, q);
			println(uf.id);// 1.5.2 打印输入每对连接后的结果
		}
		StdOut.printf("%d components.%n", uf.count());
		
		StdOut.printf("1.5.2 答案：访问数组的次数是 %d", visits);//1.5.2 访问数组的次数
	}

}
