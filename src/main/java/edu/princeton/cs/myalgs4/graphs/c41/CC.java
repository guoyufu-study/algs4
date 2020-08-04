/******************************************************************************
 *  编译:  javac CC.java
 *  执行:    java CC filename.txt
 *  依赖: Graph.java StdOut.java Queue.java
 *  数据文件:   https://algs4.cs.princeton.edu/41graph/tinyG.txt
 *                https://algs4.cs.princeton.edu/41graph/mediumG.txt
 *                https://algs4.cs.princeton.edu/41graph/largeG.txt
 *
 *  Compute connected components using depth first search.
 *  Runs in O(E + V) time.
 *
 *  % java CC tinyG.txt
 *  3 components
 *  0 1 2 3 4 5 6
 *  7 8 
 *  9 10 11 12
 *
 *  % java CC mediumG.txt 
 *  1 components
 *  0 1 2 3 4 5 6 7 8 9 10 ...
 *
 *  % java -Xss50m CC largeG.txt 
 *  1 components
 *  0 1 2 3 4 5 6 7 8 9 10 ...
 *
 *  Note: This implementation uses a recursive DFS. To avoid needing
 *        a potentially very large stack size, replace with a non-recurisve
 *        DFS ala NonrecursiveDFS.java.
 *
 ******************************************************************************/

package edu.princeton.cs.myalgs4.graphs.c41;

import edu.princeton.cs.algs4.fundamentals.Queue;
import edu.princeton.cs.algs4.io.In;
import edu.princeton.cs.algs4.io.StdOut;

/**
 * 连通分量
 * <P> 使用 DFS 找出图中所有连通分量
 */
public class CC {
    private boolean[] marked;   // marked[v] = has vertex v been marked?
    private int[] id;           // id[v] = id of connected component containing v
    private int[] size;         // size[id] = number of vertices in given component
    private int count;          // number of connected components

    /**
     * 计算无向图 {@code G} 的连通分量。
     */
    public CC(Graph G) {
        marked = new boolean[G.V()];
        id = new int[G.V()];
        size = new int[G.V()];
        for (int v = 0; v < G.V(); v++) {
            if (!marked[v]) {
                dfs(G, v);
                count++;
            }
        }
    }

    /**
     * 计算加权图 {@code G} 的连通分量
     *
     * @param G 加权图
     */
//    public CC(EdgeWeightedGraph G) {
//        marked = new boolean[G.V()];
//        id = new int[G.V()];
//        size = new int[G.V()];
//        for (int v = 0; v < G.V(); v++) {
//            if (!marked[v]) {
//                dfs(G, v);
//                count++;
//            }
//        }
//    }

    // DFS
    private void dfs(Graph G, int v) {
        marked[v] = true;
        id[v] = count;
        size[count]++;
        for (int w : G.adj(v)) {
            if (!marked[w]) {
                dfs(G, w);
            }
        }
    }

    // 加权图 DFS
//    private void dfs(EdgeWeightedGraph G, int v) {
//        marked[v] = true;
//        id[v] = count;
//        size[count]++;
//        for (Edge e : G.adj(v)) {
//            int w = e.other(v);
//            if (!marked[w]) {
//                dfs(G, w);
//            }
//        }
//    }


    /**
     * {@code V} 所在的连通分量的标识符
     *
     * @param  v 顶点
     * @return 顶点 {@code V} 所在的连通分量的标识符
     * @throws IllegalArgumentException 除非 {@code 0 <= v < V}
     */
    public int id(int v) {
        validateVertex(v);
        return id[v];
    }

    /**
     *  {@code v} 所在的连通分量的大小
     *
     * @param  v 顶点
     * @return 顶点 {@code v} 所在的连通分量的大小
     * @throws IllegalArgumentException 除非 {@code 0 <= v < V}
     */
    public int size(int v) {
        validateVertex(v);
        return size[id[v]];
    }

    /**
     * 图 {@code G} 的连通分量数
     */
    public int count() {
        return count;
    }

    /**
     *  {@code v} 和 {@code w} 连通吗？
     *
     * @param  v 一个顶点
     * @param  w 另一个顶点
     * @return {@code true} 如果 {@code v} 和 {@code w} 连通； {@code false} 其它情况
     * @throws IllegalArgumentException 除非 {@code 0 <= v < V}
     * @throws IllegalArgumentException 除非 {@code 0 <= w < V}
     */
    public boolean connected(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        return id(v) == id(w);
    }

    /**
     * {@code v} 和 {@code w} 连通吗？
     *
     * @deprecated Replaced by {@link #connected(int, int)}.
     */
    @Deprecated
    public boolean areConnected(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        return id(v) == id(w);
    }

    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }

    /**
     *  单元测试 {@code CC} 数据类型
     *
     * @param args the command-line arguments
     */
    @SuppressWarnings("unchecked")
	public static void main(String[] args) {
        In in = new In(args[0]);
        Graph G = new Graph(in);
        CC cc = new CC(G);

        // number of connected components
        int m = cc.count();
        StdOut.println(m + " components");

        // compute list of vertices in each connected component
        Queue<Integer>[] components = (Queue<Integer>[]) new Queue[m];
        for (int i = 0; i < m; i++) {
            components[i] = new Queue<Integer>();
        }
        for (int v = 0; v < G.V(); v++) {
            components[cc.id(v)].enqueue(v);
        }

        // print results
        for (int i = 0; i < m; i++) {
            for (int v : components[i]) {
                StdOut.print(v + " ");
            }
            StdOut.println();
        }
    }
}

/******************************************************************************
 *  Copyright 2002-2020, Robert Sedgewick and Kevin Wayne.
 *
 *  This file is part of algs4.jar, which accompanies the textbook
 *
 *      Algorithms, 4th edition by Robert Sedgewick and Kevin Wayne,
 *      Addison-Wesley Professional, 2011, ISBN 0-321-57351-X.
 *      http://algs4.cs.princeton.edu
 *
 *
 *  algs4.jar is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  algs4.jar is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with algs4.jar.  If not, see http://www.gnu.org/licenses.
 ******************************************************************************/
