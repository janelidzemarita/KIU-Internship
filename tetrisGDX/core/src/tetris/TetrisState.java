package tetris;

public class TetrisState {
	private int width;
	private int height;
	private int[][] field;
	private int[][] figure;
	private Pair position;
	private boolean gameOver = false;
	private int score;
	private int level = 1;

	public TetrisState() {
		super();
	}

	// Getters and setters
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int[][] getField() {
		return field;
	}

	public void setField(int[][] field) {
		this.field = field;
	}

	public int[][] getFigure() {
		return figure;
	}

	public void setFigure(int[][] figure) {
		this.figure = figure;
	}

	public Pair getPosition() {
		return position;
	}

	public void setPosition(Pair position) {
		this.position = position;
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
}
