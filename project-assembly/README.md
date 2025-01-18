# DeBruijnGraph and EulerianPathReconstructor

## Overview

This project provides utilities for constructing and working with De Bruijn graphs, and for finding Eulerian paths within these graphs. It includes two main classes:

- `DeBruijnGraph`: Represents a De Bruijn graph constructed from a sequence of characters.
- `EulerianPathReconstructor`: Finds Eulerian paths in De Bruijn graphs and reconstructs genomes from these paths.

## DeBruijnGraph

The `DeBruijnGraph` class constructs a De Bruijn graph from a sequence of characters. The graph is built using k-mers, where each k-mer is split into a prefix and a suffix. The prefix is used as the key in the graph, and the suffixes are stored in a list associated with that key.

### Methods

- **`buildGraph(String sequence, int k)`**: Constructs the De Bruijn graph from the provided sequence and k-mer length. Throws `IllegalArgumentException` if the sequence is null or its length is less than k.
- **`getGraph()`**: Retrieves the graph representation as an unmodifiable map of prefixes to suffixes.
- **`getNodes()`**: Retrieves the set of nodes (prefixes) in the graph.
- **`addEdge(String prefix, String to)`**: Adds an edge to the graph by associating the prefix with the suffix.

## EulerianPathReconstructor

The `EulerianPathReconstructor` class provides methods for finding an Eulerian path in a De Bruijn graph and reconstructing a genome from the path.

### Methods

- **`findEulerianPath(DeBruijnGraph dBGraph)`**: Finds an Eulerian path in the provided De Bruijn graph. Returns a list of strings representing the Eulerian path from the start node to the end node. If the graph is empty, returns an empty list.
- **`reconstructGenome(List<String> path)`**: Reconstructs a genome from the given Eulerian path. The path is a list of strings representing the Eulerian path.

## Usage Example

Here's how you can use the `DeBruijnGraph` and `EulerianPathReconstructor` classes:

```java
import assembly.DeBruijnGraph;
import assembly.EulerianPathReconstructor;

public class Example {
    public static void main(String[] args) {
        // Create and build a De Bruijn graph
        DeBruijnGraph dbg = new DeBruijnGraph();
        dbg.buildGraph("GATCACA", 3);

        // Find the Eulerian path
        List<String> path = EulerianPathReconstructor.findEulerianPath(dbg);
        System.out.println("Eulerian Path: " + path);

        // Reconstruct the genome
        String genome = EulerianPathReconstructor.reconstructGenome(path);
        System.out.println("Reconstructed Genome: " + genome);
    }
}
