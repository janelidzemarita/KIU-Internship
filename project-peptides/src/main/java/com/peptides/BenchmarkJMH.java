package com.peptides;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Scope;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public class BenchmarkJMH {

    static final byte[] ALPHABET = new byte[26];
    private static final int PROTEIN_SIZE = 10_000;
    private static final int LIBRARY_SIZE = 100_000;
    private static final Random random = new Random();

    private PeptidesReversed peptides;

    // Initialization
    static {
        for (byte c = 'A'; c <= 'Z'; c++) {
            ALPHABET[c - 'A'] = c;
        }
    }

    /**
     * Setup method to generate the protein and library before each benchmark iteration.
     */
    @Setup(Level.Trial)
    public void setUp() {
        List<String> library;
        String protein;
        protein = generateProtein(PROTEIN_SIZE);
        library = generateLibrary();
        peptides = new PeptidesReversed(PeptidesReversed.DEFAULT_PEPTIDE_SIZE, protein, library);
    }

    /**
     * Benchmark for searching peptides.
     */
    @Benchmark
    public void searchPeptides() {
        peptides.searchLibrary();
    }

    /**
     * Generates a random protein string of the given size.
     *
     * @param proteinSize Size of the protein to generate
     * @return The generated protein string
     */
    static String generateProtein(int proteinSize) {
        var data = new byte[proteinSize];
        for (int i = 0; i < proteinSize; i++) {
            data[i] = ALPHABET[random.nextInt(ALPHABET.length)];
        }
        return new String(data);
    }

    /**
     * Generates a random peptide library.
     *
     * @return List of generated peptides
     */
    private static List<String> generateLibrary() {
        var library = new ArrayList<String>(LIBRARY_SIZE);
        for (int i = 0; i < LIBRARY_SIZE; i++) {
            var peptide = generateProtein(Peptides.DEFAULT_PEPTIDE_SIZE);
            library.add(peptide);
        }
        return library;
    }
}

