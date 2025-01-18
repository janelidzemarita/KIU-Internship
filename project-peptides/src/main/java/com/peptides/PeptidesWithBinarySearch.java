package com.peptides;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class PeptidesWithBinarySearch {

    public static final int DEFAULT_PEPTIDE_SIZE = 8;
    private final String protein;
    private final int peptideSize;
    private final long[] peptideLongs;



    public PeptidesWithBinarySearch(int peptideSize, String protein, List<String> library) {
        this.peptideSize = peptideSize;
        this.protein = protein;
        this.peptideLongs = convertLibraryToLongs(library);
        Arrays.sort(this.peptideLongs); // Sort the long array
    }

    private long[] convertLibraryToLongs(List<String> library) {
        return library.stream()
                .mapToLong(PeptidesWithBinarySearch::stringToLong)
                .toArray();
    }

    private static long stringToLong(String peptide) {
        long result = 0;
        for (char c : peptide.toCharArray()) {
            result = result * 26 + (c - 'A');
        }
        return result;
    }

    public Map<String, List<Integer>> searchLibrary() {
        Map<String, List<Integer>> existingPeptides = new java.util.LinkedHashMap<>();
        for (int i = 0; i <= protein.length() - peptideSize; i++) {
            String kmer = protein.substring(i, i + peptideSize);
            long kmerLong = stringToLong(kmer);
            if (binarySearch(peptideLongs, kmerLong) >= 0) {
                existingPeptides.computeIfAbsent(kmer, key -> new ArrayList<>()).add(i);
            }
        }
        return existingPeptides;
    }

    private static int binarySearch(long[] array, long key) {
        int left = 0;
        int right = array.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (array[mid] == key) {
                return mid;
            } else if (array[mid] < key) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1; // Key not found
    }
}
