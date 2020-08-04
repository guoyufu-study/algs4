/******************************************************************************
 *  编译:  javac DepthFirstSearch.java
 *  执行:    java DepthFirstSearch filename.txt s
 *  依赖: Graph.java StdOut.java
 *  数据文件:   https://algs4.cs.princeton.edu/41graph/tinyG.txt
 *                https://algs4.cs.princeton.edu/41graph/mediumG.txt
 *
 *  无向图深度优先搜索
 *  运行时间  O(E + V).
 *
 *  % java DepthFirstSearch tinyG.txt 0
 *  0 1 2 3 4 5 6 
 *  NOT connected
 *
 *  % java DepthFirstSearch tinyG.txt 9
 *  9 10 11 12 
 *  NOT connected
 *
 ******************************************************************************/

package edu.princeton.cs.myalgs4.graphs.c41;

import edu.princeton.cs.algs4.io.In;
import edu.princeton.cs.algs4.io.StdOut;

/**
 * 4.1.3 连通性问题（路径探测问题）
 *
 */
public class DepthFirstSearch {

	private boolean[] marked; // marked[v] = 是否存在一条 s-v 路径?
	private int count; // 连接到 s 的顶点数
	
	public DepthFirstSearch(Graph G, int s) {
		marked = new boolean[G.V()];
		validateVertex(s);
		dfs(G, s);
	}
	
	private void dfs(Graph G, int v) {
		count++;
		marked[v] = true;
		for (int w : G.adj(v)) {
			if(!marked[w])
				dfs(G, w);
		}
	}

	/**
	 * v 和 s 是连通的吗？
	 */
	public boolean marked(int v) {
        validateVertex(v);
        return marked[v];
    }
	
	/**
	 * 与 s 连通的顶点总数
	 */
	public int count() {
        return count;
    }
	
	private void validateVertex(int v) {
		int V = marked.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
	}

	public static void main(String[] args) {
		// 读数据
		Graph G = new Graph(new In(args[0]));
		int s = Integer.parseInt(args[1]);
		
		DepthFirstSearch search = new DepthFirstSearch(G, s);
		
		// 打印
		for (int v = 0; v < G.V(); v++) {
			if(search.marked(v))
				StdOut.print(v + " ");
		}
		StdOut.println();
		
		// 是否连通图
		if(search.count != G.V()) StdOut.println("NOT connected");
		else 		StdOut.println("connected");
		
	}
}
