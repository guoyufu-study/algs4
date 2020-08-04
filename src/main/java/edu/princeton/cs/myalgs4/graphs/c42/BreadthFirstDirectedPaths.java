/******************************************************************************
 *  编译:  javac BreadthFirstDirectedPaths.java
 *  执行:    java BreadthFirstDirectedPaths digraph.txt s
 *  依赖: Digraph.java Queue.java Stack.java
 *  数据文件:   https://algs4.cs.princeton.edu/42digraph/tinyDG.txt
 *                https://algs4.cs.princeton.edu/42digraph/mediumDG.txt
 *                https://algs4.cs.princeton.edu/42digraph/largeDG.txt
 *
 *  Run breadth-first search on a digraph.
 *  运行时间 O(E + V) .
 *
 *  % java BreadthFirstDirectedPaths tinyDG.txt 3
 *  3 to 0 (2):  3->2->0
 *  3 to 1 (3):  3->2->0->1
 *  3 to 2 (1):  3->2
 *  3 to 3 (0):  3
 *  3 to 4 (2):  3->5->4
 *  3 to 5 (1):  3->5
 *  3 to 6 (-):  not connected
 *  3 to 7 (-):  not connected
 *  3 to 8 (-):  not connected
 *  3 to 9 (-):  not connected
 *  3 to 10 (-):  not connected
 *  3 to 11 (-):  not connected
 *  3 to 12 (-):  not connected
 *
 ******************************************************************************/

package edu.princeton.cs.myalgs4.graphs.c42;

import java.util.Deque;
import java.util.LinkedList;

import edu.princeton.cs.algs4.fundamentals.Queue;
import edu.princeton.cs.algs4.io.In;
import edu.princeton.cs.algs4.io.StdOut;

/**
 * 4.2.3 有向图的单（多）源最短路径问题
 * 
 * <p>
 * The {@code BreadthDirectedFirstPaths} class represents a data type for
 * finding shortest paths (number of edges) from a source vertex <em>s</em> (or
 * set of source vertices) to every other vertex in the digraph.
 * <p>
 * This implementation uses breadth-first search. The constructor takes
 * &Theta;(<em>V</em> + <em>E</em>) time in the worst case, where <em>V</em> is
 * the number of vertices and <em>E</em> is the number of edges. Each instance
 * method takes &Theta;(1) time. It uses &Theta;(<em>V</em>) extra space (not
 * including the digraph).
 * <p>
 * For additional documentation, see
 * <a href="https://algs4.cs.princeton.edu/42digraph">Section 4.2</a> of
 * <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 * @author Robert Sedgewick
 * @author Kevin Wayne
 */
public class BreadthFirstDirectedPaths {
	private static final int INFINITY = Integer.MAX_VALUE;
	private boolean[] marked; // marked[v] = 是否存在一条 s->v 路径？
	private int[] edgeTo; // edgeTo[v] = s->v 最短路径上最后一条边
	private int[] distTo; // distTo[v] = s->v 最短路径的长度

	/**
	 * 计算有向图 {@code G} 中从源点 {@code s} 到每一个顶点的最短路径。
	 * 
	 * @param G 有向图
	 * @param s 源点
	 * @throws IllegalArgumentException 除非 {@code 0 <= v < V}
	 */
	public BreadthFirstDirectedPaths(Digraph G, int s) {
		marked = new boolean[G.V()];
		distTo = new int[G.V()];
		edgeTo = new int[G.V()];
		for (int v = 0; v < G.V(); v++)
			distTo[v] = INFINITY;
		validateVertex(s);
		bfs(G, s);
	}

	// 单源BFS
	private void bfs(Digraph G, int s) {
		Queue<Integer> q = new Queue<Integer>();
		marked[s] = true;
		distTo[s] = 0;
		q.enqueue(s);
		while (!q.isEmpty()) {
			int v = q.dequeue();
			for (int w : G.adj(v)) {
				if (!marked[w]) {
					edgeTo[w] = v;
					distTo[w] = distTo[v] + 1;
					marked[w] = true;
					q.enqueue(w);
				}
			}
		}
	}

