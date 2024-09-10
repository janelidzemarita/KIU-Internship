package tetris;

import java.util.Random;

public class FigureFactory {
    private static final Random random = new Random();

	// Private constructor to prevent instantiation
	private FigureFactory() {
		throw new UnsupportedOperationException("FigureFactory is a utility class and cannot be instantiated");
	}

    public static int[][] createNextFigure() {
        return generateRandomFigure();
    }

    private static int[][] generateRandomFigure() {
        int[][][] figures = {o(), j(), i(), t(), l(), s(), z()};
        return figures[random.nextInt(figures.length)];
    }

    static int[][] o() {
        return new int[][]{
                {0, 1, 1, 0},
                {0, 1, 1, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };
    }

    static int[][] j() {
        return new int[][]{
                {0, 0, 2, 0},
                {0, 0, 2, 0},
                {0, 2, 2, 0},
                {0, 0, 0, 0},
        };
    }

    static int[][] i() {
        return new int[][]{
                {3, 3, 3, 3},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };
    }

    static int[][] t() {
        return new int[][]{
                {0, 0, 4, 0},
                {0, 4, 4, 0},
                {0, 0, 4, 0},
                {0, 0, 0, 0},
        };
    }

    static int[][] l() {
        return new int[][]{
                {0, 5, 0, 0},
                {0, 5, 0, 0},
                {0, 5, 5, 0},
                {0, 0, 0, 0},
        };
    }

    static int[][] s() {
        return new int[][]{
                {0, 6, 0, 0},
                {0, 6, 6, 0},
                {0, 0, 6, 0},
                {0, 0, 0, 0},
        };
    }

    static int[][] z() {
        return new int[][]{
                {0, 0, 7, 0},
                {0, 7, 7, 0},
                {0, 7, 0, 0},
                {0, 0, 0, 0},
        };
    }

}