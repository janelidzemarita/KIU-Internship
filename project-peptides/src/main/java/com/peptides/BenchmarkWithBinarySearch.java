package com.peptides;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.logging.*;

@State(Scope.Benchmark)
public class BenchmarkWithBinarySearch {

    static final byte[] ALPHABET = new byte[26];
    private static final Logger logger = Logger.getLogger(BenchmarkWithBinarySearch.class.getName());
    private static final int PROTEIN_SIZE = 10_000;
    private static final int LIBRARY_SIZE = 100_000;
    private static final Random random = new Random();

    private PeptidesWithBinarySearch peptides;

    static {
        for (byte c = 'A'; c <= 'Z'; c++) {
            ALPHABET[c - 'A'] = c;
        }
    }

    @Setup(Level.Trial)
    public void setup() {
        logger.log(java.util.logging.Level.INFO, "generating data...");
        String protein = generateProtein(PROTEIN_SIZE);
        List<String> library = generateLibrary();
        peptides = new PeptidesWithBinarySearch(PeptidesWithBinarySearch.DEFAULT_PEPTIDE_SIZE, protein, library);
    }

    @Benchmark
    public void searchPeptides() {
        peptides.searchLibrary();
    }

    private static String generateProtein(int proteinSize) {
        int r = random.nextInt(ALPHABET.length);
        var data = new byte[proteinSize];
        Arrays.fill(data, ALPHABET[r]);
        return new String(data);
    }

    private static List<String> generateLibrary() {
        var library = new ArrayList<String>(LIBRARY_SIZE);
        for (int i = 0; i < LIBRARY_SIZE; i++) {
            var peptide = generateProtein(PeptidesWithBinarySearch.DEFAULT_PEPTIDE_SIZE);
            library.add(peptide);
        }
        return library;
    }

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(BenchmarkWithBinarySearch.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(options).run();
    }
}
