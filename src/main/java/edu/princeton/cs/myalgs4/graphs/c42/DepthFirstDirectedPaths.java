/******************************************************************************
 *  编译:  javac DepthFirstDirectedPaths.java
 *  执行:    java DepthFirstDirectedPaths digraph.txt s
 *  依赖: Digraph.java Stack.java
 *  数据文件:   https://algs4.cs.princeton.edu/42digraph/tinyDG.txt
 *                https://algs4.cs.princeton.edu/42digraph/mediumDG.txt
 *                https://algs4.cs.princeton.edu/42digraph/largeDG.txt
 *
 *  Determine reachability in a digraph from a given vertex using
 *  depth-first search.
 *  运行时间 O(E + V) .
 *
 *  % java DepthFirstDirectedPaths tinyDG.txt 3
 *  3 to 0:  3-5-4-2-0
 *  3 to 1:  3-5-4-2-0-1
 *  3 to 2:  3-5-4-2
 *  3 to 3:  3
 *  3 to 4:  3-5-4
 *  3 to 5:  3-5
 *  3 to 6:  not connected
 *  3 to 7:  not connected
 *  3 to 8:  not connected
 *  3 to 9:  not connected
 *  3 to 10:  not connected
 *  3 to 11:  not connected
 *  3 to 12:  not connected
 *
 ******************************************************************************/

package edu.princeton.cs.myalgs4.graphs.c42;

import java.util.Deque;
import java.util.LinkedList;

import edu.princeton.cs.algs4.io.In;
import edu.princeton.cs.algs4.io.StdOut;

/**
 * 4.2.3 有向图的单（多）源路径问题
 * 
 * <p>
 *  The {@code DepthFirstDirectedPaths} class represents a data type for
 *  finding directed paths from a source vertex <em>s</em> to every
 *  other vertex in the digraph.
 *  <p>
 *  This implementation uses depth-first search.
 *  The constructor takes &Theta;(<em>V</em> + <em>E</em>) time in the
 *  worst case, where <em>V</em> is the number of vertices and <em>E</em>
 *  is the number of edges.
 *  Each instance method takes &Theta;(1) time.
 *  It uses &Theta;(<em>V</em>) extra space (not including the digraph).
 *  <p>
 *  See {@link DepthFirstDirectedPaths} for a nonrecursive implementation.
 *  For additional documentation,  
 *  see <a href="https://algs4.cs.princeton.edu/42digraph">Section 4.2</a> of  
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne. 
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public class DepthFirstDirectedPaths {
    private boolean[] marked;  // marked[v] = true 如果 v 从 s 可达
    private int[] edgeTo;	// edgeTo[v] = 从 s 到 v 的最后一条边
    private final int s;	// 源点

    /**
     * 计算有向图  {@code G} 中从源点{@code s} 到每一个顶点的一条有向路径
     * 
     * @param  G 有向图
     * @param  s 源点
     * @throws IllegalArgumentException 除非 {@code 0 <= s < V}
     */
    public DepthFirstDirectedPaths(Digraph G, int s) {
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        this.s = s;
        validateVertex(s);
        dfs(G, s);
    }

    private void dfs(Digraph G, int v) { 
        marked[v] = true;
        for (int w : G.adj(v)) {
            if (!marked[w]) {
                edgeTo[w] = v;
                dfs(G, w);
            }
        }
    }

    /**
     * 是否存在一条从源点 {@code s} 到顶点 {@code v} 的有向路径？
     * 
     * @param  v 顶点
     * @return {@code true} 如果存在一条从源点 {@code s} 到顶点 {@code v} 的有向路径, {@code false} 其它情况。
     * @throws IllegalArgumentException 除非 {@code 0 <= v < V}
     */
    public boolean hasPathTo(int v) {
        validateVertex(v);
        return marked[v];
    }

    
    /**
     * 返回从源点x {@code s} 到顶点 {@code v} 的一条有向路径, 或者返回
     * {@code null} 如果不存在这样一条路径。
     * 
     * @param  v 顶点
     * @return the sequence of vertices on a directed path from the source vertex
     *         {@code s} to vertex {@code v}, as an Iterable
     * @throws IllegalArgumentException 除非 {@code 0 <= v < V}
     */
    public Iterable<Integer> pathTo(int v) {
        validateVertex(v);
        
        if (!hasPathTo(v)) return null;
        
        Deque<Integer> path = new LinkedList<Integer>();
        for (int x = v; x != s; x = edgeTo[x])
            path.push(x);
        path.push(s);
        return path;
    }

    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }

    /**
     * 单元测试 {@code DepthFirstDirectedPaths} 数据类型
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
    	
        Digraph G = new Digraph(new In(args[0]));
        // StdOut.println(G);

        int s = Integer.parseInt(args[1]);
        DepthFirstDirectedPaths dfs = new DepthFirstDirectedPaths(G, s);

        for (int v = 0; v < G.V(); v++) {
            if (dfs.hasPathTo(v)) {
                StdOut.printf("%d to %d:  ", s, v);
                for (int x : dfs.pathTo(v)) {
                    if (x == s) StdOut.print(x);
                    else        StdOut.print("-" + x);
                }
                StdOut.println();
            }

            else {
                StdOut.printf("%d to %d:  not connected\n", s, v);
            }

        }
    }

}