	/**
	 * 计算有向图 {@code G} 中从 {@code sources} 中任意一个源点到每一个顶点的最短路径。
	 * 
	 * @param G       有向图
	 * @param sources 源点集合
	 * @throws IllegalArgumentException 如果 {@code sources} 为 {@code null}
	 * @throws IllegalArgumentException 除非 {@code sources} 中的每一个顶点 {@code v} 都满足
	 *                                  {@code 0 <= v < V}
	 */
	public BreadthFirstDirectedPaths(Digraph G, Iterable<Integer> sources) {
		marked = new boolean[G.V()];
		distTo = new int[G.V()];
		edgeTo = new int[G.V()];
		for (int v = 0; v < G.V(); v++)
			distTo[v] = INFINITY;
		validateVertices(sources);
		bfs(G, sources);
	}

	// 多源BFS
	private void bfs(Digraph G, Iterable<Integer> sources) {
		Queue<Integer> q = new Queue<Integer>();
		for (int s : sources) {
			marked[s] = true;
			distTo[s] = 0;
			q.enqueue(s);
		}
		while (!q.isEmpty()) {
			int v = q.dequeue();
			for (int w : G.adj(v)) {
				if (!marked[w]) {
					edgeTo[w] = v;
					distTo[w] = distTo[v] + 1;
					marked[w] = true;
					q.enqueue(w);
				}
			}
		}
	}

	/**
	 * 顶点 {@code v} 是否可达 ?
	 * 
	 * @param v the vertex
	 * @return {@code true} if there is a directed path, {@code false} otherwise
	 * @throws IllegalArgumentException unless {@code 0 <= v < V}
	 */
	public boolean hasPathTo(int v) {
		validateVertex(v);
		return marked[v];
	}

	/**
	 * 从源点 {@code s} 到顶点 {@code v} 的最短路径长度
	 * 
	 * @param v the vertex
	 * @return the number of edges in a shortest path
	 * @throws IllegalArgumentException unless {@code 0 <= v < V}
	 */
	public int distTo(int v) {
		validateVertex(v);
		return distTo[v];
	}

	/**
	 * 从源点 {@code s} 到顶点 {@code v} 的最短路径
	 * 
	 * @param v the vertex
	 * @return the sequence of vertices on a shortest path, as an Iterable
	 * @throws IllegalArgumentException unless {@code 0 <= v < V}
	 */
	public Iterable<Integer> pathTo(int v) {
		validateVertex(v);

		if (!hasPathTo(v))
			return null;

		Deque<Integer> path = new LinkedList<Integer>();
		int x;
		for (x = v; distTo[x] != 0; x = edgeTo[x])
			path.push(x);
		path.push(x);
		return path;
	}

	// throw an IllegalArgumentException unless {@code 0 <= v < V}
	private void validateVertex(int v) {
		int V = marked.length;
		if (v < 0 || v >= V)
			throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
	}

	// throw an IllegalArgumentException unless {@code 0 <= v < V}
	private void validateVertices(Iterable<Integer> vertices) {
		if (vertices == null) {
			throw new IllegalArgumentException("argument is null");
		}
		for (Integer v : vertices) {
			if (v == null) {
				throw new IllegalArgumentException("vertex is null");
			}
			validateVertex(v);
		}
	}

	/**
	 * 单元测试 {@code BreadthFirstDirectedPaths} 数据类型
	 *
	 * @param args 命令行参数
	 */
	public static void main(String[] args) {
		In in = new In(args[0]);
		Digraph G = new Digraph(in);
		// StdOut.println(G);

		int s = Integer.parseInt(args[1]);
		BreadthFirstDirectedPaths bfs = new BreadthFirstDirectedPaths(G, s);

		for (int v = 0; v < G.V(); v++) {
			if (bfs.hasPathTo(v)) {
				StdOut.printf("%d to %d (%d):  ", s, v, bfs.distTo(v));
				for (int x : bfs.pathTo(v)) {
					if (x == s)
						StdOut.print(x);
					else
						StdOut.print("->" + x);
				}
				StdOut.println();
			}

			else {
				StdOut.printf("%d to %d (-):  not connected\n", s, v);
			}

		}
	}

}