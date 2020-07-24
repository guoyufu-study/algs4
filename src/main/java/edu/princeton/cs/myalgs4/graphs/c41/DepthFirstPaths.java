/******************************************************************************
 *  编译:  javac DepthFirstPaths.java
 *  执行:    java DepthFirstPaths G s
 *  依赖: Graph.java Stack.java StdOut.java
 *  数据文件:   https://algs4.cs.princeton.edu/41graph/tinyCG.txt
 *                https://algs4.cs.princeton.edu/41graph/tinyG.txt
 *                https://algs4.cs.princeton.edu/41graph/mediumG.txt
 *                https://algs4.cs.princeton.edu/41graph/largeG.txt
 *
 *  Run depth-first search on an undirected graph.
 *
 *  %  java Graph ${workspace_loc:/algs4/src/main/resources/41graph/tinyCG.txt} 
 *  6 8
 *  0: 2 1 5 
 *  1: 0 2 
 *  2: 0 1 3 4 
 *  3: 5 4 2 
 *  4: 3 2 
 *  5: 3 0 
 *
 *  % java DepthFirstPaths ${workspace_loc:/algs4/src/main/resources/41graph/tinyCG.txt} 0
 *  0 to 0:  0
 *  0 to 1:  0-2-1
 *  0 to 2:  0-2
 *  0 to 3:  0-2-3
 *  0 to 4:  0-2-3-4
 *  0 to 5:  0-2-3-5
 *
 ******************************************************************************/

package edu.princeton.cs.myalgs4.graphs.c41;

import java.util.Deque;
import java.util.LinkedList;

import edu.princeton.cs.algs4.graphs.Graph;
import edu.princeton.cs.algs4.io.In;
import edu.princeton.cs.algs4.io.StdOut;

public class DepthFirstPaths {

	private boolean[] marked;
	private final int s;
	private int[] edgeTo;
	
	public DepthFirstPaths(Graph G, int s) {
		this.s = s;
		edgeTo = new int[G.V()];
		marked = new boolean[G.V()];
		validateVertex(s);
		dfs(G, s);
	}

	private void validateVertex(int v) {
		int V = marked.length;
		if(v<0 || v>=V) 
			throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
	}
	
	private void dfs(Graph G, int v) {
		marked[v] = true;
		for (int w : G.adj(v)) {
			if(!marked[w]) {
				edgeTo[w] = v;
				dfs(G, w);
			}
			
		}
	}

	public boolean hasPathTo(int v) {
		validateVertex(v);
		return marked[v];
	}
	
	public Iterable<Integer> pathTo(int v) {
		validateVertex(v);
		
		// 不存在路径
		if(!hasPathTo(v)) return null;
		
		// 存在路径
		Deque<Integer> path = new LinkedList<Integer>();
		for (int x = v; x != s; x = edgeTo[x]) {
			path.push(x);
		}
		path.push(s);
		
		return path;
	}
	
	public static void main(String[] args) {
        In in = new In(args[0]);
        Graph G = new Graph(in);
        int s = Integer.parseInt(args[1]);
        DepthFirstPaths dfs = new DepthFirstPaths(G, s);

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
