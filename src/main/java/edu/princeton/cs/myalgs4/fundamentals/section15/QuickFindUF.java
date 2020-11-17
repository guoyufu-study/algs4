package edu.princeton.cs.myalgs4.fundamentals.section15;

import edu.princeton.cs.algs4.io.StdIn;
import edu.princeton.cs.algs4.io.StdOut;

/**
 * 快查-并查集
 *
 */
public class QuickFindUF extends UF {
	
	private static int visits;// 1.5.1 访问数组的次数
	
	public QuickFindUF(int n) {
		super(n);
	}

	@Override
	public int find(int p) {
		visits++;// 1.5.1 访问数组的次数
		return id[p];
	}
	
	@Override
	public void union(int p, int q) {
		int pID = find(p);
		int qID = find(q);
		
		if(pID==qID) return ;
		
		for (int i = 0; i < id.length; i++) {
			visits++;// 1.5.1 访问数组的次数
			if(id[i]==pID) {
				visits++;// 1.5.1 访问数组的次数
				id[i] = qID;
			}
		}
		count--;
	}
	
	public static void main(String[] args) {
		int n = StdIn.readInt();
		UF uf = new QuickFindUF(n);
		while(!StdIn.isEmpty()) {// CTRL+z 结束输入
			int p = StdIn.readInt();
			int q = StdIn.readInt();
			
			StdOut.println(p + " " + q);
			if(uf.connected(p, q)) {
				println(uf.id);// 1.5.1 打印输入每对连接后的结果
				continue;
			}
			uf.union(p, q);
			println(uf.id);// 1.5.1 打印输入每对连接后的结果
		}
		StdOut.println(uf.count() + " components");
		
		StdOut.printf("1.5.1 答案：访问数组的次数是 %d", visits);//1.5.1 访问数组的次数
	}

}
