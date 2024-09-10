package assembly;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A utility class for finding an Eulerian path in a De Bruijn graph and reconstructing the genome.
 */
public class EulerianPathReconstructor {

    private static final Logger logger = Logger.getLogger(EulerianPathReconstructor.class.getName());

    // Private constructor to prevent instantiation
    private EulerianPathReconstructor() {
        throw new AssertionError("Cannot instantiate utility class");
    }

    /**
     * Finds an Eulerian path in a De Bruijn graph.
     *
     * @param dBGraph A DeBruijnGraph object representing the De Bruijn graph.
     * @return A list of strings representing the Eulerian path from the start node to the end node.
     */
    public static List<String> findEulerianPath(DeBruijnGraph dBGraph) {
        Map<String, List<String>> graph = dBGraph.getGraph();
        if (graph.isEmpty()) {
            logger.info("Graph is empty.");
            return Collections.emptyList(); // Return an empty path for an empty graph
        }

        Map<String, Integer> inDegree = new HashMap<>();
        Map<String, Integer> outDegree = new HashMap<>();

        // Calculate in-degree and out-degree
        for (Map.Entry<String, List<String>> entry : graph.entrySet()) {
            String node = entry.getKey();
            List<String> neighbors = new ArrayList<>(entry.getValue()); // Ensure mutability

            outDegree.put(node, neighbors.size());
            for (String neighbor : neighbors) {
                inDegree.put(neighbor, inDegree.getOrDefault(neighbor, 0) + 1);
            }
        }

        // Initialize start and end nodes
        String startNode = null;
        String endNode = null;

        // Find start node and end node
        for (Map.Entry<String, Integer> entry : outDegree.entrySet()) {
            String node = entry.getKey();
            int out = entry.getValue();
            int in = inDegree.getOrDefault(node, 0);

            if (out - in == 1) {
                startNode = node;  // Node with excess out-degree
            } else if (in - out == 1) {
                endNode = node;  // Node with excess in-degree
            }
        }

        // If no specific start node found, pick any node
        if (startNode == null) {
            startNode = graph.keySet().iterator().next(); // Pick any node if no specific start found
            logger.log(Level.INFO, "No specific start node found. Picking an arbitrary node: {0}", startNode);
        }

        // Perform DFS to find Eulerian path
        List<String> path = new ArrayList<>();
        dfs(startNode, graph, outDegree, path);

        if (endNode != null) {
            // Ensure path ends at the end node
            while (!path.isEmpty() && !path.get(path.size() - 1).equals(endNode)) {
                path.remove(path.size() - 1);
            }
        }

        if (path.size() == 1 && outDegree.getOrDefault(path.get(0), 0) == 0) {
            // Handle case for single node with no outgoing edges
            return path;
        }

        Collections.reverse(path);
        return path;
    }


    /**
     * Depth-First Search (DFS) to find the Eulerian path.
     *
     * @param node      The current node to start DFS from.
     * @param graph     A map representing the De Bruijn graph.
     * @param outDegree A map of nodes to their out-degrees.
     * @param path      A list to accumulate the nodes in the Eulerian path.
     */
    private static void dfs(String node, Map<String, List<String>> graph, Map<String, Integer> outDegree, List<String> path) {
        while (outDegree.getOrDefault(node, 0) > 0) {
            String nextNode = graph.get(node).remove(0);  // Get next edge
            outDegree.put(node, outDegree.get(node) - 1);
            dfs(nextNode, graph, outDegree, path);        // Recursively visit next node
        }
        path.add(node);  // Add node to the Eulerian path
    }

    /**
     * Reconstructs the genome from the Eulerian path.
     *
     * @param path A list of strings representing the Eulerian path.
     * @return A string representing the reconstructed genome.
     */
    public static String reconstructGenome(List<String> path) {
        if (path.isEmpty()) {
            return "";
        }

        StringBuilder genome = new StringBuilder(path.get(0));
        for (int i = 1; i < path.size(); i++) {
            String node = path.get(i);
            genome.append(node.charAt(node.length() - 1));  // Add only the last character of each node
        }
        return genome.toString();
    }
}
