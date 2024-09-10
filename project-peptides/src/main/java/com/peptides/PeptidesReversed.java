package com.peptides;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PeptidesReversed {
    public static final int DEFAULT_PEPTIDE_SIZE = 8;

    private final String protein;
    private final int peptideSize;
    private final List<String> library;
    // Reverse map: key is peptide, value is list of positions where peptide was found
    private final Map<String, List<Integer>> peptideMap = new HashMap<>();

    public PeptidesReversed(int peptideSize, String protein, List<String> library) {
        this.peptideSize = peptideSize;
        this.protein = protein;
        this.library = library;
        createPeptideMap();
    }

    /**
     * Create a map with peptides from the library as keys and empty lists as values.
     */
    void createPeptideMap() {
        for (String peptide : library) {
            peptideMap.putIfAbsent(peptide, new ArrayList<>());
        }
    }

    /**
     * Search for the positions of peptides in the long protein.
     * Slide through the protein and check each k-mer against the peptide map.
     */
    public Map<String, List<Integer>> searchLibrary() {
        for (int i = 0; i < protein.length() - peptideSize + 1; i++) {
            String kmer = protein.substring(i, i + peptideSize);
            // Check if the current k-mer exists in the peptide map
            if (peptideMap.containsKey(kmer)) {
                peptideMap.get(kmer).add(i); // Add position to the list for that peptide
            }
        }
        return peptideMap;
    }
}
