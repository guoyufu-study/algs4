/******************************************************************************
 *  编译:  javac DirectedCycle.java
 *  执行:    java DirectedCycle input.txt
 *  依赖: Digraph.java Stack.java StdOut.java In.java
 *  数据文件:   https://algs4.cs.princeton.edu/42digraph/tinyDG.txt
 *                https://algs4.cs.princeton.edu/42digraph/tinyDAG.txt
 *
 *  在一幅有向环中寻找有向环
 *
 *  % java DirectedCycle tinyDG.txt 
 *  Directed cycle: 3 5 4 3 
 *
 *  %  java DirectedCycle tinyDAG.txt 
 *  No directed cycle
 *
 ******************************************************************************/

package edu.princeton.cs.myalgs4.graphs.c42;

import edu.princeton.cs.algs4.fundamentals.Stack;
import edu.princeton.cs.algs4.io.In;
import edu.princeton.cs.algs4.io.StdOut;

/**
 * 寻找有向环
 * 
 *  The {@code DirectedCycle} class represents a data type for 
 *  determining whether a digraph has a directed cycle.
 *  The <em>hasCycle</em> operation determines whether the digraph has
 *  a simple directed cycle and, if so, the <em>cycle</em> operation
 *  returns one.
 *  <p>
 *  This implementation uses depth-first search.
 *  The constructor takes &Theta;(<em>V</em> + <em>E</em>) time in the worst
 *  case, where <em>V</em> is the number of vertices and <em>E</em> is
 *  the number of edges.
 *  Each instance method takes &Theta;(1) time.
 *  It uses &Theta;(<em>V</em>) extra space (not including the digraph).
 *  <p>
 *  See {@link Topological} to compute a topological order if the
 *  digraph is acyclic.
 *  <p>
 *  For additional documentation,
 *  see <a href="https://algs4.cs.princeton.edu/42digraph">Section 4.2</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public class DirectedCycle {
    private boolean[] marked;        // marked[v] = has vertex v been marked?
    private int[] edgeTo;            // edgeTo[v] = previous vertex on path to v
    private boolean[] onStack;       // onStack[v] = is vertex on the stack?
    private Stack<Integer> cycle;    // directed cycle (or null if no such cycle)

    /**
     * 确定有向图 {@code G} 是否存在一个有向环，如果存在，找出来。
     * 
     * @param G the digraph
     */
    public DirectedCycle(Digraph G) {
        marked  = new boolean[G.V()];
        onStack = new boolean[G.V()];
        edgeTo  = new int[G.V()];
        for (int v = 0; v < G.V(); v++)
            if (!marked[v] && cycle == null) dfs(G, v);
    }

    // 检查算法：计算拓扑顺序或找到一个有向环
    private void dfs(Digraph G, int v) {
        onStack[v] = true;
        marked[v] = true;
        for (int w : G.adj(v)) {

            // 如果已发现有向环，则短路
            if (cycle != null) return;

            // 找到新的顶点，递归
            else if (!marked[w]) {
                edgeTo[w] = v;
                dfs(G, w);
            }

            // 跟踪有向环
            else if (onStack[w]) {
                cycle = new Stack<Integer>();
                for (int x = v; x != w; x = edgeTo[x]) {
                    cycle.push(x);
                }
                cycle.push(w);
                cycle.push(v);
                assert check();
            }
        }
        onStack[v] = false;
    }

    /**
     * 是否存在有向环？
     * 
     * @return {@code true} if the digraph has a directed cycle, {@code false} otherwise
     */
    public boolean hasCycle() {
        return cycle != null;
    }

    /**
     * 如果存在有向环，则返回有向环；否则，返回 {@code null} 。
     * 
     * @return a directed cycle (as an iterable) if the digraph has a directed cycle,
     *    and {@code null} otherwise
     */
    public Iterable<Integer> cycle() {
        return cycle;
    }


    // 如果有向图报告有向环，证明有向图存在有向环
    private boolean check() {

        if (hasCycle()) {
            // verify cycle
            int first = -1, last = -1;
            for (int v : cycle()) {
                if (first == -1) first = v;
                last = v;
            }
            if (first != last) {
                System.err.printf("cycle begins with %d and ends with %d\n", first, last);
                return false;
            }
        }


        return true;
    }

    /**
     * 单元测试 {@code DirectedCycle} 数据类型
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
    	
        Digraph G = new Digraph(new In(args[0]));

        DirectedCycle finder = new DirectedCycle(G);
        if (finder.hasCycle()) {
            StdOut.print("Directed cycle: ");
            for (int v : finder.cycle()) {
                StdOut.print(v + " ");
            }
            StdOut.println();
        }

        else {
            StdOut.println("No directed cycle");
        }
        StdOut.println();
    }

}