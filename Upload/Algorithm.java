import java.util.ArrayList;
import java.util.Arrays;

public class Algorithm {
    public void initQueue(ArrayList<Qnode> queue, int v) {
        for (int i = 0; i < v; i++) {
            Qnode node = new Qnode();
            node.setIndex(i);
            queue.add(node);
        }
    }

    public Qnode extractMin(ArrayList<Qnode> queue) {
        Qnode minNode = queue.get(0);
        for (Qnode node : queue) {
            if (node.getKey() < minNode.getKey()) {
                minNode = node;
            }
        }
        return minNode;
    }

    public Mst primMst(Graph g) {
        Qnode srcNode;
        int vertexes = g.adjList.length;
        ArrayList<Qnode> queue = new ArrayList<>();
        Mst mst = new Mst(new Integer[vertexes], new int[vertexes]);

        initQueue(queue, vertexes);
        queue.get(0).setKey(0);
        mst.weights[0] = 0;
        mst.parents[0] = null;

        while (!queue.isEmpty()) {
            srcNode = extractMin(queue);
            queue.remove(srcNode);
            for (Node node : g.adjList[srcNode.getIndex()]) {
                Qnode qnode = queue.stream().filter(qn -> node.getIndex() == qn.getIndex()).findFirst().orElse(null);
                if (qnode != null && node.getWeight() < qnode.getKey()) {
                    mst.parents[node.getIndex()] = srcNode.getIndex();
                    mst.weights[node.getIndex()] = node.getWeight();
                    int qNodeIndex = queue.indexOf(qnode);
                    queue.get(qNodeIndex).setKey(node.getWeight());

                }
            }
        }
        return mst;
    }

    public void updateMst(Graph mst, int srcVertex, int destVertex, int weight) {
        String[] colors = new String[mst.adjList.length];
        Arrays.fill(colors, "white");
        GraphUtils.addEdge(mst, srcVertex, destVertex, weight);
        findCircle(mst, destVertex, colors, new Mst(new Integer[mst.adjList.length], new int[mst.adjList.length]), -1);
    }

    public boolean findCircle(Graph mst, int destVertex, String[] colors, Mst edgeWeights, int prevVertex) {
        boolean foundCircle;
        colors[destVertex] = "gray";

        for (Node n : mst.adjList[destVertex]) {
            if(n.getIndex() != prevVertex) {
                edgeWeights.parents[destVertex] = n.getIndex();
                edgeWeights.weights[destVertex] = n.getWeight();
                if (colors[n.getIndex()].equals("white")) {
                    foundCircle = findCircle(mst, n.getIndex(), colors, edgeWeights, destVertex);
                    if(foundCircle){
                        return true;
                    }
                } else if (colors[n.getIndex()].equals("gray")) {
                    deleteMax(mst, destVertex, n.getIndex(), edgeWeights);
                    return true;
                }
            }
        }
        colors[destVertex] = "black";
        return false;
    }

    public void deleteMax(Graph mst, int srcVertex, int destVertex, Mst edgeWeights) {
        int currVertex = destVertex;
        int maxIndex = srcVertex;
        int maxWeight = edgeWeights.weights[srcVertex];

        while(currVertex != srcVertex){
            if(maxWeight < edgeWeights.weights[currVertex]){
                maxWeight = edgeWeights.weights[currVertex];
                maxIndex = currVertex;
            }
            currVertex = edgeWeights.parents[currVertex];
        }

       int indexToRemove = maxIndex;
       Node adjToRemove = mst.adjList[edgeWeights.parents[maxIndex]].stream().filter(n -> n.getIndex() == indexToRemove).findAny().orElse(null);
       mst.adjList[edgeWeights.parents[maxIndex]].remove(adjToRemove);

       adjToRemove = mst.adjList[maxIndex].stream().filter(n -> n.getIndex() == edgeWeights.parents[indexToRemove]).findAny().orElse(null);
       mst.adjList[maxIndex].remove(adjToRemove);
    }
}