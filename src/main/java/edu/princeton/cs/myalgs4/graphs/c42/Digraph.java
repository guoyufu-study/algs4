/******************************************************************************
 *  编译:  javac Digraph.java
 *  执行:    java Digraph filename.txt
 *  依赖: Bag.java In.java StdOut.java
 *  数据文件:   https://algs4.cs.princeton.edu/42digraph/tinyDG.txt
 *                https://algs4.cs.princeton.edu/42digraph/mediumDG.txt
 *                https://algs4.cs.princeton.edu/42digraph/largeDG.txt  
 *
 *  A graph, implemented using an array of lists.
 *  允许存在平行边和自环.
 *
 *  % java Digraph tinyDG.txt
 *  13 vertices, 22 edges
 *  0: 5 1 
 *  1: 
 *  2: 0 3 
 *  3: 5 2 
 *  4: 3 2 
 *  5: 4 
 *  6: 9 4 8 0 
 *  7: 6 9
 *  8: 6 
 *  9: 11 10 
 *  10: 12 
 *  11: 4 12 
 *  12: 9 
 *  
 ******************************************************************************/

package edu.princeton.cs.myalgs4.graphs.c42;

import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.fundamentals.Bag;
import edu.princeton.cs.algs4.fundamentals.Stack;
import edu.princeton.cs.algs4.io.In;
import edu.princeton.cs.algs4.io.StdOut;

/**
 *  The {@code Digraph} class represents a directed graph of vertices
 *  named 0 through <em>V</em> - 1.
 *  It supports the following two primary operations: add an edge to the digraph,
 *  iterate over all of the vertices adjacent from a given vertex.
 *  It also provides
 *  methods for returning the indegree or outdegree of a vertex, 
 *  the number of vertices <em>V</em> in the digraph, 
 *  the number of edges <em>E</em> in the digraph, and the reverse digraph.
 *  Parallel edges and self-loops are permitted.
 *  <p>
 *  This implementation uses an <em>adjacency-lists representation</em>, which
 *  is a vertex-indexed array of {@link Bag} objects.
 *  It uses &Theta;(<em>E</em> + <em>V</em>) space, where <em>E</em> is
 *  the number of edges and <em>V</em> is the number of vertices.
 *  All instance methods take &Theta;(1) time. (Though, iterating over
 *  the vertices returned by {@link #adj(int)} takes time proportional
 *  to the outdegree of the vertex.)
 *  Constructing an empty digraph with <em>V</em> vertices takes
 *  &Theta;(<em>V</em>) time; constructing a digraph with <em>E</em> edges
 *  and <em>V</em> vertices takes &Theta;(<em>E</em> + <em>V</em>) time.
 *  <p>
 *  For additional documentation,
 *  see <a href="https://algs4.cs.princeton.edu/42digraph">Section 4.2</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */

public class Digraph {
    private static final String NEWLINE = System.getProperty("line.separator");

    private final int V;           // 顶点总数
    private int E;                 // 边的总数
    private Bag<Integer>[] adj;    // adj[v] = 顶点 v 的邻接顶点
    private int[] indegree;        // indegree[v] = 顶点 v 的入度
    
    /**
     * 创建一幅有向图，其中含有 V 个顶点，但没有边。
     * @param V 顶点总数
     */
    @SuppressWarnings("unchecked")
	public Digraph(int V) {
        if (V < 0) 
        	throw new IllegalArgumentException("Number of vertices in a Digraph must be nonnegative");
       
        this.V = V;
        this.E = 0;
        
        adj = (Bag<Integer>[]) new Bag[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new Bag<Integer>();
        }
        
        indegree = new int[V];
    }

    /**  
     * 从输入流 in 中读取一幅有向图。
     * The format is the number of vertices <em>V</em>,
     * followed by the number of edges <em>E</em>,
     * followed by <em>E</em> pairs of vertices, with each entry separated by whitespace.
     *
     */
    @SuppressWarnings("unchecked")
	public Digraph(In in) {
        if (in == null) 
        	throw new IllegalArgumentException("argument is null");
        
        try {
            this.V = in.readInt();
            if (V < 0) 
            	throw new IllegalArgumentException("number of vertices in a Digraph must be nonnegative");
            
            indegree = new int[V];
            
            adj = (Bag<Integer>[]) new Bag[V];
            for (int v = 0; v < V; v++) {
                adj[v] = new Bag<Integer>();
            }
            
            int E = in.readInt();
            if (E < 0) 
            	throw new IllegalArgumentException("number of edges in a Digraph must be nonnegative");
            
            for (int i = 0; i < E; i++) {
                int v = in.readInt();
                int w = in.readInt();
                addEdge(v, w); 
            }
        }
        catch (NoSuchElementException e) {
            throw new IllegalArgumentException("invalid input format in Digraph constructor", e);
        }
    }

    @SuppressWarnings("unchecked")
	public Digraph(Digraph G) {
        if (G == null) 
        	throw new IllegalArgumentException("argument is null");

        this.V = G.V();
        this.E = G.E();
        if (V < 0) 
        	throw new IllegalArgumentException("Number of vertices in a Digraph must be nonnegative");

        // update indegrees
        indegree = new int[V];
        for (int v = 0; v < V; v++)
            this.indegree[v] = G.indegree(v);

        // update adjacency lists
        adj = (Bag<Integer>[]) new Bag[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new Bag<Integer>();
        }

        for (int v = 0; v < G.V(); v++) {
            // reverse so that adjacency list is in same order as original
            Stack<Integer> reverse = new Stack<Integer>();
            for (int w : G.adj[v]) {
                reverse.push(w);
            }
            for (int w : reverse) {
                adj[v].add(w);
            }
        }
    }
    
    /**
     * 顶点总数
     */
    public int V() {
        return V;
    }

    /**
     * 边的总数
     */
    public int E() {
        return E;
    }


    private void validateVertex(int v) {
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }

    /**
     * 添加一条边
     * @param v 头
     * @param w 尾
     */
    public void addEdge(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        adj[v].add(w);
        indegree[w]++;
        E++;
    }

    /**
     * 顶点 v 的邻接顶点
     * @param v
     * @return
     */
    public Iterable<Integer> adj(int v) {
        validateVertex(v);
        return adj[v];
    }

    /**
     * 顶点 v 的出度
     * @param v
     * @return
     */
    public int outdegree(int v) {
        validateVertex(v);
        return adj[v].size();
    }

    /**
     * 顶点 v 的入度
     * @param v
     * @return
     */
    public int indegree(int v) {
        validateVertex(v);
        return indegree[v];
    }

    /**
     * 反向图
     * @return
     */
    public Digraph reverse() {
        Digraph reverse = new Digraph(V);
        for (int v = 0; v < V; v++) {
            for (int w : adj(v)) {
                reverse.addEdge(w, v);
            }
        }
        return reverse;
    }

    /**
     * 打印图
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(V + " vertices, " + E + " edges " + NEWLINE);
        for (int v = 0; v < V; v++) {
            s.append(String.format("%d: ", v));
            for (int w : adj[v]) {
                s.append(String.format("%d ", w));
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        StdOut.println(G);
    }

}
