package tetris;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TetrisModel implements GameEventsListener {

    public static final int DEFAULT_HEIGHT = 20;
    public static final int DEFAULT_WIDTH = 10;
    public static final int DEFAULT_COLORS_NUMBER = 7;
    private static final Logger logger = Logger.getLogger(TetrisModel.class.getName());
    public final TetrisState state = new TetrisState();
    final List<ModelListener> listeners = new ArrayList<>();
    int maxColors;
    private boolean paused = false; // Track paused state

    public TetrisModel() {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_COLORS_NUMBER);
    }

    public TetrisModel(int width, int height, int maxColors) {
        this.state.setWidth(width);
        this.state.setHeight(height);
        this.maxColors = maxColors;
        state.setField(new int[width][height]);
        if (!state.isGameOver())
            initFigure();
    }

    public void addListener(ModelListener listener) {
        listeners.add(listener);
    }

    private void initFigure() {
        state.setFigure(FigureFactory.createNextFigure());
        state.setPosition(new Pair(
                state.getWidth() / 2 - state.getFigure()[0].length / 2, state.getHeight() / 2));
    }

    public Pair size() {
        return new Pair(state.getWidth(), state.getHeight());
    }

    @Override
    public void slideDown() {
        if (state.isGameOver() || paused) return; // Check for pause

        var newPosition = new Pair(state.getPosition().x(), state.getPosition().y() - 1);
        if (isNewFiguresPositionValid(newPosition)) {
            state.setPosition(newPosition);
            notifyListeners();
        } else {
            pasteFigure();
            deleteFullRows();
            initFigure();
            notifyListeners();
            if (!isNewFiguresPositionValid(state.getPosition())) {
                gameOver();
            }
        }
    }

    @Override
    public void moveLeft() {
        if (state.isGameOver() || paused) return; // Check for pause

        var newPosition = new Pair(state.getPosition().x() - 1, state.getPosition().y());
        if (isNewFiguresPositionValid(newPosition)) {
            state.setPosition(newPosition);
            notifyListeners();
        }
    }

    @Override
    public void moveRight() {
        if (state.isGameOver() || paused) return; // Check for pause

        var newPosition = new Pair(state.getPosition().x() + 1, state.getPosition().y());
        if (isNewFiguresPositionValid(newPosition)) {
            state.setPosition(newPosition);
            notifyListeners();
        }
    }

    @Override
    public void rotate() {
        if (state.isGameOver() || paused) return; // Check for pause

        int[][] rotatedFigure = new int[4][4];
        for (int r = 0; r < state.getFigure().length; r++) {
            for (int c = 0; c < state.getFigure()[r].length; c++) {
                rotatedFigure[c][3 - r] = state.getFigure()[r][c];
            }
        }
        if (isNewFiguresPositionValidAfterRotation(state.getPosition(), rotatedFigure)) {
            state.setFigure(rotatedFigure);
            notifyListeners();
        }
    }

    @Override
    public void drop() {
        if (state.isGameOver() || paused) return; // Check for pause

        var droppedFigurePosition = new Pair(state.getPosition().x(), state.getPosition().y() - 1);

        while (isNewFiguresPositionValid(droppedFigurePosition)) {
            state.setPosition(droppedFigurePosition);
            droppedFigurePosition = new Pair(state.getPosition().x(), state.getPosition().y() - 1);
        }

        pasteFigure();
        deleteFullRows();
        initFigure();
        notifyListeners();
    }

    @Override
    public void pause() {
        if (state.isGameOver()) return; // Don't pause if game is over

        paused = !paused; // Toggle pause state
        if (paused) {
            logger.log(Level.INFO, "Game paused.");
        } else {
            logger.log(Level.INFO, "Game resumed.");
        }
        notifyListeners(); // Notify listeners about pause state change
    }

    public boolean isNewFiguresPositionValid(Pair newPosition) {
        return isNewFiguresPositionValidAfterRotation(newPosition, state.getFigure());
    }

    public boolean isNewFiguresPositionValidAfterRotation(Pair newPosition, int[][] figure) {
        boolean[] result = new boolean[1];
        result[0] = true;

        walkThroughAllFigureCells(newPosition, figure, (absPos, k) -> {
            if (result[0]) {
                result[0] = checkAbsPos(absPos);
            }
        });

        return result[0];
    }

    private void walkThroughAllFigureCells(Pair newPosition, int[][] figure, BiConsumer<Pair, Pair> payload) {
        for (int row = 0; row < figure.length; row++) {
            for (int col = 0; col < figure[row].length; col++) {
                if (figure[row][col] == 0)
                    continue;
                int absCol = newPosition.x() + col;
                int absRow = newPosition.y() + row;
                payload.accept(new Pair(absCol, absRow), new Pair(col, row));
            }
        }
    }

    private boolean checkAbsPos(Pair absPos) {
        var absCol = absPos.x();
        var absRow = absPos.y();
        if (isColumnPositionOutOfBoundaries(absCol))
            return false;
        if (isRowPositionOutOfBoundaries(absRow))
            return false;
        return state.getField()[absRow][absCol] == 0;
    }

    private boolean isRowPositionOutOfBoundaries(int absRow) {
        return absRow < 0 || absRow >= state.getHeight();
    }

    private boolean isColumnPositionOutOfBoundaries(int absCol) {
        return absCol < 0 || absCol >= state.getWidth();
    }

    public void pasteFigure() {
        walkThroughAllFigureCells(state.getPosition(), state.getFigure(), (absPos, relPos) -> {
            logger.log(Level.INFO, "y: {0}", absPos.y());
            logger.log(Level.INFO, "x: {0}", absPos.x());
            state.getField()[absPos.y()][absPos.x()] = state.getFigure()[relPos.y()][relPos.x()];
        });
    }

    private boolean isFullRow(int[] fieldRow) {
        return Arrays.stream(fieldRow).noneMatch(value -> value == 0);
    }

    private void deleteFullRows() {
        for (int row = 0; row < state.getField().length; row++) {
            if (isFullRow(state.getField()[row])) {
                deleteRow(row);
                calculateScore();
                notifyListeners();
            }
        }
    }

    private void calculateScore() {
        state.setScore(state.getScore() + state.getWidth());
        notifyListenersScoreChanged();
        checkLevelUp();
    }

    private void checkLevelUp() {
        if (state.getScore() % 500 == 0) {
            state.setLevel(state.getLevel() + 1);
            notifyListenersLevelChanged();
        }
    }

    private void deleteRow(int row) {
        for (int r = row; r > 0; r--) {
            state.getField()[r] = Arrays.copyOf(
                    state.getField()[r + 1], state.getField()[r + 1].length
            );
        }
        state.getField()[0] = new int[state.getWidth()];
    }

    public void gameOver() {
        state.setGameOver(true);
        notifyListenersGameOver();
    }

    public void restartGame() {
        state.setGameOver(false);

        state.setScore(0);
        notifyListenersScoreChanged();

        state.setLevel(1);
        notifyListenersGameOver();

        for (int[] row : state.getField()) {
            Arrays.fill(row, 0);
        }

        initFigure();
        notifyListeners();
    }

    private void notifyListeners() {
        listeners.forEach(listener -> listener.onChange(this));
    }

    private void notifyListenersScoreChanged() {
        listeners.forEach(listener -> listener.scoreHasChanged(this));
    }

    private void notifyListenersLevelChanged() {
        listeners.forEach(listener -> listener.levelHasChanged(this));
    }

    private void notifyListenersGameOver() {
        listeners.forEach(listener -> listener.gameOver(this));
    }
}
