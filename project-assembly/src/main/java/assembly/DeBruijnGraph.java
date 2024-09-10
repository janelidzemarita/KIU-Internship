package assembly;

import java.util.*;

/**
 * A class representing a De Bruijn graph constructed from a sequence of characters.
 */
public class DeBruijnGraph {

    private final Map<String, List<String>> graph = new LinkedHashMap<>();

    /**
     * Builds the De Bruijn graph from a given sequence and k-mer length.
     *
     * <p>The graph is constructed by creating k-mers from the sequence, where each k-mer is split
     * into a prefix and a suffix. The prefix serves as the key in the graph, and the suffixes are
     * stored in a list associated with that key.</p>
     *
     * @param sequence The input sequence from which to build the graph.
     * @param k        The length of the k-mers used to construct the graph.
     * @throws IllegalArgumentException if the sequence is null or its length is less than k.
     */
    public void buildGraph(String sequence, int k) {
        if (sequence == null || sequence.length() < k) {
            throw new IllegalArgumentException("Sequence length must be at least as long as k.");
        }

        for (int i = 0; i <= sequence.length() - k; i++) {
            String kmer = sequence.substring(i, i + k);
            String prefix = kmer.substring(0, k - 1);  // first k-1 characters
            String suffix = kmer.substring(1);         // last k-1 characters

            addEdge(prefix, suffix);
        }
    }

    /**
     * Retrieves the graph representation as an unmodifiable map.
     *
     * <p>The map contains the prefix-suffix pairs of the De Bruijn graph where each prefix maps
     * to a list of suffixes.</p>
     *
     * @return An unmodifiable map representing the De Bruijn graph.
     */
    public Map<String, List<String>> getGraph() {
        return Map.copyOf(graph);
    }


    public void addEdge(String prefix, String to) {
        graph.computeIfAbsent(prefix, key -> new ArrayList<>()).add(to);
    }
}
