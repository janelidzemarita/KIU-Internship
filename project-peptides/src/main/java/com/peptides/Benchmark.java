package com.peptides;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Benchmark {

    static final byte[] ALPHABET = new byte[26];
    private static final Logger logger = Logger.getLogger(Benchmark.class.getName());
    private static final int PROTEIN_SIZE = 10_000;
    private static final int LIBRARY_SIZE = 100_000;
    private static final Random random = new Random();

    static {
        for (byte c = 'A'; c <= 'Z'; c++) {
            ALPHABET[c - 'A'] = c;
        }
    }

    public static void main(String[] args) {

        logger.log(Level.INFO, "generating data...");

        String protein = generateProtein(PROTEIN_SIZE);
        List<String> library = generateLibrary();
        Peptides peptides = new Peptides(Peptides.DEFAULT_PEPTIDE_SIZE, protein, library);

        logger.log(Level.INFO, "searching peptides...");
        long start = System.currentTimeMillis();
        peptides.searchLibrary();
        long stop = System.currentTimeMillis();

        logger.log(Level.INFO, "Elapsed: {0}", stop - start);


    }

    static String generateProtein(int proteinSize) {
        int r = random.nextInt(ALPHABET.length);
        var data = new byte[proteinSize];
        Arrays.fill(data, ALPHABET[r]);
        return new String(data);
    }

    private static List<String> generateLibrary() {
        var library = new ArrayList<String>(Benchmark.LIBRARY_SIZE);
        for (int i = 0; i < Benchmark.LIBRARY_SIZE; i++) {
            var peptide = generateProtein(Peptides.DEFAULT_PEPTIDE_SIZE);
            library.add(peptide);
        }
        return library;
    }


}
