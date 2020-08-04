/******************************************************************************
 *  编译:  javac Graph.java        
 *  执行:    java Graph input.txt
 *  依赖: Bag.java Stack.java In.java StdOut.java
 *  数据文件:   https://algs4.cs.princeton.edu/41graph/tinyG.txt
 *                https://algs4.cs.princeton.edu/41graph/mediumG.txt
 *                https://algs4.cs.princeton.edu/41graph/largeG.txt
 *
 *  A graph, implemented using an array of sets.
 *  允许平行边和自环.
 *
 *  % java Graph tinyG.txt
 *  13 vertices, 13 edges 
 *  0: 6 2 1 5 
 *  1: 0 
 *  2: 0 
 *  3: 5 4 
 *  4: 5 6 3 
 *  5: 3 4 0 
 *  6: 0 4 
 *  7: 8 
 *  8: 7 
 *  9: 11 10 12 
 *  10: 9 
 *  11: 9 12 
 *  12: 11 9 
 *
 *  % java Graph mediumG.txt
 *  250 vertices, 1273 edges 
 *  0: 225 222 211 209 204 202 191 176 163 160 149 114 97 80 68 59 58 49 44 24 15 
 *  1: 220 203 200 194 189 164 150 130 107 72 
 *  2: 141 110 108 86 79 51 42 18 14 
 *  ...
 *  
 ******************************************************************************/

package edu.princeton.cs.myalgs4.graphs.c41;

import java.util.Deque;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.fundamentals.Bag;
import edu.princeton.cs.algs4.io.In;
import edu.princeton.cs.algs4.io.StdOut;

/**
 * 无向图数据类型
 *
 */
public class Graph {

	private static final String NEWLINE = System.getProperty("line.separator");

	private final int V;
	private int E;
	private Bag<Integer>[] adj;

	/**
	 * 创建一幅无边图
	 * 
	 * @param V
	 */
	@SuppressWarnings("unchecked")
	public Graph(int V) {
		if (V < 0)
			throw new IllegalArgumentException("Number of vertices must be nonnegative");

		this.V = V;
		this.E = 0;
		adj = (Bag<Integer>[]) new Bag[V];
		for (int v = 0; v < V; v++) {
			adj[v] = new Bag<Integer>();
		}
	}

	/**
	 * 从输入流 in 中读图
	 * 
	 * @param in
	 */
	public Graph(In in) {
		this(in.readInt());

		try {
			int E = in.readInt();
			if (E < 0)
				throw new IllegalArgumentException("number of edges in a Graph must be nonnegative");

			for (int i = 0; i < E; i++) {
				int v = in.readInt();
				int w = in.readInt();
				addEdge(v, w);
			}
		} catch (NoSuchElementException e) {
			throw new IllegalArgumentException("invalid input format in Graph constructor", e);
		}

	}

	/**
	 * 添加一条边(v, w)
	 * 
	 * @param v
	 * @param w
	 */
	public void addEdge(int v, int w) {
		validateVertex(v);
		validateVertex(w);
		E++;
		adj[v].add(w);
		adj[w].add(v);

	}

	private void validateVertex(int v) {
		if (v < 0 || v >= V)
			throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
	}

	/**
	 * 复制图
	 */
	@SuppressWarnings("unchecked")
	public Graph(Graph G) {
		if (G.V() < 0)
			throw new IllegalArgumentException("Number of vertices must be nonnegative");
		this.V = G.V();

		this.E = G.E();

		adj = (Bag<Integer>[]) new Bag[V];
		for (int v = 0; v < V; v++) {
			adj[v] = new Bag<Integer>();
		}

		for (int v = 0; v < G.V(); v++) {
			Deque<Integer> reverse = new LinkedList<Integer>();
			for (int w : G.adj[v]) {
				reverse.push(w);
			}
			for (int w : reverse) {
				adj[v].add(w);
			}
		}
	}

	public int V() {
		return V;
	}

	public int E() {
		return E;
	}

	public Iterable<Integer> adj(int v) {
		validateVertex(v);
		return adj[v];
	}

	public int degree(int v) {
		validateVertex(v);
		return adj[v].size();
	}

	/**
	 * 度数
	 */
	public static int degree(Graph G, int v) {
		return G.adj[v].size();
	}

	/**
	 * 最大度数
	 */
	public static int maxDegree(Graph G) {
		int max = 0;
		for (int v = 0; v < G.V(); v++) {
			if (max < degree(G, v)) {
				max = degree(G, v);
			}
		}

		return max;
	}

	/**
	 * 平均度数
	 */
	public static double avgDegree(Graph G) {
		return 2.0 * G.E() / G.V();
	}

	/**
	 * 自环数
	 */
	public static int numberOfSelfLoops(Graph G) {
		int count = 0;
		for (int v = 0; v < G.V(); v++) {
			for (int w : G.adj(v)) {
				if (w == v)
					count++;
			}
		}

		return count / 2;
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(V)
			.append(" vertices, ")
			.append(E)
			.append(" edges ")
			.append(NEWLINE);
		
		for (int v = 0; v < V; v++) {
			s.append(v).append(": ");
			for (int w : adj[v]) {
				s.append(w).append(" ");
			}
			s.append(NEWLINE);
		}
		return s.toString();
	}

	public static void main(String[] args) {
		In in = new In(args[0]);
		Graph G = new Graph(in);
		StdOut.println(G);
	}
}
