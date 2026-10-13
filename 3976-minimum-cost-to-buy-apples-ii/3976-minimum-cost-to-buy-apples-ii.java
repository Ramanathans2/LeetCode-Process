class Solution {

    static class Pair {
        long dist;
        int node;

        Pair(long dist, int node) {
            this.dist = dist;
            this.node = node;
        }
    }

    static class Edge {
        int to;
        long weight;

        Edge(int to, long weight) {
            this.to = to;
            this.weight = weight;
        }
    }

    public int[] minCost(int n, int[] prices, int[][] roads) {

        // normal travel graph
        List<Edge>[] emptyGraph = new ArrayList[n];

        // carrying apples graph
        List<Edge>[] carryGraph = new ArrayList[n];

        for(int i = 0; i < n; i++) {
            emptyGraph[i] = new ArrayList<>();
            carryGraph[i] = new ArrayList<>();
        }

        for(int[] e : roads) {

            int u = e[0];
            int v = e[1];
            int cost = e[2];
            int taxi = e[3];

            emptyGraph[u].add(new Edge(v, cost));
            emptyGraph[v].add(new Edge(u, cost));

            carryGraph[u].add(
                new Edge(v, 1L * cost * taxi));

            carryGraph[v].add(
                new Edge(u, 1L * cost * taxi));
        }

        long INF = (long)2e18;

        int[] ans = new int[n];

        long[] emptyDist = new long[n];
        long[] carryDist = new long[n];

        for(int src = 0; src < n; src++) {

            // EMPTY TRAVEL
            Arrays.fill(emptyDist, INF);

            PriorityQueue<Pair> pq =
                new PriorityQueue<>(
                    (a, b) -> Long.compare(a.dist, b.dist)
                );

            emptyDist[src] = 0;

            pq.offer(new Pair(0, src));

            while(!pq.isEmpty()) {

                Pair cur = pq.poll();

                long d = cur.dist;
                int u = cur.node;

                if(d > emptyDist[u]) continue;

                for(Edge edge : emptyGraph[u]) {

                    int v = edge.to;
                    long w = edge.weight;

                    if(emptyDist[v] > d + w) {

                        emptyDist[v] = d + w;

                        pq.offer(
                            new Pair(emptyDist[v], v)
                        );
                    }
                }
            }

            // CARRYING APPLES
            Arrays.fill(carryDist, INF);

            carryDist[src] = 0;

            pq.offer(new Pair(0, src));

            while(!pq.isEmpty()) {

                Pair cur = pq.poll();

                long d = cur.dist;
                int u = cur.node;

                if(d > carryDist[u]) continue;

                for(Edge edge : carryGraph[u]) {

                    int v = edge.to;
                    long w = edge.weight;

                    if(carryDist[v] > d + w) {

                        carryDist[v] = d + w;

                        pq.offer(
                            new Pair(carryDist[v], v)
                        );
                    }
                }
            }

            // FIND BEST SHOP
            long best = prices[src];

            for(int shop = 0; shop < n; shop++) {

                if(emptyDist[shop] == INF ||
                   carryDist[shop] == INF)
                    continue;

                long total =
                    emptyDist[shop] +
                    carryDist[shop] +
                    prices[shop];

                best = Math.min(best, total);
            }

            ans[src] = (int)best;
        }

        return ans;
    }
}