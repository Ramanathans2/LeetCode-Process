import java.util.*;

class Solution {
    int maxQuality = 0;

    public int maximalPathQuality(int[] values, int[][] edges, int maxTime) {
        int n = values.length;

        // Build graph
        List<int[]>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) graph[i] = new ArrayList<>();

        for (int[] e : edges) {
            graph[e[0]].add(new int[]{e[1], e[2]});
            graph[e[1]].add(new int[]{e[0], e[2]});
        }

        int[] visited = new int[n];

        dfs(0, 0, 0, values, graph, visited, maxTime);

        return maxQuality;
    }

    private void dfs(int node, int time, int score, int[] values,
                     List<int[]>[] graph, int[] visited, int maxTime) {

        if (time > maxTime) return;

        // If first time visiting node → add value
        if (visited[node] == 0) {
            score += values[node];
        }

        visited[node]++;

        // If back at node 0 → update answer
        if (node == 0) {
            maxQuality = Math.max(maxQuality, score);
        }

        // Explore neighbors
        for (int[] nei : graph[node]) {
            int next = nei[0];
            int t = nei[1];

            dfs(next, time + t, score, values, graph, visited, maxTime);
        }

        // Backtrack
        visited[node]--;
    }
}